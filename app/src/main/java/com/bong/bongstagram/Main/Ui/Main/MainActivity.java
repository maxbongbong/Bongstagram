package com.bong.bongstagram.Main.Ui.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bong.bongstagram.Main.Data.GpsTracker;
import com.bong.bongstagram.Main.Data.Movie;
import com.bong.bongstagram.Main.Model.MovieList;
import com.bong.bongstagram.Main.Ui.Activity.ActivityFragment;
import com.bong.bongstagram.Main.Ui.Gallery.GalleryFragment;
import com.bong.bongstagram.Main.Ui.GoogleMap.GoogleMapFragment;
import com.bong.bongstagram.Main.Ui.Home.HomeAdapter;
import com.bong.bongstagram.Main.Ui.Home.HomeFragment;
import com.bong.bongstagram.Main.Ui.Profile.ProfileFragment;
import com.bong.bongstagram.Main.Ui.Search.SearchFragment;
import com.bong.bongstagram.Main.Ui.Splash.SplashFragment;
import com.bong.bongstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleMapFragment.OnApplySelectedListener {
    public static EditText edittext;
    private ImageView logo;
    private String address;
    private ImageView searchImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment splashFragment = new SplashFragment();
        changeFragment(Type.splash, splashFragment);
        bottomNavi(Type.splash);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void bottomNavi(Type t){
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        if (t.ordinal() == 0 || t.ordinal() == 3) {
            bottomNavigationView.setVisibility(View.GONE);
        }else{
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        }
    }

    public void Toolbar(Type type){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        logo = findViewById(R.id.logo_image);
        searchImage = findViewById(R.id.search_image);
        edittext = findViewById(R.id.main_search_bar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView text = findViewById(R.id.toolbar_title);
        text.setVisibility(View.GONE);
        switch (type){
            case splash:
                getSupportActionBar().hide();
                break;
            case home:
                getSupportActionBar().show();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_camera_alt_24);
                logo.setVisibility(View.VISIBLE);
                searchImage.setVisibility(View.GONE);
                edittext.setVisibility(View.GONE);
                break;
            case search:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                logo.setVisibility(View.GONE);
                searchImage.setVisibility(View.VISIBLE);
                edittext.setVisibility(View.VISIBLE);
                break;
            case gallery:
                hideBar(Type.gallery);
                break;
            case activity:
                hideBar(Type.activity);
                break;
            case profile:
                hideBar(Type.profile);
                break;
            case local:
                hideBar(Type.local);
                break;
            case google:
                hideBar(Type.google);
                break;
        }
    }

    private void hideBar(Type type){
        logo = findViewById(R.id.logo_image);
        searchImage = findViewById(R.id.search_image);
        edittext = findViewById(R.id.main_search_bar);
        logo.setVisibility(View.INVISIBLE);
        searchImage.setVisibility(View.INVISIBLE);
        edittext.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        TextView text = findViewById(R.id.toolbar_title);
        switch (type){
            case gallery:
                getSupportActionBar().setTitle(R.string.title_gallery);
                break;
            case activity:
                getSupportActionBar().setTitle(R.string.title_activity);
                break;
            case profile:
                getSupportActionBar().setTitle(R.string.title_profile);
                break;
            case local:
                getSupportActionBar().setTitle(R.string.title_local);
                break;
            case google:
                if (type.google == Type.google) {
                    text.setVisibility(View.VISIBLE);
                    text.setText(address);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_keyboard_backspace_24);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                Fragment homeFragment = new HomeFragment();
                changeFragment(Type.home, homeFragment);
                break;
            case R.layout.recyclerview:
                return true;
        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCategoryApplySelected(String address) {
        this.address = address;
    }

    public enum Type{
        splash, home, search, gallery, activity, profile, local, google
    }

    public void changeFragment(Type type, Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right_left, R.anim.fragment_close_exit);
        if (type.ordinal() <= 1) {
            transaction.replace(R.id.contentFrame, fragment).commit();
        } else if (type.ordinal() > 1) {
            transaction.addToBackStack(null);
            transaction.replace(R.id.contentFrame, fragment).commit();
        }
    }

    public void reFairView(){
//        Display display = getWindowManager().getDefaultDisplay();
//        LinearLayout layout = findViewById(R.id.linearLayout7);
//        int w = display.getWidth();
//        int h = display.getHeight();
//        Log.e("높이", "높이 = " + h);
//        LinearLayout.LayoutParams position = new LinearLayout.LayoutParams(w, h);
//        layout.setLayoutParams(new LinearLayout.LayoutParams(position));
////        DisplayMetrics outMetrics = new DisplayMetrics();
////        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
////        h = 100 * (int)outMetrics.density;
//        Log.e("높이", "높이 = " + h);
        int bottom = 0;
        int resourceBottom = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if(resourceBottom > 0 ) bottom = getResources().getDimensionPixelSize(resourceBottom);
        Log.e("bottom", "bottom = " + bottom);
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()){
                case R.id.miHome:
                    HomeFragment fragmentHome = new HomeFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentHome).commit();
                    break;
                case R.id.miSearch:
                    SearchFragment fragmentSearch = new SearchFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentSearch).commit();
                    break;
                case R.id.miGallery:
                    GalleryFragment fragmentGallery = new GalleryFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentGallery).commit();
                    break;
                case R.id.miActivity:
                    ActivityFragment fragmentActivity = new ActivityFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentActivity).commit();
                    break;
                case R.id.miProfile:
                    ProfileFragment fragmentProfile = new ProfileFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentProfile).commit();
                    break;
            }
            return true;
        }
    }
}






