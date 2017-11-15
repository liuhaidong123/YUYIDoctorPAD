package com.technology.yuyidoctorpad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/11/14.
 */

public class DepartmentAda extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public DepartmentAda(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();

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
        DHolder dHolder = null;
        if (view == null) {
            dHolder = new DHolder();
            view = inflater.inflate(R.layout.department_listview_item, null);
            dHolder.textView = view.findViewById(R.id.department_id);
            view.setTag(dHolder);
        } else {
            dHolder = (DHolder) view.getTag();
        }

        dHolder.textView.setText(list.get(i));
        return view;
    }

    class DHolder {
        TextView textView;
    }
}
