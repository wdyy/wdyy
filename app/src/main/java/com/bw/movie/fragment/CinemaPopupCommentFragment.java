package com.bw.movie.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.CinemaCommentBean;
import com.bw.movie.bean.CinemaPopupDetailsBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.fragment.adapter.CinemaCommentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 范瑞旗
 * Date: 2019/1/27 13:32
 * Description: 影院弹框评论-Fragment
 */
public class CinemaPopupCommentFragment extends BaseFragment {

    @BindView(R.id.fragment_cinema_recommend_popupWindow_comment_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.rl_comment)
    RelativeLayout mRelativeLayout;

    private int mCinemaId;
    private String mCommentContent;

    @Override
    public void initView(View view) {


    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);

        doNetRequestData(String.format(Apis.URL_CINEMA_All_COMMENT,mCinemaId),null,CinemaCommentBean.class,"get");


    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void sjx(CinemaPopupDetailsBean detailsBean){

         mCinemaId = detailsBean.getId();

    }

    @OnClick(R.id.fragment_cinema_recommend_popupWindow_comment_img_comment)
    public void onImgCommentClickListener(){

        // 显示评论框
        //mRelativeLayout.setVisibility(View.VISIBLE);
        View inflate= View.inflate(getActivity(), R.layout.item_cinema_comment, null);

        PopupWindow window = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setFocusable(true);
        window.setTouchable(true);
        window.showAsDropDown(inflate);

        final EditText textView_content = inflate.findViewById(R.id.comment_content);
        Button button = inflate.findViewById(R.id.comment_send);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCommentContent = textView_content.getText().toString().trim();

                Map<String, String> map = new HashMap<>();
                map.put("cinemaId",mCinemaId+"");
                map.put("commentContent",mCommentContent);

                doNetRequestData(Apis.URL_CINEMA_COMMENT,map,RegisterBean.class,"post");

            }
        });

    }



    @Override
    public int getContent() {
        return R.layout.fragment_cinema_detail_popup_comment;
    }

    @Override
    public void success(Object data) {



        if (data instanceof CinemaCommentBean){
            CinemaCommentBean cinemaCommentBean = (CinemaCommentBean) data;
            List<CinemaCommentBean.ResultBean> result = cinemaCommentBean.getResult();

            if (result.size()!=0){
                CinemaCommentAdapter adapter = new CinemaCommentAdapter(getActivity(), result);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

            }

        }else if (data instanceof RegisterBean){

            RegisterBean registerBean = (RegisterBean) data;
            Toast.makeText(getActivity(),registerBean.getMessage(),Toast.LENGTH_SHORT).show();

            doNetRequestData(String.format(Apis.URL_CINEMA_All_COMMENT,mCinemaId),null,CinemaCommentBean.class,"get");

        }
    }

    @Override
    public void fail(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
