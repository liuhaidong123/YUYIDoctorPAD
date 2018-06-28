package com.technology.yuyidoctorpad.lzhUtils;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.RongCloudUtils.RongProvider;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;


/**
 * Created by wanyu on 2017/9/27.
 */

public class MyApplication extends Application {

    //友盟分享
    {
        PlatformConfig.setWeixin("wxcc229f9e409d872f", "38ab251ce7aef8c30e59a0824e61a079");
        PlatformConfig.setQQZone("1106534096", "swVpTHpkOok0iywr");
        PlatformConfig.setSinaWeibo("242239420", "9ae3a7c3e1c7054e4aa977a28b58ce66", "http://sns.whalecloud.com/sina2/callback");
    }
    //友盟分享
    public static Activity activityCurrent;
    private static List<Activity> list;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(this);     		// 初始化 JPush
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        UMShareAPI.get(this);//友盟
        Config.isJumptoAppStore = true;//如果用户没有安装qq,微信客户端会自动跳转到应用商店地址去下载（微博不会，微博只会打开网页端）
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


        RongIM.init(this);
        RongIM.setUserInfoProvider(RongProvider.getInstance(),true);

        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus) {
                    case CONNECTED://连接成功。
                        Log.e("connection", "连接成功");
                        break;
                    case DISCONNECTED://断开连接。
                        Log.e("connection", "断开连接");
                        break;
                    case CONNECTING://连接中。
                        Log.e("connection", "连接中");
                        break;
                    case NETWORK_UNAVAILABLE://网络不可用。
                        Log.e("connection", "网络不可用");
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                        Log.e("connection", "用户账户在其他设备登录，本机会被踢掉线");
                        break;
                }
            }
        });
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                if (message != null) {
                    String text=new String(message.getContent().encode());
                    UserInfo info= RongProvider.getInstance().getUserInfo(message.getTargetId());
                    try {
                        JSONObject job=new JSONObject(text);
                        text=job.getString("content");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                    builder.setContentTitle(info.getName()).
                            setContentText(text).
                            setTicker("收到一条新消息").setWhen(System.currentTimeMillis())
                            .setPriority(100).
                            setAutoCancel(true).
                            setDefaults(Notification.DEFAULT_ALL)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo)).setSmallIcon(R.mipmap.logo);
                    Notification notification = builder.build();
                    notification.defaults = Notification.DEFAULT_ALL;
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    Uri uri = Uri.parse("rong://" + getApplicationContext().getApplicationInfo().packageName).
                            buildUpon().appendPath("conversationlist").appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                            .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                            .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                            .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false").build();
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(uri);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    notification.contentIntent = pendingIntent;
                    manager.notify(message.getMessageId(), notification);
                }
                return true;
            }
        });
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
