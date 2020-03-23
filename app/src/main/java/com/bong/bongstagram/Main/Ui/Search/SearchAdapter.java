package com.bong.bongstagram.Main.Ui.Search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Model.MovieList;
import com.bong.bongstagram.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder> implements Filterable {
    Context context;
    private ArrayList<MovieList> unFilterlist;
    private ArrayList<MovieList> filteredlist;
    private LayoutInflater mInflate;

    public SearchAdapter(Context context, ArrayList<MovieList> movieList){
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.unFilterlist = movieList;
        this.filteredlist = movieList;
    }

    @NonNull
    @Override
    public SearchAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflate.inflate(R.layout.movie_search, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        MovieList item = filteredlist.get(position);
        holder.textView1.setText(item.getTitle());
        holder.textView2.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return filteredlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;
        public Holder(@NonNull View itemView){
            super(itemView);
            textView1 = itemView.findViewById(R.id.movieName);
            textView2 = itemView.findViewById(R.id.fullname);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    filteredlist = unFilterlist;
                } else {
                    ArrayList<MovieList> filteringList = new ArrayList<>();
                    for(MovieList item : unFilterlist){
                        if (item.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(item);
                        }
                    }
                    filteredlist = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredlist;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredlist = (ArrayList<MovieList>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
