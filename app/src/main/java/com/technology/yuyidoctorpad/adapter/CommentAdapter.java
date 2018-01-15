package com.technology.yuyidoctorpad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.lhdUtils.TimeUtils;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.bean.CommendListBean.Result;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by liuhaidong on 2017/3/28.
 */

public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Result> mList = new ArrayList<>();

    public CommentAdapter(Context mContext, List<Result> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmList(List<Result> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommendHolder commendHolder=null;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.comment_listview_item, null);
            commendHolder=new CommendHolder();
            commendHolder.img= (RoundImageView) convertView.findViewById(R.id.comment_userhead);
            commendHolder.name= (TextView) convertView.findViewById(R.id.comment_username);
            commendHolder.time= (TextView) convertView.findViewById(R.id.comment_time);
            commendHolder.content= (TextView) convertView.findViewById(R.id.comment_content);
            convertView.setTag(commendHolder);
        }else {
            commendHolder= (CommendHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE+mList.get(position).getAvatar()).error(R.mipmap.erroruser).into(commendHolder.img);
        commendHolder.name.setText(mList.get(position).getTrueName());

        commendHolder.content.setText(mList.get(position).getContent());
        commendHolder.time.setText(TimeUtils.getTime(mList.get(position).getCreateTimeString()));
        return convertView;
    }

    class CommendHolder{
        RoundImageView img;
        TextView name;
        TextView time;
        TextView content;
    }
}
