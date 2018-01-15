package com.technology.yuyidoctorpad.activity.Login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.Net.OkUtils;
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

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends MyActivity implements ILogin {
    int successSMScount = 0;//获取验证码成功的次数（大于5次时，该用户违法，不能再继续获取验证码，防止用户过多的获取）
    boolean SMS = true;
    LoginType type = LoginType.DOCTOR;//当前登录方式
    LoginPresenter presenter;
    @BindView(R.id.loginTitle)
    TextView loginTitle;//登录标题
    @BindView(R.id.loginTitleSelect)
    TextView loginTitleSelect;//切换登录的按钮
    @BindView(R.id.my_userlogin_edit_name)
    EditText my_userlogin_edit_name;//手机号输入框
    @BindView(R.id.my_userlogin_edit_smdCode)
    EditText my_userlogin_edit_smdCode;//验证码输入框
    @BindView(R.id.my_userlogin_getSMScode)
    TextView my_userlogin_getSMScode;//获取验证码按钮


    private EditText mMyStatus_Num;//动态验证码
    private ImageView mMyStatus_Img;//动态验证码图片
    private long mCurrentMillis;//当前毫秒数
    private String myCooike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_login);
        presenter = new LoginPresenter();
        mCurrentMillis = System.currentTimeMillis();
        mMyStatus_Num = findViewById(R.id.my_status_num_edit);
        mMyStatus_Img = findViewById(R.id.my_status_num_img);
        getDynamicNumAndCookie();//获取动态验证码以及cookie
        //重新获取动态验证码
        mMyStatus_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentMillis = System.currentTimeMillis();
                getDynamicNumAndCookie();//获取动态验证码以及cookie
            }
        });


    }

    @OnClick({R.id.loginTitleSelect, R.id.my_userlogin_logninButton, R.id.my_userlogin_getSMScode})
    public void click(View vi) {
        switch (vi.getId()) {
            case R.id.loginTitleSelect://切换登录
                loginTitleSelect.setText(LoginType.getLoginTypeString(type));
                type = LoginType.getOppositeType(type);
                loginTitle.setText(LoginType.getLoginTypeString(type));
                my_userlogin_edit_smdCode.setText("");
                //验证码获取重置
                presenter.stopTimer();
                my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_loginbutton));
                my_userlogin_getSMScode.setClickable(true);
                my_userlogin_getSMScode.setText("发送验证码");

                break;
            case R.id.my_userlogin_logninButton://登录
                presenter.onLogin(my_userlogin_edit_name.getText().toString(), my_userlogin_edit_smdCode.getText().toString(), this, type);
                break;
            case R.id.my_userlogin_getSMScode://获取验证码按钮
                if (SMS == false) {
                    toast.toast(this, "您请求验证码的次数过多，请退出后重试！");
                    return;
                }
                my_userlogin_getSMScode.setClickable(false);
                my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_unclick));
                presenter.onGetSmsCode(my_userlogin_edit_name.getText().toString(), String.valueOf(mCurrentMillis), mMyStatus_Num.getText().toString(), myCooike, this, type);
                //Log.e("传递中的myCooike=", myCooike);
                break;
        }
    }

    @Override
    public void onError(String msg) {
        toast.toast(this, msg);
    }

    //医生登录成功
    @Override
    public void onLoginSuccess(BeanDoc beanDoc) {
        JpRegister.getInstance().setAlias(this, beanDoc.getPhysician().getTelephone() + "");
        User.saveLogin(this, beanDoc.getResult(), beanDoc.getPhysician().getTelephone() + "", beanDoc.getPhysician().getId() + "", User.LoginTP.DOC);
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
        User.token = bean.getResult();//保存token
        int state = bean.getState();
        switch (state) {
            case 0:
                startActivity(new Intent(LoginActivity.this, WriteHospitalMessageActivity.class));
                finish();
                break;
            case 1://通过审核的（已经注册过的）
                toast.toast(this, "通过审核");
                User.saveHospitalId(this, bean.getHospitalId());//保存医院id
                User.saveLogin(this, bean.getResult(), "", "", User.LoginTP.HOS);
                startActivity(new Intent(LoginActivity.this, HospitalHomePageActivity.class));
                finish();
                break;
            case 2://审核中
                showDialog("资料审核中", "您上次提交的资料还在审核中，是否重新填写审核信息？", 2);
                break;
            case 3://未通过审核
                showDialog("审核未通过", "您上次提交的资料未通过审核，是否重新填写审核信息？", 3);
                break;
        }
    }

    public void showDialog(String title, String message, final int tp) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle(title).setIcon(R.mipmap.logo).setMessage(message)
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
                        finish();
                    }
                }).show();
    }

    @Override
    public void getSMSCodeSuccess() {
        successSMScount++;
        if (successSMScount == 5) {
            SMS = false;
        }
        presenter.onTimer(this);
    }

    @Override
    public void getSMSCodeError(String msg) {
        toast.toast(this, msg);
        my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_loginbutton));
        my_userlogin_getSMScode.setClickable(true);
        my_userlogin_getSMScode.setText("发送验证码");
    }

    @Override
    public void getTimeOut(int count) {
        if (my_userlogin_getSMScode == null) {
            return;
        }
        if (count > 0) {
            my_userlogin_getSMScode.setText("剩余 " + count + "s");
        } else if (count == 0) {
            my_userlogin_getSMScode.setText("发送验证码");
        } else {
            presenter.stopTimer();
            my_userlogin_getSMScode.setClickable(true);
            my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_loginbutton));
        }
    }


    /**
     * 获取动态验证码以及cookie
     */
    public void getDynamicNumAndCookie() {
        Call call = OkUtils.getCall(UrlTools.BASE + UrlTools.URL_GET_DYNAMIC_NUM + "ts=" + mCurrentMillis, null, 0);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("获取动态验证码错误", e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    myCooike = response.headers().get("Set-Cookie");
                    // Log.e("动态验证码myCooike=", myCooike);
                    InputStream inputStream = response.body().byteStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap != null) {
                                mMyStatus_Img.setImageBitmap(bitmap);
                            } else {
                                mMyStatus_Img.setBackgroundResource(R.color.color_cecece);
                            }

                        }
                    });
                } else {
                    Log.e("onResponse--", "获取动态验证码错误");
                }

            }
        });
    }
}
