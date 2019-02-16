package com.bw.movie.apis;

/**
 * Author: 范瑞旗
 * Date: 2019/1/24 14:33
 * Description: ${DESCRIPTION}
 */
public class Apis {
    //登录
    public static final String URL_LOGIN="user/v1/login";
    //微信登录
    public static final String URL_WX="user/v1/weChatBindingLogin";
    //注册
    public static final String URL_REGISTER="user/v1/registerUser";
    //轮播图
    public static final String URL_MOVIE_BANNER="movie/v1/findReleaseMovieList?page=1&count=10";
    //热门电影
    public static final String URL_MOVIE_HOT="movie/v1/findHotMovieList?page=1&count=10";
    //正在上映
    public static final String URL_MOVIE_LOADING="movie/v1/findReleaseMovieList?page=1&count=10";
    //即将上映
    public static final String URL_MOVIE_WAIT="movie/v1/findComingSoonMovieList?page=1&count=10";


    //根据电影ID查询电影信息
    public static final String URL_QUERY_MOVIE_BYID="movie/v1/findMoviesById?movieId=%s";
    //电影详情
    public static final String URL_MOVIE_DETAILS="movie/v1/findMoviesDetail?movieId=%s";
    //关注电影
    public static final String URL_FOLLOW_MOVIE="movie/v1/verify/followMovie?movieId=%s";
    //取消关注电影
    public static final String URL_CANCLE_FLLOW_MOVIE="movie/v1/verify/cancelFollowMovie?movieId=%s";
    //查询影片评论
    public static final String URL_QUERY_COMMENT="movie/v1/findAllMovieComment?movieId=%s&page=1&count=10";
    //添加用户对影片的评论
    public static final String URL_INSERT_COMMENT="movie/v1/verify/movieComment?id=%s&commentContent=%s";
    //查询影片评论回复
    public static final String URL_QUERY_COMMENT_REPLAY="movie/v1/findCommentReply?commentId=%s&page=%s&count=%s";
    //添加用户对评论的回复
    public static final String URL_INSERT_COMMENT_REPLAY="movie/v1/verify/commentReply?commentId=%s&replyContent=%s";
    //电影评论点赞
    public static final String URL_MOVIE_COMMENT_PRISE="movie/v1/verify/movieCommentGreat?commentId=%s";


    //根据影院ID查询该影院当前排期的电影列表
    public static final String URL_QUERY_BYCINEMAID_MOVIE="movie/v1/findAllMovieComment?cinemaId=%s";
    //根据电影ID和影院ID查询电影排期列表
    public static final String URL_QUERY_BYFILMID_BYCINEMAID_MOVIE="movie/v1/verify/movieComment?cinemasId=%s&movieId=%s";
    //根据电影ID查询当前排片该电影的影院列表
    public static final String URL_QUERY_FILMID_CINEMA="movie/v1/findCommentReply?movieId=%s";
    //购票下单
    public static final String URL_BUY="movie/v1/verify/buyMovieTicket";
    //支付
    public static final String URL_PAY="movie/v1/verify/movieComment?payType=%s&orderId=%s";
    //根据影院ID查询该影院下即将上映的电影列表
    public static final String URL_QUERY_BYCINEMAID_WILL="movie/v1/findCommentReply?cinemaId=%s";


    //查询推荐影院信息 - <影院>
    public static final String UEL_FIND_RECOMMEND_CINEMAS="cinema/v1/findRecommendCinemas?page=1&count=10";
    //查询附近影院
    public static final String UEL_FIND_NEARLY_CINEMAS="cinema/v1/findNearbyCinemas?longitude=116.30551391385724&latitude=40.04571807462411&page=1&count=10";
    //关注影院
    public static final String UEL_FOLLOW_CINEMA="cinema/v1/verify/followCinema?cinemaId=%s";
    //取消关注影院
    public static final String UEL_CANCEL_FOLLOW_CINEMA="cinema/v1/verify/cancelFollowCinema?cinemaId=%s";
    //查询电影信息明细
    public static final String URL_FIND_CINEMA_INFO="cinema/v1/findCinemaInfo?cinemaId=%s";
    //根据影院ID查询该影院当前排期的电影列表
    public static final String URL_FIND_MOVIE_LIST="movie/v1/findMovieListByCinemaId?cinemaId=%s";
    //根据电影ID和影院ID查询电影排期列表
    public static final String URL_FIND_MOVIE_SCHEDULE_LIST="movie/v1/findMovieScheduleList?cinemasId=%s&movieId=%s";
    //影院评论
    public static final String URL_CINEMA_COMMENT="cinema/v1/verify/cinemaComment";
    //查询影院用户评论列表
    public static final String URL_CINEMA_All_COMMENT="cinema/v1/findAllCinemaComment?cinemaId=%s&page=1&count=10";


}
