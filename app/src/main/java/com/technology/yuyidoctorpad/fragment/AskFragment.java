package com.technology.yuyidoctorpad.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.adapter.CommentAdapter;
import com.technology.yuyidoctorpad.adapter.FirstPageListviewAdapter;
import com.technology.yuyidoctorpad.bean.CommendListBean.Result;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends Fragment implements View.OnClickListener ,UMShareListener{
    private LinearLayout mRecommend_ll, mNew_LL, mHot_ll;

    private TextView mToday_tv;
    private TextView mNew_tv;
    private TextView mHot_tv;
    private View mToday_line;
    private View mNew_line;
    private View mHot_line;
    private FirstPageListviewAdapter mFirstPageAdapter;
    private ListView mMyListview;
    private List<com.technology.yuyidoctorpad.bean.TodayRecommendBean.Result> mList = new ArrayList<>();
    private HttpTools mHttptools;
    private int mStart = 0;
    private int mLimit = 10;
    private View footer;
    private ProgressBar mBar;
    private int mPostion = 0;//推荐，最新，热门每条数据的下标
    //详情
    private ListView mMessageListView;
    private CommentAdapter mCommentAdapter;
    private List<Result> mCommentList = new ArrayList<>();
    private View mMessageFooter, mMessageHeader;
    private TextView mTitle, mMessage, Comment_Num;
    private ProgressBar mMessageBar;
    private EditText mCommend_edit;
    private ImageView mShareImg;
    private int mCommentStart=0,mCommentLimit=10;
    private boolean mMoreFlag=false;//false代表没有点击评论加载更多
    private int flag=0;//0表示推荐1表示最新2表示热门
    private UMImage image;
    private UMImage thumb;
    private UMWeb umWeb;
    private RelativeLayout mRight_Rl,mRight_NoData_Rl;
    private Handler mHttpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3) {//今日推荐,最新，热门
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.TodayRecommendBean.Root) {
                    com.technology.yuyidoctorpad.bean.TodayRecommendBean.Root root = (com.technology.yuyidoctorpad.bean.TodayRecommendBean.Root) o;
                    if (root.getCode().equals("0")) {
                        MyDialog.stopDia();
                        mBar.setVisibility(View.INVISIBLE);
                        List<com.technology.yuyidoctorpad.bean.TodayRecommendBean.Result> list = new ArrayList<>();
                        list = root.getResult();
                        mList.addAll(list);
                        mMyListview.removeFooterView(footer);
                        mFirstPageAdapter.setmList(mList);
                        mFirstPageAdapter.notifyDataSetChanged();
                        if (mList.size() != 0) {
                            mHttptools.getADMessageDetial(mHttpHandler, mList.get(mPostion).getId(),User.token);//获取今日推荐，最新，热门资讯详情
                            mHttptools.getCommendList(mHttpHandler, mList.get(mPostion).getId(), mCommentStart, mCommentLimit);//获取评论列表
                        } else {
                            ToastUtils.myToast(getContext(), "抱歉，没有帖子哦");
                        }
                        if (list.size() < 10) {//隐藏加载更多
                            mMyListview.removeFooterView(footer);
                        } else {
                            mMyListview.addFooterView(footer);
                        }
                    }
                }

            } else if (msg.what == 102) {
                mMyListview.removeFooterView(footer);
                MyDialog.stopDia();
                mBar.setVisibility(View.INVISIBLE);
            } else if (msg.what == 2) {//广告，今日推荐，最新，热门资讯详情
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.AdMessageDetial.Root) {
                    com.technology.yuyidoctorpad.bean.AdMessageDetial.Root root = (com.technology.yuyidoctorpad.bean.AdMessageDetial.Root) o;
                    //分享时需要的图片和内容
                    image = new UMImage(getContext(), UrlTools.BASE + root.getPicture());//设置要分享的图片
                    thumb = new UMImage(getContext(), UrlTools.BASE + root.getPicture());//设置分享图片的缩略图
                    image.setThumb(thumb);//图片设置缩略图
                    image.compressStyle = UMImage.CompressStyle.SCALE;
                    umWeb = new UMWeb("http://www.baidu.com");
                    umWeb.setThumb(thumb);
                    umWeb.setTitle(root.getTitle());
                    umWeb.setDescription(root.getContent());
                    mMessageListView.setSelection(0);
                    mTitle.setText(root.getTitle());
                    mMessage.setText(root.getContent());
                    if (root.getCommentNum() == null) {//评论设值
                        Comment_Num.setText("0");
                    } else {
                        Comment_Num.setText("");
                        Comment_Num.setText(root.getCommentNum() + "");
                    }

                }
            } else if (msg.what == 101) {
                ToastUtils.myToast(getContext(), "数据错误");
            } else if (msg.what == 4) {//评论列表
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.CommendListBean.Root) {
                    com.technology.yuyidoctorpad.bean.CommendListBean.Root root = (com.technology.yuyidoctorpad.bean.CommendListBean.Root) o;
                    if (root.getCode().equals("0")) {
                        List<Result> list = new ArrayList<>();
                        list = root.getResult();
                        if (mMoreFlag){
                            mCommentList.addAll(list);
                        }else {
                            mCommentList.clear();
                            mCommentList.addAll(list);
                        }

                        mCommentAdapter.notifyDataSetChanged();
                        mMessageListView.removeFooterView(mMessageFooter);

                        if (list.size() == 10) {
                            mMessageListView.addFooterView(mMessageFooter);
                            mMessageBar.setVisibility(View.GONE);
                        } else {
                            mMessageListView.removeFooterView(mMessageFooter);
                        }
                    }
                }
            } else if (msg.what == 103) {
                ToastUtils.myToast(getContext(), "获取评论列表失败");
                mMessageListView.removeFooterView(mMessageFooter);
            }else if (msg.what == 5) {//提交评论返回结果
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.SubmitComment.Root) {
                    com.technology.yuyidoctorpad.bean.SubmitComment.Root rootSubmit = (com.technology.yuyidoctorpad.bean.SubmitComment.Root) o;
                    if (rootSubmit.getCode().equals("0")) {
                        mCommend_edit.setText("");
                        mCommentStart = 0;
                        mHttptools.getCommendList(mHttpHandler, mList.get(mPostion).getId(), mCommentStart, mCommentLimit);//获取评论列表
                        Comment_Num.setText(Integer.valueOf(Comment_Num.getText().toString()) + 1 + "");
                        ToastUtils.myToast(getContext(), "评论成功");
                    } else {
                        ToastUtils.myToast(getContext(), "评论失败");
                    }
                }
            } else if (msg.what == 104) {
                ToastUtils.myToast(getContext(), "提交评论失败");
            }
        }
    };


    public AskFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getTodayRecommend(mHttpHandler, mStart, mLimit);//今日推荐
        //今日推荐，最新，热门
        mRecommend_ll = (LinearLayout) view.findViewById(R.id.recommend_ll);
        mNew_LL = (LinearLayout) view.findViewById(R.id.new_ll);
        mHot_ll = (LinearLayout) view.findViewById(R.id.hot_ll);
        mRecommend_ll.setOnClickListener(this);
        mNew_LL.setOnClickListener(this);
        mHot_ll.setOnClickListener(this);

        mToday_tv = (TextView) view.findViewById(R.id.today_tv);
        mNew_tv = (TextView) view.findViewById(R.id.new_tv);
        mHot_tv = (TextView) view.findViewById(R.id.hot_tv);
        mToday_line = view.findViewById(R.id.today_line);
        mNew_line = view.findViewById(R.id.new_line);
        mHot_line = view.findViewById(R.id.hot_line);
        showTodayLine();

        mMyListview = view.findViewById(R.id.information_listview);
        mFirstPageAdapter = new FirstPageListviewAdapter(getContext(), mList);
        mMyListview.setAdapter(mFirstPageAdapter);
        footer = LayoutInflater.from(getContext()).inflate(R.layout.footerview, null);
        mBar = footer.findViewById(R.id.loading_progressBar);
        mMyListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==mList.size()){//加载更多
                    mStart+=10;
                    if (flag==0){//推荐
                        mHttptools.getTodayRecommend(mHttpHandler, mStart, mLimit);//今日推荐
                    }else if (flag==1){//最新
                        mHttptools.getNew(mHttpHandler, mStart, mLimit);
                    }else {//热门
                        mHttptools.getHot(mHttpHandler, mStart, mLimit);
                    }
                }else {//点击某条数据
                    mCommentStart=0;
                    mPostion=i;
                    mMoreFlag=false;
                    mHttptools.getADMessageDetial(mHttpHandler, mList.get(mPostion).getId(),User.token);//获取今日推荐，最新，热门资讯详情
                    mHttptools.getCommendList(mHttpHandler, mList.get(mPostion).getId(), mCommentStart, mCommentLimit);//获取评论列表

                }
            }
        });

        //详情
        Comment_Num = view.findViewById(R.id.comment_num_tv);
        mMessageListView = view.findViewById(R.id.message_listview);
        mCommentAdapter = new CommentAdapter(getContext(), mCommentList);
        mMessageListView.setAdapter(mCommentAdapter);
        mMessageFooter = LayoutInflater.from(getContext()).inflate(R.layout.footerview, null);
        mMessageFooter.setOnClickListener(this);
        mMessageBar = mMessageFooter.findViewById(R.id.loading_progressBar);
        mMessageHeader = LayoutInflater.from(getContext()).inflate(R.layout.information_header, null);
        mTitle = mMessageHeader.findViewById(R.id.information_title);
        mMessage = mMessageHeader.findViewById(R.id.information_mess);
        mMessageListView.addHeaderView(mMessageHeader);
        mCommend_edit=view.findViewById(R.id.my_comment_edit);
        mShareImg=view.findViewById(R.id.share_img);
        mShareImg.setOnClickListener(this);
        //发送按钮
        mCommend_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getEditContent().equals("")) {
                        ToastUtils.myToast(getContext(), "请输入评论内容");
                    } else {
                        if (!"".equals(User.tele)) {
                            mHttptools.submitCommentContent(mHttpHandler, Long.valueOf(User.tele),mList.get(mPostion).getId(), getEditContent());
                            mCommend_edit.setText("");
                            //隐藏软键盘
                            ((InputMethodManager)getContext(). getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        } else {
                            ToastUtils.myToast(getContext(), "请登录您的账号");
                        }
                    }
                    return true;
                }
                return false;
            }

        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mRecommend_ll.getId()) {//推荐
            showTodayLine();
            mStart = 0;
            mPostion = 0;//每次点击下标回归0，请求的是列表中的第一条数据
            flag=0;
            mMoreFlag=false;
            mCommentStart=0;
            MyDialog.showDialog(this.getActivity());
            mList.clear();
            mFirstPageAdapter.notifyDataSetChanged();
            mHttptools.getTodayRecommend(mHttpHandler, mStart, mLimit);//今日推荐
        } else if (id == mNew_LL.getId()) {//最新
            showNewLine();
            mStart = 0;
            mPostion = 0;//每次点击下标回归0，请求的是列表中的第一条数据
            flag=1;
            mMoreFlag=false;
            mCommentStart=0;
            MyDialog.showDialog(this.getActivity());
            mList.clear();
            mFirstPageAdapter.notifyDataSetChanged();
            mHttptools.getNew(mHttpHandler, mStart, mLimit);

        } else if (id == mHot_ll.getId()) {//热门
            showHotLine();
            mStart = 0;
            mPostion = 0;//每次点击下标回归0，请求的是列表中的第一条数据
            flag=2;
            mMoreFlag=false;
            mCommentStart=0;
            MyDialog.showDialog(this.getActivity());
            mList.clear();
            mFirstPageAdapter.notifyDataSetChanged();
            mHttptools.getHot(mHttpHandler, mStart, mLimit);
        }else if (id==mMessageFooter.getId()){//评论加载更多
            mMoreFlag=true;
            mCommentStart+=10;
            mHttptools.getCommendList(mHttpHandler, mList.get(mPostion).getId(), mCommentStart, mCommentLimit);//获取评论列表

        }else if (id==mShareImg.getId()){
           init();
        }
    }
    public static final int REQUEST_CODE_ASK_READ_PHONE = 123;
    public void init() {
        //sdk版本>=23时，
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //如果读取电话权限没有授权
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
                //注意 ：如果是在fragment中申请权限，不要使用ActivityCompat.requestPermissions，
                //直接使用requestPermissions （new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_READ_PHONE）
                //否则不会调用onRequestPermissionsResult（）方法。
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_READ_PHONE);
                return;
                //如果已经授权，执行业务逻辑
            } else {
                if (image != null && thumb != null && umWeb != null) {
                    new ShareAction(getActivity())
                            .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withMedia(umWeb)
                            .setCallback(this)
                            .open();
                }

            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            if (image != null && thumb != null && umWeb != null) {
                new ShareAction(getActivity())
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(umWeb)
                        .setCallback(this)
                        .open();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_READ_PHONE:
                //点击了允许，授权成功
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (image != null && thumb != null && umWeb != null) {
                        new ShareAction(getActivity())
                                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_CIRCLE)
                                .withMedia(umWeb)
                                .setCallback(this)
                                .open();
                    }
                    //点击了拒绝，授权失败
                } else {
                    // Permission Denied
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //显示今日推荐
    public void showTodayLine() {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mToday_tv.measure(spec, spec);
        int measuredWidthTicketNum = mToday_tv.getMeasuredWidth();

        ViewGroup.LayoutParams params = mToday_line.getLayoutParams();
        params.width = measuredWidthTicketNum;
        mToday_line.setLayoutParams(params);
        mToday_line.setVisibility(View.VISIBLE);
        mHot_line.setVisibility(View.GONE);
        mNew_line.setVisibility(View.GONE);

        mToday_tv.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.color_333333));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mToday_tv.setTypeface(font);

        mNew_tv.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.color_333333));
        Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mNew_tv.setTypeface(font2);

        mHot_tv.setTextColor(ContextCompat.getColor(this.getActivity(), R.color.color_333333));
        Typeface font3 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mHot_tv.setTypeface(font3);
    }

    //显示最新
    public void showNewLine() {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mNew_tv.measure(spec, spec);
        int measuredWidthTicketNum = mNew_tv.getMeasuredWidth();

        ViewGroup.LayoutParams params = mNew_line.getLayoutParams();
        params.width = measuredWidthTicketNum;
        mNew_line.setLayoutParams(params);

        mNew_line.setVisibility(View.VISIBLE);
        mHot_line.setVisibility(View.GONE);
        mToday_line.setVisibility(View.GONE);

        mToday_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.color_333333));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mToday_tv.setTypeface(font);

        mNew_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.color_333333));
        Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mNew_tv.setTypeface(font2);

        mHot_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.color_333333));
        Typeface font3 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mHot_tv.setTypeface(font3);
    }

    //显示热门
    public void showHotLine() {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mHot_tv.measure(spec, spec);
        int measuredWidthTicketNum = mHot_tv.getMeasuredWidth();

        ViewGroup.LayoutParams params = mHot_line.getLayoutParams();
        params.width = measuredWidthTicketNum;
        mHot_line.setLayoutParams(params);

        mHot_line.setVisibility(View.VISIBLE);
        mNew_line.setVisibility(View.GONE);
        mToday_line.setVisibility(View.GONE);

        mToday_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.color_333333));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mToday_tv.setTypeface(font);

        mNew_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.color_333333));
        Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mNew_tv.setTypeface(font2);
        mHot_tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.color_333333));
        Typeface font3 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mHot_tv.setTypeface(font3);
    }

    /**
     * 获取输入框的内容
     *
     * @return
     */
    public String getEditContent() {
        String content = mCommend_edit.getText().toString().trim();
        if (content.equals("")) {
            return "";
        }
        return content;
    }


    //分享回掉接口
    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.e("开始",share_media.toString());
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.e("结果",share_media.toString());
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.e("错误",share_media.toString()+throwable.toString());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.e("取消",share_media.toString());
    }
}
