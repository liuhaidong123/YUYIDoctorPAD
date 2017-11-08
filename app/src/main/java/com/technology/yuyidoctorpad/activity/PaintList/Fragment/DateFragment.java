package com.technology.yuyidoctorpad.activity.PaintList.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.PaintList.Bean.BeanTempPress;
import com.technology.yuyidoctorpad.activity.PaintList.Model.IDate;
import com.technology.yuyidoctorpad.lzhViews.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wanyu on 2017/11/3.
 */
//血压／体温
public class DateFragment extends BaseFragment implements IDate{
    String id;
    DatePresenter presenter;
    @BindView(R.id.image_press)ImageView image_press;
    @BindView(R.id.image_tem)ImageView image_tem;
    @BindView(R.id.paintmsg)RelativeLayout paintmsg;//显示状态的view
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.frag_date,null);
        unbinder= ButterKnife.bind(this,vi);
        image_press.setSelected(true);
        image_tem.setSelected(false);
        presenter=new DatePresenter(getActivity(),paintmsg);
        presenter.getPressTemp(id,this);
        return vi;
    }

    public void setIds(String ids){
        this.id=ids;
    }

    @OnClick({R.id.rela_press,R.id.rela_tem})
    public void 点击事件(View vi){
        switch (vi.getId()){
            case R.id.rela_press://血压
                image_press.setSelected(true);
                image_tem.setSelected(false);
                presenter.showView(DatePresenter.Type.PRESS);
                break;
            case R.id.rela_tem://体温
                image_press.setSelected(false);
                image_tem.setSelected(true);
                presenter.showView(DatePresenter.Type.TEMP);
                break;
        }
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(BeanTempPress bean) {
        presenter.setDate(bean);//格式化获取到到数据并给相应的view设置
    }
}
