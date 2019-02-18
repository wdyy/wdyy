package com.bw.movie.bean;

/**
 * Author: 范瑞旗
 * Date: 2019/1/28 13:43
 * Description: 往选座页传递数据
 */
public class CinemaSeatTableDetailBean {

    private int scheduleId;
    private String MovieName;
    private String beginTime;
    private String endTime;
    private String hall;
    private double price;


    public CinemaSeatTableDetailBean(int scheduleId, String movieName, String beginTime, String endTime, String hall, double price) {
        this.scheduleId = scheduleId;

        MovieName = movieName;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.hall = hall;
        this.price = price;
    }

    public int getScheduleId() {
        return scheduleId;
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

    public double getPrice() {
        return price;
    }
}
