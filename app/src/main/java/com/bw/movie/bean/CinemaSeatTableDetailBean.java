package com.bw.movie.bean;

/**
 * Author: 范瑞旗
 * Date: 2019/1/28 13:43
 * Description: 往选座页传递数据
 */
public class CinemaSeatTableDetailBean {

    private int cinemasId;
    private String MovieName;
    private String beginTime;
    private String endTime;
    private String hall;
    private int seatsTotal;

    public CinemaSeatTableDetailBean(int cinemasId, String movieName, String beginTime, String endTime, String hall, int seatsTotal) {
        this.cinemasId = cinemasId;
        MovieName = movieName;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.hall = hall;
        this.seatsTotal = seatsTotal;
    }

    public int getCinemasId() {
        return cinemasId;
    }

    public String getMovieName() {
        return MovieName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getHall() {
        return hall;
    }

    public int getSeatsTotal() {
        return seatsTotal;
    }
}
