package com.technology.yuyidoctorpad.activity.MyPost.Fragment;

/**
 * Created by wanyu on 2017/11/7.
 */

public class PostInfoPresenter {
    PostInfoModel model;
    //获取帖子详情信息
    public void getPostInfo(int st,int limit,String ids,IPostInfo listener){
        if (model==null){
            model=new PostInfoModel();
        }
        model.getPostInfo(st,limit,ids,listener);
    }
    //点赞操作
    public void makePraise(String ids,IPostInfo lis){
        if (model==null){
            model=new PostInfoModel();
        }
        model.makePraise(ids,lis);
    }

    //评论
    public void submitCircleComment(String ids,String content,IPostInfo lis){
        if (model==null){
            model=new PostInfoModel();
        }
        model.makeComment(ids,content,lis);
    }
}
