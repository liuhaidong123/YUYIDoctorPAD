package com.technology.yuyidoctorpad.bean.CircleBean;

/**
 * Created by liuhaidong on 2017/4/10.
 */

public class Rows {
    private String createTimeString;

    private String updateTimeString;

    private boolean isLike;

    private String title;

    private Integer shareNum;

    private String content;

    private String picture;

    private String avatar;

    private String trueName;

    private Integer likeNum;

    private Integer commentNum;

    private Integer physicianId;

    private long id;

    public void setCreateTimeString(String createTimeString){
        this.createTimeString = createTimeString;
    }
    public String getCreateTimeString(){
        return this.createTimeString;
    }
    public void setUpdateTimeString(String updateTimeString){
        this.updateTimeString = updateTimeString;
    }
    public String getUpdateTimeString(){
        return this.updateTimeString;
    }
    public void setIsLike(boolean isLike){
        this.isLike = isLike;
    }
    public boolean getIsLike(){
        return this.isLike;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setShareNum(Integer shareNum){
        this.shareNum = shareNum;
    }
    public Integer getShareNum(){
        return this.shareNum;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }
    public String getPicture(){
        return this.picture;
    }
    public void setLikeNum(Integer likeNum){
        this.likeNum = likeNum;
    }
    public Integer getLikeNum(){
        return this.likeNum;
    }
    public void setCommentNum(Integer commentNum){
        this.commentNum = commentNum;
    }
    public Integer getCommentNum(){
        return this.commentNum;
    }
    public void setPhysicianId(Integer physicianId){
        this.physicianId = physicianId;
    }
    public Integer getPhysicianId(){
        return this.physicianId;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
