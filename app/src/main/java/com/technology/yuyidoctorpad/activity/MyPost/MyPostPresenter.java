package com.technology.yuyidoctorpad.activity.MyPost;

import android.support.v4.app.FragmentManager;

import com.technology.yuyidoctorpad.activity.MyPost.Fragment.PostInfoFragment;

/**
 * Created by wanyu on 2017/11/7.
 */

public class MyPostPresenter {
    MyPostModel model;
    PostInfoFragment myPost_fragment;
    public void initFragment(FragmentManager manager,int resId){
        myPost_fragment=new PostInfoFragment();
        manager.beginTransaction().add(resId,myPost_fragment).show(myPost_fragment).commit();
    }
    public void setPraise(boolean isLike,int pos){
        myPost_fragment.setPraise(isLike,pos);
    }
    //获取我的帖子
    public void getMyPost(int st,int limit,IListener listener){
        if (model==null){
            model=new MyPostModel();
        }
        model.getMyPost(st,limit,listener);
    }
    //请求帖子详情
    public void setPostId(int pos,String id){
        myPost_fragment.setPostId(pos,id);
    }
}
