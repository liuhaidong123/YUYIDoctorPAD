package com.technology.yuyidoctorpad.activity.MyPost;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.MyPost.Fragment.PostInfoFragment;
import com.technology.yuyidoctorpad.activity.SelectImgActivity;
import com.technology.yuyidoctorpad.adapter.PostCardAdapter;
import com.technology.yuyidoctorpad.lhdUtils.ImgUitls;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.PopupSettings;

import net.tsz.afinal.http.AjaxParams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/11/7.
 */

public class MyPostPresenter implements View.OnClickListener{
    private HttpTools mHttptools;
    IListener listener;
    Activity ac;
    MyPostModel model;
    PostInfoFragment myPost_fragment;
    PopupWindow pop;
    PostCardAdapter mCardImgAda;
    TextView mPostBtn;
    TextView  mTitle_Edit;
    TextView  mContent_Edit;
    GridView mImg_GridView;
    View   mPopView;
    private static final int READ_WRITE_PERMISS_CODE = 123;
    public List<String> mCardListImg = new ArrayList<>();
    private AlertDialog.Builder builder;
    private AlertDialog mAlert;
    private int mPosition = -1;
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    listener.onMakeCommentError("网络异常！");
                    break;
                case 17:
                    try {
                        Object o = msg.obj;
                        MyDialog.stopDia();
                        mPostBtn.setClickable(true);
                        if (o != null && o instanceof com.technology.yuyidoctorpad.bean.CirclePostCard.Root) {
                            com.technology.yuyidoctorpad.bean.CirclePostCard.Root root = (com.technology.yuyidoctorpad.bean.CirclePostCard.Root) o;
                            if (root != null) {
                                if ("0".equals(root.getCode())) {
                                    listener.onMakCommentSuccess();
                                } else if ("-1".equals(root.getCode())) {
                                    listener.onMakeCommentError("您没有发帖的权限！");
                                    pop.dismiss();
                                } else if ("10000".equals(root.getCode())) {
                                    listener.onMakeCommentError("发帖失败，请重新登陆！");
                                } else {
                                    listener.onMakeCommentError("发帖失败！");
                                }

                            } else {
                                mPostBtn.setClickable(true);
                                ToastUtils.myToast(ac, "发帖解析错误");
                            }
                        }
                        else {
                            listener.onMakeCommentError("发帖失败！");
                        }
                    } catch (Exception e) {
                        listener.onMakeCommentError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public void initFragment(FragmentManager manager,int resId){
        myPost_fragment=new PostInfoFragment();
        manager.beginTransaction().add(resId,myPost_fragment).show(myPost_fragment).commit();
    }
    public void setPraise(boolean isLike,int pos){
        myPost_fragment.setPraise(isLike,pos);
    }
    //获取我的帖子
    public void getMyPost(int st,int limit,IListener listener){
        if (model==null){
            model=new MyPostModel();
        }
        model.getMyPost(st,limit,listener);
    }
    //请求帖子详情
    public void setPostId(int pos,String id){
        myPost_fragment.setPostId(pos,id);
    }

    public void showWindow(Activity acs, View parent, IListener listener) {
        this.ac=acs;
        this.listener=listener;
        if (pop==null){
            pop=new PopupWindow();
        }
        //发帖弹框
        mPopView = LayoutInflater.from(ac).inflate(R.layout.post_card_layout, null);
        mPostBtn = mPopView.findViewById(R.id.post_btn);
        mPostBtn.setOnClickListener(this);
         mTitle_Edit = mPopView.findViewById(R.id.title_edit);
         mContent_Edit = mPopView.findViewById(R.id.content_edit);
            mImg_GridView = mPopView.findViewById(R.id.img_gridview);

        mCardImgAda = new PostCardAdapter(ac, mCardListImg);
        mImg_GridView.setAdapter(mCardImgAda);
        mImg_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCardListImg.size() == 0) {
                    //跳转相册
                    checkPermiss(ac);
                } else {
                    if (mCardListImg.size() == 6) {
                        if (i != mCardListImg.size()) {//是否删除图片
                            mPosition = i;
                            mAlert.show();
                        } else {
                            Toast.makeText(ac, "亲，最多选择6张图片哦", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (i == mCardListImg.size()) {
                            //跳转相册
                            checkPermiss(ac);
                        } else {//是否删除图片
                            mPosition = i;
                            mAlert.show();
                        }

                    }

                }
            }
        });

