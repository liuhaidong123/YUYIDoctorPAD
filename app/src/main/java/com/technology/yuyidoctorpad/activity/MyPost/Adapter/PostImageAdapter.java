package com.technology.yuyidoctorpad.activity.MyPost.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.R;

import java.util.List;

/**
 * Created by wanyu on 2017/11/8.
 */

public class PostImageAdapter extends BaseAdapter{
    Context con;
    List<String>list;
    public PostImageAdapter(Context con,List<String>list){
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
            view= LayoutInflater.from(con).inflate(R.layout.item_img,null);
            hodler=new ViewHodler();
            hodler.listImage=view.findViewById(R.id.listImage);
            view.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) view.getTag();
        }
        Picasso.with(con).load(Ip.imagePath+list.get(i)).placeholder(R.mipmap.errorpicture).error(R.mipmap.errorpicture).into(hodler.listImage);
        return view;
    }

    class ViewHodler{
        ImageView listImage;
    }
}
