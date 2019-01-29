package com.bw.movie.movie.popupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.bean.moviebean.MovieDetailsBean;
import com.bw.movie.movie.adapter.FilmStillsAdapter;
import com.bw.movie.movie.frescotobitmap.FrescoBitmapCallback;
import com.bw.movie.movie.frescotobitmap.FrescoLoadUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: 王帅
 * Date: 2019/1/26 20:10
 * Description: ${DESCRIPTION}
 */
public class PopuWindowStills {
    private PopupWindow popupWindow;

    private Context context;
    private MovieDetailsBean resultBean;

    public PopuWindowStills(Context context, MovieDetailsBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //ConstraintLayout inflate = (ConstraintLayout)getLayoutInflater().inflate(R.layout.filmactivity_item_details, null);
        RelativeLayout inflate= (RelativeLayout) View.inflate(context, R.layout.film_popup_stills, null);
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
        RecyclerView film_stills_recycle = inflate.findViewById(R.id.film_stills_recycle);

        FilmStillsAdapter filmStillsAdapter = new FilmStillsAdapter(context);
        film_stills_recycle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        List<String> posterList = resultBean.getResult().getPosterList();
        final List<Bitmap> mlist = new ArrayList<>();
        for (int i = 0; i < posterList.size(); i++) {
            FrescoLoadUtil.getInstance().loadImageBitmap(posterList.get(i), new FrescoBitmapCallback<Bitmap>() {
                @Override
                public void onSuccess(Uri uri, Bitmap result) {
                    mlist.add(result);
                }

                @Override
                public void onFailure(Uri uri, Throwable throwable) {

                }

                @Override
                public void onCancel(Uri uri) {

                }
            });

        }
        filmStillsAdapter.setData(mlist);

        film_stills_recycle.setAdapter(filmStillsAdapter);


        //收起
        ImageView film_details_button_down = inflate.findViewById(R.id.film_details_button_down);
        //收起按钮点击监听
        film_details_button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
