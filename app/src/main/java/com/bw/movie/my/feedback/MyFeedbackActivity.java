package com.bw.movie.my.feedback;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.feedback.bean.MyOptionEntity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFeedbackActivity extends BaseActivity {
    @BindView(R.id.my_option_tj)
    Button mMyOptionTj;
    @BindView(R.id.my_option_info)
    EditText mMyOptionInfo;
    @BindView(R.id.viewsub_loading)
    ViewStub mViewsubLoading;

    @BindView(R.id.yijianlinear)
    LinearLayout yijianlinear;
    @BindView(R.id.my_back)
    ImageView mMyBack;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContent() {
        return R.layout.activity_my_feedback;
    }

    @Override
    public void success(Object data) {
        mViewsubLoading.setVisibility(View.INVISIBLE);
        String image = String.valueOf(R.drawable.my_option_success);
        Uri uri = Uri.parse(image);
        yijianlinear.setVisibility(View.GONE);
    }

    @Override
    public void fail(String error) {

    }

    @OnClick({R.id.my_option_tj, R.id.my_option_info, R.id.viewsub_loading, R.id.my_back})
    public void onClick(View v) {
        String info = mMyOptionInfo.getText().toString().trim();
        switch (v.getId()) {
            default:
                break;
            case R.id.my_option_tj:
                String trim = mMyOptionInfo.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Toast.makeText(this, "请输入您要反馈意见", Toast.LENGTH_SHORT).show();
                } else {
                   /* mPresenter = new MyOptionPresenter(this);
                    mPresenter.getOption(info);
                    isSuccess = true;*/
                    Map<String, String> map = new HashMap<>();
                    map.put("content", info);
                    doNetRequestData(MineUrlConstant.FEEDBACK, map,MyOptionEntity.class,"post");
                }
                break;
            case R.id.my_option_info:
                break;
            case R.id.viewsub_loading:
                break;
            case R.id.my_back:
                finish();
                break;
        }
    }
}
