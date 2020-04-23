package com.bong.bongstagram.Main.Ui.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Model.MovieList;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder> implements Filterable {
    private Context context;
    private ArrayList<MovieList> unFilterList;
    private ArrayList<MovieList> filteredList;
    private LayoutInflater mInflate;

    public SearchAdapter(Context context, ArrayList<MovieList> movieList){
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.unFilterList = movieList;
        this.filteredList = movieList;
    }

    @NonNull
    @Override
    public SearchAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflate.inflate(R.layout.fragment_search, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        MovieList item = filteredList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imageView);
        holder.textView1.setText(item.getTitle());
        holder.textView2.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView1, textView2;
        public Holder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_profile);
            textView1 = itemView.findViewById(R.id.movieName);
            textView2 = itemView.findViewById(R.id.fullName);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    filteredList = unFilterList;
                } else {
                    ArrayList<MovieList> filteringList = new ArrayList<>();
                    for(MovieList item : unFilterList){
                        if (item.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(item);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<MovieList>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
