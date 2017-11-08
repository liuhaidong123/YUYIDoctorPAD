package com.technology.yuyidoctorpad.activity.MyPost.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.Bean_MyPostData;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.Bean_MyPostDataPriase;
import com.technology.yuyidoctorpad.activity.MyPost.IListener;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lzhUtils.DataUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/27.
 */

public class My_forumPosts_Adapter extends BaseAdapter {
    private List<Bean_MyPostData.RowsBean> list;
    IListener listener;
    private Context context;
    private Boolean isPraise=false;//是否正在执行点赞操作
    private int CurrentPosition;
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isPraise=false;
                    break;
                case 1:
                    isPraise=false;
                    try{
                        Bean_MyPostDataPriase priase= gson.gson.fromJson(resStr,Bean_MyPostDataPriase.class);
                        if (priase!=null){
                            if ("0".equals(priase.getCode())){
                                Log.i("---isLink--",list.get(CurrentPosition).isIsLike()+"");
                                if (list.get(CurrentPosition).isIsLike()==false){
                                    listener.onPraise(true,CurrentPosition);
                                    Toast.makeText(context,"点赞成功", Toast.LENGTH_SHORT).show();
                                    list.get(CurrentPosition).setIsLike(true);
                                    Long isLinkNum=list.get(CurrentPosition).getLikeNum();
                                    if (isLinkNum==null){
                                        isLinkNum=0L;
                                    }
                                    list.get(CurrentPosition).setLikeNum(isLinkNum+1);
                                }
                                else {
                                    listener.onPraise(false,CurrentPosition);
                                    Toast.makeText(context,"取消点赞成功", Toast.LENGTH_SHORT).show();
                                    list.get(CurrentPosition).setIsLike(false);
                                    Long isLinkNum=list.get(CurrentPosition).getLikeNum();
                                    if (isLinkNum!=null&&isLinkNum>0){
                                        list.get(CurrentPosition).setLikeNum(isLinkNum-1);
                                    }
                                    else {
                                        return;
                                        }
                                }
                                notifyDataSetChanged();
                            }
                            else {
                                if (list.get(CurrentPosition).isIsLike()){
                                    Toast.makeText(context,"点赞失败", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context,"取消点赞失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public My_forumPosts_Adapter(IListener listener,List<Bean_MyPostData.RowsBean> list, Context context){
        this.list=list;
        this.context=context;
        this.listener=listener;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            hodler=new ViewHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.forumposts_listview_item,null);
            hodler.forumpost_listitem_Image= (RoundImageView) convertView.findViewById(R.id.forumpost_listitem_Image);
            hodler.forumpost_listitem_Name= (TextView) convertView.findViewById(R.id.forumpost_listitem_Name);
            hodler.forumpost_listitem_title= (TextView) convertView.findViewById(R.id.forumpost_listitem_title);

            hodler.forumpost_listitem_content= (TextView) convertView.findViewById(R.id.forumpost_listitem_content);

            hodler.forumpost_listitem_time= (TextView) convertView.findViewById(R.id.forumpost_listitem_time);

            hodler.forumposts_listview_item_msgNum= (TextView) convertView.findViewById(R.id.forumposts_listview_item_msgNum);

            hodler.forumposts_listview_item_postNum= (TextView) convertView.findViewById(R.id.forumposts_listview_item_postNum);

            hodler.forumposts_listview_item_postImage= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_postImage);

            hodler.forumposts_listview_item_msgImage= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_msgImage);

            hodler.forumpost_listitem_layout_msg= (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_msg);

            hodler.forumpost_listitem_layout_post= (LinearLayout) convertView.findViewById(R.id.forumpost_listitem_layout_post);

            hodler.forumposts_listview_item_photoImage= (ImageView) convertView.findViewById(R.id.forumposts_listview_item_photoImage);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        hodler.forumpost_listitem_title.setText(list.get(position).getTitle());
        hodler.forumpost_listitem_content.setText(list.get(position).getContent());
        if (!"".equals(list.get(position).getCreateTimeString())&&!TextUtils.isEmpty(list.get(position).getCreateTimeString()))
        {
//            2017-04-06 16:56:24
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long millionSeconds;
            try{
                millionSeconds = sdf.parse(list.get(position).getCreateTimeString()).getTime();//毫秒
                }
            catch (Exception e){
                e.printStackTrace();
                millionSeconds=0;
            }
            if (millionSeconds!=0){
                String data= DataUtils.getData(millionSeconds);
                if (!"0".equals(data)&&!TextUtils.isEmpty(data)){
                    hodler.forumpost_listitem_time.setText(data);
                }
                else {
                    hodler.forumpost_listitem_time.setText(list.get(position).getCreateTimeString());
                }
            }
            else {
                hodler.forumpost_listitem_time.setText(list.get(position).getCreateTimeString());
            }
        }
        hodler.forumposts_listview_item_msgNum.setText(list.get(position).getCommentNum()==null?"0":Math.round(list.get(position).getCommentNum())+"");
        hodler.forumposts_listview_item_postNum.setText(list.get(position).getLikeNum()==null?"0":(Math.round(list.get(position).getLikeNum()))+"");
        if (!"".equals(list.get(position).getPicture())&&!TextUtils.isEmpty(list.get(position).getPicture())){
            String url=list.get(position).getPicture();
            if (url.contains(";")){
                url=url.substring(0,url.indexOf(";"));
            }
            Log.i("uri-----", Ip.imagePath+url);
            Picasso.with(context).load(Ip.imagePath+url).error(R.mipmap.errorpicture).placeholder(R.mipmap.errorpicture).into(hodler.forumposts_listview_item_photoImage);
        }
        else {
            hodler.forumposts_listview_item_photoImage.setVisibility(View.GONE);
        }
        hodler.forumpost_listitem_layout_post.setTag(position);
        hodler.forumpost_listitem_layout_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                if (isPraise==true){//当前正在执行点赞／取消点赞操作，不能再点击
                    if (list.get(pos).isIsLike()){
                        Toast.makeText(context,"正在取消点赞，请等待", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context,"正在点赞，请等待", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    isPraise=true;
                    setPraise(pos);
                }

            }
        });
        if (list.get(position).isIsLike()){
            hodler.forumposts_listview_item_postImage.setSelected(true);
        }
        else {
            hodler.forumposts_listview_item_postImage.setSelected(false);
        }
        Picasso.with(context).load(Ip.imagePath+list.get(position).getAvatar()).placeholder(R.mipmap.erroruser).error(R.mipmap.erroruser).into(hodler.forumpost_listitem_Image);
        Log.i("头像:",Ip.imagePath+list.get(position).getAvatar());
        hodler.forumpost_listitem_Name.setText(list.get(position).getTrueName());
        return convertView;
    }

    class ViewHodler{
        RoundImageView forumpost_listitem_Image;//头像
        TextView forumpost_listitem_Name;//姓名
        TextView forumpost_listitem_title,forumpost_listitem_content,forumpost_listitem_time;//标题，内容，时间
        TextView forumposts_listview_item_msgNum,forumposts_listview_item_postNum;//评论代数量，点赞的数量
        ImageView forumposts_listview_item_postImage,forumposts_listview_item_msgImage;//点赞，评论的image
        LinearLayout forumpost_listitem_layout_post,forumpost_listitem_layout_msg;//点赞,评论的layout;
        ImageView forumposts_listview_item_photoImage;
    }

    //点赞的接口http://192.168.1.55:8080/yuyi/likes/LikeNum.do?id=1&token=820F140709A478E3358AB5DA911C91E6
    public void setPraise(int id){
        CurrentPosition=id;
        Map<String,String> mp=new HashMap<>();
        mp.put("id",list.get(id).getId()+"");
        mp.put("token", User.token);
        OkUtils.getCall(Ip.path+Ip.interface_MyPostDataPraise,mp,OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("取消／点赞我的帖子---",resStr);
                    handler.sendEmptyMessage(1);
            }
        });

    }
}
