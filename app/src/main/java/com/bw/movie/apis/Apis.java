package com.bw.movie.apis;

/**
 * Author: 范瑞旗
 * Date: 2019/1/24 14:33
 * Description: ${DESCRIPTION}
 */
public class Apis {
    //登录
    public static final String URL_LOGIN="user/v1/login";
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
    public static final String URL_QUERY_COMMENT="movie/v1/findAllMovieComment?movieId=%s&page=1&count=5";
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
    public static final String URL_BUY="movie/v1/verify/commentReply?commentId=%s&replyContent=%s";
    //支付
    public static final String URL_PAY="movie/v1/verify/movieComment?payType=%s&orderId=%s";
    //根据影院ID查询该影院下即将上映的电影列表
    public static final String URL_QUERY_BYCINEMAID_WILL="movie/v1/findCommentReply?cinemaId=%s";


}
