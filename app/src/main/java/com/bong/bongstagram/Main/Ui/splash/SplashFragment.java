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

public class SplashFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_splash, container, false);
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 5000);
        Log.e("view", "view = splash화면");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fragment homeFragment = new HomeFragment();
        ((MainActivity)getActivity()).changeFragment(MainActivity.Type.home, homeFragment);
    }

    private class splashhandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getActivity(), HomeFragment.class));
        }
    }
}
