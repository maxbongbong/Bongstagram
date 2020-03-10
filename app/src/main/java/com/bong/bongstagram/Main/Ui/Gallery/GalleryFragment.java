package com.bong.bongstagram.Main.Ui.Gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bong.bongstagram.Main.Ui.main.MainActivity;
import com.bong.bongstagram.R;

public class GalleryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.fragment_gallery, container, false);
        ((MainActivity)getActivity()).bottomNavi(MainActivity.Type.gallery);
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.gallery);
        return view;
    }
}