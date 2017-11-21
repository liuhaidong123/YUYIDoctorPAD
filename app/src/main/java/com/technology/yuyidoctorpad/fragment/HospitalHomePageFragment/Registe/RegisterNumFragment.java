package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Registe;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.Registion.MyListAdapter;
import com.technology.yuyidoctorpad.activity.Registion.WheelViewData;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.IDepart;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.BaseFragment;
import com.technology.yuyidoctorpad.wheelView.MyWheelAdapter;
import com.technology.yuyidoctorpad.wheelView.MyWheelView;
import com.technology.yuyidoctorpad.wheelView.OnWheelChangedListener;
import com.technology.yuyidoctorpad.wheelView.WheelView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//挂号
/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterNumFragment extends BaseFragment implements IRegister,IDepart,RegisterListViewAdapter.IonSelect,MyListView.IonScrollBottomListener {
    int count=0;//获取科室信息最多请求2次（count=1时，不在请求）
    boolean flag=false;//获取科室信息是否成功
    View fragView;

    private PopupWindow popSucc;//科室弹窗
    private ImageView my_registration_image;
    private MyListAdapter adapterPop;
    private ExpandableListView my_registration_pop_listview;
    boolean isSubmit=false;//是否正提交挂号信息
    RegisterPresenter presenter;
//    @BindView(R.id.searchView)SearchView searchView;
    @BindView(R.id.registion_imgAllSelect)ImageView registion_imgAllSelect;//全选按钮
    @BindView(R.id.registion_KSName)TextView registion_KSName;//当前的科室名称
    @BindView(R.id.registion_TimeText)TextView registion_TimeText;//当前的时间
    @BindView(R.id.registion_RegisterNum)EditText registion_RegisterNum;//挂号数
    @BindView(R.id.registion_listView)MyListView registion_listView;//listView
    List<DoctorListBean.ResultBean> list;//医生信息
    List<ListDoctorResult.ResultBean>listSubmit;//需要设置挂号信息的list
    List<DepartmentBean.ResultBean> listAllDepart;//科室信息的list集合
    RegisterListViewAdapter adapter;
    int start=0;
    int limit=10;

    final int AM=1;//上午
    final int PM=0;//下午
    int selectIsAm;//当前选定的上下午
    String selectDate="";//存放选定的年月日（2017年11月20日）
    String selectDepartId="0";//当前选择的科室id


    //挂号时间筛选弹窗

    int IsAm;//上下午临时的
    String currentDate;//存放临时的日期
    PopupWindow pop;
    MyWheelAdapter adapterTime;
    List<String> listTime;
    MyWheelAdapter adapterdate;
    List<String>listDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            fragView=inflater.inflate(R.layout.fragment_register_num, container, false);
            unbinder= ButterKnife.bind(this,fragView);
            initData();
        return fragView;
    }
    public void init(){
        Date date=new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        String tim="上午";
        if (date.getHours()>=0&&date.getHours()<12){
            selectIsAm=AM;
            tim="上午";
        }
        else {
            selectIsAm=PM;
            tim="下午";
        }
        selectDate=c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"+c.get(Calendar.DAY_OF_MONTH)+"日";
        registion_TimeText.setText((date.getMonth()+1)+"月"+date.getDate()+"日"+"-"+tim);
    }
    private void initData() {
        init();
        listAllDepart=new ArrayList<>();
        DepartmentBean.ResultBean b=new DepartmentBean.ResultBean();
        b.setDepartmentName("全部");
        b.setId(0);
        listAllDepart.add(b);
        list=new ArrayList<>();
        presenter=new RegisterPresenter();
        adapter=new RegisterListViewAdapter(list,getActivity(),this);
//        registion_listView.setFooterViewVisiable(false);
        registion_listView.setAdapter(adapter);
        registion_listView.setLoading();
        registion_listView.setOnScrollBottomListener(this);
        adapter.setAfterNoon(selectIsAm==0?true:false);//0下午，1上午
        presenter.getAllDepartment(this);//获取所有科室信息
        presenter.getDoctorList(selectDepartId,this,start,limit,formatData());
    }

    @OnClick({R.id.registion_imgAllSelect,R.id.registion_layoutKS,R.id.registion_layoutTime,R.id.registion_submitT})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.registion_imgAllSelect://全选按钮
                if(list!=null&&list.size()>0){
                    boolean flag=registion_imgAllSelect.isSelected();
                    for (int i=0;i<list.size();i++){
                        list.get(i).setCanSelect(!flag);
                    }
                    adapter.notifyDataSetChanged();
                    registion_imgAllSelect.setSelected(!registion_imgAllSelect.isSelected());
                }
                break;
            case R.id.registion_layoutKS://科室选择的layout
                if (flag==true){//弹出科室列表
                    showDepartWindow();
                }
                else {
                    if (count==0){
                        toast.toast(getActivity(),"正在加载科室信息，请稍后重试！");
                    }
                    else {
                        toast.toast(getActivity(),"无法获取到科室信息！");
                    }
                }
                break;
            case R.id.registion_layoutTime://时间选择
                showWindowTime();
                break;
            case R.id.registion_submitT://提交挂号信息
                if (Empty.notEmpty(registion_RegisterNum.getText().toString())){
                    int count=Integer.parseInt(registion_RegisterNum.getText().toString());
                    if (count>=0&&count<=100){
                        if (flag==true){
                            if (isSubmit){
                                toast.toast(getActivity(),"正在操作，请稍后。。。");
                                return;
                            }
                            else {
                                if (list.size()>0&&!isListSelectEmpty(count)){
                                    isSubmit=true;
                                    ListDoctorResult b=new ListDoctorResult();
                                    b.setList(listSubmit);
                                    presenter.submit(b,this);
                                }
                                else {
                                    toast.toast(getActivity(),"您还没有选择挂号医生！");
                                }
                            }
                        }
                        else {
                            toast.toast(getActivity(),"没有获取到科室信息！");
                        }
                    }
                    else {
                        if (count<0){
                            toast.toast(getActivity(),"挂号数不能小于0！");
                        }
                        else {
                            toast.toast(getActivity(),"挂号数不能大于100！");
                        }

                    }

                }
                else {
                    toast.toast(getActivity(),"挂号数不能为空！");
                }
                break;
        }
    }
    //科室列表的window
    private void showDepartWindow() {
            popSucc = new PopupWindow();
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.departpop, null);
            ListView listView=v.findViewById(R.id.listView);
                PopAdapter ad=new PopAdapter(listAllDepart,getActivity());
            listView.setAdapter(ad);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    registion_listView.removeEmptyView();
                    registion_listView.setLoading();
                    registion_imgAllSelect.setSelected(false);

                    selectDepartId=listAllDepart.get(i).getId()+"";
                    registion_KSName.setText(Empty.notEmpty(listAllDepart.get(i).getDepartmentName())?listAllDepart.get(i).getDepartmentName():"科室未命名");
                    start=0;
                    presenter.getDoctorList(selectDepartId,RegisterNumFragment.this,start,limit,formatData());
                    popSucc.dismiss();
                }
            });
            View parent =  fragView.findViewById(R.id.registion_layoutKS);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams params =  getActivity().getWindow().getAttributes();
            params.alpha = 0.8f;
            getActivity().getWindow().setAttributes(params);

            popSucc.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popSucc.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

            popSucc.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popSucc.setContentView(v);
            popSucc.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
            popSucc.setTouchable(true);
            popSucc.setFocusable(true);
            popSucc.setOutsideTouchable(true);


            popSucc.setAnimationStyle(R.style.popup4_anim);
            popSucc.showAsDropDown(parent,-35,10);
            popSucc.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    getActivity(). getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    WindowManager.LayoutParams params =  getActivity().getWindow().getAttributes();
                    params.alpha = 1f;
                    getActivity(). getWindow().setAttributes(params);
                }
            });
    }
    public void initList(){
        listDate=new ArrayList<>();
        listTime=new ArrayList<>();
        listDate= WheelViewData.getInstance().getDate();
        listTime.add("上午");
        listTime.add("下午");
    }
    //选择日期
    private void showWindowTime() {
        if (listTime==null|listDate==null){
            initList();
        }
        pop=new PopupWindow();
        View vi= LayoutInflater.from(getActivity()).inflate(R.layout.timeselectlayout,null);
        TextView PopTime_cancle= (TextView) vi.findViewById(R.id.PopTime_cancle);
        TextView PopTime_submit= (TextView) vi.findViewById(R.id.PopTime_submit);
        TextView PopTime_Month= (TextView) vi.findViewById(R.id.PopTime_Month);
        PopTime_Month.setVisibility(View.GONE);
        IsAm=AM;
        currentDate=listDate.get(0);
        MyWheelView pop_wheel_date= (MyWheelView) vi.findViewById(R.id.pop_wheel_date);//日期
        MyWheelView pop_wheel_time= (MyWheelView) vi.findViewById(R.id.pop_wheel_time);//上下午
        adapterTime=new MyWheelAdapter(getActivity(),listTime);
        pop_wheel_time.setViewAdapter(adapterTime);
        List<String>l=new ArrayList<>();
        for (int i=0;i<listDate.size();i++){
            l.add(listDate.get(i).substring(5));
        }
        adapterdate=new MyWheelAdapter(getActivity(), l);
        pop_wheel_date.setViewAdapter(adapterdate);
        pop_wheel_date.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentDate=listDate.get(newValue);
            }
        });

        pop_wheel_time.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                switch (newValue){
                    case 0:
                        IsAm=AM;
                        break;
                    case 1:
                        IsAm=PM;
                        break;
                }
            }
        });
        PopTime_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        PopTime_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIsAm=IsAm;
                String tim="上下午";
                switch (selectIsAm){
                    case 0:
                        tim="下午";
                        break;
                    case 1:
                        tim="上午";
                        break;
                }
                selectDate=currentDate;
                String text=selectDate.substring(5);
                registion_TimeText.setText(text+"-"+tim);

                list.clear();
                adapter.notifyDataSetChanged();
                registion_listView.removeEmptyView();
                registion_listView.setLoading();
                registion_imgAllSelect.setSelected(false);

                presenter.getDoctorList(selectDepartId,RegisterNumFragment.this,start,limit,formatData());
                pop.dismiss();
            }
        });
        View parent = fragView.findViewById(R.id.la);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params =  getActivity().getWindow().getAttributes();
        params.alpha = 0.6f;
        getActivity().getWindow().setAttributes(params);

        pop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        pop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);

        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(vi);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);

        pop.setAnimationStyle(R.style.popup_anim);
        pop.showAtLocation(parent, Gravity.BOTTOM,0,0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params =  getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
    }
    //获取医生信息失败
    @Override
    public void onGetDocError(String msg) {
        toast.toast(getActivity(),msg);
        registion_listView.setEmpey(msg);
        registion_listView.setLoadingComplete();
    }
    //获取医生信息成功
    @Override
    public void onGetDocSuccess(DoctorListBean listBean) {
        List<DoctorListBean.ResultBean>li=listBean.getResult();
        if (li!=null&&li.size()>0){
            start+=li.size();
//            if (li.size()==limit){
//                registion_listView.setScroll(true);
//            }
//            else {
//                registion_listView.setLoadingComplete();
//            }
            registion_listView.setLoadingComplete();
            list.addAll(li);
            adapter.setAfterNoon(selectIsAm==0?true:false);//0下午，1上午
            adapter.notifyDataSetChanged();
            registion_imgAllSelect.setSelected(false);
        }
        else {
            registion_listView.setEmpey("没有查询到数据！");
            registion_listView.setLoadingComplete();
        }
    }
    //挂号设置成功
    @Override
    public void onRegisterError(String msg) {
        isSubmit=false;
        toast.toast(getActivity(),msg);
    }
    //挂号设置失败
    @Override
    public void onRegisterSuccess() {
        isSubmit=false;
    }

    //获取科室信息失败
    @Override
    public void onGetDrpartError(String msg) {
        if (count==0){
            count=1;
            presenter.getAllDepartment(this);//第二次获取科室信息
        }
    }
    //获取科室信息成功
    @Override
    public void onGetDrpartSuccess(List<DepartmentBean.ResultBean> listDepart) {
            if (listDepart!=null&&listDepart.size()>0){
                listAllDepart.addAll(listDepart);
                flag=true;
            }
            else {
                count=1;
            }
    }

    //提交挂号判断是否有选中医生信息
    public boolean isListSelectEmpty(int count){
        listSubmit=new ArrayList<>();
        boolean empty=true;
        if (list!=null&&list.size()>0){
            SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
            String dt=formatData();
                for (int i=0;i<list.size();i++){
                    if (list.get(i).isCanSelect()){//当前被选中
                        list.get(i).setDate(formatData());
                        switch (selectIsAm){
                            case AM:
                                list.get(i).setBeforNum(count);
                                break;
                            case PM:
                                list.get(i).setAfterNum(count);
                                break;
                            }
                        ListDoctorResult.ResultBean bean=new ListDoctorResult.ResultBean();
                        bean.setAfterNum(list.get(i).getAfterNum());
                        bean.setBeforNum(list.get(i).getBeforNum());
                        Timestamp d=Timestamp.valueOf(list.get(i).getDate()+" 00:00:00");
                        bean.setDate(d);
                        bean.setPhysicianId(""+list.get(i).getPhysicianId());
                        listSubmit.add(bean);
                        empty=false;
                    }
                }
        }
        adapter.notifyDataSetChanged();
        return empty;
    }

    @Override
    public void isAllSelect(boolean flag) {
        registion_imgAllSelect.setSelected(flag);
    }

    @Override
    public void onScrollBottom() {
        presenter.getDoctorList(selectDepartId,this,start,limit,formatData());
    }
    //格式化日期（2018-12-10）
    private String formatData(){
        String dt="";
        Log.e("日期",selectDate);
        try {
            SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
            Date d=format.parse(selectDate);
            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
            dt=format1.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            init();
            return selectDate.substring(0,3)+"-"+selectDate.substring(5,6)+"-"+selectDate.substring(8,9);
        }
        return dt;
    }
}
