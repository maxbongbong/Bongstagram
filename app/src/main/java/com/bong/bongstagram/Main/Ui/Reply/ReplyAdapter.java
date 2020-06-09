package com.bong.bongstagram.Main.Ui.Reply;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Model.ReplyList;
import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;

import java.util.ArrayList;

public class ReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater mInflate;
    private ArrayList<ReplyList> replyLists;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private OnItemClickListener mListener;
    static boolean setEnabled = true;

    public interface OnItemClickListener {
        void onItemSelected(View v, int position);
        void onLayOutSelected(View v, int position, SparseBooleanArray mSelectedItems, boolean setEnabled);
    }

    ReplyAdapter(Context context, ArrayList<ReplyList> replyLists, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.replyLists = replyLists;
        this.mListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parent.setBackgroundColor(context.getResources().getColor(R.color.colorDefault));
        if (viewType == ReplyFragment.VIEW_TYPE_A) {
            assert mInflate != null;
            View view = mInflate.inflate(R.layout.reply_list, parent, false);
            return new AHolder(view);
        } else {
            assert mInflate != null;
            View view = mInflate.inflate(R.layout.reply_to_list, parent, false);
            return new BHolder(view);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView replyToTextView = holder.itemView.findViewById(R.id.reply_To);
        LinearLayout replyToLayout = holder.itemView.findViewById(R.id.reply_layout);
        if (holder instanceof AHolder) {
            userName(((AHolder) holder).replyName, position);
            toggleTextView(((AHolder) holder).replyShortText, ((AHolder) holder).replyLongText, position);
            likeMethod(((AHolder) holder).likeBtn, position, ((AHolder) holder).replyLikeCount);
        } else {
            userName(((BHolder) holder).replyName, position);
            toggleTextView(((BHolder) holder).replyShortText, ((BHolder) holder).replyLongText, position);
            likeMethod(((BHolder) holder).likeBtn, position, ((BHolder) holder).replyLikeCount);
        }
        replyToTextView.setOnClickListener(v -> mListener.onItemSelected(v, position));
        replyToLayout.setOnClickListener(v -> mListener.onLayOutSelected(v, position, mSelectedItems, setEnabled));
        Log.e("adapter", "setEnabled = " + setEnabled);
        if (setEnabled) {
            replyToTextView.setEnabled(true);
            replyToTextView.setTextColor(context.getResources().getColor(R.color.grey_400));
        } else {
            replyToTextView.setEnabled(false);
            replyToTextView.setTextColor(context.getResources().getColor(R.color.grey_300));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (replyLists.get(position).getItemViewType() == 0) {
            return ReplyFragment.VIEW_TYPE_A;
        } else {
            return ReplyFragment.VIEW_TYPE_B;
        }
    }

    @Override
    public int getItemCount() {
        return replyLists.size();
    }

    private void toggleTextView(TextView replyShortText, TextView replyLongText, int position) {
        ReplyList item = replyLists.get(position);
        int width = (int) replyShortText.getPaint().measureText(item.getReply());

        if (width > 615) {
            replyShortText.setVisibility(View.GONE);
            replyLongText.setVisibility(View.VISIBLE);
            replyLongText.setText(item.getReply());
        } else {
            replyShortText.setVisibility(View.VISIBLE);
            replyLongText.setVisibility(View.GONE);
            replyShortText.setText(item.getReply());
        }
    }

    private void userName(TextView textView, int position) {
        textView.setText(replyLists.get(position).getParentId().substring(0, 3));
    }

    private void likeMethod(ImageView likeBtn, int position, TextView replyLikeCount) {
        if (setEnabled) {
            replyLikeCount.setTextColor(context.getResources().getColor(R.color.grey_400));
            likeBtn.setEnabled(true);
            likeBtn.setOnClickListener(v -> {
                ReplyList item = replyLists.get(position);
                int likeCount;
                Animation mAnim = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.scale_heart);
                mAnim.setInterpolator(context.getApplicationContext(), android.R.anim.accelerate_interpolator);
                v.startAnimation(mAnim);
                if (!item.isHeart()) {
                    likeBtn.setSelected(true);
                    item.setHeart(true);
                    item.setLike(item.getLike() + 1);
                    likeCount = item.getLike();
                    if (likeCount != 0) {
                        replyLikeCount.setVisibility(View.VISIBLE);

                        replyLikeCount.setText(R.string.title_reply_like);
                        replyLikeCount.append(likeCount + "개");
                    }
                } else {
                    likeBtn.setSelected(false);
                    item.setHeart(false);
                    item.setLike(item.getLike() - 1);
                    likeCount = item.getLike();
                    if (likeCount == 0) replyLikeCount.setVisibility(View.GONE);
                    else {
                        replyLikeCount.setVisibility(View.VISIBLE);
                        replyLikeCount.setText(R.string.title_reply_like);
                        replyLikeCount.append(likeCount + "개");
                    }
                }
            });
        } else {
            likeBtn.setEnabled(false);
            replyLikeCount.setTextColor(context.getResources().getColor(R.color.grey_300));
        }
    }

    static class AHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        LinearLayout replyLayout, replyShowHide;
        TextView replyName, replyShortText, replyLongText, replyDate, replyTo, replyLikeCount, reReplyText;
        ImageView likeBtn;

        @SuppressLint("ClickableViewAccessibility")
        AHolder(@NonNull View itemView) {
            super(itemView);

            replyName = itemView.findViewById(R.id.reply_name);
            replyShortText = itemView.findViewById(R.id.reply_short_text);
            replyLongText = itemView.findViewById(R.id.reply_long_text);
            userImage = itemView.findViewById(R.id.reply_user_image);
            replyDate = itemView.findViewById(R.id.reply_date);
            replyLikeCount = itemView.findViewById(R.id.reply_like_count);
            replyLayout = itemView.findViewById(R.id.reply_layout);
            likeBtn = itemView.findViewById(R.id.btn_favorite);
            replyShowHide = itemView.findViewById(R.id.replyShowHide);
            replyTo = itemView.findViewById(R.id.reply_To);
            reReplyText = itemView.findViewById(R.id.reReply_text);
        }
    }

    static class BHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        LinearLayout replyLayout;
        TextView replyName, replyShortText, replyLongText, replyDate, replyTo, replyLikeCount, reReplyText;
        ImageView likeBtn;

        @SuppressLint("ClickableViewAccessibility")
        BHolder(@NonNull View itemView) {
            super(itemView);

            replyName = itemView.findViewById(R.id.reply_name);
            replyShortText = itemView.findViewById(R.id.reply_short_text);
            replyLongText = itemView.findViewById(R.id.reply_long_text);
            userImage = itemView.findViewById(R.id.reply_user_image);
            replyDate = itemView.findViewById(R.id.reply_date);
            replyLikeCount = itemView.findViewById(R.id.reply_like_count);
            replyLayout = itemView.findViewById(R.id.reply_layout);
            likeBtn = itemView.findViewById(R.id.btn_favorite);
            replyTo = itemView.findViewById(R.id.reply_To);
            reReplyText = itemView.findViewById(R.id.reReply_text);
        }
    }
}
