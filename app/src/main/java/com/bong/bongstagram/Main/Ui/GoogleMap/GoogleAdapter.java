package com.bong.bongstagram.Main.Ui.GoogleMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Model.ImageList;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GoogleAdapter extends RecyclerView.Adapter<GoogleAdapter.Holder> {
    private Context context;
    private ArrayList<ImageList> imageLists;
    private LayoutInflater mInflate;

    public GoogleAdapter(Context context, ArrayList<ImageList> imageLists){
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.imageLists = imageLists;
    }

    @NonNull
    @Override
    public GoogleAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflate.inflate(R.layout.image_dummy, parent, false);

        return new Holder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener btnTouchListener = (v, event) ->  {
        ImageView view = (ImageView)v;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                view.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                Log.e("손가락", "눌림");
                return true;
            case MotionEvent.ACTION_MOVE:
                view.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                Log.e("손가락", "움직임");
                return true;
            case MotionEvent.ACTION_UP:
                view.setColorFilter(null);
                Log.e("손가락", "때짐");
                return true;
            case MotionEvent.ACTION_CANCEL:
                view.setColorFilter(null);
                Log.e("손가락", "취소");
                return true;
        }
        return true;
    };

    public void onBindViewHolder(@NonNull GoogleAdapter.Holder holder, int position) {
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

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView1, imageView2, imageView3;

        @SuppressLint("ClickableViewAccessibility")
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.dummy_image1);
            imageView2 = itemView.findViewById(R.id.dummy_image2);
            imageView3 = itemView.findViewById(R.id.dummy_image3);

            imageView1.setOnTouchListener(btnTouchListener);
            imageView2.setOnTouchListener(btnTouchListener);
            imageView3.setOnTouchListener(btnTouchListener);
        }
    }
}
