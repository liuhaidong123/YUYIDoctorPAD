package com.technology.yuyidoctorpad.Net;

/**
 * Created by wanyu on 2017/11/3.
 */

public interface Ip {
//    "http://59.110.169.148:8080/
    String path="http://192.168.1.168:8082/yuyi";
    String imagePath="http://59.110.169.148:8080";
//    http://192.168.1.168:8080/smarthome/mobileapi/
    //患者列表接口
    String IPaintList="/homeuser/findMyUserList.do?";
    //患者病历列表
    String interface_PaintEleList="/medical/homeuserMedicalTime.do?";
    //患者数据（血压，体温数据）
    String IPaintDate= "/homeuser/findOnePh.do?";
    //获取用户信息
    String interface_UserInfo="/physician/get.do?";
    //获取有无未读消息
    String interface_HasUnReadMsg="/messagePhysician/hasmessage.do?";
    //我的帖子点赞
    String interface_MyPostDataPraise="/likes/LikeNum.do?";
    //获取我的帖子列表
    String interface_MyPostData="/academicpaper/findmylist.do?";
    //帖子详情
    String interface_MyPostMsg="/academicpaper/academicpaperComment.do?";
    //帖子评论
    String interface_MyPostComment="/comment/AddConment2.do?";
}
