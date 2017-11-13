package com.technology.yuyidoctorpad.activity.MyPost.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.BeanCommentPraise;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.BeanPostInfo;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lzhUtils.DataUtils;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/8.
 */

//评论的适配器
public class PostCommentAdapter  extends BaseAdapter{
    Context con;
    List<BeanPostInfo.ResultBean.CommentListBean> list;
    int selectPost=0;
    boolean flag=false;//是否正在点赞
    String resStr;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    flag=false;
                    break;
                case 1:
                    flag=false;
                    try{
                        BeanCommentPraise bean= gson.gson.fromJson(resStr,BeanCommentPraise.class);
                        if (bean!=null&&"0".equals(bean.getCode())){
                            list.get(selectPost).setIsLike(!list.get(selectPost).isIsLike());
                            notifyDataSetChanged();
                        }
                        else {
                            toast.toast(con,"操作失败！");
                        }
                    }
                    catch (Exception e){
                        toast.toast(con,"操作失败！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public PostCommentAdapter(Context con,List<BeanPostInfo.ResultBean.CommentListBean> list){
        this.con=con;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler;
        if (view==null){
            view= LayoutInflater.from(con).inflate(R.layout.item_comment,null);
            hodler=new ViewHodler(view);
            view.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) view.getTag();
        }
        Picasso.with(con).load(Ip.imagePath+list.get(i).getAvatar()).placeholder(R.mipmap.erroruser).error(R.mipmap.erroruser).into(hodler.head_img);
        hodler.name.setText(list.get(i).getTrueName());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:Ss");
        long mills=0;
        try{
            Date d=format.parse(list.get(i).getCreateTimeString());
            mills=d.getTime();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            hodler.time.setText(DataUtils.getData(mills));
            hodler.car_user_praise_img.setSelected(list.get(i).isIsLike());
            hodler.card_user_praise_num.setText(Empty.notEmpty(list.get(i).getLikeNum())?list.get(i).getLikeNum():"0");
            hodler.content.setText(list.get(i).getContent());
            hodler.car_user_praise_img.setTag(i);
            hodler.car_user_praise_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (flag){
                        toast.toast(con,"正在操作，请稍后。。。");
                        return;
                    }
                    flag=true;
                    selectPost= (int) view.getTag();
                    onPrarse(list.get(selectPost).getId()+"");
                }
            });
        }

        return view;
    }

    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
        @BindView(R.id.head_img) RoundImageView head_img;//头像
        @BindView(R.id.name) TextView name;//姓名
        @BindView(R.id.time) TextView time;//时间
        @BindView(R.id.car_user_praise_img)ImageView car_user_praise_img;//点赞的image
        @BindView(R.id.card_user_praise_num) TextView card_user_praise_num;//点赞的次数
        @BindView(R.id.content) TextView content;//评论内容
    }
    public void onPrarse(String ids){
        Map<String,String>mp=new HashMap<>();
        mp.put("id",ids);
        mp.put("token", User.token);
        OkUtils.getCall(Ip.path+Ip.interface_MyPostCommentPraise,mp,OkUtils.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("帖子评论点赞",resStr);
                    handler.sendEmptyMessage(1);
            }
        });
    }
}
