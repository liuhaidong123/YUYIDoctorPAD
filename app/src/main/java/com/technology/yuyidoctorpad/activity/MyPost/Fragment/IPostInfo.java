package com.technology.yuyidoctorpad.activity.MyPost.Fragment;

import com.technology.yuyidoctorpad.activity.MyPost.Bean.BeanPostInfo;

/**
 * Created by wanyu on 2017/11/7.
 */

public interface IPostInfo {
    //获取帖子详情
    void onGetPostInfoSuccess(BeanPostInfo infoBean);
    void onGetPostInfoError(String msg);
//    点赞
    void onPriseSuccess();//点赞成功
    void onPriseError(String msg);
//    评论
    void onCommentError(String msg);
    void onCommentSuccess();
}
