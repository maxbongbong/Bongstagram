package com.bong.bongstagram.Main.Ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bong.bongstagram.Main.Ui.Activity.ActivityFragment;
import com.bong.bongstagram.Main.Ui.Gallery.GalleryFragment;
import com.bong.bongstagram.Main.Ui.Home.HomeFragment;
import com.bong.bongstagram.Main.Ui.Profile.ProfileFragment;
import com.bong.bongstagram.Main.Ui.Search.SearchFragment;
import com.bong.bongstagram.Main.Ui.splash.SplashFragment;
import com.bong.bongstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment splashFragment = new SplashFragment();
        changeFragment(Type.splash, splashFragment);
        Toolbar(Type.splash);
        bottomNavi(Type.splash);
        Log.e("view", "view = Mainactivity화면");
    }

    public void bottomNavi(Type t){
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        if (t.ordinal() == 0) {
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }else{
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        }
    }

    public void Toolbar(Type type){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switch (type){
            case splash:
                getSupportActionBar().hide();
                break;
            case home:
                getSupportActionBar().show();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_camera_alt_24);
                getSupportActionBar().setTitle(R.string.title_home);
                break;
            case serarch:
                getSupportActionBar().setTitle(R.string.title_search);
                break;
            case gallery:
                getSupportActionBar().setTitle(R.string.title_gallery);
                break;
            case activity:
                getSupportActionBar().setTitle(R.string.title_activity);
                break;
            case profile:
                getSupportActionBar().setTitle(R.string.title_profile);
                break;
        }
    }

    public enum Type{
        splash, home, serarch, gallery, activity, profile
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






