package com.technology.yuyidoctorpad.activity.AddPaintRecord;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

import static com.technology.yuyidoctorpad.R.id.text5;


/**
 * Created by wanyu on 2017/4/11.
 */

public class PostListAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    public PostListAdapter(Context context, List<String> list){
        this.list=list;
        this.context=context;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.post_listview_item,null);
            hodler=new ViewHodler();
            hodler.post_listitem_img= (ImageView) convertView.findViewById(R.id.post_listitem_img);
            hodler.text5=convertView.findViewById(text5);
            AbsListView.LayoutParams param = new AbsListView.LayoutParams((int)context.getResources().getDimension(R.dimen.grid),(int)context.getResources().getDimension(R.dimen.grid));
            convertView.setLayoutParams(param);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        if (position==list.size()-1){
           hodler.text5.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params=hodler.post_listitem_img.getLayoutParams();
            params.width=(int)context.getResources().getDimension(R.dimen.grid)/3;
            params.height=(int)context.getResources().getDimension(R.dimen.grid)/3;
            hodler.post_listitem_img.setLayoutParams(params);
            hodler.post_listitem_img.setScaleType(ImageView.ScaleType.FIT_XY);
            hodler.post_listitem_img.setImageResource(R.mipmap.add);
        }
        else {
            ViewGroup.LayoutParams params=hodler.post_listitem_img.getLayoutParams();
            params.width=(int)context.getResources().getDimension(R.dimen.grid);
            params.height=(int)context.getResources().getDimension(R.dimen.grid);
            hodler.post_listitem_img.setLayoutParams(params);
            hodler.post_listitem_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            hodler.text5.setVisibility(View.GONE);
            Log.i("url222=="+list.size(),list.get(position));
            Picasso.with(context).load(list.get(position))
                   .placeholder(R.mipmap.errorpicture).error(R.mipmap.errorpicture).into(hodler.post_listitem_img);
            }
        return convertView;
    }

    class ViewHodler{
        ImageView post_listitem_img;
        TextView text5;
    }

}
