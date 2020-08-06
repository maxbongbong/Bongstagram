package com.bong.bongstagram.Main.Ui.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bong.bongstagram.Main.Ui.Activity.ActivityFragment;
import com.bong.bongstagram.Main.Ui.Gallery.GalleryFragment;
import com.bong.bongstagram.Main.Ui.GoogleMap.GoogleMapFragment;
import com.bong.bongstagram.Main.Ui.Home.HomeFragment;
import com.bong.bongstagram.Main.Ui.Profile.ProfileFragment;
import com.bong.bongstagram.Main.Ui.Search.SearchFragment;
import com.bong.bongstagram.Main.Ui.Splash.SplashFragment;
import com.bong.bongstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleMapFragment.OnApplySelectedListener {
    @SuppressLint("StaticFieldLeak")
    public static EditText edittext;
    private String address;
    private TextView toolbarTitle;
    private FrameLayout mainFrame;
    private Toolbar toolbar;
    public FragmentManager fm = getSupportFragmentManager();
    private String fragmentTag = fm.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment splashFragment = new SplashFragment();
        changeFragment(Type.splash, splashFragment);
        bottomNavigation(Type.splash);
    }

    public void bottomNavigation(Type t) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        if (t.ordinal() == 0 || t.ordinal() == 3 || t.ordinal() == 8 || t.ordinal() == 10) {
            bottomNavigationView.setVisibility(View.GONE);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        }
    }

    public void Toolbar(Type type) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainFrame = findViewById(R.id.main_frame);
        toolbarTitle = findViewById(R.id.toolbar_title);
        ImageView logo = findViewById(R.id.logo_image);
        ImageView searchImage = findViewById(R.id.search_image);
        edittext = findViewById(R.id.main_search_bar);

        switch (type) {
            case splash:
                getSupportActionBar().hide();
                break;
            case home:
                getSupportActionBar().show();
                mainFrame.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
                searchImage.setVisibility(View.GONE);
                edittext.setVisibility(View.GONE);
                toolbarTitle.setVisibility(View.GONE);
                break;
            case search:
                getSupportActionBar().show();
                mainFrame.setVisibility(View.VISIBLE);
                logo.setVisibility(View.GONE);
                searchImage.setVisibility(View.VISIBLE);
                edittext.setVisibility(View.VISIBLE);
                toolbarTitle.setVisibility(View.GONE);
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
            case reply:
                hideBar(Type.reply);
                break;
            case hide:
                toolbar.setVisibility(View.GONE);
                break;
        }
    }

    private void hideBar(Type type) {
        mainFrame.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        switch (type) {
            case gallery:
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(R.string.title_gallery);
                break;
            case activity:
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(R.string.title_activity);
                break;
            case profile:
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(R.string.title_profile);
                break;
            case local:
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(R.string.title_local);
                break;
            case google:
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(address);
                break;
            case reply:
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(R.string.title_reply_layout);
                break;
        }
    }

    @Override
    public void onCategoryApplySelected(String address) {
        this.address = address;
    }

    public enum Type {
        splash, home, search, gallery, activity, profile, local, google, reply, hide, modify
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void changeFragment(Type type, Fragment fragment) {
        fragmentTag = fragment.getClass().getSimpleName();
        if (type.ordinal() <= 1) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFrame, fragment);
            transaction.commit();
        } else {
            fm.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction transaction = fm.beginTransaction();
//            Log.e("", "Name = " + fragmentTag);
            transaction.replace(R.id.contentFrame, fragment);
            transaction.addToBackStack(fragmentTag);
            transaction.commit();
        }
    }

    public void changeFragmentInNavigation(Fragment fragment) {
        fragmentTag = fragment.getClass().getSimpleName();
        fm.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.addToBackStack(fragmentTag);
        transaction.commit();
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.miHome:
                    fragment = new HomeFragment();
                    changeFragmentInNavigation(fragment);
                    break;
                case R.id.miSearch:
                    fragment = new SearchFragment();
                    changeFragmentInNavigation(fragment);
                    break;
                case R.id.miGallery:
                    fragment = new GalleryFragment();
                    changeFragmentInNavigation(fragment);
                    break;
                case R.id.miActivity:
                    fragment = new ActivityFragment();
                    changeFragmentInNavigation(fragment);
                    break;
                case R.id.miProfile:
                    fragment = new ProfileFragment();
                    changeFragmentInNavigation(fragment);
                    break;
            }
            return true;
        }
    }
}






