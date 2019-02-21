package com.bw.movie.movie.popupwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.moviebean.MovieCommentDetailsBean;
import com.bw.movie.bean.moviebean.MovieDetailsBean;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.movie.activity.FilmDetailsActivity;
import com.bw.movie.movie.adapter.FilmCommentAdapter;
import com.bw.movie.netutil.RetrofitManager;
import com.bw.movie.precenter.IPrecenter;
import com.bw.movie.precenter.IPrecenterImpl;
import com.bw.movie.view.IView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.Map;


/**
 * Author: 邵文龙
 * Date: 2019/1/26 20:10
 * Description: ${DESCRIPTION}
 */
public class PopuWindowComment implements IView {
    private PopupWindow popupWindow;
    private Context context;
    private MovieCommentDetailsBean.ReplayBean mResultBeanTwo;
    private int p=0;
    private int movieId;
    private FilmCommentAdapter mCommentAdapter;
    private IPrecenterImpl mIPrecenter;
    MovieCommentDetailsBean mDetailsBean;

    public PopuWindowComment(Context context, MovieCommentDetailsBean detailsBean, int movieid) {
        this.context = context;
        mDetailsBean = detailsBean;
        this.movieId = movieid;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }

        RelativeLayout inflate= (RelativeLayout) View.inflate(context, R.layout.film_popup_comment, null);
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

    private void setButtonListeners(final RelativeLayout inflate) {
        mIPrecenter = new IPrecenterImpl(this);
        RecyclerView film_comment_recycle = inflate.findViewById(R.id.film_comment_recycle);
        //评论图片
        ImageView comment_image = inflate.findViewById(R.id.comment_image);
        //收起
        ImageView film_details_button_down = inflate.findViewById(R.id.film_details_button_down);
        //收起按钮点击监听
        film_details_button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        mCommentAdapter = new FilmCommentAdapter(context,mDetailsBean.getResult());
        film_comment_recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        film_comment_recycle.setAdapter(mCommentAdapter);
        mCommentAdapter.setImageClick(new FilmCommentAdapter.onImageClickListener() {
            @Override
            public void onImgClick(int commentId, FilmCommentAdapter.ViewHolder holder, int position) {
                Map<String,String> map = new HashMap<>();
                map.put("commentId",commentId+"");
                mIPrecenter.startRequestData(Apis.URL_MOVIE_COMMENT_PRISE,map,RegisterBean.class,"post");
                p=position;
            }

            @Override
            public void onImgCancelClick(int commentId, FilmCommentAdapter.ViewHolder holder, int position) {
                Map<String,String> map = new HashMap<>();
                map.put("commentId",commentId+"");
                mIPrecenter.startRequestData(Apis.URL_MOVIE_COMMENT_PRISE,map,RegisterBean.class,"post");
                p=position;
            }

            @Override
            public void onImgCommentClick(int commentId) {
                mIPrecenter.startRequestData(String.format(Apis.URL_QUERY_COMMENT_REPLAY,commentId),null,MovieCommentDetailsBean.ReplayBean.class,"get");
            }


        });

        comment_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View comment_edit_show = li.inflate(R.layout.comment_edit_show, null);
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setView(comment_edit_show);

                final EditText comment_edit = comment_edit_show.findViewById(R.id.editTextDialogUserInput);

                //点击对话框以外的区域是否让对话框消失
                builder.setCancelable(true);
                //设置正面按钮
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //设置反面按钮
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String,String> map = new HashMap<>();
                        map.put("movieId",movieId+"");
                        map.put("commentContent",comment_edit.getText().toString());
                        mIPrecenter.startRequestData(Apis.URL_INSERT_COMMENT,map,RegisterBean.class,"post");


                    }
                });
                android.support.v7.app.AlertDialog dialog = builder.create();
                //显示对话框
                dialog.show();
            }
        });

    }

    @Override
    public void onSuccess(Object data) {
            if (data instanceof MovieCommentDetailsBean.ReplayBean){
                mResultBeanTwo = (MovieCommentDetailsBean.ReplayBean) data;
//                FilmCommentAdapter commentAdapter = new FilmCommentAdapter(context,MovieCommentDetailsBean.ReplayBean.class);
//                commentAdapter.setData(mResultBeanTwo.getResult());
            }else if (data instanceof RegisterBean){
                RegisterBean registerBean = (RegisterBean) data;
                if (registerBean.getMessage().equals("请先登陆")){

                /*LoginAlertDialog dialog = new LoginAlertDialog(getActivity());
                dialog.alert();*/
                    //LoginAlertDialog.alert(getActivity());
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                    builder.setTitle("提示：");
                    builder.setMessage("请先登录！");
                    builder.setIcon(R.mipmap.ic_launcher_round);
                    //点击对话框以外的区域是否让对话框消失
                    builder.setCancelable(true);
                    //设置正面按钮
                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    //设置反面按钮
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            context.startActivity(new Intent(context,LoginActivity.class));

                        }
                    });
                    android.support.v7.app.AlertDialog dialog = builder.create();
                    //显示对话框
                    dialog.show();
                }else if (registerBean.getMessage().equals("点赞成功")){
                    mCommentAdapter.setImgClick(null,p);
                    mDetailsBean.getResult().get(p).setIsGreat(1);
                    mDetailsBean.getResult().get(p).setGreatNum(mDetailsBean.getResult().get(p).getGreatNum()+1);
                    Toast.makeText(context,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
                }else if (registerBean.getMessage().equals("不能重复点赞")){

                    mCommentAdapter.setImgCancelClick(null,p);

                    Toast.makeText(context,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
                }else if (registerBean.getMessage().equals("请求成功")){

                    Toast.makeText(context,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
                }else if (registerBean.getMessage().equals("评论成功")){
                    mIPrecenter.startRequestData(String.format(Apis.URL_QUERY_COMMENT,mDetailsBean.getResult().get(p).getCommentId()),null,MovieCommentDetailsBean.class,"get");
                    Toast.makeText(context,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
    }

    @Override
    public void onFail(String error) {

    }
}
