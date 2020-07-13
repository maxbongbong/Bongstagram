package com.bong.bongstagram.Main.Ui.Reply;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Data.Image;
import com.bong.bongstagram.Main.Model.ReplyList;
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

    /**
     * 생성자
     */
    ReplyAdapter(Context context, ArrayList<ReplyList> replyLists, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mInflate = LayoutInflater.from(context);
        this.replyLists = replyLists;
        this.mListener = onItemClickListener;
    }

    public void setData(ArrayList<ReplyList> data) {
        replyLists = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parent.setBackgroundColor(context.getResources().getColor(R.color.colorDefault));
        if (viewType == ReplyFragment.VIEW_TYPE_A) {
            View view = mInflate.inflate(R.layout.reply_list, parent, false);
            return new AHolder(view);
        } else {
            View view = mInflate.inflate(R.layout.reply_to_list, parent, false);
            return new BHolder(view);
        }
    }

    private boolean isItemSelected(int position) {
        return mSelectedItems.get(position, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AHolder) {
            userName(((AHolder) holder).replyName, position);
            toggleTextView(((AHolder) holder).replyShortText, ((AHolder) holder).replyLongText, position);
            likeMethod(((AHolder) holder).likeBtn, position, ((AHolder) holder).replyLikeCount, holder);
        } else {
            userName(((BHolder) holder).replyName, position);
            toggleTextView(((BHolder) holder).replyShortText, ((BHolder) holder).replyLongText, position);
            likeMethod(((BHolder) holder).likeBtn, position, ((BHolder) holder).replyLikeCount, holder);
        }

        TextView replyToTextView = holder.itemView.findViewById(R.id.reply_To);
        LinearLayout replyToLayout = holder.itemView.findViewById(R.id.reply_layout);
        replyToLayout.setSelected(isItemSelected(position));
        replyToLayout.setBackground(context.getDrawable(R.drawable.item_change_background));
        replyToTextView.setOnClickListener(v -> mListener.onItemSelected(v, position));
        replyToLayout.setOnClickListener(v -> mListener.onLayOutSelected(v, position, mSelectedItems, setEnabled));

        if (setEnabled) {
            replyToTextView.setEnabled(true);
            replyToTextView.setTextColor(context.getResources().getColor(R.color.grey_400));
        } else {
            replyToTextView.setEnabled(false);
            replyToTextView.setTextColor(context.getResources().getColor(R.color.grey_300));
        }
    }

    /**
     * 좋아요 버튼 메소드
     **/
    private void likeMethod(ImageView likeBtn, int position, TextView replyLikeCount, RecyclerView.ViewHolder holder) {
        ReplyList item = replyLists.get(position);
        likeBtn.setSelected(item.getHeart());

        if (item.getHeart()) {
            replyLikeCount.setVisibility(View.VISIBLE);
            replyLikeCount.setText(R.string.title_reply_like);
            replyLikeCount.append(item.getLike() + "개");
        } else {
            replyLikeCount.setVisibility(View.GONE);
        }

        if (setEnabled) {
            Animation mAnim = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.scale_heart);
            mAnim.setInterpolator(context.getApplicationContext(), android.R.anim.accelerate_interpolator);
            replyLikeCount.setTextColor(context.getResources().getColor(R.color.grey_400));
            likeBtn.setEnabled(true);
            likeBtn.setOnClickListener(v -> {
                boolean heart = replyLists.get(holder.getAdapterPosition()).getHeart();
                likeBtn.setSelected(!heart);
                animationListener(mAnim, likeBtn, position);
            });

        } else {
            likeBtn.setEnabled(false);
            replyLikeCount.setTextColor(context.getResources().getColor(R.color.grey_300));
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

    /**
     * 유저가 쓴글의 길이에 따라 적용되는 텍스트뷰 메소드
     **/
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

    /**
     * 유저 아이디 텍스트뷰 메소드
     **/
    private void userName(TextView textView, int position) {
        textView.setText(replyLists.get(position).getParentId().substring(0, 3));
    }

    /**
     * 애니메이션 클릭 메소드
     **/
    private void animationListener(Animation mAnim, ImageView likeBtn, int position) {
        mAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                likeCount(position, likeBtn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        likeBtn.startAnimation(mAnim);
    }

    /**
     * 좋아요 개수 카운트 메소드
     **/
    private void likeCount(int position, ImageView likeBtn) {
        ReplyList item = replyLists.get(position);
        if (!item.getHeart()) {
            likeBtn.setSelected(true);
            item.setHeart(true);
            item.setLike(item.getLike() + 1);
        } else {
            likeBtn.setSelected(false);
            item.setHeart(false);
            item.setLike(item.getLike() - 1);
        }
        notifyItemChanged(position);
    }

    /**
     * 댓글 홀더
     **/
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

    /**
     * 대댓글 홀더
     **/
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
