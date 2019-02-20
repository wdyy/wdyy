package com.bw.movie.my.api;

/**
 * 郭佳兴
 **/
public interface MineUrlConstant {
    //查询用户信息
    String USERINFO = "user/v1/verify/getUserInfoByUserId";
    //修改用户信息
    String UPDATEUSERINFO = "user/v1/verify/modifyUserInfo";
    //修改密码
    String UPDATEPWD = "user/v1/verify/modifyUserPwd";
    ///上传头像
    String UPLOADHEAD = "user/v1/verify/uploadHeadPic";
    //查询用户关注的影院信息
    String ATTENTIONCINEMA = "cinema/v1/verify/findCinemaPageList?page=1&count=3";
    //查询用户关注的影片信息
    String ATTENTIONFILM = "movie/v1/verify/findMoviePageList?page=1&count=3";
    //用户签到
    String QIAAAAN = "user/v1/verify/userSignIn";
    //查询用户购票记录
    //待付款
    String QUERYPENDING1 = "user/v1/verify/findUserBuyTicketRecordList?page=1&count=3&status=1";
    //已付款
    String QUERYPENDING2 = "user/v1/verify/findUserBuyTicketRecordList?page=1&count=3&status=2";
    //意见反馈
    String FEEDBACK = "tool/v1/verify/recordFeedBack";
    //检测更新
    String DETECTIONUPDATE = "tool/v1/findNewVersion?ak=0110010010000";
    //查询未读消息条数
    String WEIMESSAGE = "tool/v1/verify/findUnreadMessageCount";
    ///上传信鸽推送的token
    String XINGELINGPAI = "tool/v1/verify/uploadPushToken";
    //系统消息状态修改
    String MESSAGEUPDATE = "tool/v1/verify/changeSysMsgStatus?id=0";
    //查询系统消息
    String XITONGMESSAGE = "tool/v1/verify/findAllSysMsgList?page=1&count=5";
}