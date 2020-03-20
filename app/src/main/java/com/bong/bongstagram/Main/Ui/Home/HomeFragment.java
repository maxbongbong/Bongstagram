package com.bong.bongstagram.Main.Ui.Home;

import android.content.Context;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.Apiservice;
import com.bong.bongstagram.Main.Data.Post;
import com.bong.bongstagram.Main.Network.RetrofitMaker;
import com.bong.bongstagram.Main.Ui.main.MainActivity;
import com.bong.bongstagram.R;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bong.bongstagram.R.drawable.instalogo;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    ArrayList<MovieList> data = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list, container, false);
        Context context = view.getContext();
        ((MainActivity) getActivity()).bottomNavi(MainActivity.Type.home);
        ((MainActivity) getActivity()).Toolbar(MainActivity.Type.home);

        recyclerView = view.findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);

        homeAdapter = new HomeAdapter(context, data);
        recyclerView.setAdapter(homeAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataset(instalogo, "첫번째 타이틀", "첫번째 설명 첫번째 설명 첫번째 설명 첫번째 설명 첫번째 설명 첫번째 설명 첫번째 설명 첫번째 설명 첫번째 설명");
        initDataset(instalogo, "두번째 타이틀", "두번째 설명 두번째 설명 두번째 설명 두번째 설명 두번째 설명 두번째 설명 두번째 설명 두번째 설명 두번째 설명");
        initDataset(instalogo, "세번째 타이틀", "세번째 설명 세번째 설명 세번째 설명 세번째 설명 세번째 설명 세번째 설명 세번째 설명 세번째 설명 세번째 설명");

        homeAdapter.notifyDataSetChanged();
    }

    public void initDataset(int image, String title, String desc){


        MovieList item = new MovieList();

        item.setImagedrw(image);
        item.setTitle(title);
        item.setDesc(desc);

        data.add(item);
    }
}