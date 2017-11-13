package com.technology.yuyidoctorpad.activity.MyPost.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.MyPost.Adapter.PostCommentAdapter;
import com.technology.yuyidoctorpad.activity.MyPost.Adapter.PostImageAdapter;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.BeanPostInfo;
import com.technology.yuyidoctorpad.activity.MyPost.MyPostActivity;
import com.technology.yuyidoctorpad.lhdUtils.InformationListView;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.technology.yuyidoctorpad.lzhUtils.DataUtils;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.id;
import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by wanyu on 2017/11/7.
 */
//帖子详情
public class PostInfoFragment extends BaseFragment implements IPostInfo{
    int start=0;
    int limit=3;
    int DEFAULT=-10;//不更改评论数量或者点赞数量时的默认值
    String postId;//帖子id
    int currentId;//当前帖子属于Activity中listview的那一个子item
    PostInfoPresenter presenter;
    @BindView(R.id.car_user_head_img)RoundImageView car_user_head_img;//头像
    @BindView(R.id.car_user_name)TextView car_user_name;//姓名
    @BindView(R.id.car_user_time)TextView car_user_time;//时间
    @BindView(R.id.car_user_praise_tv)TextView car_user_praise_tv;//点赞数量
    @BindView(R.id.car_user_praise_img)ImageView car_user_praise_img;//点赞的image
    @BindView(R.id.card_title)TextView card_title;//标题
    @BindView(R.id.card_message)TextView card_message;//内容
    @BindView(R.id.card_img_listview)InformationListView card_img_listview;//图片listview
    PostImageAdapter adapterImg;//图片适配器
    @BindView(R.id.card_comment_num)TextView card_comment_num;//评论数量
    @BindView(R.id.card_comment_listview)InformationListView card_comment_listview;//评论的listview
    List<BeanPostInfo.ResultBean.CommentListBean>listComment;
    PostCommentAdapter adapterComment;

    @BindView(R.id.more_relative)RelativeLayout more_relative;//加载更多的layout
    @BindView(R.id.pbLocate)ProgressBar pbLocate;
    @BindView(R.id.footer_tv)TextView footer_tv;//加载更多的text

