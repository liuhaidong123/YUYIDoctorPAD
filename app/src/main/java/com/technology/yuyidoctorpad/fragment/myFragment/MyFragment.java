package com.technology.yuyidoctorpad.fragment.myFragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Enum.IntentValue;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.PermissionCheck.checkNotificationAllowed;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.AddPaintRecord.PaintRecordAddActicity;
import com.technology.yuyidoctorpad.activity.Message.MessageActivity;
import com.technology.yuyidoctorpad.activity.MyPost.MyPostActivity;
import com.technology.yuyidoctorpad.activity.MyPraise.MyPraiseActivity;
import com.technology.yuyidoctorpad.activity.PaintData.PaintDataListActivity;
import com.technology.yuyidoctorpad.activity.PatientActivity;
import com.technology.yuyidoctorpad.activity.Registion.RegistionActivity;
import com.technology.yuyidoctorpad.activity.Rong.RongConversationList_Activity;
import com.technology.yuyidoctorpad.activity.Rong.UnRongListActivity;
import com.technology.yuyidoctorpad.activity.Settings.SettingActivity;
import com.technology.yuyidoctorpad.activity.UserInfo.UserInfoActivity;
import com.technology.yuyidoctorpad.code.ExitLogin;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.BaseFragment;
import com.technology.yuyidoctorpad.lzhViews.NewsCircle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements IListener{
    myPresenter presenter;
    UserBean us;
    @BindView(R.id.my_userPhoto)RoundImageView my_userPhoto;//头像
    @BindView(R.id.my_userName)TextView my_userName;//姓名
    @BindView(R.id.my_userZC)TextView my_userZC;//职称
    @BindView(R.id.my_hospitalName)TextView my_hospitalName;//医院
    @BindView(R.id.my_userDepartment)TextView my_userDepartment;//科室
    @BindView(R.id.my_newsCircle)NewsCircle my_newsCircle;//消息小红点
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_my, container, false);
        unbinder= ButterKnife.bind(this,vi);
        presenter=new myPresenter();
        presenter.checkNotification(getActivity());//检查通知权限
        presenter.getUserInfo(this);
        presenter.getUnReadMessage(this);
        return vi;
    }
    @OnClick({R.id.my_post,R.id.my_praise,R.id.my_consulting,R.id.my_data,R.id.my_register,R.id.my_addPaintRecord,R.id.my_settings,R.id.my_newslayout,R.id.my_userMsg})
    public void 点击事件(View vi){
        Intent intent=new Intent();
        switch (vi.getId()){
            case R.id.my_newslayout://消息
                my_newsCircle.setVisibility(View.GONE);//隐藏消息圆点
                intent.setClass(getActivity(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.my_userMsg://用户信息
                if (us!=null){
                    intent.setClass(getActivity(), UserInfoActivity.class);
                    intent.putExtra(IntentValue.UserInfo,us);
                    startActivity(intent);
                    }
                else {
                    toast.toast(getActivity(),"没有获取到用户信息！");
                }
                break;
            case R.id.my_post://我的帖子
                intent.setClass(getActivity(), MyPostActivity.class);
                startActivity(intent);
                break;

            case R.id.my_praise://我的点赞
                intent.setClass(getActivity(), MyPraiseActivity.class);
                startActivity(intent);
                break;

            case R.id.my_consulting://患者
                intent.setClass(getActivity(), PatientActivity.class);
                       startActivity(intent);
//                if (us!=null){
//                    if (User.hasvRongPri){
//                        intent.setClass(getActivity(), RongConversationList_Activity.class);
//                        startActivity(intent);
//                    }
//                    else {
//                        intent.setClass(getActivity(), UnRongListActivity.class);
//                        startActivity(intent);
//                    }
//                }
                break;

            case R.id.my_data://查看数据
                if (us!=null){
                    intent.setClass(getActivity(), PaintDataListActivity.class);
                    startActivity(intent);
                }
                else {
                    toast.toast(getActivity(),"没有查询到您注册的医院信息，没有使用该功能！");
                }
                break;

            case R.id.my_register://挂号接收
                if (us!=null){
                    intent.setClass(getActivity(), RegistionActivity.class);
                    startActivity(intent);
                }
                else {
                    toast.toast(getActivity(),"没有查询到您注册的医院信息，没有使用该功能！");
                }
                break;

            case R.id.my_addPaintRecord://病历添加
                if (us!=null){
                    intent.setClass(getActivity(), PaintRecordAddActicity.class);
                    startActivity(intent);
                }
                else {
                    toast.toast(getActivity(),"没有查询到您注册的医院信息，没有使用该功能！");
                }
                break;

            case R.id.my_settings://设置
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onError(String msg,UserBean user) {
        toast.toast(getActivity(),msg);
        if (user!=null){
            initView(user);
        }
    }
    @Override
    public void onSuccess(UserBean user) {
        initView(user);
    }

    @Override
    public void onTokenError() {
        ExitLogin.getInstance().showLogin(getActivity());
    }

    @Override
    public void HaveUnReadMsg(boolean flag) {
        my_newsCircle.setVisibility(flag?View.VISIBLE:View.INVISIBLE);
    }
    private void initView(UserBean user) {
        this.us=user;
        Picasso.with(getActivity()).load(Ip.imagePath+user.getPhysician().getAvatar()).placeholder(R.mipmap.erroruser).error(R.mipmap.erroruser).into(my_userPhoto);
        my_userName.setText(user.getPhysician().getTrueName());
        my_userZC.setText(user.getPhysician().getTitle());
        my_hospitalName.setText(user.getPhysician().getHospitalName());
        my_userDepartment.setText(user.getPhysician().getDepartmentName());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkNotificationAllowed.isNOtificationOpen(getActivity()) == false) {//当用户没有通知栏权限时
            SharedPreferences preferences = getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_APPEND);
            if (preferences.contains("notifi") == false) {//用户第一次点击修改权限弹窗时写入，用于判断是否显示跳转到权限修改到界面（true：用户之前已经进入过修改权限到页面，但不给予通知但权限，false：用户没有进入过）
                showWindowRevampLimit();
            }
        }
    }

    //跳转到修改权限页面到弹窗
    private void showWindowRevampLimit() {
        new AlertDialog.Builder(getActivity()).setTitle("应用通知栏权限被禁止").
                setMessage("无法接收到应用发送的相关通知，需要您手动打开通知权限，是否现在去打开？").setIcon(R.mipmap.logo).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_APPEND).edit().putBoolean("notifi", true).commit();
                        checkNotificationAllowed.goToSet(getActivity());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).show();
    }
}
