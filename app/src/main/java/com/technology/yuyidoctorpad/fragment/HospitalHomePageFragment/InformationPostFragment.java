package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.InformationDetailsActivity;
import com.technology.yuyidoctorpad.activity.InformationPostActivity;
import com.technology.yuyidoctorpad.bean.HospitalInformation.Root;
import com.technology.yuyidoctorpad.bean.HospitalInformation.Rows;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯
 */
public class InformationPostFragment extends Fragment implements View.OnClickListener {

    private ImageView mBottomTime_Img, mTopTime_Img;
    private RelativeLayout mWay_Rl, mTwo_rl;
    private TextView mWay_Tv, mYuYi_tv, mYuYiDoctor_tv;
    private RelativeLayout mPost_Btn;
    private ListView mListView;
    private List<Rows> mList = new ArrayList<>();
    private inforAda mInforAda;
    private View footer;
    private ProgressBar bar;
    private int time = 1;//升序，降序
    private int wayid = 1;//发布渠道1=宇医2=宇医医生
    private int start = 0, limit = 10;
    private boolean moreFlag = false;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 60) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null) {
                        mListView.removeFooterView(footer);
                        bar.setVisibility(View.GONE);
                        if (moreFlag) {//如果是加载更多
                            mList.addAll(root.getRows());
                        } else {
                            mList = root.getRows();
                        }
                        mInforAda.notifyDataSetChanged();
                        if (root.getRows().size() == 10) {
                            mListView.addFooterView(footer);
                        } else {
                            mListView.removeFooterView(footer);
                        }
                    }

                } else {
                    ToastUtils.myToast(getContext(), "获取资讯列表错误");
                }
            }
        }
    };

    public InformationPostFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_post, container, false);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getHospitalInfomationLIst(mHandler, time, wayid, start, limit);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        mBottomTime_Img = view.findViewById(R.id.pull_bottom);//降序
        mBottomTime_Img.setOnClickListener(this);
        mTopTime_Img = view.findViewById(R.id.pull_top);//升序
        mTopTime_Img.setOnClickListener(this);

        mWay_Rl = view.findViewById(R.id.i_select_way_rl);//点击显示发布渠道
        mWay_Rl.setOnClickListener(this);
        mWay_Tv = view.findViewById(R.id.i_wat_tv);

        mTwo_rl = view.findViewById(R.id.two_way_rl);//显示宇医，宇医医生
        mTwo_rl.setOnClickListener(this);
        mYuYi_tv = view.findViewById(R.id.yuyi_tv);
        mYuYi_tv.setOnClickListener(this);
        mYuYiDoctor_tv = view.findViewById(R.id.yuyidoctor_tv);
        mYuYiDoctor_tv.setOnClickListener(this);

        mPost_Btn = view.findViewById(R.id.post_rl);//发布
        mPost_Btn.setOnClickListener(this);
        mListView = view.findViewById(R.id.i_listview);
        mInforAda = new inforAda();
        mListView.setAdapter(mInforAda);
        footer = LayoutInflater.from(getContext()).inflate(R.layout.circle_listview_footer, null);
        bar = footer.findViewById(R.id.pbLocate);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == mList.size()) {//加载更多
                    start += 10;
                    moreFlag = true;
                    bar.setVisibility(View.VISIBLE);
                    mHttptools.getHospitalInfomationLIst(mHandler, time, wayid, start, limit);
                } else {

                    Intent intent=new Intent(getContext(),InformationDetailsActivity.class);
                    intent.putExtra("inforID",mList.get(i).getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mBottomTime_Img.getId()) {//降序
            time = 1;
            start = 0;
            moreFlag = false;
            mHttptools.getHospitalInfomationLIst(mHandler, time, wayid, start, limit);
        } else if (id == mTopTime_Img.getId()) {//升序
            time = 2;
            start = 0;
            moreFlag = false;
            mHttptools.getHospitalInfomationLIst(mHandler, time, wayid, start, limit);
        } else if (id == mWay_Rl.getId()) {//显示发布渠道
            if (mTwo_rl.getVisibility() == View.VISIBLE) {
                mTwo_rl.setVisibility(View.GONE);
            } else {
                mTwo_rl.setVisibility(View.VISIBLE);
            }

        } else if (id == mPost_Btn.getId()) {//发布
            startActivity(new Intent(getContext(),InformationPostActivity.class));
        } else if (id == mYuYi_tv.getId()) {//宇医
            wayid = 1;
            start = 0;
            moreFlag = false;
            mWay_Tv.setText("宇医");
            mTwo_rl.setVisibility(View.GONE);
            mHttptools.getHospitalInfomationLIst(mHandler, time, wayid, start, limit);
        } else if (id == mYuYiDoctor_tv.getId()) {//宇医医生
            wayid = 2;
            start = 0;
            moreFlag = false;
            mWay_Tv.setText("宇医医生");
            mTwo_rl.setVisibility(View.GONE);
            mHttptools.getHospitalInfomationLIst(mHandler, time, wayid, start, limit);
        }
    }


    class inforAda extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            InforHolder inforHolder = null;
            if (view == null) {
                inforHolder = new InforHolder();
                view = LayoutInflater.from(getContext()).inflate(R.layout.information_listview_item, null);
                inforHolder.img = view.findViewById(R.id.i_hospital_image);
                inforHolder.title = view.findViewById(R.id.i_sh_tv);
                inforHolder.message = view.findViewById(R.id.i_hospital_name);
                inforHolder.status = view.findViewById(R.id.i_hospital_message);
                view.setTag(inforHolder);
            } else {
                inforHolder = (InforHolder) view.getTag();
            }
            Picasso.with(getContext()).load(UrlTools.BASE + mList.get(i).getPicture()).into(inforHolder.img);
            inforHolder.title.setText(mList.get(i).getTitle());
            inforHolder.message.setText(mList.get(i).getContent());
            if (mList.get(i).getAuditState() == 1) {
                inforHolder.status.setText("审核通过");
            } else if (mList.get(i).getAuditState() == 2) {
                inforHolder.status.setText("审核中");
            } else {
                inforHolder.status.setText("审核失败");
            }
            return view;
        }

        class InforHolder {
            ImageView img;
            TextView title, message, status;
        }
    }

}
