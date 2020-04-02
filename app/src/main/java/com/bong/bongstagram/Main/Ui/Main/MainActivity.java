package com.bong.bongstagram.Main.Ui.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bong.bongstagram.Main.Ui.Activity.ActivityFragment;
import com.bong.bongstagram.Main.Ui.Gallery.GalleryFragment;
import com.bong.bongstagram.Main.Ui.Home.HomeFragment;
import com.bong.bongstagram.Main.Ui.Profile.ProfileFragment;
import com.bong.bongstagram.Main.Ui.Search.SearchFragment;
import com.bong.bongstagram.Main.Ui.Splash.SplashFragment;
import com.bong.bongstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static EditText edittext;
    private ImageView logo;
    private ImageView searchimage;

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
        searchimage = findViewById(R.id.search_iamge);
        edittext = findViewById(R.id.main_search_bar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        switch (type){
            case splash:
                getSupportActionBar().hide();
                break;
            case home:
                getSupportActionBar().show();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_camera_alt_24);
                logo.setVisibility(View.VISIBLE);
                searchimage.setVisibility(View.INVISIBLE);
                edittext.setVisibility(View.INVISIBLE);
                break;
            case search:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                logo.setVisibility(View.INVISIBLE);
                searchimage.setVisibility(View.VISIBLE);
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
        }
    }

    private void hideBar(Type type){
        logo = findViewById(R.id.logo_image);
        searchimage = findViewById(R.id.search_iamge);
        edittext = findViewById(R.id.main_search_bar);
        logo.setVisibility(View.INVISIBLE);
        searchimage.setVisibility(View.INVISIBLE);
        edittext.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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
                getSupportActionBar().setTitle("새 게시물");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.layout.recyclerview:
                return  true;
        }
            return super.onOptionsItemSelected(item);
    }

    public enum Type{
        splash, home, search, gallery, activity, profile, local
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

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()){
                case R.id.miHome:
                    HomeFragment fragmenthome = new HomeFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmenthome).commit();
                    break;
                case R.id.miSearch:
                    SearchFragment fragmentsearch = new SearchFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentsearch).commit();
                    break;
                case R.id.miGallery:
                    GalleryFragment fragmentgallery = new GalleryFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentgallery).commit();
                    break;
                case R.id.miActivity:
                    ActivityFragment fragmentActivity = new ActivityFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentActivity).commit();
                    break;
                case R.id.miProfile:
                    ProfileFragment fragmentprofile = new ProfileFragment();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragmentprofile).commit();
                    break;
            }
            return true;
        }
    }

}






