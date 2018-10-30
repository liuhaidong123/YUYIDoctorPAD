package com.technology.yuyidoctorpad.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.constant.WBConstants;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.Message.MessageActivity;
import com.technology.yuyidoctorpad.activity.SelectImgActivity;
import com.technology.yuyidoctorpad.activity.WriteHospitalMessageActivity;
import com.technology.yuyidoctorpad.adapter.CardMessageCommentAdapter;
import com.technology.yuyidoctorpad.adapter.CardMessageImgAdapter;
import com.technology.yuyidoctorpad.adapter.CircleHotAdpater;
import com.technology.yuyidoctorpad.adapter.CircleNewAdapter;
import com.technology.yuyidoctorpad.adapter.CircleSelectAda;
import com.technology.yuyidoctorpad.adapter.PostCardAdapter;
import com.technology.yuyidoctorpad.bean.CircleBean.Root;
import com.technology.yuyidoctorpad.bean.CircleBean.Rows;
import com.technology.yuyidoctorpad.bean.CircleBean.SelectBean.Result;
import com.technology.yuyidoctorpad.bean.CircleMessageBean.CommentList;
import com.technology.yuyidoctorpad.fragment.myFragment.IListener;
import com.technology.yuyidoctorpad.fragment.myFragment.myModel;
import com.technology.yuyidoctorpad.lhdUtils.ImgUitls;
import com.technology.yuyidoctorpad.lhdUtils.InformationListView;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lhdUtils.PostCardPopUtils;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lhdUtils.TimeUtils;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

