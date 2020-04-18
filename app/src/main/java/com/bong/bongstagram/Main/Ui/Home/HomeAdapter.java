package com.bong.bongstagram.Main.Ui.Home;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.GpsTracker;
import com.bong.bongstagram.Main.Model.MovieList;
import com.bong.bongstagram.Main.Ui.Dialog.EventDialogFragment;
import com.bong.bongstagram.Main.Ui.GoogleMap.GoogleMapFragment;
import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {
    private Context context;
    private ArrayList<MovieList> movieLists;
    private LayoutInflater mInflate;
    private GpsTracker gpsTracker;

    public HomeAdapter(Context context, ArrayList<MovieList> movieList){
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.movieLists = movieList;
    }

    @NonNull
    @Override
    public HomeAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflate.inflate(R.layout.fragment_home, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.Holder holder, int position) {
        MovieList item = movieLists.get(position);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imageView);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imageView2);
        holder.textView1.setText(item.getTitle());
        holder.textView2.setText(gpsTracker(item.getLatitude(), item.getLongitude()));
        holder.textView2.setOnClickListener(v -> {
            Fragment googleFragment = new GoogleMapFragment();
            Bundle bundle = new Bundle();
            bundle.putString("image", item.getUrl());
            bundle.putString("address", gpsTracker(item.getLatitude(), item.getLongitude()));
            bundle.putDouble("latitude", item.getLatitude());
            bundle.putDouble("longitude", item.getLongitude());
            googleFragment.setArguments(bundle);
            ((MainActivity)context).changeFragment(MainActivity.Type.google, googleFragment);
        });
        holder.textView3.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView, imageView2;
        TextView textView1, textView2, textView3;

        public Holder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_frame);
            imageView2 = itemView.findViewById(R.id.image_profile);
            textView1 = itemView.findViewById(R.id.username);
            textView2 = itemView.findViewById(R.id.fullname);
            textView3 = itemView.findViewById(R.id.homeText);
        }
    }

    private String gpsTracker(double latitude, double longitude){
        gpsTracker = new GpsTracker(context);
//       double latitude = gpsTracker.getLatitude();
//       double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);

        return address;
    }

    public String getCurrentAddress(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        }catch (IOException e){
            Toast.makeText(context, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        }catch (IllegalArgumentException e){
            Toast.makeText(context, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }
        if(addresses == null || addresses.size() == 0){
            Toast.makeText(context, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0) + "\n";
    }

    public interface addressData{
        void itemAddress(String address);
    }


}

