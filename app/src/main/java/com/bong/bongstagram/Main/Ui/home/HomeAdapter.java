package com.bong.bongstagram.Main.Ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.GpsTracker;
import com.bong.bongstagram.Main.Model.MovieList;
import com.bong.bongstagram.Main.Ui.googleMap.GoogleMapFragment;
import com.bong.bongstagram.Main.Ui.main.MainActivity;
import com.bong.bongstagram.Main.Ui.reply.ReplyFragment;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {
    private Context context;
    private ArrayList<MovieList> movieLists;
    private LayoutInflater mInflate;
    private Animation mAnim;
    private Animation animation;

    HomeAdapter(Context context, ArrayList<MovieList> movieList) {
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.movieLists = movieList;
        mAnim = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.scale_heart);
        mAnim.setInterpolator(context.getApplicationContext(), android.R.anim.overshoot_interpolator);
        animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.big_scale_heart);
        animation.setInterpolator(context.getApplicationContext(), android.R.anim.overshoot_interpolator);
    }

    @NonNull
    @Override
    public HomeAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflate.inflate(R.layout.fragment_home, parent, false);

        return new Holder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.Holder holder, int position) {
        MovieList item = movieLists.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imageFrame);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imageProfile);

        holder.topUserName.setText(item.getTitle());
        holder.fullName.setText(gpsTracker(item.getLatitude(), item.getLongitude()));
        holder.fullName.setOnClickListener(v -> {
            Fragment googleFragment = new GoogleMapFragment();
            Bundle bundle = new Bundle();
            bundle.putString("image", item.getUrl());
            bundle.putString("address", gpsTracker(item.getLatitude(), item.getLongitude()));
            bundle.putDouble("latitude", item.getLatitude());
            bundle.putDouble("longitude", item.getLongitude());
            googleFragment.setArguments(bundle);
            ((MainActivity) context).changeFragment(MainActivity.Type.google, googleFragment);
        });

        doubleTapMethod(holder, item);

        holder.homeText.setText(TextBold(item.getTitle()));
        holder.homeText.append("   " + item.getDesc());

        heartAnimationMethod(holder.heartBtn, item);

        holder.replyBtn.setOnClickListener(v -> {
            Fragment replyFragment = new ReplyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", item.getTitle());
            bundle.putString("desc", item.getDesc());
            bundle.putString("image", item.getUrl());
            bundle.putString("date", item.getDate());
            replyFragment.setArguments(bundle);
            ((MainActivity) context).changeFragment(MainActivity.Type.reply, replyFragment);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void doubleTapMethod(Holder holder, MovieList item) {
        GestureDetector gd = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });

        GestureDetector.OnDoubleTapListener tap = new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                startAnimation(holder.heartBtn, holder.bigHeartImage);
                if (!item.isHeart()) {
                    holder.heartBtn.setSelected(true);
                    item.setHeart(true);
                }
                return true;
            }
        };

        gd.setOnDoubleTapListener(tap);

        holder.imageFrame.setOnTouchListener((v, event) -> {
            gd.onTouchEvent(event);
            return true;
        });
    }

    private void startAnimation(ImageView imageButton, ImageView imageView) {
        imageButton.startAnimation(mAnim);
        imageView.setVisibility(View.VISIBLE);
        imageView.startAnimation(animation);
        imageView.setVisibility(View.INVISIBLE);
    }

    private void heartAnimationMethod(ImageView imageView, MovieList item) {
        imageView.setOnClickListener(v -> {
            Log.e("item", "item = " + item.getTitle());
            v.startAnimation(mAnim);
            if (!item.isHeart()) {
                imageView.setSelected(true);
                item.setHeart(true);
            } else {
                imageView.setSelected(false);
                item.setHeart(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageFrame, bigHeartImage;
        CircleImageView imageProfile;
        TextView topUserName, fullName, homeText;
        ImageView heartBtn, replyBtn;

        @SuppressLint("ClickableViewAccessibility")
        Holder(@NonNull View itemView) {
            super(itemView);
            imageFrame = itemView.findViewById(R.id.image_frame);
            imageProfile = itemView.findViewById(R.id.image_profile);
            bigHeartImage = itemView.findViewById(R.id.image_favorite_Big);
            topUserName = itemView.findViewById(R.id.topUsername);
            fullName = itemView.findViewById(R.id.fullName);
            homeText = itemView.findViewById(R.id.homeText);
            heartBtn = itemView.findViewById(R.id.btn_favorite);
            replyBtn = itemView.findViewById(R.id.reply_btn);
        }
    }

    private String gpsTracker(double latitude, double longitude) {
        GpsTracker gpsTracker = new GpsTracker(context);
//       double latitude = gpsTracker.getLatitude();
//       double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);

        return address;
    }

    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Toast.makeText(context, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(context, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0) + "\n";
    }

    public interface addressData {
        void itemAddress(String address);
    }

    private SpannableString TextBold(String text) {
        SpannableString sb = new SpannableString(text);
        int start = text.indexOf(text);
        int end = start + text.length();
        sb.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}

