package com.bong.bongstagram.Main.Ui.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bong.bongstagram.Main.Ui.Activity.ActivityFragment;
import com.bong.bongstagram.Main.Ui.Gallery.GalleryFragment;
import com.bong.bongstagram.Main.Ui.GoogleMap.GoogleMapFragment;
import com.bong.bongstagram.Main.Ui.Home.HomeFragment;
import com.bong.bongstagram.Main.Ui.Local.LocalFragment;
import com.bong.bongstagram.Main.Ui.Profile.ProfileFragment;
import com.bong.bongstagram.Main.Ui.Reply.ReplyFragment;
import com.bong.bongstagram.Main.Ui.Search.SearchFragment;
import com.bong.bongstagram.Main.Ui.Splash.SplashFragment;
import com.bong.bongstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements GoogleMapFragment.OnApplySelectedListener {
    @SuppressLint("StaticFieldLeak")
    public static EditText edittext;
    private ImageView logo, searchImage;
    private String address;
    private TextView toolbarTitle;
    private FrameLayout mainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment splashFragment = new SplashFragment();
        changeFragment(Type.splash, splashFragment);
        bottomNavigation(Type.splash);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void bottomNavigation(Type t){
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        if (t.ordinal() == 0 || t.ordinal() == 3 || t.ordinal() == 8) {
            bottomNavigationView.setVisibility(View.GONE);
        }else{
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        }
    }

    public void Toolbar(Type type){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainFrame = findViewById(R.id.main_frame);
        toolbarTitle = findViewById(R.id.toolbar_title);
        logo = findViewById(R.id.logo_image);
        searchImage = findViewById(R.id.search_image);
        edittext = findViewById(R.id.main_search_bar);

        switch (type){
            case splash:
                getSupportActionBar().hide();
                break;
            case home:
                getSupportActionBar().show();
                mainFrame.setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.outline_camera_alt_24);
                logo.setVisibility(View.VISIBLE);
                searchImage.setVisibility(View.GONE);
                edittext.setVisibility(View.GONE);
                break;
            case search:
                mainFrame.setVisibility(View.VISIBLE);
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
            case reply:
                toolbar.setVisibility(View.VISIBLE);
                hideBar(Type.reply);
                break;
            case hide:
                toolbar.setVisibility(View.GONE);
                break;
        }
    }

    private void hideBar(Type type){
        mainFrame.setVisibility(View.GONE);
        switch (type){
            case gallery:
                toolbarTitle.setText(R.string.title_gallery);
                break;
            case activity:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toolbarTitle.setText(R.string.title_activity);
                break;
            case profile:
                toolbarTitle.setText(R.string.title_profile);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_keyboard_arrow_down_24);
                break;
            case local:
                toolbarTitle.setText(R.string.title_local);
                break;
            case google:
                toolbarTitle.setText(address);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_keyboard_backspace_24);
                break;
            case reply:
                toolbarTitle.setText(R.string.title_reply_layout);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_keyboard_backspace_24);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
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
        splash, home, search, gallery, activity, profile, local, google, reply, hide
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
            switch (menuItem.getItemId()){
                case R.id.miHome:
                    fragment(Type.home);
                    break;
                case R.id.miSearch:
                    fragment(Type.search);
                    break;
                case R.id.miGallery:
                    fragment(Type.gallery);
                    break;
                case R.id.miActivity:
                    fragment(Type.activity);
                    break;
                case R.id.miProfile:
                    fragment(Type.profile);
                    break;
            }
            return true;
        }
    }

    private void fragment(Type type){
        switch (type.ordinal()){
            case 1:
                HomeFragment fragmentHome = new HomeFragment();
                test(fragmentHome);
                break;
            case 2:
                SearchFragment fragmentSearch = new SearchFragment();
                test(fragmentSearch);
                break;
            case 3:
                GalleryFragment fragmentGallery = new GalleryFragment();
                test(fragmentGallery);
                break;
            case 4:
                ActivityFragment fragmentActivity = new ActivityFragment();
                test(fragmentActivity);
                break;
            case 5:
                ProfileFragment fragmentProfile = new ProfileFragment();
                test(fragmentProfile);
                break;
            case 6:
                LocalFragment localFragment = new LocalFragment();
                test(localFragment);
                break;
            case 7:
                GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                test(googleMapFragment);
                break;
            case 8:
                ReplyFragment replyFragment = new ReplyFragment();
                test(replyFragment);
                break;
        }
    }

    private void test(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.contentFrame, fragment).commit();
    }
}






