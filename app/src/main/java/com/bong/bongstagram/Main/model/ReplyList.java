package com.bong.bongstagram.Main.model;

public class ReplyList {
    private boolean heart = false;
    private String reply;
    private int like = 0;
    private String parentId;
    private int itemViewType;

    public ReplyList(String parentId, String reply){
        this.parentId = parentId;
        this.reply = reply;
    }

    public int getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean getHeart() {
        return heart;
    }

    public void setHeart(boolean heart) {
        this.heart = heart;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
