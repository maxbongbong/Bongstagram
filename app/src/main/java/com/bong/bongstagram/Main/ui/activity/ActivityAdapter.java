package com.bong.bongstagram.Main.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.model.MovieList;
import com.bong.bongstagram.Main.ui.customView.CustomText;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.Holder> {
    private Context context;
    private ArrayList<MovieList> movieLists;
    private LayoutInflater mInflate;
    private CustomText textView;

    ActivityAdapter(Context context, ArrayList<MovieList> movieList){
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.movieLists = movieList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.activity_dummy, parent, false);
        mInflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        MovieList item = movieLists.get(position);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imageView);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imageView1);
        period(position);
//        textView.setText(period(position).getText());
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView, imageView1;
        Holder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_profile);
            imageView1 = itemView.findViewById(R.id.image_dummy);
            textView = itemView.findViewById(R.id.movieName);
        }
    }

    private void period(int position) {

        SimpleDateFormat nowDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String data = movieLists.get(position).getDate();
        String result = movieLists.get(position).getTitle();
        String shared = context.getResources().getString(R.string.title_image_shared);
        String time = "";

        try {
            Date dataDate = nowDate.parse(data);
            Date today = Calendar.getInstance().getTime();

            assert dataDate != null;
            long dateDay = (today.getTime() - dataDate.getTime()) / (24 * 60 * 60 * 1000);
            long dateWeek = dateDay / 7;
            if (dateDay == 0) time = " 오늘";
            else if(dateDay >= 1 && dateDay < 7) time = " " + dateDay + "일";
            else if(dateDay >= 7) time = " " + dateWeek + "주";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SpannableStringBuilder sp = new SpannableStringBuilder(result);
        sp.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, result.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.append(sp);
        textView.append(shared);

        sp = new SpannableStringBuilder(time);
        sp.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.append(sp);
    }
}
