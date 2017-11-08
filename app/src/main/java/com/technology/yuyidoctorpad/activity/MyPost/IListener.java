package com.technology.yuyidoctorpad.activity.MyPost;

import com.technology.yuyidoctorpad.activity.MyPost.Bean.Bean_MyPostData;

/**
 * Created by wanyu on 2017/11/7.
 */

public interface IListener {
    void onTokenError();
    void onGetPostListError(String msg);
    void onGetPostListSuccess(Bean_MyPostData bean);
    void onPraise(boolean isLike,int pos);//当前的点赞状态
}
