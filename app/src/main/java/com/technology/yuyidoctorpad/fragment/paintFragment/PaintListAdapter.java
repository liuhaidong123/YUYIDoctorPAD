package com.technology.yuyidoctorpad.fragment.paintFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Context;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/3.
 */

public class PaintListAdapter extends BaseAdapter{
    Context con;
    List<paintListBean.RowsBean> list;
    public PaintListAdapter(Context con,List<paintListBean.RowsBean>list){
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
            view= LayoutInflater.from(con).inflate(R.layout.list_paintlist,null);
            hodler=new ViewHodler(view);
            view.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) view.getTag();
        }
        Picasso.with(con).load(Ip.imagePath+list.get(i).getAvatar()).placeholder(R.mipmap.erroruser).error(R.mipmap.erroruser).into(hodler.paintlist_item_image);
        hodler.paintlist_item_name.setText(list.get(i).getTrueName());
        return view;
    }

    class ViewHodler{
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
       @BindView(R.id.paintlist_item_image) RoundImageView paintlist_item_image;//头像
        @BindView(R.id.paintlist_item_name) TextView paintlist_item_name;
    }
}
