package com.bw.movie.movie.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.moviebean.MovieDetailsBean;
import com.bw.movie.custom.CollapsibleTextView;
import com.bw.movie.movie.adapter.FilmCosplayAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.regex.Pattern;


/**
 * Author: 王帅
 * Date: 2019/1/26 20:10
 * Description: ${DESCRIPTION}
 */
public class PopuWindowDetails{
    private PopupWindow popupWindow;

    private Context context;
    private MovieDetailsBean resultBean;

    public PopuWindowDetails(Context context, MovieDetailsBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //ConstraintLayout inflate = (ConstraintLayout)getLayoutInflater().inflate(R.layout.filmactivity_item_details, null);
        RelativeLayout inflate= (RelativeLayout) View.inflate(context, R.layout.film_popup_details, null);
        popupWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(inflate);
    }

    private void setButtonListeners(RelativeLayout inflate) {
        //图片
        SimpleDraweeView film_details_button_image = inflate.findViewById(R.id.film_details_button_image);
        //收起
        ImageView film_details_button_down = inflate.findViewById(R.id.film_details_button_down);
        //电影类型、导演、时长、产地
        TextView film_details_button_type = inflate.findViewById(R.id.film_details_button_type);
        TextView film_details_button_director = inflate.findViewById(R.id.film_details_button_director);
        TextView film_details_button_time = inflate.findViewById(R.id.film_details_button_time);
        TextView film_details_button_location = inflate.findViewById(R.id.film_details_button_location);
        //剧情简介
        CollapsibleTextView film_details_button_message = inflate.findViewById(R.id.film_details_button_message);
        //演员详情
        RecyclerView film_details_button_recycle = inflate.findViewById(R.id.film_details_button_recycle);

        //设置文本
        film_details_button_image.setImageURI(resultBean.getResult().getImageUrl());
        film_details_button_type.setText(resultBean.getResult().getMovieTypes());
        film_details_button_director.setText(resultBean.getResult().getDirector());
        film_details_button_time.setText(resultBean.getResult().getDuration());
        film_details_button_location.setText(resultBean.getResult().getPlaceOrigin());
        film_details_button_message.setDesc(resultBean.getResult().getSummary());
        //收起按钮点击监听
        film_details_button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        FilmCosplayAdapter cosplayAdapter = new FilmCosplayAdapter(context,resultBean.getResult().getStarring());
        film_details_button_recycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        film_details_button_recycle.setAdapter(cosplayAdapter);
    }

}
