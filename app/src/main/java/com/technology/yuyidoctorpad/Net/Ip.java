package com.technology.yuyidoctorpad.Net;

/**
 * Created by wanyu on 2017/11/3.
 */

public interface Ip {
    //"http://59.110.169.148:8080
    //http://192.168.1.168:8082/yuyi
    //http://59.110.169.148:8080
    String path = "http://59.110.169.148:8080";
    String imagePath = "http://59.110.169.148:8080";
    String path_F = "http://59.110.169.148:8080";
    //http://192.168.1.168:8080/smarthome/mobileapi/
    //患者列表接口
    String IPaintList = "/homeuser/findMyUserList.do?";
    //患者病历列表
    String interface_PaintEleList = "/medical/homeuserMedicalTime.do?";
    //患者数据（血压，体温数据）
    String IPaintDate = "/homeuser/findOnePh.do?";
    //获取用户信息
    String interface_UserInfo = "/physician/get.do?";
    //获取有无未读消息
    String interface_HasUnReadMsg = "/messagePhysician/hasmessage.do?";
    //我的帖子点赞
    String interface_MyPostDataPraise = "/likes/LikeNum.do?";
    //获取我的帖子列表
    String interface_MyPostData = "/academicpaper/findmylist.do?";
    //帖子详情
    String interface_MyPostMsg = "/academicpaper/academicpaperComment.do?";
    //帖子评论
    String interface_MyPostComment = "/comment/AddConment2.do?";
    //帖子评论点赞
    String interface_MyPostCommentPraise = "/likes/CommentLike.do?";
    //我的点赞列表
    String interface_MyPraise = "/likes/findPage.do?";
    //取消点赞
    String interface_DeleteMyPraise = "/likes/delete.do?";
    //我的消息列表接口http://192.168.1.55:8080/yuyi/messagePhysician/findPage.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
    String interface_MyMessageList = "/messagePhysician/findPage.do?";

    //消息已读标记://192.168.1.55:8080/yuyi/messagePhysicianLog/save.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&messageId=1
    String interface_MyMessageRead = "/messagePhysicianLog/save.do?";
    //医生登录
    String interface_Login = "/physician/login.do?";
    //获取验证码
    String interface_SMSCode = "/physician/vcode.do?";
    //医院获取验证码
    String interface_SMSCodeHospital = "/hospital/vcode.do?";
    //http://localhost:8080/yuyi/hospital/AdministratorLogin.do?administratorsTelephone=18782931356&vcode=524740
    String interface_HospitalLogin = "/hospital/AdministratorLogin.do?";

    //获取挂号列表接口http://192.168.1.55:8080/yuyi/register/findList.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5&departmentId=1&clinicId=1
    String interface_MyRegisterGH = "/register/findList.do?";
    //获取挂号中所有科室的列表:http://192.168.1.55:8080/yuyi/department/getallph.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10
    String interface_MyRegisterKS = "/department/getallph.do?";
    //获取患者列表
    String interface_MyPaintList = "/homeuser/findAllUserList.do?";
    //病历详情
    String interface_MyRegisterGH_Msg = "/register/get.do?";
    //关于我们
    String interface_AboutUs = "/aboutUs/getph.do";
    //意见反馈
    String interface_MySetting_FeadUs = "/feedback/saveforph.do?";
    //获取科室列表（医院方）
    String interface_getDepartment = "/department/getdepartment.do?";
    //添加科室门诊
    String interface_addDepart = "/department/saveBatch.do?";
    //删除科室http://192.168.1.44:8080/yuyi/department/DeleteDepartmentId.do?DepartmentId=5&hospitalId=1
    String interface_deleteDepart = "/department/DeleteDepartmentId.do?";
    //修改科室http://192.168.1.44:8080/yuyi/department/ClinicUpdateAndDelete.do?
    String interface_eidtDepart = "/department/ClinicUpdateAndDelete.do?";
    //获取科室的所有医生http://192.168.1.44:8080/yuyi/physician/findList.do?hospitalId=1&departmentId=1
    String interface_getDepartmentDoctors = "/physician/findList.do?";
    //获取科室挂号信息
    String interface_getDepartRecord = "/datenumber/findnumber.do?";
    //挂号设置
    String interface_getDepart = "/datenumber/saveNumber.do?";
    //添加病历http://localhost:8080/yuyi/medical/SaveMedical.do?telethone=
    String interface_addRecord = "/medical/SaveMedical.do?";
    //获取用户的容云信息
    String interface_getRongInfo = "/homeuser/findMyUserInfo.do?";
    //检查当前用户是否有权限接收视频，咨询的接口http://192.168.1.37:8080/yuyi/
    String interface_CheckPri = "/physician/doctoryrongyuntoken.do?";
}
