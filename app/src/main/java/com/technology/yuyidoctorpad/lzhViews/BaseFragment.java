package com.technology.yuyidoctorpad.lzhViews;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by wanyu on 2017/11/3.
 */

public class BaseFragment extends Fragment{
   public Unbinder unbinder;
//    @BindView(R.id.titleinclude_text)TextView titleV;
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }

}
