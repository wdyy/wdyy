package com.bw.movie.movie.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
import com.bw.movie.movie.adapter.FilmNoticeAdapter;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Author: 王帅
 * Date: 2019/1/26 20:10
 * Description: ${DESCRIPTION}
 */
public class PopuWindowNotice {
    private PopupWindow popupWindow;

    private Context context;
    private MovieDetailsBean resultBean;

    public PopuWindowNotice(Context context, MovieDetailsBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //ConstraintLayout inflate = (ConstraintLayout)getLayoutInflater().inflate(R.layout.filmactivity_item_details, null);
        RelativeLayout inflate= (RelativeLayout) View.inflate(context, R.layout.film_popup_notice, null);
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
        RecyclerView film_notice_recycle = inflate.findViewById(R.id.film_notice_recycle);
        //收起
        ImageView film_details_button_down = inflate.findViewById(R.id.film_details_button_down);
        //收起按钮点击监听
        film_details_button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        FilmNoticeAdapter filmNoticeAdapter = new FilmNoticeAdapter(context);
        filmNoticeAdapter.setData(resultBean.getResult().getShortFilmList());
        film_notice_recycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        film_notice_recycle.setAdapter(filmNoticeAdapter);
    }

}