    @BindView(R.id.myPost_edit)EditText myPost_edit;//编辑的editext
    boolean isLoading=false;//是否已经加载完毕
    boolean isPraise=false;//是否正在点赞
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.frag_postinfo,null);
        unbinder= ButterKnife.bind(this,vi);
        presenter=new PostInfoPresenter();
        listComment=new ArrayList<>();
        adapterComment=new PostCommentAdapter(getActivity(),listComment);
        card_comment_listview.setAdapter(adapterComment);

        myPost_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!Empty.notEmpty(myPost_edit.getText().toString().trim())) {
                       toast.toast(getActivity(),"请输入评论内容！");
                    } else {
                        if (id!=-1){
                            presenter.submitCircleComment(postId,myPost_edit.getText().toString(),PostInfoFragment.this);
                            myPost_edit.setText("");
                            //隐藏软键盘
                            ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }else {
                            ToastUtils.myToast(getActivity(), "评论数据错误");
                        }

                    }
                    return true;
                }

                return false;
            }
        });
        return vi;
    }
    @OnClick({R.id.more_relative,R.id.car_user_praise_img})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.more_relative://加载更多
                if (!isLoading){//没有请求网络数据的时候，防止重复请求
                    setLoadingState(LoadingState.Loading);
                    presenter.getPostInfo(start,limit,postId,this);
                }
                break;
            case R.id.car_user_praise_img://帖子点赞
                if (isPraise){
                    toast.toast(getActivity(),"正在操作，请稍后。。。");
                }
                else {
                    isPraise=true;
                    presenter.makePraise(postId,this);
                }
                break;
        }
    }
    private  void setLoadingState(LoadingState state){
        switch (state){
            case Loading:
                pbLocate.setVisibility(View.VISIBLE);
                footer_tv.setText("正在加载。。。");
                break;
            case Complete:
                pbLocate.setVisibility(View.GONE);
                footer_tv.setText("加载更多");
                break;
        }
    }
     enum LoadingState{
        Loading,Complete;//正在加载，加完完毕
    }
    //用与Activity向fragment传递当前的帖子的id刷新页面
    public void setPostId(int CurrentPos,String id){
        if (id.equals(postId)){
            return;
            }
        currentId=CurrentPos;
        this.postId=id;
        start=0;
        isLoading=true;
        isPraise=false;
        //清除原先的评论数据
        listComment.clear();
        adapterComment.notifyDataSetChanged();
        more_relative.setVisibility(View.VISIBLE);
        setLoadingState(LoadingState.Loading);
        presenter.getPostInfo(start,limit,id,this);//获取帖子详情
    }
    //activity中listview的item点赞操作回调
    public void setPraise(boolean isLike,int pos){
       if (currentId==pos){//如果需要改变的时当前显示的帖子
           boolean flag=car_user_praise_img.isSelected();//当前的点赞图标的状态
           int count=Integer.parseInt(car_user_praise_tv.getText().toString());
           if (isLike!=flag){
               if (flag==false){//当前是没有点赞
                   count++;//点赞数量加1;
               }
               else {//当前状态是点赞
                   count--;
                   if (count<0){
                       count=0;
                   }
               }
               car_user_praise_tv.setText(count+"");
               car_user_praise_img.setSelected(isLike);
           }
       }
    }
    @Override
    public void onGetPostInfoSuccess(BeanPostInfo infoBean) {
        Picasso.with(getActivity()).load(Ip.imagePath+infoBean.getResult().getAvatar()).placeholder(R.mipmap.erroruser).error(R.mipmap.erroruser).into(car_user_head_img);
        car_user_name.setText(infoBean.getResult().getTrueName());
        String time=infoBean.getResult().getCreateTimeString();
//        2017-11-07 15:48:35
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:Ss");
        long mills=0;
        try{
            Date d=format.parse(time);
            mills=d.getTime();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            MyPostActivity ac= (MyPostActivity) getActivity();
            ac.setPraiseAndComment(currentId,infoBean.getResult().getLikeNum(),infoBean.getResult().getCommentNum(),infoBean.getResult().isIsLike());
            car_user_time.setText(DataUtils.getData(mills));
            car_user_praise_tv.setText(infoBean.getResult().getLikeNum()+"");
            car_user_praise_img.setSelected(infoBean.getResult().isIsLike());
            card_title.setText(infoBean.getResult().getTitle());
            card_message.setText(infoBean.getResult().getContent());
            card_comment_num.setText(infoBean.getResult().getCommentNum()+"");
            String picture=infoBean.getResult().getPicture();
            if (Empty.notEmpty(picture)){
                String[]pic=picture.split(";");
                if (pic!=null&&pic.length>0){
                    List<String>listImage=new ArrayList<>();
                    for (String s:pic){
                        listImage.add(s);
                    }
                    adapterImg=new PostImageAdapter(getActivity(),listImage);
                    card_img_listview.setAdapter(adapterImg);
                }
            }
            List<BeanPostInfo.ResultBean.CommentListBean>li=infoBean.getResult().getCommentList();
            more_relative.setVisibility(View.GONE);
            setLoadingState(LoadingState.Complete);
            if (li!=null&&li.size()>0){
                start+=li.size();
                if (li.size()==limit){
                    more_relative.setVisibility(View.VISIBLE);
                }
                listComment.addAll(li);
                adapterComment.notifyDataSetChanged();
            }
            isLoading=false;
        }
    }
    @Override
    public void onGetPostInfoError(String msg) {
        more_relative.setVisibility(View.GONE);
        isLoading=false;
        toast.toast(getActivity(),msg);
    }

    @Override
    public void onPriseSuccess() {
        isPraise=false;
        int count=Integer.parseInt(car_user_praise_tv.getText().toString());
        int praise=count;
        if (car_user_praise_img.isSelected()){
            car_user_praise_img.setSelected(false);
            praise=count-1;
            car_user_praise_tv.setText(count-1<0?"0":(count-1)+"");
        }
        else {
            praise=count+1;
            car_user_praise_img.setSelected(true);
            car_user_praise_tv.setText((count+1)+"");
            }

        MyPostActivity ac= (MyPostActivity) getActivity();
        if (praise<0){
            praise=0;
        }
        ac.setPraiseAndComment(currentId,praise,DEFAULT,car_user_praise_img.isSelected());
    }

    @Override
    public void onPriseError(String msg) {
        isPraise=false;
        toast.toast(getActivity(),msg);
    }

    @Override
    public void onCommentError(String msg) {
        toast.toast(getActivity(),msg);
    }

    @Override
    public void onCommentSuccess() {
        toast.toast(getActivity(),"评论成功");
        start=0;
        isLoading=true;
        isPraise=false;
        //清除原先的评论数据
        listComment.clear();
        adapterComment.notifyDataSetChanged();
        more_relative.setVisibility(View.VISIBLE);
        setLoadingState(LoadingState.Loading);
        presenter.getPostInfo(start,limit,postId,this);//获取帖子详情
    }
}
