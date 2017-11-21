package com.technology.yuyidoctorpad.HttpTools;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class UrlTools {
    public static final String BASE = "http://192.168.1.44:8080/yuyi";
    //public static final String BASE = "http://59.110.169.148:8080";
    public static final String URL_AD = "/doctorlyinformation/getall.do";//广告数据
    public static final String URL_AD_MESSAGE = "/doctorlyinformation/get.do?";//广告,今日，最新，热门数据详情（需传id=,token）
    public static final String URL_TODAY_RECOMMEND = "/doctorlyinformation/getTodayAll.do?";//今日推荐（需传start，limit）
    public static final String URL_NEW = "/doctorlyinformation/findPage.do?";//最新（需传start，limit）
    public static final String URL_HOT = "/doctorlyinformation/find.do?";//热门（需传start，limit）
    public static final String URL_COMMEND_LIST = "/comment/getConmentAll.do?";//评论列表（需传：id=4&start=0&limit=6）
    public static final String URL_COMMEND = "/comment/AddConment.do?";//评论(需telephone=18782931355&content_id=1&Content=haha)
    public static final String URL_PRAISE = "/likes/UpdateLikeNum.do?";//点赞接口（需：id=&token=）

    public static final String URL_CIRCLE_HOT = "/academicpaper/findhot.do?";//学术圈热门（start=0&limit=6,token=）
    public static final String URL_CIRCLE_SELECT = "/academicpaper/Selected.do?";//学术圈精选（start=0&limit=6token=）
    public static final String URL_CIRCLE_NEW = "/academicpaper/findtime.do?";//学术圈最新（start=0&limit=6token=）
    public static final String URL_NEW_SELECT_HOT_MESSAGE = "/academicpaper/academicpaperComment.do?";//token=start=0&limit=2&id=1热门，精选，最新详情
    public static final String URL_POST_CARD = "/academicpaper/AddAcademicpaper.do?";
    public static final String URL_CIRCLE_PRAISE = "/likes/LikeNum.do?";//id=1&token=820F140709A478E3358AB5DA911C91E6 学术圈点赞接口

    public static final String URL_PATIENT_DATA = "/homeuser/findOnePh.do?";//token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&humeuserId=1患者详情中的患者数据
    public static final String URL_PATIENT_ELE = "/homeuser/findMyUserList.do?";//token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&humeuserId=1患者详情中的电子病历

    public static final String URL_CIRCLE_COMMEND = "/comment/AddConment2.do?";//telephone=18782931355&content_id=1&Content=haha学术圈详情评论
    public static final String URL_CIRCLE_COMMEND_PRAISE = "/likes/CommentLike.do?";//id=1&token=CEDA9F4E7D5FEC556E1BB035FA18E54E 学术圈详情评论点赞
    public static final String URL_MY_PATIENT = "/homeuser/findMyUserList.do?";//我的患者接口token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
    public static final String URL_SHARE_CARD = "/share/AcademicpaperShare.do?";//id=1&token=CEDA9F4E7D5FEC556E1BB035FA18E54E&shareType=1  帖子详情分享

    public static final String URL_SHARE_INFORMATION = "/share/UpdateShar.do?";//id=1&shareType=1&token=0365866B04DA5283B29330178F625C64  资讯详情分享



//医院接口
    public static final String URL_DEPARTMENT_LIST = "/department/getdepartment.do?";//科室列表参数hospitalId=1
    public static final String URL_SUBMIT_DOCTOR="/physician/AddphysicianData.do?";//添加医生  trueName=医生名称&title=医生职称&telephone=医生电话&hospitalId=医院编号&departmentId=科室编号&clinicId=门诊编号&permissionInfo=咨询权限&permissionData=查看数据权限
    public static final String URL_DOCTOR_LIST="/physician/findList.do?";//医生列表参数hospitalId=1&departmentId=1
    public static final String URL_DOCTOR_DETAILS="/physician/findDoctoryData.do?";//单个医生详情physicianid=2
    public static final String URL_SEARCH_DOCTOR="/physician/findvaguetrueName.do?";//搜索医生接口hospitalId=1&truename=刘海东
    public static final String URL_HOSPITAL_INFOMATION_LIST="/doctorlyinformation/findTimeAndPublishchannel.do?";//资讯列表Publishchannel=1&time=1&start=0&limit=10
    public static final String URL_INFORMATION_DATAILS="/doctorlyinformation/getIdPersonal.do?";//资讯详情id=12
    public static final String URL_POST_INFOR="/doctorlyinformation/PublishInformation.do?";//发布详情
    public static final String URL_HOSPITAL_HOMEPAGE="/hospital/get.do?";//医院主页
    public static final String URL_UPDATE_DOCTOR="/physician/UpdatephysicianData.do?";//修改医生信息
    public static final String URL_SUBMIT_MANAGER_MSG="/hospital/AddHospitaInformation.do?";//提交医院管理员信息

}
