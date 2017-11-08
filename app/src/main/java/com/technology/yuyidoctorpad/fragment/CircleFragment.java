package com.technology.yuyidoctorpad.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.adapter.CircleHotAdpater;
import com.technology.yuyidoctorpad.adapter.CircleNewAdapter;
import com.technology.yuyidoctorpad.adapter.CircleSelectAda;
import com.technology.yuyidoctorpad.bean.CircleBean.Root;
import com.technology.yuyidoctorpad.bean.CircleBean.Rows;
import com.technology.yuyidoctorpad.bean.CircleBean.SelectBean.Result;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment extends Fragment implements View.OnClickListener {
    private ImageView mPost_Img, mSearch_Img, mSmallRed_Img;
    private FrameLayout mMessage_Btn;
    private TextView mHot_tv, mSelect_tv, mNew_tv;//热门，精选，最新
    private View mHot_line, mSelect_line, mNew_line;//线
    private String mColorSelect = "#1ebeec";
    private String mNoSelectColor = "#6a6a6a";

    private RelativeLayout mMany_Box;//加载更多
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
    private int mFlag = 0;//加载更多时的标志
    private RelativeLayout mParent_Rl;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 7) {//学术圈热门
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mBar.setVisibility(View.INVISIBLE);
                    Root root = (Root) o;
                    List<Rows> list = new ArrayList<>();
                    list = root.getRows();

                    mList.addAll(list);
                    // mAdapter.setList(mList);
                    mListview.setAdapter(mAdapter);
//                    setListViewHeightBasedOnChildren(mListview);
                    mListview.setVisibility(View.VISIBLE);
                    mHot_tv.setClickable(true);
                    Log.e("kkkkk", "快步");
                    if (list.size() != 10) {
                        mListview.removeFooterView(mFooter);
                    } else {
                        mListview.addFooterView(mFooter);
                    }
                }

            } else if (msg.what == 106) {
                mHot_tv.setClickable(true);
                ToastUtils.myToast(getActivity(), "网络错误");
                mListview.removeFooterView(mFooter);
                mBar.setVisibility(View.INVISIBLE);

            } else if (msg.what == 8) {//学术圈精选
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.CircleBean.SelectBean.Root) {
                    com.technology.yuyidoctorpad.bean.CircleBean.SelectBean.Root root = (com.technology.yuyidoctorpad.bean.CircleBean.SelectBean.Root) o;
                    if (root.getCode().equals("0")) {
                        mBar.setVisibility(View.INVISIBLE);
                        List<Result> list = new ArrayList<>();
                        list = root.getResult();
                        mSelectList.addAll(list);
                        //mSelectAdapter.setList(mSelectList);
                        mListview.setAdapter(mSelectAdapter);
                        mListview.setVisibility(View.VISIBLE);
                        mSelect_tv.setClickable(true);
                        if (list.size() != 10) {
                            mListview.removeFooterView(mFooter);
                        } else {
                            mListview.addFooterView(mFooter);
                        }

                    }
                }
            } else if (msg.what == 107) {
                mSelect_tv.setClickable(true);
                ToastUtils.myToast(getActivity(), "网络错误");
                mBar.setVisibility(View.INVISIBLE);
                mListview.removeFooterView(mFooter);
            } else if (msg.what == 1100) {//学术圈最新
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    mBar.setVisibility(View.INVISIBLE);
                    Root root = (Root) o;
                    List<Rows> list = new ArrayList<>();
                    list = root.getRows();
                    mNewList.addAll(list);
                    // mNewAdpter.setList(mNewList);
                    mListview.setAdapter(mNewAdpter);
                    mListview.setVisibility(View.VISIBLE);
                    mNew_tv.setClickable(true);
                    if (list.size() != 10) {
                        mListview.removeFooterView(mFooter);
                    } else {
                        mListview.addFooterView(mFooter);
                    }
                }

            } else if (msg.what == 1101) {
                mNew_tv.setClickable(true);
                ToastUtils.myToast(getActivity(), "网络错误");
                mListview.removeFooterView(mFooter);
                mBar.setVisibility(View.INVISIBLE);

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

        mPost_Img = view.findViewById(R.id.post_img);
        mSearch_Img = view.findViewById(R.id.search_img);
        mSmallRed_Img = view.findViewById(R.id.red_img);//小红点
        mMessage_Btn = view.findViewById(R.id.information_img);   //消息
        mPost_Img.setOnClickListener(this);
        mSearch_Img.setOnClickListener(this);
        mMessage_Btn.setOnClickListener(this);
        //热门，精选，最新
        mHot_tv = view.findViewById(R.id.circle_hot_tv);
        mSelect_tv = view.findViewById(R.id.circle_select_tv);
        mNew_tv = view.findViewById(R.id.circle_new_tv);
        mHot_line = view.findViewById(R.id.circle_hot_line);
        mSelect_line = view.findViewById(R.id.circle_select_line);
        mNew_line = view.findViewById(R.id.circle_new_line);
        mHot_tv.setOnClickListener(this);
        mSelect_tv.setOnClickListener(this);
        mNew_tv.setOnClickListener(this);
        mListview = view.findViewById(R.id.circle_listview);
        //热门适配器
        mAdapter = new CircleHotAdpater(getActivity(), mList);
        //精选适配器
        mSelectAdapter = new CircleSelectAda(this.getActivity(), mSelectList);
        //最新适配器
        mNewAdpter = new CircleNewAdapter(this.getActivity(), mNewList);
        mFooter = LayoutInflater.from(getActivity()).inflate(R.layout.circle_listview_footer, null);
        //mListview.addFooterView(mFooter);
        mFooter.setOnClickListener(this);
        mBar = (ProgressBar) mFooter.findViewById(R.id.pbLocate);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mPost_Img.getId()) {//发布

        } else if (id == mSearch_Img.getId()) {//查找

        } else if (id == mMessage_Btn.getId()) {//消息

        } else if (id == mHot_tv.getId()) {//热门
            mHot_tv.setClickable(false);
            mFlag = 0;
            mStart = 0;
            mList.clear();
            //mMany_Box.setVisibility(View.GONE);
            mListview.setVisibility(View.GONE);
            mHttptools.circleHot(mHandler, mStart, mLimit, User.token);
            showHotLine();
        } else if (id == mSelect_tv.getId()) {//精选
            mSelect_tv.setClickable(false);
            mFlag = 1;
            mStart = 0;
            mSelectList.clear();
          //  mMany_Box.setVisibility(View.GONE);
            mListview.setVisibility(View.GONE);
            mHttptools.circleSelect(mHandler, mStart, mLimit, User.token);
            showSelectLine();
        } else if (id == mNew_tv.getId()) {//最新
            mNew_tv.setClickable(false);
            mFlag = 2;
            mStart = 0;
            mNewList.clear();
           // mMany_Box.setVisibility(View.GONE);
            mListview.setVisibility(View.GONE);
            mHttptools.circleNew(mHandler, mStart, mLimit, User.token);
            showNewLine();
        } else if (id == mFooter.getId()) {//加载更多
            mStart += 10;
            mBar.setVisibility(View.VISIBLE);
            if (mFlag == 0) {//学术圈热门
                mHttptools.circleHot(mHandler, mStart, mLimit, User.token);
            } else if (mFlag == 1) {//学术圈精选
                mHttptools.circleSelect(mHandler, mStart, mLimit, User.token);
            } else if (mFlag == 2) {//学术圈最新
                mHttptools.circleNew(mHandler, mStart, mLimit, User.token);
            }
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

    public void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
