package com.bong.bongstagram.Main.Ui.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;

public class ActivityFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.fragment_activity, container, false);
        ((MainActivity)getActivity()).bottomNavi(MainActivity.Type.activity);
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.activity);
        return view;
    }
}
