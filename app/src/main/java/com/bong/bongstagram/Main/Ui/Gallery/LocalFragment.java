package com.bong.bongstagram.Main.Ui.Gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;

public class LocalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.local);
        View view = inflater.inflate(R.layout.fragment_local, container, false);


        Log.e("Local화면", "Local화면");
        return view;
    }
}
