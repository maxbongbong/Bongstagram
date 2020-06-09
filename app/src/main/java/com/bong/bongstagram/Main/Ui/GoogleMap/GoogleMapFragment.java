package com.bong.bongstagram.Main.Ui.GoogleMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.GpsTracker;
import com.bong.bongstagram.Main.Data.Image;
import com.bong.bongstagram.Main.Ui.Dialog.EventDialogFragment;
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
    private RecyclerView.LayoutManager mLayoutManager;
    private Marker currentMarker = null;
    private double latitude;
    private double longitude;
    private TextView popularText;
    private TextView recentText;
    public GoogleMapFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.activity = (Activity)context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnApplySelectedListener{
        void onCategoryApplySelected(String address);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_googlemap, container, false);
        popularText = view.findViewById(R.id.popular_Text);
        popularText.setSelected(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView profile = view.findViewById(R.id.circle_Image);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.google_recycler);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);
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
        }else{
            Log.e("TODO", "넘어온 값 없음");
        }
        mapView = view.findViewById(R.id.map);
        mapView.getMapAsync(this);

        TextView textView = view.findViewById(R.id.google_textView);
        textView.setText(distance());
        ((OnApplySelectedListener)activity).onCategoryApplySelected(address);
        ((MainActivity) getActivity()).bottomNavigation(MainActivity.Type.google);
        ((MainActivity) getActivity()).Toolbar(MainActivity.Type.google);
        setHasOptionsMenu(true);

        TextView btn = view.findViewById(R.id.google_Btn);
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
        if(mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    public String getCurrentAddress(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        }catch (IOException e){
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        }catch (IllegalArgumentException e){
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }
        if(addresses == null || addresses.size() == 0){
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }else{
            Address address = addresses.get(0);
            return address.getAddressLine(0) + "\n";
        }
    }

    @SuppressLint("DefaultLocale")
    private String distance(){
        address = getArguments().getString("address");
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

        return  distanceText;
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
        } else{
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

    public void setDefaultLocation(){
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인하세요";

        if(currentMarker != null)currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_air, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
