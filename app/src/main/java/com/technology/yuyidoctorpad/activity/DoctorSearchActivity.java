package com.technology.yuyidoctorpad.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.bean.DoctorSrarchResult.Result;
import com.technology.yuyidoctorpad.bean.DoctorSrarchResult.Root;
import com.technology.yuyidoctorpad.lhdUtils.SeachDoctorDB;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class DoctorSearchActivity extends AppCompatActivity {

    private EditText mSearch_edit;
    private List<String> mHostory_List = new ArrayList<>();
    private List<Result> mResult_List = new ArrayList<>();
    private ListView mHostoryListView, mResultListView;
    private TextView mResult_Submit_tv, mCanle_btn;
    private View footer;
    private SeachDoctorDB seachDoctorDB;
    private SQLiteDatabase sqLiteDatabase;
    private HostoryAda mHostoryAda;
    private ResultAda mResultAda;
    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 59) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null && root.getResult() != null && root.getResult().size() != 0) {
                        mResult_List = root.getResult();
                        mResultAda.notifyDataSetChanged();
                        mResult_Submit_tv.setVisibility(View.GONE);
                        mHostoryListView.setVisibility(View.GONE);
                        mResultListView.setVisibility(View.VISIBLE);
                    } else {
                        mResult_Submit_tv.setText("抱歉，没有搜索到医生");
                        mResult_Submit_tv.setVisibility(View.VISIBLE);
                        mHostoryListView.setVisibility(View.GONE);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search);
        mHttptools = HttpTools.getHttpToolsInstance();
        initUI();
    }

    private void initUI() {

        //医生数据库
        seachDoctorDB = new SeachDoctorDB(this, "doctor_db", null, 1);
        sqLiteDatabase = seachDoctorDB.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from doctor order by _id desc", null);
        //查询曾经搜索过的医生
        while (cursor.moveToNext()) {
            mHostory_List.add(cursor.getString(cursor.getColumnIndex("doctorname")));
            Log.e("name", cursor.getString(cursor.getColumnIndex("doctorname")));
        }
        cursor.close();
        //历史
        mHostoryListView = (ListView) findViewById(R.id.search_history_listview);
        footer = LayoutInflater.from(getApplicationContext()).inflate(R.layout.doctor_f, null);
        if (mHostory_List.size() != 0) {
            mHostoryListView.addFooterView(footer);
        }
        mHostoryAda = new HostoryAda();
        mHostoryListView.setAdapter(mHostoryAda);
        mHostoryListView.setVisibility(View.VISIBLE);
        //点击历史查询医生
        mHostoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mHostory_List.size()) {
                    sqLiteDatabase.execSQL("delete from doctor");
                    mHostory_List.clear();
                    mHostoryAda.notifyDataSetChanged();
                    mResult_Submit_tv.setText("暂无历史");
                    mHostoryListView.removeFooterView(footer);
                } else {
                    mHostoryListView.setVisibility(View.GONE);
                    mHttptools.searchDoctor(handler, Long.valueOf(User.HospitalId), mHostory_List.get(i));//查询医生
                }
            }
        });
        //搜索时的结果
        mResultListView = (ListView) findViewById(R.id.search_result_listview);
        mResultAda = new ResultAda();
        mResultListView.setAdapter(mResultAda);
        //点击搜索结果跳转医生详情
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DoctorSearchActivity.this, DoctorDetilsActivity.class);
                intent.putExtra("doctorId", mResult_List.get(i).getId());
                startActivity(intent);
                finish();
            }
        });
        //提示
        mResult_Submit_tv = (TextView) findViewById(R.id.prompt_result);
        mCanle_btn = (TextView) findViewById(R.id.cancel_tv);
        mCanle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //搜索
        mSearch_edit = (EditText) findViewById(R.id.edit_box);
        mSearch_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if ("" != getInputContent()) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            //将搜索过得数据插入表中
                            ContentValues valuesDrug = new ContentValues();
                            valuesDrug.put("doctorname", getInputContent());
                            sqLiteDatabase.insert("doctor", null, valuesDrug);
                            mResult_Submit_tv.setVisibility(View.GONE);
                            mHttptools.searchDoctor(handler, Long.valueOf(User.HospitalId), getInputContent());//查询医生
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        }

                    } else {
                        ToastUtils.myToast(getApplicationContext(), "请输入医生姓名");
                    }
                    return true;

                }
                return false;
            }
        });
    }

    public String getInputContent() {
        if (!"".equals(mSearch_edit.getText().toString().trim())) {
            return mSearch_edit.getText().toString().trim();
        }
        return "";
    }

    //历史
    class HostoryAda extends BaseAdapter {

        @Override
        public int getCount() {
            return mHostory_List.size();
        }

        @Override
        public Object getItem(int i) {
            return mHostory_List.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            HostoryHolder hostoryHolder = null;
            if (view == null) {
                hostoryHolder = new HostoryHolder();
                view = LayoutInflater.from(DoctorSearchActivity.this).inflate(R.layout.doctor_hostory_item, null);
                hostoryHolder.doctorname = view.findViewById(R.id.name);
                view.setTag(hostoryHolder);
            } else {
                hostoryHolder = (HostoryHolder) view.getTag();
            }
            hostoryHolder.doctorname.setText(mHostory_List.get(i));
            return view;
        }

        class HostoryHolder {
            TextView doctorname;
        }
    }

    //结果
    class ResultAda extends BaseAdapter {

        @Override
        public int getCount() {
            return mResult_List.size();
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
            ResultHolder resultHolder = null;
            if (view == null) {
                resultHolder = new ResultHolder();
                view = LayoutInflater.from(DoctorSearchActivity.this).inflate(R.layout.doctor_hostory_item, null);
                resultHolder.name = view.findViewById(R.id.name);
                view.setTag(resultHolder);
            } else {
                resultHolder = (ResultHolder) view.getTag();
            }

            resultHolder.name.setText(mResult_List.get(i).getTrueName());
            return view;
        }

        class ResultHolder {
            TextView name;
        }
    }
}