import net.tsz.afinal.http.AjaxParams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment extends Fragment implements View.OnClickListener {
    private ImageView mPost_Img, mSmallRed_Img;
    private FrameLayout mMessage_Btn;
    private TextView mHot_tv, mSelect_tv, mNew_tv;//热门，精选，最新
    private View mHot_line, mSelect_line, mNew_line, mMline, equip_line;//线
    private String mColorSelect = "#1ebeec";
    private String mNoSelectColor = "#6a6a6a";
    private ProgressBar mBar;
    private View mFooter;
    private ListView mListview;
    private CircleHotAdpater mAdapter;
    private List<Rows> mList = new ArrayList<>();//最新集合
    private CircleSelectAda mSelectAdapter;//精选适配器
    private List<Result> mSelectList = new ArrayList<>();//精选集合
    private CircleNewAdapter mNewAdpter;
    private List<Rows> mNewList = new ArrayList<>();//，最新集合
    private HttpTools mHttptools;
    private int mStart = 0;
    private int mLimit = 10;
    private int mFlag = 0;//0.热门1.精选2.最新的标志
    //详情
    private InformationListView mImgListView, mCommentListView;
    private CardMessageImgAdapter mImgAdapter;
    private String[] mStrImg = new String[0];//图片集合
    private CommAdapter commAdapter;
    //private CardMessageCommentAdapter mCommentAdapter;
    private List<CommentList> mCommentList = new ArrayList<>();//评论列表集合
    private int mCommPosition;
    private RelativeLayout mScrollRl;
    private int mNewPostion = 0, mSelectPostion = 0, mHotPostion = 0;//点击热门某条数据下标，点击精选某条数据下标，点击最新某条数据下标，
    private RoundImageView mHead_img;
    private TextView mName;
    private TextView mTime;
    private TextView mPraise_num;
    private ImageView mPraise_img;
    private TextView mTitle;
    private TextView mContent;
    private TextView mComment_allNum;

    private int mStart3 = 0;
    private int mLimit3 = 10;
    private RelativeLayout mMany_Box3;//加载更多
    private ProgressBar mBar3;
    //评论框
    private EditText mEdit;
    private RelativeLayout mComment_rl, mRightNoData_rl, card_comment_box;
    //RelativeLayout again_login_rl;
    //发帖弹框
    private View mPopView;
    private TextView mPostBtn;
    private EditText mTitle_Edit, mContent_Edit;
    private GridView mImg_GridView;
    private PostCardAdapter mCardImgAda;
    private List<String> mCardListImg = new ArrayList<>();
    private RelativeLayout mPopParentView;
    private PopupWindow popupWindow;
    private static final int READ_WRITE_PERMISS_CODE = 123;
    private AlertDialog.Builder builder;
    private AlertDialog mAlert;
    private View mAlertView;
    private TextView mSure, mCancle;
    private int mPosition = -1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 7) {//学术圈热门
                MyDialog.stopDia();
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mBar.setVisibility(View.INVISIBLE);
                    Root root = (Root) o;
                    List<Rows> list = new ArrayList<>();
                    list = root.getRows();
                    mListview.removeFooterView(mFooter);
                    mList.addAll(list);
                    mListview.setAdapter(mAdapter);
                    //请求热门第一条数据详情
                    if (mList.size() != 0) {
                        card_comment_box.setVisibility(View.VISIBLE);
                        equip_line.setVisibility(View.VISIBLE);
                        mMline.setBackgroundResource(R.color.color_cccccc);
                        mScrollRl.setVisibility(View.VISIBLE);
                        mComment_rl.setVisibility(View.VISIBLE);
                        mRightNoData_rl.setVisibility(View.GONE);
                        mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mList.get(mHotPostion).getId());
                    } else {
                        //again_login_rl.setVisibility(View.GONE);
                        card_comment_box.setVisibility(View.GONE);
                        equip_line.setVisibility(View.GONE);
                        mMline.setBackgroundResource(R.color.color_ffffff);
                        mScrollRl.setVisibility(View.GONE);
                        mComment_rl.setVisibility(View.GONE);
                        mRightNoData_rl.setVisibility(View.VISIBLE);
                        mHotPostion = -1;
                        ToastUtils.myToast(getContext(), "数据走丢了");
                    }

                    mListview.setVisibility(View.VISIBLE);
                    mHot_tv.setClickable(true);
                    if (list.size() != 10) {
                        mListview.removeFooterView(mFooter);
                    } else {
                        mListview.addFooterView(mFooter);
                    }
                }

            } else if (msg.what == 106) {
               // again_login_rl.setVisibility(View.VISIBLE);
                ToastUtils.myToast(getContext(), "请检查网络");
                MyDialog.stopDia();
                mHot_tv.setClickable(true);
                mListview.removeFooterView(mFooter);
                mBar.setVisibility(View.INVISIBLE);

            } else if (msg.what == 8) {//学术圈精选
                Object o = msg.obj;
                MyDialog.stopDia();
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.CircleBean.SelectBean.Root) {
                    com.technology.yuyidoctorpad.bean.CircleBean.SelectBean.Root root = (com.technology.yuyidoctorpad.bean.CircleBean.SelectBean.Root) o;
                    if (root.getCode().equals("0")) {
                        mBar.setVisibility(View.INVISIBLE);
                        List<Result> list = new ArrayList<>();
                        list = root.getResult();
                        mSelectList.addAll(list);
                        mListview.setAdapter(mSelectAdapter);
                        mListview.setVisibility(View.VISIBLE);
                        mSelect_tv.setClickable(true);
                        mListview.removeFooterView(mFooter);
                        //请求精选第一条数据详情
                        if (mSelectList.size() != 0) {
                            card_comment_box.setVisibility(View.VISIBLE);
                            equip_line.setVisibility(View.VISIBLE);
                            mMline.setBackgroundResource(R.color.color_cccccc);
                            mScrollRl.setVisibility(View.VISIBLE);
                            mComment_rl.setVisibility(View.VISIBLE);
                            mRightNoData_rl.setVisibility(View.GONE);
                            mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mSelectList.get(mSelectPostion).getId());
                        } else {
                            //again_login_rl.setVisibility(View.GONE);
                            card_comment_box.setVisibility(View.GONE);
                            equip_line.setVisibility(View.GONE);
                            mMline.setBackgroundResource(R.color.color_ffffff);
                            mScrollRl.setVisibility(View.GONE);
                            mComment_rl.setVisibility(View.GONE);
                            mRightNoData_rl.setVisibility(View.VISIBLE);
                            mSelectPostion = -1;
                            ToastUtils.myToast(getContext(), "数据走丢了");
                        }
                        if (list.size() != 10) {
                            mListview.removeFooterView(mFooter);
                        } else {
                            mListview.addFooterView(mFooter);
                        }

                    }
                }
            } else if (msg.what == 107) {
                MyDialog.stopDia();
                mSelect_tv.setClickable(true);
               // again_login_rl.setVisibility(View.VISIBLE);
                ToastUtils.myToast(getContext(), "请检查网络");
                mBar.setVisibility(View.INVISIBLE);
                mListview.removeFooterView(mFooter);
            } else if (msg.what == 1100) {//学术圈最新
                Object o = msg.obj;
                MyDialog.stopDia();
                if (o != null && o instanceof Root) {
                    mBar.setVisibility(View.INVISIBLE);
                    Root root = (Root) o;
                    List<Rows> list = new ArrayList<>();
                    list = root.getRows();
                    mNewList.addAll(list);
                    mListview.removeFooterView(mFooter);
                    mListview.setAdapter(mNewAdpter);
                    mListview.setVisibility(View.VISIBLE);
                    mNew_tv.setClickable(true);
                    //请求最新第一条数据详情
                    if (mNewList.size() != 0) {
                        equip_line.setVisibility(View.VISIBLE);
                        card_comment_box.setVisibility(View.VISIBLE);
                        mMline.setBackgroundResource(R.color.color_cccccc);
                        mScrollRl.setVisibility(View.VISIBLE);
                        mComment_rl.setVisibility(View.VISIBLE);
                        mRightNoData_rl.setVisibility(View.GONE);
                        mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mNewList.get(mNewPostion).getId());
                    } else {
                       // again_login_rl.setVisibility(View.GONE);
                        card_comment_box.setVisibility(View.GONE);
                        equip_line.setVisibility(View.GONE);
                        mMline.setBackgroundResource(R.color.color_ffffff);
                        mScrollRl.setVisibility(View.GONE);
                        mComment_rl.setVisibility(View.GONE);
                        mRightNoData_rl.setVisibility(View.VISIBLE);
                        mNewPostion = -1;
                        ToastUtils.myToast(getContext(), "数据走丢了");
                    }
                    if (list.size() != 10) {
                        mListview.removeFooterView(mFooter);
                    } else {
                        mListview.addFooterView(mFooter);
                    }
                }

            } else if (msg.what == 1101) {
                MyDialog.stopDia();
                mNew_tv.setClickable(true);
                //again_login_rl.setVisibility(View.VISIBLE);
                ToastUtils.myToast(getContext(), "请检查网络");
                mListview.removeFooterView(mFooter);
                mBar.setVisibility(View.INVISIBLE);

            } else if (msg.what == 1012) {//详情
                mMline.setVisibility(View.VISIBLE);
                mComment_rl.setVisibility(View.VISIBLE);
                card_comment_box.setVisibility(View.VISIBLE);
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.CircleMessageBean.Root) {
                    com.technology.yuyidoctorpad.bean.CircleMessageBean.Root root = (com.technology.yuyidoctorpad.bean.CircleMessageBean.Root) o;
                    if (root.getCode().equals("0")) {

                        Picasso.with(getContext()).load(UrlTools.BASE + root.getResult().getAvatar()).error(R.mipmap.error_small).into(mHead_img);
                        mName.setText(root.getResult().getTrueName());
                        mTime.setText(TimeUtils.getTime(root.getResult().getCreateTimeString()));
                        mContent.setText(root.getResult().getContent());
                        mTitle.setText(root.getResult().getTitle());
                        mPraise_num.setText(root.getResult().getLikeNum() + "");


                        //点赞设值
                        if (root.getResult().getLikeNum() == null) {
                            mPraise_num.setText("0");
                        } else {
                            mPraise_num.setText(root.getResult().getLikeNum() + "");
                        }

                        if (root.getResult().getIsLike()) {
                            mPraise_img.setImageResource(R.mipmap.like_selected);
                        } else {
                            mPraise_img.setImageResource(R.mipmap.like);
                        }


                        String strImg = root.getResult().getPicture();
                        if (!strImg.equals("") && strImg != null) {
                            mStrImg = strImg.split(";");
                        }
                        mImgAdapter.setStr(mStrImg);
                        mImgAdapter.notifyDataSetChanged();


                        //评论设值
                        if (root.getResult().getCommentNum() == null) {
                            mComment_allNum.setText("0");
                        } else {
                            mComment_allNum.setText(root.getResult().getCommentNum() + "");
                        }
                        mBar3.setVisibility(View.INVISIBLE);
                        List<CommentList> list = new ArrayList<>();
                        list = root.getResult().getCommentList();
                        mCommentList.addAll(list);
                        //mCommentAdapter.setList(mCommentList);
                        // mCommentAdapter.notifyDataSetChanged();

                        commAdapter.notifyDataSetChanged();
                        if (list.size() != 10) {
                            mMany_Box3.setVisibility(View.GONE);
                        } else {
                            mMany_Box3.setVisibility(View.VISIBLE);
                        }

                    }
                }
            } else if (msg.what == 108) {
                //again_login_rl.setVisibility(View.VISIBLE);
                ToastUtils.myToast(getContext(), "请检查网络");
            } else if (msg.what == 12) {//学术圈提交评论
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.SubmitComment.Root) {
                    com.technology.yuyidoctorpad.bean.SubmitComment.Root rootSubmit = (com.technology.yuyidoctorpad.bean.SubmitComment.Root) o;
                    if (rootSubmit.getCode().equals("0")) {
                        mEdit.setText("");
                        mStart3 = 0;
                        mCommentList.clear();
                        if (mFlag == 0) {//热门
                            mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mList.get(mHotPostion).getId());
                        } else if (mFlag == 1) {//精选
                            mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mSelectList.get(mSelectPostion).getId());
                        } else {//最新
                            mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mNewList.get(mNewPostion).getId());
                        }


                    } else {
                        ToastUtils.myToast(getContext(), "评论失败");
                    }
                }
            } else if (msg.what == 112) {
                ToastUtils.myToast(getContext(), "提交失败");
            } else if (msg.what == 9) {//学术圈详情点赞接口
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.InformationPraise.Root) {
                    com.technology.yuyidoctorpad.bean.InformationPraise.Root root = (com.technology.yuyidoctorpad.bean.InformationPraise.Root) o;
                    if (root.getCode().equals("0")) {
                        if (root.getResult().equals("点赞成功")) {
                            mPraise_img.setImageResource(R.mipmap.like_selected);
                            mPraise_num.setText(Integer.valueOf(mPraise_num.getText().toString()) + 1 + "");
                        } else {
                            mPraise_img.setImageResource(R.mipmap.like);
                            mPraise_num.setText(Integer.valueOf(mPraise_num.getText().toString()) - 1 + "");
                        }
                    }
                }
            } else if (msg.what == 109) {
                ToastUtils.myToast(getContext(), "点赞失败");
            } else if (msg.what == 17) {//发帖
                Object o = msg.obj;
                MyDialog.stopDia();
                mPostBtn.setClickable(true);
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.CirclePostCard.Root) {
                    com.technology.yuyidoctorpad.bean.CirclePostCard.Root root = (com.technology.yuyidoctorpad.bean.CirclePostCard.Root) o;
                    if (root != null) {
                        if ("0".equals(root.getCode())) {
                            Toast.makeText(getContext(), "帖子发布成功", Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                            mTitle_Edit.setText("");
                            mContent_Edit.setText("");
                            mTitle_Edit.setHint("标题(不超过20个字) ");
                            mContent_Edit.setHint("输入内容");
                            mCardListImg.clear();
                            mCardImgAda.notifyDataSetChanged();
                        } else if ("-1".equals(root.getCode())) {
                            Toast.makeText(getContext(), "发帖失败：您没有发帖的权限", Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        } else if ("10000".equals(root.getCode())) {
                            Toast.makeText(getContext(), "发帖失败，请重新登陆", Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(getContext(), "发帖失败", Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        }

                    } else {
                        mPostBtn.setClickable(true);
                        ToastUtils.myToast(getContext(), "发帖解析错误");
                    }
                }
            } else if (msg.what == 1010) {//消息有红点
                mSmallRed_Img.setVisibility(View.VISIBLE);
            } else if (msg.what == -2) {//消息没有红点
                mSmallRed_Img.setVisibility(View.GONE);
            } else if (msg.what == 13) {
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.InformationPraise.Root) {
                    com.technology.yuyidoctorpad.bean.InformationPraise.Root root = (com.technology.yuyidoctorpad.bean.InformationPraise.Root) o;
                    if (root.getCode().equals("0")) {
                        if (root.getResult().equals("点赞成功")) {
                            mCommentList.get(mCommPosition).setLike(true);
                            if (mCommentList.get(mCommPosition).getLikeNum() == null) {
                                mCommentList.get(mCommPosition).setLikeNum(1);
                            } else {
                                mCommentList.get(mCommPosition).setLikeNum(mCommentList.get(mCommPosition).getLikeNum() + 1);
                            }
                           commAdapter.notifyDataSetChanged();

                        } else if (root.getResult().equals("撤销点赞成功")) {
                            mCommentList.get(mCommPosition).setLike(false);
                            mCommentList.get(mCommPosition).setLikeNum(mCommentList.get(mCommPosition).getLikeNum() - 1);
                            commAdapter.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtils.myToast(getContext(), "点赞失败");
                    }
                }
            } else if (msg.what == 113) {
                ToastUtils.myToast(getContext(), "数据错误");
            }
        }
    };


    public CircleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.circleHot(mHandler, mStart, mLimit, User.token); //刚进来显示热门
        initUI(view);
        showHotLine();
        return view;
    }

    private void initUI(View view) {
//        again_login_rl = view.findViewById(R.id.again_login_rl);
//        again_login_rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mHttptools.circleHot(mHandler, mStart, mLimit, User.token); //刚进来显示热门
//            }
//        });
        card_comment_box = view.findViewById(R.id.card_comment_box);
        mComment_rl = view.findViewById(R.id.scroll_relative);
        mRightNoData_rl = view.findViewById(R.id.right_nodata_rl);
        mPost_Img = view.findViewById(R.id.post_img);
        mSmallRed_Img = view.findViewById(R.id.red_img);//小红点
        mMessage_Btn = view.findViewById(R.id.information_img);   //消息
        mPost_Img.setOnClickListener(this);
        mMessage_Btn.setOnClickListener(this);
        //热门，精选，最新
        mHot_tv = view.findViewById(R.id.circle_hot_tv);
        mSelect_tv = view.findViewById(R.id.circle_select_tv);
        mNew_tv = view.findViewById(R.id.circle_new_tv);
        mHot_line = view.findViewById(R.id.circle_hot_line);
        mSelect_line = view.findViewById(R.id.circle_select_line);
        mNew_line = view.findViewById(R.id.circle_new_line);
        mMline = view.findViewById(R.id.view_line);
        equip_line = view.findViewById(R.id.equip_line);
        mHot_tv.setOnClickListener(this);
        mSelect_tv.setOnClickListener(this);
        mNew_tv.setOnClickListener(this);
        mListview = view.findViewById(R.id.circle_listview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mFlag == 0) {//热门
                    mHotPostion = i;
                    mStart3 = 0;
                    mCommentList.clear();
                    commAdapter.notifyDataSetChanged();
                    mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mList.get(mHotPostion).getId());
                } else if (mFlag == 1) {//精选
                    mSelectPostion = i;
                    mStart3 = 0;
                    mCommentList.clear();
                    commAdapter.notifyDataSetChanged();
                    mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mSelectList.get(mSelectPostion).getId());
                } else {//最新
                    mNewPostion = i;
                    mStart3 = 0;
                    mCommentList.clear();
                    commAdapter.notifyDataSetChanged();
                    mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mNewList.get(mNewPostion).getId());
                }
            }
        });

        //热门适配器
        mAdapter = new CircleHotAdpater(getActivity(), mList);
        //精选适配器
        mSelectAdapter = new CircleSelectAda(this.getActivity(), mSelectList);
        //最新适配器
        mNewAdpter = new CircleNewAdapter(this.getActivity(), mNewList);
        mFooter = LayoutInflater.from(getActivity()).inflate(R.layout.circle_listview_footer, null);
        mFooter.setOnClickListener(this);
        mBar = (ProgressBar) mFooter.findViewById(R.id.pbLocate);

        //详情
        mHead_img = (RoundImageView) view.findViewById(R.id.car_user_head_img);
        mName = (TextView) view.findViewById(R.id.car_user_name);
        mTime = (TextView) view.findViewById(R.id.car_user_time);
        mPraise_num = (TextView) view.findViewById(R.id.car_user_praise_tv);
        mPraise_img = (ImageView) view.findViewById(R.id.car_user_praise_img);
        mPraise_img.setOnClickListener(this);
        mTitle = (TextView) view.findViewById(R.id.card_title);
        mContent = (TextView) view.findViewById(R.id.card_message);
        mComment_allNum = (TextView) view.findViewById(R.id.card_comment_num);
        //帖子详情页面的图片listview
        mImgListView = view.findViewById(R.id.card_img_listview);
        mImgAdapter = new CardMessageImgAdapter(getContext(), mStrImg);
        mImgListView.setAdapter(mImgAdapter);
        //帖子详情页面的评论listview
        mCommentListView = view.findViewById(R.id.card_comment_listview);
        // mCommentAdapter = new CardMessageCommentAdapter(getContext(), mCommentList);
        commAdapter = new CommAdapter();
        mCommentListView.setAdapter(commAdapter);
        //将scrollView定位到顶部
        mScrollRl = (RelativeLayout) view.findViewById(R.id.scroll_relative);
        mScrollRl.setFocusable(true);
        mScrollRl.setFocusableInTouchMode(true);
        mScrollRl.requestFocus();
        //加载更多
        mMany_Box3 = (RelativeLayout) view.findViewById(R.id.more_relative1);
        mMany_Box3.setOnClickListener(this);
        mBar3 = (ProgressBar) view.findViewById(R.id.pbLocate1);
        //评论框
        mEdit = (EditText) view.findViewById(R.id.circle_edit);
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (getEditContent().equals("")) {
                        ToastUtils.myToast(getContext(), "请输入评论内容");
                    } else {

                        if (mFlag == 0) {//评论的是热门的某条数据
                            if (mHotPostion != -1) {
                                Map<String ,String> map=new HashMap<String, String>();
                                map.put("Content",getEditContent());
                                mHttptools.submitCircleComment(mHandler, Long.valueOf(User.tele), mList.get(mHotPostion).getId(), map);
                                //隐藏软键盘
                                ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            } else {
                                ToastUtils.myToast(getActivity(), "无法选择评论数据");
                            }
                        } else if (mFlag == 1) {//评论的是精选的某条数据
                            if (mSelectPostion != -1) {
                                Map<String ,String> map=new HashMap<String, String>();
                                map.put("Content",getEditContent());
                                mHttptools.submitCircleComment(mHandler, Long.valueOf(User.tele), mSelectList.get(mSelectPostion).getId(), map);
                                //隐藏软键盘
                                ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            } else {
                                ToastUtils.myToast(getActivity(), "无法选择评论数据");
                            }
                        } else {//评论的是最新的某条数据
                            if (mNewPostion != -1) {
                                Map<String ,String> map=new HashMap<String, String>();
                                map.put("Content",getEditContent());
                                mHttptools.submitCircleComment(mHandler, Long.valueOf(User.tele), mNewList.get(mNewPostion).getId(), map);
                                //隐藏软键盘
                                ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            } else {
                                ToastUtils.myToast(getActivity(), "无法选择评论数据");
                            }
                        }
                    }
                    return true;
                }

                return false;
            }
        });

        //删除图片是的弹框
        builder = new AlertDialog.Builder(getContext());
        mAlert = builder.create();
        mAlertView = LayoutInflater.from(getContext()).inflate(R.layout.alert_box, null);
        mAlert.setView(mAlertView);
        mSure = (TextView) mAlertView.findViewById(R.id.sure);
        mCancle = (TextView) mAlertView.findViewById(R.id.cancle);
        mSure.setOnClickListener(this);
        mCancle.setOnClickListener(this);
        //发帖弹框
        mPopView = LayoutInflater.from(getActivity()).inflate(R.layout.post_card_layout, null);
        mPostBtn = mPopView.findViewById(R.id.post_btn);
        mPostBtn.setOnClickListener(this);
        mTitle_Edit = mPopView.findViewById(R.id.title_edit);
        mContent_Edit = mPopView.findViewById(R.id.content_edit);
        mImg_GridView = mPopView.findViewById(R.id.img_gridview);
        mPopParentView = view.findViewById(R.id.parent_rl);
        popupWindow = new PopupWindow(mPopView);
        mCardImgAda = new PostCardAdapter(getContext(), mCardListImg);
        mImg_GridView.setAdapter(mCardImgAda);
        mImg_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCardListImg.size() == 0) {
                    //跳转相册
                    checkPermiss();
                } else {
                    if (mCardListImg.size() == 6) {
                        if (i != mCardListImg.size()) {//是否删除图片
                            mPosition = i;
                            mAlert.show();
                        } else {
                            Toast.makeText(getContext(), "亲，最多选择6张图片哦", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (i == mCardListImg.size()) {
                            //跳转相册
                            checkPermiss();
                        } else {//是否删除图片
                            mPosition = i;
                            mAlert.show();
                        }

                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mPost_Img.getId()) {//发布弹框
            PostCardPopUtils.showCardPop(getActivity(), mPopView, dip2px(400), dip2px(600), mPopParentView, popupWindow);
        } else if (id == mPostBtn.getId()) {//发布
            if (getTitle().equals("")) {
                ToastUtils.myToast(getContext(), "请填写标题");
            } else {
                if (getContent().equals("")) {
                    ToastUtils.myToast(getContext(), "请填写内容");
                } else {
                    mPostBtn.setClickable(false);
                    MyDialog.showDialog(getContext());
                    File[] files = new File[mCardListImg.size()];
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("title", getTitle());
                    ajaxParams.put("content", getContent());
                    ajaxParams.put("token", User.token);
                    if (mCardListImg.size() == 0) {
                        Log.e("没有上传图片", "---");
                    } else {

                        for (int k = 0; k < mCardListImg.size(); k++) {
                            try {
                                files[k] = transImage(mCardListImg.get(k), ImgUitls.getWith(getContext()), ImgUitls.getHeight(getContext()), 90, "图片" + k);
                                ajaxParams.put("图片" + k, transImage(mCardListImg.get(k), ImgUitls.getWith(getContext()), ImgUitls.getHeight(getContext()), 90, "图片" + k));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                        mHttptools.postCirclrCard(mHandler, User.token, ajaxParams);
                    }

                }

            }

        } else if (id == mMessage_Btn.getId()) {//消息
            startActivity(new Intent(getContext(), MessageActivity.class));
        } else if (id == mHot_tv.getId()) {//热门
            mMline.setVisibility(View.INVISIBLE);
            mComment_rl.setVisibility(View.GONE);
            card_comment_box.setVisibility(View.GONE);
            MyDialog.showDialog(getContext());
            mHotPostion = 0;
            mSelectPostion = 0;
            mNewPostion = 0;
            //mHot_tv.setClickable(false);
            mFlag = 0;
            mStart = 0;
            mList.clear();
            mAdapter.notifyDataSetChanged();
            mCommentList.clear();
            commAdapter.notifyDataSetChanged();
            mListview.setVisibility(View.GONE);
            mHttptools.circleHot(mHandler, mStart, mLimit, User.token);
            showHotLine();
        } else if (id == mSelect_tv.getId()) {//精选
            mMline.setVisibility(View.INVISIBLE);
            mComment_rl.setVisibility(View.GONE);
            card_comment_box.setVisibility(View.GONE);
            MyDialog.showDialog(getContext());
            mHotPostion = 0;
            mSelectPostion = 0;
            mNewPostion = 0;
            //mSelect_tv.setClickable(false);
            mFlag = 1;
            mStart = 0;
            mSelectList.clear();
            mSelectAdapter.notifyDataSetChanged();
            mCommentList.clear();
            commAdapter.notifyDataSetChanged();
            mListview.setVisibility(View.GONE);
            mHttptools.circleSelect(mHandler, mStart, mLimit, User.token);
            showSelectLine();
        } else if (id == mNew_tv.getId()) {//最新
            mMline.setVisibility(View.INVISIBLE);
            mComment_rl.setVisibility(View.GONE);
            card_comment_box.setVisibility(View.GONE);
            MyDialog.showDialog(getContext());
            mHotPostion = 0;
            mSelectPostion = 0;
            mNewPostion = 0;
            // mNew_tv.setClickable(false);
            mFlag = 2;
            mStart = 0;
            mNewList.clear();
            mNewAdpter.notifyDataSetChanged();
            mCommentList.clear();
            commAdapter.notifyDataSetChanged();
            mListview.setVisibility(View.GONE);
            mHttptools.circleNew(mHandler, mStart, mLimit, User.token);
            showNewLine();
        } else if (id == mFooter.getId()) {//热门精选最新加载更多
            mStart += 10;
            mBar.setVisibility(View.VISIBLE);
            if (mFlag == 0) {//学术圈热门
                mHttptools.circleHot(mHandler, mStart, mLimit, User.token);
            } else if (mFlag == 1) {//学术圈精选
                mHttptools.circleSelect(mHandler, mStart, mLimit, User.token);
            } else if (mFlag == 2) {//学术圈最新
                mHttptools.circleNew(mHandler, mStart, mLimit, User.token);
            }
        } else if (id == mPraise_img.getId()) {//详情里边点赞
            if (mFlag == 0) {//评论的是热门的某条数据
                if (mHotPostion != -1) {
                    mHttptools.circlePraise(mHandler, mList.get(mHotPostion).getId(), User.token);
                } else {
                    ToastUtils.myToast(getActivity(), "无法点赞");
                }
            } else if (mFlag == 1) {//评论的是精选的某条数据
                if (mSelectPostion != -1) {
                    mHttptools.circlePraise(mHandler, mSelectList.get(mSelectPostion).getId(), User.token);
                } else {
                    ToastUtils.myToast(getActivity(), "无法点赞");
                }
            } else {//评论的是最新的某条数据
                if (mNewPostion != -1) {
                    mHttptools.circlePraise(mHandler, mNewList.get(mNewPostion).getId(), User.token);
                } else {
                    ToastUtils.myToast(getActivity(), "无法点赞");
                }
            }

        } else if (id == mMany_Box3.getId()) {
            mStart3 += 10;
            mBar3.setVisibility(View.VISIBLE);
            if (mFlag == 0) {//热门的数据
                if (mHotPostion != -1) {
                    mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mList.get(mHotPostion).getId());
                }
            } else if (mFlag == 1) {//精选的数据
                if (mSelectPostion != -1) {
                    mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mSelectList.get(mSelectPostion).getId());
                }
            } else {//最新的数据
                if (mNewPostion != -1) {
                    mHttptools.getHotSelectNewMessage(mHandler, User.token, mStart3, mLimit3, mNewList.get(mNewPostion).getId());
                }
            }
        } else if (id == mSure.getId()) {//确定删除图片
            mCardListImg.remove(mPosition);
            mCardImgAda.setmList(mCardListImg);
            mCardImgAda.notifyDataSetChanged();
            mAlert.dismiss();
        } else if (id == mCancle.getId()) {//取消
            mAlert.dismiss();
        }
    }

    //显示精选
    public void showSelectLine() {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mSelect_tv.measure(spec, spec);
        int measuredWidthTicketNum = mSelect_tv.getMeasuredWidth();

        ViewGroup.LayoutParams params = mSelect_line.getLayoutParams();
        params.width = measuredWidthTicketNum;
        mSelect_line.setLayoutParams(params);
        mSelect_line.setVisibility(View.VISIBLE);
        mHot_line.setVisibility(View.GONE);
        mNew_line.setVisibility(View.GONE);

        mSelect_tv.setTextColor(Color.parseColor(mColorSelect));
        mHot_tv.setTextColor(Color.parseColor(mNoSelectColor));
        mNew_tv.setTextColor(Color.parseColor(mNoSelectColor));
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
        mSelect_line.setVisibility(View.GONE);

        mSelect_tv.setTextColor(Color.parseColor(mNoSelectColor));
        mHot_tv.setTextColor(Color.parseColor(mNoSelectColor));
        mNew_tv.setTextColor(Color.parseColor(mColorSelect));
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
        mSelect_line.setVisibility(View.GONE);

        mSelect_tv.setTextColor(Color.parseColor(mNoSelectColor));
        mHot_tv.setTextColor(Color.parseColor(mColorSelect));
        mNew_tv.setTextColor(Color.parseColor(mNoSelectColor));
    }

    /**
     * 获取输入框的内容
     *
     * @return
     */
    public String getEditContent() {
        String content = mEdit.getText().toString().trim();
        if (content.equals("")) {
            return "";
        }
        return content;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //判断选择图片是的权限
    public void checkPermiss() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permiss = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

            //如果没有授权
            if (permiss != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_WRITE_PERMISS_CODE);
                return;
                //如果已经授权，执行业务逻辑
            } else {
                startSelectImgActivity();
            }
            //版本小于23时，不需要判断敏感权限，执行业务逻辑
        } else {
            startSelectImgActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case READ_WRITE_PERMISS_CODE:
                //点击了允许，授权成功
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startSelectImgActivity();
                    //点击了拒绝，授权失败
                } else {


                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //跳转到选择图片的页面
    public void startSelectImgActivity() {
        Intent intent = new Intent(getContext(), SelectImgActivity.class);
        //intent.putExtra("num", 3);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra("imgList");
            if (mCardListImg.size() != 6) {
                for (int i = 0; i < list.size(); i++) {
                    mCardListImg.add(list.get(i));
                    Log.e("路经", list.get(i));
                    if (mCardListImg.size() == 6) {
                        break;
                    }
                }
                //mCardImgAda.setmList(mCardListImg);
                mCardImgAda.notifyDataSetChanged();
            }
        }
    }

    //标题
    public String getTitle() {
        if ("".equals(mTitle_Edit.getText().toString().trim())) {
            return "";
        }
        return mTitle_Edit.getText().toString().trim();
    }
    //内容

    public String getContent() {
        if ("".equals(mContent_Edit.getText().toString().trim())) {
            return "";
        }
        return mContent_Edit.getText().toString().trim();
    }


    //获取有无未读消息
    public void getUnReadMsg() {
        Map<String, String> mp = new HashMap<>();
        mp.put("token", User.token);
        OkUtils.getCall(Ip.path + Ip.interface_HasUnReadMsg, mp, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendEmptyMessage(-2);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String resStr2 = response.body().string();
                Log.i("有无未读消息---", resStr2);
                try {
                    myModel.Msg ms = gson.gson.fromJson(resStr2, myModel.Msg.class);
                    if (ms != null) {
                        if ("0".equals(ms.getCode()) && ms.isHasMessage()) {
                            mHandler.sendEmptyMessage(1010);
                        } else {
                            mHandler.sendEmptyMessage(-2);
                        }
                    } else {
                        mHandler.sendEmptyMessage(-2);
                    }
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(-2);
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @param pathName 图片路径
     * @param width    屏幕宽度
     * @param height   屏幕 高度
     * @param quality  图片质量
     * @param fileName 图片名称
     * @return
     */
    public File transImage(String pathName, int width, int height, int quality, String fileName) {
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
                file = new File(getContext().getFilesDir(), fileName + ".jpg");
                Log.e("图片名称：", fileName + ".jpg");
                Log.e("图片文件夹名称：", getContext().getFilesDir().toString());

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


    //评论详情adapter

    class CommAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCommentList.size();
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            CommHolder holder = null;
            if (view == null) {
                holder = new CommHolder();
                view = LayoutInflater.from(getContext()).inflate(R.layout.card_comment_listview_item, null);
                holder.img = (RoundImageView) view.findViewById(R.id.head_img);
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.time = (TextView) view.findViewById(R.id.time);
                holder.content_tv = (TextView) view.findViewById(R.id.content);
                holder.praise_img = (ImageView) view.findViewById(R.id.car_user_praise_img);
                holder.praise_num = (TextView) view.findViewById(R.id.card_user_praise_num);
                view.setTag(holder);

            } else {
                holder = (CommHolder) view.getTag();
            }
            Picasso.with(getContext()).load(UrlTools.BASE + mCommentList.get(i).getAvatar()).error(R.mipmap.erroruser).into(holder.img);
            holder.name.setText(mCommentList.get(i).getTrueName());
            holder.time.setText(TimeUtils.getTime(mCommentList.get(i).getCreateTimeString()));
            holder.content_tv.setText(mCommentList.get(i).getContent());
            if (mCommentList.get(i).getLikeNum() == null) {
                holder.praise_num.setText("0");
            } else {
                holder.praise_num.setText(mCommentList.get(i).getLikeNum() + "");
            }

            if (mCommentList.get(i).isLike()) {
                holder.praise_img.setImageResource(R.mipmap.like_selected);
            } else {
                holder.praise_img.setImageResource(R.mipmap.like);
            }

            holder.praise_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCommPosition = i;
                    mHttptools.circleCommendPrise(mHandler, mCommentList.get(i).getId(), User.token);
                    Log.e("id", mCommentList.get(i).getId() + "");
                    Log.e("token", User.token);
                }
            });


            return view;
        }

        class CommHolder {
            RoundImageView img;
            TextView name;
            TextView time;
            TextView content_tv;
            ImageView praise_img;
            TextView praise_num;
        }
    }
}
