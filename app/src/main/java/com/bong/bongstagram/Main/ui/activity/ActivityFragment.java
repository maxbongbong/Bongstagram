package com.bong.bongstagram.Main.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.data.Movie;
import com.bong.bongstagram.Main.model.MovieList;
import com.bong.bongstagram.Main.ui.main.MainActivity;
import com.bong.bongstagram.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ActivityFragment extends Fragment {
    private ArrayList<MovieList>newActivity = new ArrayList<>();
    private ArrayList<MovieList>yesterdayData = new ArrayList<>();
    private ArrayList<MovieList>thisWeekData = new ArrayList<>();
    private ArrayList<MovieList>beforeActivityData = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_activity, container, false);

        ((MainActivity)getActivity()).bottomNavigation(MainActivity.Type.activity);
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.activity);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();

        divideData();

        RecyclerView recyclerView1 = view.findViewById(R.id.recycler_newActivity);
        RecyclerView recyclerView2 = view.findViewById(R.id.recycler_yesterday);
        RecyclerView recyclerView3 = view.findViewById(R.id.recycler_thisWeek);
        RecyclerView recyclerView4 = view.findViewById(R.id.recycler_before);

        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView4.setHasFixedSize(true);

        recyclerView1.setLayoutManager(new LinearLayoutManager(context));
        recyclerView1.setAdapter(new ActivityAdapter(context, newActivity));

        recyclerView2.setLayoutManager(new LinearLayoutManager(context));
        recyclerView2.setAdapter(new ActivityAdapter(context, yesterdayData));

        recyclerView3.setLayoutManager(new LinearLayoutManager(context));
        recyclerView3.setAdapter(new ActivityAdapter(context, thisWeekData));

        recyclerView4.setLayoutManager(new LinearLayoutManager(context));
        recyclerView4.setAdapter(new ActivityAdapter(context, beforeActivityData));
    }

    private void divideData(){

        ArrayList<MovieList> movie = new Movie().getItems();
        LinearLayout layout;
        int j = 0;

        for(int i = 0; i < movie.size(); i++){

            SimpleDateFormat nowDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
            String data = movie.get(i).getDate();
            Date today = Calendar.getInstance().getTime();

            try{
                Date dataDate = nowDate.parse(data);
                long dateDay = (today.getTime() - dataDate.getTime()) / (24 * 60 * 60 * 1000);
//                long dateWeek = dateDay / 7;
                if(dateDay == 0){
                    newActivity.add(movie.get(i));
                } else if (dateDay == 1){
                    yesterdayData.add(movie.get(i));
                } else if ((2 <= dateDay) && (dateDay < 7)) {
                    thisWeekData.add(movie.get(i));
                } else {
                    beforeActivityData.add(movie.get(i));
                }
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        sorting(newActivity);
        sorting(yesterdayData);
        sorting(thisWeekData);
        sorting(beforeActivityData);

        switch (j){
            case 0:
                layout = view.findViewById(R.id.activity_layout1);
                if(newActivity.size() == 0)  layout.setVisibility(View.GONE);
                else layout.setVisibility(View.VISIBLE);
                break;
            case 1:
                layout = view.findViewById(R.id.activity_layout2);
                if(yesterdayData.size() == 0) layout.setVisibility(View.GONE);
                else layout.setVisibility(View.VISIBLE);
                break;
            case 2:
                layout = view.findViewById(R.id.activity_layout3);
                if(thisWeekData.size() == 0) layout.setVisibility(View.GONE);
                else layout.setVisibility(View.VISIBLE);
                break;
            case 3:
                layout = view.findViewById(R.id.activity_layout4);
                if(beforeActivityData.size() == 0) layout.setVisibility(View.GONE);
                else layout.setVisibility(View.VISIBLE);
                break;
        }
    }
    //오름차순 정렬
    private void sorting(ArrayList arrayList){
        Collections.sort(arrayList, new Comparator<MovieList>(){
            @Override
            public int compare(MovieList o1, MovieList o2){
                return o2.getDate().compareTo(o1.getDate());
            }
        });
    }
}
