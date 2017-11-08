package com.technology.yuyidoctorpad.activity.MyPost.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.BeanPostInfo;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lzhUtils.DataUtils;
import com.technology.yuyidoctorpad.lzhUtils.Empty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/8.
 */

//评论的适配器
public class PostCommentAdapter  extends BaseAdapter{
    Context con;
    List<BeanPostInfo.ResultBean.CommentListBean> list;
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
}
