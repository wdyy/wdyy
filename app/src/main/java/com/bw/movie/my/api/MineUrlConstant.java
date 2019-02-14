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
    String ATTENTIONCINEMA = "cinema/v1/verify/findCinemaPageList";
    //查询用户关注的影片信息
    String ATTENTIONFILM = "movie/v1/verify/findMoviePageList?page=1&count=5";
    //查询用户购票记录
    //待付款
    String QUERYPENDING1 = "user/v1/verify/findUserBuyTicketRecordList?page=1&count=5&status=1";
    //已付款
    String QUERYPENDING2 = "user/v1/verify/findUserBuyTicketRecordList?page=1&count=5&status=2";
    //意见反馈
    String FEEDBACK = "tool/v1/verify/recordFeedBack";
    //检测更新
    String DETECTIONUPDATE = "tool/v1/findNewVersion";
    //查询未读消息条数
    String WEIMESSAGE = "tool/v1/verify/findUnreadMessageCount";
    ///上传飞鸽令牌
    String FEIGELINGPAI = "tool/v1/verify/uploadPushToken";
    //系统消息状态修改
    String MESSAGEUPDATE = "tool/v1/verify/changeSysMsgStatus";
}
