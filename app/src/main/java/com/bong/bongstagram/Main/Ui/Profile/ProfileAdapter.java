package com.bong.bongstagram.Main.Ui.Profile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.Movie;
import com.bong.bongstagram.Main.Model.ImageList;
import com.bong.bongstagram.Main.Model.MovieList;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.Holder> {
    private Context context;
    private ArrayList<ImageList> imageLists;
    private LayoutInflater mInflate;

    public ProfileAdapter(Context context, ArrayList<ImageList> imageLists) {
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.imageLists = imageLists;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflate.inflate(R.layout.image_dummy, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ImageList item = imageLists.get(position);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imageView1);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl2())
                .into(holder.imageView2);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl3())
                .into(holder.imageView3);
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView1, imageView2, imageView3;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.dummy_image1);
            imageView2 = itemView.findViewById(R.id.dummy_image2);
            imageView3 = itemView.findViewById(R.id.dummy_image3);
        }
    }
}