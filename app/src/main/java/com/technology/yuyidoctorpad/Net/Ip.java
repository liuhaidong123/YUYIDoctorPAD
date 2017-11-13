package com.technology.yuyidoctorpad.Net;

/**
 * Created by wanyu on 2017/11/3.
 */

public interface Ip {
//    "http://59.110.169.148:8080/
    String path="http://192.168.1.168:8082/yuyi";
    String imagePath="http://59.110.169.148:8080";
    String path_F="http://192.168.1.44:8080/yuyi";
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
    //帖子评论点赞
    String interface_MyPostCommentPraise="/likes/CommentLike.do?";
    //我的点赞列表
    String interface_MyPraise="/likes/findPage.do?";
    //取消点赞
    String interface_DeleteMyPraise="/likes/delete.do?";
    //我的消息列表接口http://192.168.1.55:8080/yuyi/messagePhysician/findPage.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
     String interface_MyMessageList="/messagePhysician/findPage.do?";

    //消息已读标记://192.168.1.55:8080/yuyi/messagePhysicianLog/save.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&messageId=1
   String interface_MyMessageRead="/messagePhysicianLog/save.do?";
    //医生登录
    String interface_Login="/physician/login.do?";
    //获取验证码
    String interface_SMSCode="/physician/vcode.do?";
    //医院获取验证码
    String interface_SMSCodeHospital="/hospital/vcode.do?";
    //http://localhost:8080/yuyi/hospital/AdministratorLogin.do?administratorsTelephone=18782931356&vcode=524740
    String interface_HospitalLogin="/hospital/AdministratorLogin.do?";
}
