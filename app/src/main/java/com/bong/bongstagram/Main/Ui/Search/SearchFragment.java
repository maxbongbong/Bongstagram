package com.bong.bongstagram.Main.Ui.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Ui.main.MainActivity;
import com.bong.bongstagram.R;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ((MainActivity)getActivity()).bottomNavi(MainActivity.Type.serarch);
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.serarch);
        return view;
    }


}
