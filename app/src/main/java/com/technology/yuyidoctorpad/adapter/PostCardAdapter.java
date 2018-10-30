package com.technology.yuyidoctorpad.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lhdUtils.ImgUitls;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by liuhaidong on 2017/11/6.
 */

public class PostCardAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mList = new ArrayList<>();

    public PostCardAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater=LayoutInflater.from(mContext);
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImgHolder imgHolder=null;
        Log.e("i=", ""+i);
        if (view==null){
            imgHolder=new ImgHolder();
            view=mInflater.inflate(R.layout.card_item,null);
            imgHolder.add_tv=view.findViewById(R.id.add_tv);
            imgHolder.img=view.findViewById(R.id.card_img);
            view.setTag(imgHolder);
        }else {
            imgHolder= (ImgHolder) view.getTag();
        }

       if (viewGroup.getChildCount()==i){
            if (mList.size()==0){
                imgHolder.img.setImageResource(R.mipmap.add_img);
                Log.e("add_img", ":mList.size()==0");
                //imgHolder.add_tv.setVisibility(View.GONE);
            }else {
                if (i==mList.size()){
                    imgHolder.img.setImageResource(R.mipmap.add_img);
                    Log.e("add_img", ":mList.size()==i"+i);
                    //imgHolder.add_tv.setVisibility(View.GONE);
                }else {
                    Log.e("实际图像", ""+i);
                    //imgHolder.add_tv.setVisibility(View.GONE);
                    Picasso.with(mContext).load(mList.get(i)).resize(ImgUitls.getWith(mContext)/6,ImgUitls.getWith(mContext)/6).error(R.mipmap.errorpicture).into(imgHolder.img);
                }


            }
        }else {

        }





        return view;
    }

    class ImgHolder{
        TextView add_tv;
        ImageView img;
    }
}
