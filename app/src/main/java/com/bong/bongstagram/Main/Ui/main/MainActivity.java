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

import com.bong.bongstagram.Main.Ui.Activity.ActivityFragment;
import com.bong.bongstagram.Main.Ui.Gallery.GalleryFragment;
import com.bong.bongstagram.Main.Ui.Home.HomeFragment;
import com.bong.bongstagram.Main.Ui.Profile.ProfileFragment;
import com.bong.bongstagram.Main.Ui.Search.SearchFragment;
import com.bong.bongstagram.Main.Ui.splash.SplashFragment;
import com.bong.bongstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private HomeFragment fragmenthome = new HomeFragment();
    private SearchFragment fragmentsearch = new SearchFragment();
    private GalleryFragment fragmentgallery = new GalleryFragment();
    private ActivityFragment fragmentActivity = new ActivityFragment();
    private ProfileFragment fragmentprofile = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment splashFragment;
        splashFragment = new SplashFragment();
//        Toolbar(0);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        changeFragment(Type.splash, splashFragment);
        Log.e("view", "view = Mainactivity화면");
    }

    public void Toolbar(int num){
        Toolbar toolbar = (Toolbar) findViewById(R.id.navigationView);
        toolbar.setNavigationIcon(R.drawable.outline_home_24);
        toolbar.setNavigationIcon(R.drawable.baseline_search_24);
        toolbar.setNavigationIcon(R.drawable.baseline_add_circle_outline_black_18);
        toolbar.setNavigationIcon(R.drawable.baseline_favorite_border_24);
        toolbar.setNavigationIcon(R.drawable.baseline_account_box_24);
        setSupportActionBar(toolbar);

        switch (num){
            case 0:
                getSupportActionBar().hide();
                break;
            case 1:
                getSupportActionBar().show();
                getSupportActionBar().setTitle(R.string.title_home);
                break;
            case 2:
                getSupportActionBar().setTitle(R.string.title_search);
                break;
            case 3:
                getSupportActionBar().setTitle(R.string.title_gallery);
                break;
            case 4:
                getSupportActionBar().setTitle(R.string.title_activity);
                break;
            case 5:
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
            transaction.replace(R.id.contentFrame, fragment);
        } else if (type.ordinal() > 1) {
            transaction.addToBackStack(null);
            transaction.replace(R.id.contentFrame, fragment);
        }
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()){
                case R.id.miHome:
                    transaction.replace(R.id.contentFrame, fragmenthome).commitAllowingStateLoss();
                    break;
                case R.id.miSearch:
                    transaction.replace(R.id.contentFrame, fragmentsearch).commitAllowingStateLoss();
                    break;
                case R.id.miGallery:
                    transaction.replace(R.id.contentFrame, fragmentgallery).commitAllowingStateLoss();
                    break;
                case R.id.miActivity:
                    transaction.replace(R.id.contentFrame, fragmentActivity).commitAllowingStateLoss();
                    break;
                case R.id.miProfile:
                    transaction.replace(R.id.contentFrame, fragmentprofile).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}






