package com.bong.bongstagram.Main.Ui.GoogleMap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.GpsTracker;
import com.bong.bongstagram.Main.Data.Image;
import com.bong.bongstagram.Main.Ui.CustomView.CustomBtn;
import com.bong.bongstagram.Main.Ui.Dialog.EventDialogFragment;
import com.bong.bongstagram.Main.Ui.Local.LocalFragment;
import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {
    private Activity activity;
    private String address;
    private GoogleMap mMap;
    private GoogleAdapter googleAdapter;
    private MapView mapView = null;
    private RecyclerView recyclerView;
    private Marker currentMarker = null;
    private double latitude;
    private double longitude;
    private TextView popularText;
    private TextView recentText;
    private static final int GPS_ENABLE_REQUEST_CODE = 200;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private boolean check_result;

    public GoogleMapFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnApplySelectedListener {
        void onCategoryApplySelected(String address);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_googlemap, container, false);
        popularText = view.findViewById(R.id.popular_Text);
        popularText.setSelected(true);
        if (!checkLocationServicesStatus()) showDialogForLocationServiceSetting();
        else checkRunTimePermission();

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            check_result = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if (check_result) {
                //위치값 가져올수있음.
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0]) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(getContext(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean checkLocationServicesStatus() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다. \n" + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    void checkRunTimePermission() {
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)

            // 3.  위치 값을 가져올 수 있음
        } else {
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult 에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult 에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView profile = view.findViewById(R.id.circle_Image);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.google_recycler);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);

        googleAdapter = new GoogleAdapter(context, new Image().getDummy());
        recyclerView.setAdapter(googleAdapter);

        popularText = view.findViewById(R.id.popular_Text);
        recentText = view.findViewById(R.id.recent_Text);

        if (getArguments() != null) {
            String imageUrl = getArguments().getString("image");
            Glide.with(getContext())
                    .load(imageUrl)
                    .into(profile);
        } else {
            Log.e("TODO", "넘어온 값 없음");
        }
        mapView = view.findViewById(R.id.map);
        mapView.getMapAsync(this);

        TextView textView = view.findViewById(R.id.google_textView);
        textView.setText(distance());
        ((OnApplySelectedListener) activity).onCategoryApplySelected(address);
        ((MainActivity) getActivity()).bottomNavigation(MainActivity.Type.google);
        ((MainActivity) getActivity()).Toolbar(MainActivity.Type.google);

        TextView btn = view.findViewById(R.id.google_Btn);
        if (check_result) {
//            btn.setEnabled(true);
            btn.setOnClickListener(v -> {
                EventDialogFragment e = EventDialogFragment.getInstance();
                Bundle bundle = new Bundle();
                latitude = getArguments().getDouble("latitude");
                longitude = getArguments().getDouble("longitude");
                bundle.putString("address", address);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                e.setArguments(bundle);
                e.show(getActivity().getSupportFragmentManager(), EventDialogFragment.TAG_EVENT_DIALOG);
            });
        } else {
            Toast.makeText(context, "위치 정보 제공 동의 시 사용할 수 있습니다.", Toast.LENGTH_LONG).show();
            btn.setOnClickListener(v -> {
                Toast.makeText(context, "위치 정보 제공 동의 시 사용할 수 있습니다.", Toast.LENGTH_LONG).show();
                checkRunTimePermission();
            });
        }

        Button localDetail = view.findViewById(R.id.custom_btn);
        localDetail.setOnClickListener(v -> {
            Log.e("click", "local");
            Fragment localFragment = new LocalFragment();
            ((MainActivity) getActivity()).changeFragment(MainActivity.Type.local, localFragment);
        });

        popularText = view.findViewById(R.id.popular_Text);
        recentText = view.findViewById(R.id.recent_Text);

        popularText.setOnClickListener(v -> {
            popularText.setSelected(true);
            recentText.setSelected(false);
            googleAdapter = new GoogleAdapter(context, new Image().getDummy());
            recyclerView.setAdapter(googleAdapter);
        });

        recentText.setOnClickListener(v -> {
            recentText.setSelected(true);
            popularText.setSelected(false);
            googleAdapter = new GoogleAdapter(context, new Image().secondDummy());
            recyclerView.setAdapter(googleAdapter);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0) + "\n";
        }
    }

    @SuppressLint("DefaultLocale")
    private String distance() {
        if (getArguments() == null) {
            address = "null";
        } else {
            address = getArguments().getString("address");
        }

        String distanceText;
        String distanceStr;
        GpsTracker gpsTracker = new GpsTracker(getActivity());
        Location locationA = new Location("point A");
        Location locationB = new Location("point B");

        double latitudeA = getArguments().getDouble("latitude");
        double longitudeA = getArguments().getDouble("longitude");

        locationA.setLatitude(latitudeA);
        locationA.setLongitude(longitudeA);
        locationB.setLatitude(gpsTracker.getLatitude());
        locationB.setLongitude(gpsTracker.getLongitude());

        float distance = locationA.distanceTo(locationB) / 1000;
        if (distance < 1) {
            distance = locationA.distanceTo(locationB);
            distanceStr = String.format("%.1f", distance) + "m";
        } else {
            distanceStr = String.format("%.1f", distance) + "km";
        }
        distanceText = address + " - 약 " + distanceStr;

        return distanceText;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (getArguments() != null) {
            double latitudeA = getArguments().getDouble("latitude");
            double longitudeA = getArguments().getDouble("longitude");

            LatLng latLng = new LatLng(latitudeA, longitudeA);
            String address = getCurrentAddress(latitudeA, longitudeA);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(address);
            mMap = googleMap;
            setDefaultLocation();
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            Log.e("", "넘어온 값 없음.");
        }
//        GpsTracker gpsTracker = new GpsTracker(getActivity());
//        double latitudeB = gpsTracker.getLatitude();
//        double longitudeB = gpsTracker.getLongitude();
//        LatLng local = new LatLng(latitude, longitude);
//        String address = getCurrentAddress(latitude, longitude);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(local);
//        markerOptions.title(address);
//        markerOptions.snippet("현재 위치");
//        mMap = googleMap;
//        setDefaultLocation();
//        mMap.addMarker(markerOptions);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(local));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public void setDefaultLocation() {
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인하세요";

        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);
    }
}
