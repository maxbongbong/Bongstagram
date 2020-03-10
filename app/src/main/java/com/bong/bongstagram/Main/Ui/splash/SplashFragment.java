package com.bong.bongstagram.Main.Ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bong.bongstagram.Main.Ui.Home.HomeFragment;
import com.bong.bongstagram.Main.Ui.main.MainActivity;
import com.bong.bongstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SplashFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ((MainActivity)getActivity()).bottomNavi(MainActivity.Type.splash);
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.splash);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 2000);
        Log.e("view", "view = splash화면");
    }

    private class splashhandler implements Runnable{
        @Override
        public void run() {
            Fragment homeFragment = new HomeFragment();
            ((MainActivity)getActivity()).changeFragment(MainActivity.Type.home, homeFragment);
        }
    }
}