        PopupSettings.getInstance().showWindowCenter(ac,pop,mPopView,parent);
    }

    public void setImgList(){
        mCardImgAda.setmList(mCardListImg);
        mCardImgAda.notifyDataSetChanged();
    }
    //判断选择图片是的权限
    public void checkPermiss(Activity ac) {
        if (Build.VERSION.SDK_INT >= 23) {
            int permiss = ContextCompat.checkSelfPermission(ac, Manifest.permission.READ_EXTERNAL_STORAGE);

            //如果没有授权
            if (permiss != PackageManager.PERMISSION_GRANTED) {
                ac.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_WRITE_PERMISS_CODE);
                return;
                //如果已经授权，执行业务逻辑
            } else {
                startSelectImgActivity(ac);
            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            startSelectImgActivity(ac);
        }
    }

    public void startSelectImgActivity(Activity ac) {
        Intent intent = new Intent(ac, SelectImgActivity.class);
        //intent.putExtra("num", 3);
        ac.startActivityForResult(intent, 100);
    }


    @Override
    public void onClick(View view) {
        if (!Empty.notEmpty(mTitle_Edit.getText().toString())) {
            listener.onMakeCommentError("标题不能为空！");
        } else {
            if (!Empty.notEmpty(mContent_Edit.getText().toString())) {
                listener.onMakeCommentError("内容不能为空！");
            } else {
                mHttptools=HttpTools.getHttpToolsInstance();
                mPostBtn.setClickable(false);
                MyDialog.showDialog(ac);
                File[] files = new File[mCardListImg.size()];
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put("title", mTitle_Edit.getText().toString());
                ajaxParams.put("content",mContent_Edit.getText().toString());
                ajaxParams.put("token", User.token);
                if (mCardListImg.size() == 0) {
                    Log.e("没有上传图片", "---");
                } else {
                    for (int k = 0; k < mCardListImg.size(); k++) {
                        try {
                            files[k] = transImage(ac,mCardListImg.get(k), ImgUitls.getWith(ac), ImgUitls.getHeight(ac), 90, "图片" + k);
                            pop.dismiss();
                            ajaxParams.put("图片" + k, transImage(ac,mCardListImg.get(k), ImgUitls.getWith(ac), ImgUitls.getHeight(ac), 90, "图片" + k));
                        } catch (FileNotFoundException e) {
                            listener.onMakeCommentError("无法获取到图片！");
                            e.printStackTrace();
                        }
                    }
                    mHttptools.postCirclrCard(mHandler, User.token, ajaxParams);
                }
            }

        }
    }


    public File transImage(Activity acs,String pathName, int width, int height, int quality, String fileName) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(pathName);
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();

            // 缩放图片的尺寸
            float scaleWidth = (float) width / bitmapWidth - 0.1f;
            float scaleHeight = (float) height / bitmapHeight - 0.1f;
            Log.e("bitmapWidth", bitmapWidth + "");
            Log.e("bitmapHeight", bitmapHeight + "");
            Log.e("scaleWidth", scaleWidth + "");
            Log.e("scaleHeight", scaleHeight + "");
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 产生缩放后的Bitmap对象
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
            File file = null;
            //存储媒体已经挂载，并且挂载点可读/写
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//可写
                //保存
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName + ".jpg");
                Log.e("图片名称：", file.getName());
                Log.e("图片文件夹名称：", Environment.DIRECTORY_PICTURES);

            } else {
                file = new File(acs.getFilesDir(), fileName + ".jpg");
                Log.e("图片名称：", fileName + ".jpg");
                Log.e("图片文件夹名称：",acs.getFilesDir().toString());

            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)) {
                bos.flush();
                bos.close();
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();//记得释放资源，否则会内存溢出
            }
            if (!resizeBitmap.isRecycled()) {
                resizeBitmap.recycle();
            }
            Log.e("--file-大小----", file.length() / 1024 + "");
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
