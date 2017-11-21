package com.technology.yuyidoctorpad.activity.Registion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhUtils.MyListView.IonScrollBottomListener;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;
import com.technology.yuyidoctorpad.wheelView.MyWheelAdapter;
import com.technology.yuyidoctorpad.wheelView.MyWheelView;
import com.technology.yuyidoctorpad.wheelView.OnWheelChangedListener;
import com.technology.yuyidoctorpad.wheelView.WheelView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class RegistionActivity extends MyActivity implements IonScrollBottomListener{
    @BindView(R.id.titleinclude_text)TextView title;
    private final Context con = RegistionActivity.this;
    private MyListView my_registration_listview;
    TextView myRegistration_textVTime;//显示的挂号的时间
    final int AM=1;//上午
    final int PM=0;//下午
    int isAm;//上下午的参数
    String visitTime="";//挂号的时间2017-09-17
    RelativeLayout myRegistration_layout;//显示时间的layout

    //挂号时间筛选弹窗

    int currentTime;
    String currentDate;
    String month="";
    String year="";
    PopupWindow pop;
    MyWheelAdapter adapterTime;
    List<String> listTime;

    MyWheelAdapter adapterdate;
    List<String>listDate;

    private My_Registration_Adapter adapter;
    private TextView my_registration_ks;//科室名字
    private RelativeLayout my_registration_relayout;
    private PopupWindow popSucc;
    private ImageView my_registration_image;
    private MyListAdapter adapterPop;
    private String resStr;
    private int start = 0;
    private final int limit = 10;
    private List<Bean_MyRegistrationKS.ResultBean> listKS;
    private ExpandableListView my_registration_pop_listview;
    private List<Bean_RegistertionList.RowsBean> lis;//挂号列表


    private Long prId;
    private Long chId;
    int st=0;
    //----
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MyDialog.stopDia();
                    my_registration_listview.setEmpey("网络异常！");
                    my_registration_listview.setLoadingComplete();
                    break;
                case 1:
                    MyDialog.stopDia();
                    try {
                        Bean_MyRegistrationKS ks = gson.gson.fromJson(resStr, Bean_MyRegistrationKS.class);
                        if (ks != null) {
                            if ("0".equals(ks.getCode())) {
                                if (ks.getResult() != null && ks.getResult().size() > 0) {
                                    listKS.addAll(ks.getResult());
                                } else {
                                   toast.toast(con,"无法获取科室信息！");
                                }
                            } else if ("-1".equals(ks.getCode())) {
                                Toast.makeText(con, "没有挂号接收权限", Toast.LENGTH_SHORT).show();
                            } else {
                                if (st==0){
                                    st=1;
                                    getKSData();
                                }
                                else {
                                    toast.toast(con,"无法获取科室信息！");
                                }
                            }
                        } else {
                            toast.toast(con,"无法获取科室信息！");
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;
                case 2://挂号列表患者列表
                    MyDialog.stopDia();
                    try {
                        Bean_RegistertionList resgit = gson.gson.fromJson(resStr, Bean_RegistertionList.class);
                        if (resgit != null) {
                            List<Bean_RegistertionList.RowsBean> lt = resgit.getRows();
                            if (lt != null && lt.size() > 0) {
                                start += lt.size();
                                lis.addAll(lt);
                                if (lt.size() == limit) {//还有数据
                                    my_registration_listview.setScroll(true);
                                } else {//还有数据
                                    my_registration_listview.setLoadingComplete();
                                }

                            } else {
                                my_registration_listview.setEmpey("没有查询到数据！");
                                my_registration_listview.setLoadingComplete();
                            }
                        } else {
                            my_registration_listview.setEmpey("没有查询到数据！");
                            my_registration_listview.setLoadingComplete();
                        }
                    } catch (Exception e) {
                        my_registration_listview.setEmpey("数据异常！");
                        e.printStackTrace();
                    }
                    finally {
                        if (lis.size()==0){
                            my_registration_listview.setEmpey("没有查询到数据！");
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 3:
                    showWindowSelect();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_registion);
        title.setText("挂号接收");
        initView();
        listKS = new ArrayList<>();
        adapterPop = new MyListAdapter(this,listKS);//科室列表的adaprer
        getKSData();
        prId = -1L;
        chId = -1L;
        getRegsiterData(-1L, -1L, start, limit);
    }


    private void initView() {
        Date date=new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        Log.e("shijian---","月份："+(c.get(Calendar.MONTH)+1)+"--日期:"+c.get(Calendar.DAY_OF_MONTH)+"--xiaoshi:"+ c.get(Calendar.HOUR_OF_DAY));
        myRegistration_layout= (RelativeLayout) findViewById(R.id.myRegistration_layout);
        myRegistration_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindowTime();//日期选择
            }
        });
        myRegistration_textVTime= (TextView) findViewById(R.id.myRegistration_textVTime);
        String tim="上午";
        if (date.getHours()>=0&&date.getHours()<12){
            isAm=AM;
            tim="上午";
        }
        else {
            isAm=PM;
            tim="下午";
        }
        month=(c.get(Calendar.MONTH)+1)+"";
        year=c.get(Calendar.YEAR)+"";
        visitTime=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
        myRegistration_textVTime.setText((date.getMonth()+1)+"月"+date.getDate()+"日"+tim);

        lis = new ArrayList<>();
        my_registration_image = (ImageView) findViewById(R.id.my_registration_image);

        my_registration_listview = (MyListView) findViewById(R.id.my_registration_listview);
        adapter = new My_Registration_Adapter(this,lis);
        my_registration_listview.setAdapter(adapter);

        my_registration_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RegistionActivity.this, My_registration_Info_Activity.class);
                intent.putExtra("id", lis.get(position).getId() + "");
                startActivity(intent);
            }
        });

        my_registration_ks = (TextView) findViewById(R.id.my_registration_ks);
        my_registration_relayout = (RelativeLayout) findViewById(R.id.my_registration_relayout);
        my_registration_relayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popSucc != null && popSucc.isShowing()) {
                    return;
                }
                showWindowSelect();
            }
        });
    }

    //选择日期
    private void showWindowTime() {
        if (listTime==null|listDate==null){
            initList();
        }
        pop=new PopupWindow();
        View vi= LayoutInflater.from(con).inflate(R.layout.timeselectlayout,null);
        TextView PopTime_cancle= (TextView) vi.findViewById(R.id.PopTime_cancle);
        TextView PopTime_submit= (TextView) vi.findViewById(R.id.PopTime_submit);
        TextView PopTime_Month= (TextView) vi.findViewById(R.id.PopTime_Month);
        PopTime_Month.setText(month+"月");
        currentTime=AM;
        currentDate=listDate.get(0);
        MyWheelView pop_wheel_date= (MyWheelView) vi.findViewById(R.id.pop_wheel_date);//日期
        MyWheelView pop_wheel_time= (MyWheelView) vi.findViewById(R.id.pop_wheel_time);//上下午
        adapterTime=new MyWheelAdapter(con,listTime);
        pop_wheel_time.setViewAdapter(adapterTime);
        adapterdate=new MyWheelAdapter(con,listDate);
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
                        currentTime=AM;
                        break;
                    case 1:
                        currentTime=PM;
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
                isAm=currentTime;
                String tim="上下午";
                switch (isAm){
                    case 0:
                        tim="下午";
                        break;
                    case 1:
                        tim="上午";
                        break;
                }
                currentDate=currentDate.replace("日","");
                myRegistration_textVTime.setText(month+"月"+currentDate+tim);
                visitTime=year+"-"+month+"-"+currentDate;
                Log.i("isAm=="+isAm,"visitTime=="+visitTime);
                st=0;
                lis.clear();
                adapter.notifyDataSetChanged();
                getRegsiterData(prId,chId,st,limit);
                pop.dismiss();
            }
        });

        LinearLayout parent = (LinearLayout) findViewById(R.id.activity_registion);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);

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
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    //所有科室
    public void showWindowSelect() {
        if (listKS != null && listKS.size() > 0) {
            popSucc = new PopupWindow();
            View v = LayoutInflater.from(RegistionActivity.this).inflate(R.layout.my_registration_pop, null);
            my_registration_pop_listview = (ExpandableListView) v.findViewById(R.id.my_registration_pop_listview);
            my_registration_pop_listview.setGroupIndicator(null);
            my_registration_pop_listview.setAdapter(adapterPop);
            my_registration_pop_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    start = 0;
                    lis.clear();
                    adapter.notifyDataSetChanged();
                    getRegsiterData(listKS.get(groupPosition).getId(), -1L, start, limit);
                    prId = listKS.get(groupPosition).getId();
                    chId = -1L;
                    my_registration_ks.setText(listKS.get(groupPosition).getDepartmentName());
                    return true;
                }
            });

            my_registration_pop_listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    if (popSucc != null) {
                        popSucc.dismiss();
                    }
                    start = 0;
                    lis.clear();
                    adapter.notifyDataSetChanged();
                    my_registration_ks.setText(listKS.get(groupPosition).getClinicList().get(childPosition).getClinicName());
                    prId = listKS.get(groupPosition).getId();
                    chId = listKS.get(groupPosition).getClinicList().get(childPosition).getId();
                    getRegsiterData(listKS.get(groupPosition).getId(), listKS.get(groupPosition).getClinicList().get(childPosition).getId(), start, limit);
                    return true;
                }
            });
            RelativeLayout parent = (RelativeLayout) findViewById(R.id.my_registration_relayout);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 0.8f;
            getWindow().setAttributes(params);

            popSucc.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popSucc.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

            popSucc.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popSucc.setContentView(v);
            popSucc.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
            popSucc.setTouchable(true);
            popSucc.setFocusable(true);
            popSucc.setOutsideTouchable(true);


            popSucc.setAnimationStyle(R.style.popup4_anim);
            popSucc.showAsDropDown(parent,parent.getMeasuredWidth()-v.getMeasuredWidth(),20);
            my_registration_image.setSelected(true);
            popSucc.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    my_registration_image.setSelected(false);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                }
            });
            for (int i = 0; i < adapterPop.getGroupCount(); i++) {
                my_registration_pop_listview.expandGroup(i);
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }


    //获取科室列表接口
    public void getKSData() {
        Map<String, String> m = new HashMap<>();
        m.put("token", User.token);
        OkUtils.getCall(Ip.path + Ip.interface_MyRegisterKS, m, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("获取所有科室----", resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }


    //获取挂号列表接口http://192.168.1.55:8080/yuyi/register/findList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5&departmentId=1&clinicId=1
    public void getRegsiterData(Long parentId, Long ChildId, int st, int lim) {
        //请求全部不加clinicId,departmentId
        //请求大类不加clinicId
        if (popSucc != null) {
            popSucc.dismiss();
        }

        Map<String, String> mp = new HashMap<>();
        if (ChildId != -1) {//父view点击事件
            mp.put("clinicId", ChildId + "");
        }
        if (parentId != -1) {
            mp.put("departmentId", "" + parentId);
        }
        mp.put("token", User.token);
        mp.put("start", st + "");
        mp.put("limit", limit + "");
        mp.put("isAm",isAm+"");
        mp.put("visitTime",visitTime);
        Log.i("start---" + st, "limit---" + lim);


        OkUtils.getCall(Ip.path + Ip.interface_MyRegisterGH, mp, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("获取挂号列表请求---", resStr);
                handler.sendEmptyMessage(2);
            }
        });
    }

    public void initList(){
        listDate=new ArrayList<>();
        listTime=new ArrayList<>();
        listDate= WheelViewData.getInstance().getTimeList();
        listTime.add("上午");
        listTime.add("下午");
    }

    @Override
    public void onScrollBottom() {
        getRegsiterData(prId, chId, start, limit);
    }
}
