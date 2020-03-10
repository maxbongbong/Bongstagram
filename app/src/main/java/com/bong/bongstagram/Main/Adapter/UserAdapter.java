package com.bong.bongstagram.Main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Model.User;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ImageViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private boolean isFragment;

    public UserAdapter(Context context, List<User> users, boolean isFragment){
        mContext = context;
        mUsers = users;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public UserAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ImageViewHolder holder, int position, @NonNull List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);

        final User user = mUsers.get(position);

        holder.btn_follow.setVisibility(View.VISIBLE);
        isFollowing(user.getId(), holder.btn_follow);

        holder.username.setText(user.getUsername());
        holder.fullname.setText(user.getFullname());
        Glide.with(mContext).load(user.getImageurl()).into(holder.image_profile);

//        if (user.getId().equals()) {
//        holder.btn_follow.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ImageViewHolder extends  RecyclerView.ViewHolder{

        public TextView username;
        public TextView fullname;
        public CircleImageView image_profile;
        public Button btn_follow;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            fullname = itemView.findViewById(R.id.fullname);
            image_profile = itemView.findViewById(R.id.image_profile);
            btn_follow = itemView.findViewById(R.id.btn_follow);
        }
    }

    private void isFollowing(final String userid, final Button button){

    }
}
