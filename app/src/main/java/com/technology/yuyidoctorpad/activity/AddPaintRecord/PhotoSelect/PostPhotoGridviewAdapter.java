package com.technology.yuyidoctorpad.activity.AddPaintRecord.PhotoSelect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lzhUtils.BitMapUtils;
import com.technology.yuyidoctorpad.lzhViews.SelectImageVIew;

import java.util.List;
import java.util.Map;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by wanyu on 2017/4/11.
 */

public class PostPhotoGridviewAdapter extends BaseAdapter {
    private List<Map<String,String>> list;
    private Context context;
    private SelectIn selectIn;
    private int pos;
    public PostPhotoGridviewAdapter(List<Map<String,String>> list, Context context, SelectIn selectIn){
        this.context=context;
        this.list=list;
        this.selectIn=selectIn;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHodler hodler;
        if (convertView==null){
            hodler=new ViewHodler();
            convertView= LayoutInflater.from(context).inflate(R.layout.item,null);
//            ViewGroup.LayoutParams params=convertView.getLayoutParams();
//            params.width=BitMapUtils.getWindowWidth(context)/8;
//            params.height=BitMapUtils.getWindowWidth(context)/8;
//            convertView.setLayoutParams(params);
            hodler.image= (SelectImageVIew) convertView.findViewById(R.id.itemImage);
            hodler.CheckBox= (ImageView) convertView.findViewById(R.id.img2);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
//        Log.i("uri--",list.get(position).get("url"));
        String url=list.get(position).get("url");
        Picasso.with(context).load(url).centerCrop().resize(BitMapUtils.getWindowWidth(context)/8,
                BitMapUtils.getWindowWidth(context)/8).placeholder(R.mipmap.errorpicture).error(R.mipmap.errorpicture).into(hodler.image);
        String type=list.get(position).get("select");
        if ("0".equals(type)){//未选中
            hodler.CheckBox.setSelected(false);
            hodler.image.setState(0);
        }
        else if ("1".equals(type)){
            hodler.CheckBox.setSelected(true);
            hodler.image.setState(1);
        }

        hodler.CheckBox.setTag(position);
        hodler.CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                if ("0".equals(list.get(pos).get("select"))){//未选中状态
                    int c=getSelectCount();
                    if (getSelectCount()<3){//做多6张图
                        list.get(pos).put("select","1");
                        v.setSelected(true);
                        notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context,"最多可以选择3张图", Toast.LENGTH_SHORT).show();
                    }
                }
                else if ("1".equals(list.get(pos).get("select"))){//选中状态
                    list.get(pos).put("select","0");
                    v.setSelected(false);
                    notifyDataSetChanged();
                }
                selectIn.select(getSelectCount());
            }
        });
        return convertView;
    }
    class ViewHodler{
        SelectImageVIew image;
        ImageView CheckBox;
    }

    public interface SelectIn{
        void select(int count);
    }

    public int getSelectCount(){
        int count=0;
        for (int i=0;i<list.size();i++){
            if ("1".equals(list.get(i).get("select"))){
                count++;
            }
        }
        return count;
    }
}
