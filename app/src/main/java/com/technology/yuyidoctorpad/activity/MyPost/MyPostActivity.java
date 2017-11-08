package com.technology.yuyidoctorpad.activity.MyPost;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.MyPost.Adapter.My_forumPosts_Adapter;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.Bean_MyPostData;
import com.technology.yuyidoctorpad.code.ExitLogin;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyPostActivity extends MyActivity implements IListener,MyListView.IonScrollBottomListener,AdapterView.OnItemClickListener{
    @BindView(R.id.myPost_empty)RelativeLayout myPost_empty;//没有数据时显示
    boolean isFirst=true;//刚进入页面时，请求到数据后加载第一条数据到详情
    MyPostPresenter presenter;
    My_forumPosts_Adapter adapter;
    private List<Bean_MyPostData.RowsBean> list;
    int start=0;
    int limit=10;
    @BindView(R.id.myPost_listView)MyListView myPost_listView;//列表
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_my_post);
        presenter=new MyPostPresenter();
        presenter.initFragment(getSupportFragmentManager(),R.id.myPost_FragLayout);

        list=new ArrayList<>();
        adapter=new My_forumPosts_Adapter(this,list,this);
        myPost_listView.setAdapter(adapter);
        myPost_listView.setOnScrollBottomListener(this);
        myPost_listView.setOnItemClickListener(this);
        presenter.getMyPost(start,limit,this);
    }
    @OnClick({R.id.myPost_eidt})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.myPost_eidt://发帖按钮

                 break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        presenter.setPostId(i,list.get(i).getId()+"");
    }

    @Override
    public void onTokenError() {
        setVisi();
        ExitLogin.getInstance().showLogin(this);
    }

    @Override
    public void onGetPostListError(String msg) {
        toast.toast(this,msg);
        myPost_listView.setEmpey(msg);
        setVisi();
    }

    @Override
    public void onGetPostListSuccess(Bean_MyPostData bean) {
            if (bean.getRows()!=null){
                start+=bean.getRows().size();
                if (bean.getRows().size()==limit){
                    myPost_listView.setScroll(true);
                }
                else {
                    myPost_listView.setLoadingComplete();
                }
                list.addAll(bean.getRows());
                if (isFirst&&list.size()>0){
                    //获取第一条帖子的详情
                    presenter.setPostId(0,list.get(0).getId()+"");
                }
                else {
                    isFirst=false;
                }
                adapter.notifyDataSetChanged();
            }
        else {
                myPost_listView.setLoadingComplete();
                myPost_listView.setEmpey("没有查询到数据！");
            }
        setVisi();
    }

    @Override
    public void onPraise(boolean isLike, int pos) {
        presenter.setPraise(isLike,pos);
    }

    @Override
    public void onScrollBottom() {
        presenter.getMyPost(start,limit,this);
    }



    //没有数据或者网络有问题时显示的页面
    private void setVisi(){
        if (adapter!=null&&adapter.getCount()==0){
            myPost_empty.setVisibility(View.VISIBLE);
        }
        else {
            myPost_empty.setVisibility(View.GONE);
        }
    }
    //设置评论数量以及点赞数量（fragment中操作后的回调）
    //CurrentPos：当前需要更改的item的下表
    //isLinkNum:点赞数量（不需要更改时值默认为-10）
    //  CommentNum：评论数量不需要更改时的值为-10
    //isLike:是否点赞
    public void setPraiseAndComment(int CurrentPos,int isLikeNum,int CommentNum,boolean isLike){
        if (isLikeNum!=-10){
            list.get(CurrentPos).setLikeNum((long)isLikeNum);
        }
        if (CommentNum!=-10){
            list.get(CurrentPos).setCommentNum((long)CommentNum);
        }
        list.get(CurrentPos).setIsLike(isLike);
        adapter.notifyDataSetChanged();
    }
}