package com.bong.bongstagram.Main.Ui.Search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.Movie;
import com.bong.bongstagram.Main.Model.MovieList;
import com.bong.bongstagram.Main.Ui.main.MainActivity;
import com.bong.bongstagram.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText search_bar;
    private SearchAdapter searchAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Context context = view.getContext();
        ((MainActivity)getActivity()).bottomNavi(MainActivity.Type.serarch);
        ((MainActivity)getActivity()).Toolbar(MainActivity.Type.serarch);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);

        searchAdapter = new SearchAdapter(context, new Movie().getItems());

        search_bar = view.findViewById(R.id.search_bar);
        recyclerView.setAdapter(searchAdapter);

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = search_bar.getText().toString().toLowerCase(Locale.getDefault());
                Log.e("test", "text : " + text);
                searchAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
}
