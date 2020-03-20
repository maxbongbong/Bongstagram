package com.bong.bongstagram.Main.Ui.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {
    Context context;
    private ArrayList<MovieList> movieLists = null;
    private LayoutInflater mInflate;

    public HomeAdapter(Context context, ArrayList<MovieList> movieList){
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.movieLists = movieList;
    }

    @NonNull
    @Override
    public HomeAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflate.inflate(R.layout.fragment_home, parent, false);
        HomeAdapter.Holder holder = new HomeAdapter.Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.Holder holder, int position) {

        MovieList item = movieLists.get(position);
        holder.imageView.setImageResource(item.getImagedrw());
        holder.textView1.setText(item.getTitle());
        holder.textView2.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public Holder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_frame);
            textView1 = itemView.findViewById(R.id.username);
            textView2 = itemView.findViewById(R.id.hometext);
        }
    }
}

