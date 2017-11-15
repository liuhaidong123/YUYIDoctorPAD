package com.technology.yuyidoctorpad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lhdUtils.ImgUitls;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by liuhaidong on 2017/5/3.
 */

public class SelectImgAda extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mList=new ArrayList<>();

    public SelectImgAda(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater= LayoutInflater.from(this.mContext);
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
        SelectImgHolder holder=null;
        if (convertView==null){
            holder=new SelectImgHolder();
            convertView=mInflater.inflate(R.layout.repair_select_img_gridview_item,null);
            holder.img= (ImageView) convertView.findViewById(R.id.select_img);
            holder.smallImg= (ImageView) convertView.findViewById(R.id.select_small_img);
            convertView.setTag(holder);
        }else {
            holder= (SelectImgHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(mList.get(position)).centerCrop().resize(ImgUitls.getWith(mContext)/3,
                ImgUitls.getWith(mContext)/3).error(R.mipmap.errorpicture).into(holder.img);


        return convertView;
    }

    class SelectImgHolder{
        ImageView smallImg;
        ImageView img;
    }
}
