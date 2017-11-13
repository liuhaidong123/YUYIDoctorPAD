package com.technology.yuyidoctorpad.lzhUtils;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanyu on 2017/9/27.
 */

public class MyApplication extends Application {

    //友盟分享
    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("1106534096", "swVpTHpkOok0iywr");
        PlatformConfig.setSinaWeibo("242239420", "9ae3a7c3e1c7054e4aa977a28b58ce66", "http://sns.whalecloud.com/sina2/callback");
    }
    //友盟分享
    public static Activity activityCurrent;
    private static List<Activity> list;

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);//友盟
        Config.DEBUG = true;
        if (Build.VERSION.SDK_INT >= 14) {//4.0以上
            list = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= 14) {//4.0以上
                registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        list.add(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                        activityCurrent = activity;
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        list.remove(activity);
                    }
                });
            }
        }
    }

    //退出登录
    public static void removeActivity() {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Activity activity = list.get(i);
                Log.i("remove-名字--", activity.getClass().getSimpleName());
                activity.finish();
            }
            list.clear();
        }
    }
}
