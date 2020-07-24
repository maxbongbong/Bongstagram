package com.bong.bongstagram.Main.Ui.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.Image;
import com.bong.bongstagram.Main.Data.Movie;
import com.bong.bongstagram.Main.Model.ImageList;
import com.bong.bongstagram.Main.Model.MovieList;
import com.bong.bongstagram.Main.Ui.CustomView.CustomProfileCorrection;
import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private LinearLayout gridOn;
    private LinearLayout assignment;
    private ProfileAdapter profileAdapter;
    private RecyclerView recyclerView;
    private Fragment profileModifyFragment;
    private TextView tv1, tv2, tv3, tv4;
    private CircleImageView ProfileImage;
    private Context context;
    private View correction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity) getActivity()).bottomNavigation(MainActivity.Type.profile);
        ((MainActivity) getActivity()).Toolbar(MainActivity.Type.hide);
        setHasOptionsMenu(true);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();

        tv1 = view.findViewById(R.id.profile_Name);
        tv2 = view.findViewById(R.id.profile_Toolbar_Title);
        tv3 = view.findViewById(R.id.profile_Website);
        tv4 = view.findViewById(R.id.profile_Bio);
        ProfileImage = view.findViewById(R.id.Profile);

        recyclerView = view.findViewById(R.id.profile_recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);

        profileAdapter = new ProfileAdapter(context, new Image().UserImage());
        recyclerView.setAdapter(profileAdapter);

        gridOn = view.findViewById(R.id.profile_GridOn);
        assignment = view.findViewById(R.id.profile_Assignment);

        View promotion, insite;
        correction = view.findViewById(R.id.profile_Custom_Correction);
        promotion = view.findViewById(R.id.profile_Custom_Promotion);
        insite = view.findViewById(R.id.profile_Custom_Insite);

        Button correctionBtn, promotionBtn, insiteBtn;
        correctionBtn = view.findViewById(R.id.profile_ModifyBtn);
        promotionBtn = view.findViewById(R.id.profile_promotion);
        insiteBtn = view.findViewById(R.id.profile_insite);

        profileModifyFragment = new ProfileModifyFragment();
        View gridView = view.findViewById(R.id.gridon_View);
        View assignmentView = view.findViewById(R.id.assignment_View);
        gridOn.setSelected(true);

        touchListenerMethod(correction, correctionBtn);
        touchListenerMethod(promotion, promotionBtn);
        touchListenerMethod(insite, insiteBtn);

        getSharedPreferences();

        tv3.setOnClickListener(v -> {
            connectURL();
            if (connectURL().resolveActivity(context.getPackageManager()) != null) {
                startActivity(connectURL());
            }
        });

        gridOn.setOnClickListener(v -> {
            gridView.setVisibility(View.VISIBLE);
            assignmentView.setVisibility(View.INVISIBLE);
            gridOn.setSelected(true);
            assignment.setSelected(false);
            profileAdapter = new ProfileAdapter(context, new Image().UserImage());
            recyclerView.setAdapter(profileAdapter);
        });

        assignment.setOnClickListener(v -> {
            assignmentView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            assignment.setSelected(true);
            gridOn.setSelected(false);
            profileAdapter = new ProfileAdapter(context, new Image().getDummy());
            recyclerView.setAdapter(profileAdapter);
        });
    }

    private void getSharedPreferences() {
        String sfName = "test";
        SharedPreferences sf = context.getSharedPreferences(sfName, Context.MODE_PRIVATE);

        String Profile = sf.getString("Profile", null);
        String Name = sf.getString("Name", null);
        String Username = sf.getString("Username", null);
        String Website = sf.getString("Website", null);
        String Bio = sf.getString("Bio", null);

//        Log.e("image", "imagePath = " + Profile);

        if (Profile != null) {
            Glide.with(context).load(Profile).into(ProfileImage);
        } else {
            ProfileImage.setBackground(context.getDrawable(R.drawable.circlebackground));
            Glide.with(context).load(context.getDrawable(R.mipmap.test)).into(ProfileImage);
        }
        textViewCheck(tv1, Name);
        textViewCheck(tv2, Username);
        textViewCheck(tv3, Website);
        textViewCheck(tv4, Bio);
//        Log.e("data", "Name = " + Name + ", Username = " + Username + ", Website = " + Website + ", Bio = " + Bio);
    }

    @SuppressLint("CommitPrefEdits")
    private Intent connectURL() {
        String url = tv3.getText().toString();
        String sfName = "test";
        String https = "https://";
        Intent intent;
        Log.e("boolean", "test = " + url.startsWith(https));
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = https + url;
            SharedPreferences sf = context.getSharedPreferences(sfName, Context.MODE_PRIVATE);
            sf.edit().putString("Website", url).apply();
            Log.e("url", "url = " + sf.getString("Website", null));
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        }
        return intent;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void touchListenerMethod(View view, Button btn) {
        btn.setOnTouchListener((v, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    view.setBackground(context.getDrawable(R.drawable.btn_effect_true));
                    return true;
                case MotionEvent.ACTION_MOVE:
                    view.setBackground(context.getDrawable(R.drawable.btn_effect_true));
                    return false;
                case MotionEvent.ACTION_UP:
                    view.setBackground(context.getDrawable(R.drawable.btn_effect_false));
                    if (view == correction)
                        ((MainActivity) getActivity()).changeFragment(MainActivity.Type.modify, profileModifyFragment);
                    return true;
                case MotionEvent.ACTION_CANCEL:
                    view.setBackground(context.getDrawable(R.drawable.btn_effect_false));
                    return true;
            }
            return false;
        });
    }

    private void textViewCheck(TextView textView, String str) {
        if (str == null || str.equals("")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.append(str);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
