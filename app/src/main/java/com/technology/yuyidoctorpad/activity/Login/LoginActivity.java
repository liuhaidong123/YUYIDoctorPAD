package com.technology.yuyidoctorpad.activity.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.HospitalHomePageActivity;
import com.technology.yuyidoctorpad.activity.Login.Bean.BeanDoc;
import com.technology.yuyidoctorpad.activity.Login.Bean.BeanHosLogin;
import com.technology.yuyidoctorpad.activity.MainActivity;
import com.technology.yuyidoctorpad.activity.WriteHospitalMessageActivity;
import com.technology.yuyidoctorpad.lzhUtils.JpRegister;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends MyActivity implements ILogin{
    int successSMScount=0;//获取验证码成功的次数（大于5次时，该用户违法，不能再继续获取验证码，防止用户过多的获取）
    boolean SMS=true;
    LoginType type=LoginType.DOCTOR;//当前登录方式
    LoginPresenter presenter;
    @BindView(R.id.loginTitle)TextView loginTitle;//登录标题
    @BindView(R.id.loginTitleSelect)TextView loginTitleSelect;//切换登录的按钮
    @BindView(R.id.my_userlogin_edit_name)EditText my_userlogin_edit_name;//手机号输入框
    @BindView(R.id.my_userlogin_edit_smdCode)EditText my_userlogin_edit_smdCode;//验证码输入框
    @BindView(R.id.my_userlogin_getSMScode)TextView my_userlogin_getSMScode;//获取验证码按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_login);
        presenter=new LoginPresenter();
    }
    @OnClick({R.id.loginTitleSelect,R.id.my_userlogin_logninButton,R.id.my_userlogin_getSMScode})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.loginTitleSelect://切换登录
                loginTitleSelect.setText(LoginType.getLoginTypeString(type));
                type=LoginType.getOppositeType(type);
                loginTitle.setText(LoginType.getLoginTypeString(type));
                my_userlogin_edit_smdCode.setText("");
                //验证码获取重置
                presenter.stopTimer();
                my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_loginbutton));
                my_userlogin_getSMScode.setClickable(true);
                my_userlogin_getSMScode.setText("发送验证码");

                break;
            case R.id.my_userlogin_logninButton://登录
                presenter.onLogin(my_userlogin_edit_name.getText().toString(),my_userlogin_edit_smdCode.getText().toString(),this,type);
                break;
            case R.id.my_userlogin_getSMScode://获取验证码按钮
                if (SMS==false){
                    toast.toast(this,"您请求验证码的次数过多，请退出后重试！");
                    return;
                }
                my_userlogin_getSMScode.setClickable(false);
                my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_unclick));
                presenter.onGetSmsCode(my_userlogin_edit_name.getText().toString(),this,type);
                break;
        }
    }

    @Override
    public void onError(String msg) {
        toast.toast(this,msg);
    }
    //医生登录成功
    @Override
    public void onLoginSuccess( BeanDoc beanDoc) {
        JpRegister.getInstance().setAlias(this,beanDoc.getPhysician().getTelephone()+"");
        User.saveLogin(this,beanDoc.getResult(),beanDoc.getPhysician().getTelephone()+"",beanDoc.getPhysician().getId()+"",User.LoginTP.DOC);
        presenter.stopTimer();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    //医院登录成功
    @Override
    public void onHospitalLoginSuccess(BeanHosLogin bean) {
//        tate    int      0表示从未注册过
//        1表示资料审核通过
//        2表示资料再审核中
//        3表示审核未通过
        User.token=bean.getResult();//保存token
        int state= bean.getState();
            switch (state){
                case 0:
                    startActivity(new Intent(LoginActivity.this, WriteHospitalMessageActivity.class));
                    break;
                case 1://通过审核的（已经注册过的）
                    toast.toast(this,"通过审核");
                    User.saveHospitalId(this,bean.getHospitalId());//保存医院id
                    User.saveLogin(this,bean.getResult(),"","",User.LoginTP.HOS);
                    startActivity(new Intent(LoginActivity.this, HospitalHomePageActivity.class));
                    break;
                case 2://审核中
                    showDialog("资料审核中","您上次提交的资料还在审核中，是否重新填写审核信息？",2);
                    break;
                case 3://未通过审核
                    showDialog("审核未通过","您上次提交的资料未通过审核，是否重新填写审核信息？",3);
                    break;
            }
    }
    public void showDialog(String title,String message, final int tp){
        AlertDialog dialog=new AlertDialog.Builder(this).setTitle(title).setIcon(R.mipmap.logo).setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                       startActivity(new Intent(LoginActivity.this, WriteHospitalMessageActivity.class));
                    }
                }).show();
    }
    @Override
    public void getSMSCodeSuccess(){
        successSMScount++;
        if (successSMScount==5){
            SMS=false;
        }
        presenter.onTimer(this);
    }

    @Override
    public void getSMSCodeError(String msg) {
        toast.toast(this,msg);
        my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_loginbutton));
        my_userlogin_getSMScode.setClickable(true);
        my_userlogin_getSMScode.setText("发送验证码");
    }

    @Override
    public void getTimeOut(int count) {
        if (my_userlogin_getSMScode==null){
            return;
        }
        if (count>0){
            my_userlogin_getSMScode.setText("剩余 "+count+"s");
        }
        else if (count==0){
            my_userlogin_getSMScode.setText("发送验证码");
        }
        else {
            presenter.stopTimer();
            my_userlogin_getSMScode.setClickable(true);
            my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_loginbutton));
            }
    }
}
