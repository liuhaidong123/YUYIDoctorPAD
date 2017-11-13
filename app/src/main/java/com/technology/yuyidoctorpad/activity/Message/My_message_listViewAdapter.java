package com.technology.yuyidoctorpad.activity.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lzhViews.MyImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by wanyu on 2017/3/27.
 */

public class My_message_listViewAdapter extends BaseAdapter {
    private Context context;
    private List<Bean_MyMessage.RowsBean> list;
    public My_message_listViewAdapter(Context context, List<Bean_MyMessage.RowsBean> list){
        this.context=context;
        this.list=list;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.my_message_listview_item,null);
            hodler=new ViewHodler();
            hodler.my_message_titleImage_item= (MyImageView) convertView.findViewById(R.id.my_message_titleImage_item);
            hodler.my_message_notifi_name_item= (TextView) convertView.findViewById(R.id.my_message_notifi_name_item);
            hodler.my_message_notifi_time_item= (TextView) convertView.findViewById(R.id.my_message_notifi_time_item);
            hodler.my_message_notifi_msg_item= (TextView) convertView.findViewById(R.id.my_message_notifi_msg_item);
            hodler.msgItemTitle= (TextView) convertView.findViewById(R.id.msgItemTitle);
            convertView.setTag(hodler);
        }
        hodler= (ViewHodler) convertView.getTag();
        if (list.get(position).isIsRead()==true){
            hodler.my_message_titleImage_item.setIsRead(true);
        }
        else {
            hodler.my_message_titleImage_item.setIsRead(false);
        }
        Picasso.with(context).load(Ip.imagePath+list.get(position).getAvatar()).error(R.mipmap.erroruser).placeholder(R.mipmap.erroruser).into(hodler.my_message_titleImage_item);
//        hodler.my_message_notifi_msg_item.setText(list.get(position).getContent());
        hodler.my_message_notifi_name_item.setText(list.get(position).getTitle());
        hodler.msgItemTitle.setText(list.get(position).getContent());

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date d= format.parse(list.get(position).getCreateTimeString());
            SimpleDateFormat fom=new SimpleDateFormat("HH:mm");
            hodler.my_message_notifi_time_item.setText(fom.format(d));
        }
        catch (Exception e){
            hodler.my_message_notifi_time_item.setText("日期未获取");
            e.printStackTrace();
        }
        return convertView;
    }
    class ViewHodler{
        TextView msgItemTitle;//帖子标题
        MyImageView my_message_titleImage_item;
        TextView my_message_notifi_name_item,my_message_notifi_time_item,my_message_notifi_msg_item;//名字，时间，内容
    }
}
