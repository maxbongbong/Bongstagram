package com.bong.bongstagram.Main.Ui.Splash;

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
import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;

public class SplashFragment extends Fragment {
    Fragment fragment = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ((MainActivity)getActivity()).bottomNavigation(MainActivity.Type.splash);
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.splash);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 1000);
    }

    private class splashHandler implements Runnable{
        @Override
        public void run() {
            Fragment homeFragment = new HomeFragment();
            ((MainActivity)getActivity()).changeFragment(MainActivity.Type.home, homeFragment);
        }
    }
}
