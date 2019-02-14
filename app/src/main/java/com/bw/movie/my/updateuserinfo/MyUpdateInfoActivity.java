package com.bw.movie.my.updateuserinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Constant;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.updatehead.UpdateHeadEntity;
import com.bw.movie.my.updateuserinfo.bean.UpDateUserInfoEntity;
import com.bw.movie.my.updateuserpwd.MyUpdatePwdActivity;
import com.bw.movie.my.userInfo.MyInfoActivity;
import com.bw.movie.util.ImageUtil;
import com.bw.movie.util.LunBanUtil;
import com.bw.movie.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyUpdateInfoActivity extends BaseActivity {

    @BindView(R.id.mtouxiang)
    SimpleDraweeView mMtouxiang;
    @BindView(R.id.mnicheng)
    TextView mMnicheng;
    @BindView(R.id.mxingbie)
    TextView mMxingbie;
    @BindView(R.id.mshoujihao)
    TextView mMshoujihao;
    @BindView(R.id.myouxiang)
    TextView mMyouxiang;
    @BindView(R.id.mriqi)
    TextView mMriqi;
    @BindView(R.id.chongzhimima1)
    RelativeLayout mChongzhimima;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int sex1 = intent.getIntExtra(Constant.SEX, 0);
        if ("1".equals(sex1)) {
            mMxingbie.setText("男");
        } else if ("2".equals(sex1)) {
            mMxingbie.setText("女");
        }

        String email = intent.getStringExtra("mEmail");
        mMyouxiang.setText(email);
        String headPic = intent.getStringExtra(Constant.HEADPIC);
        if (headPic == null) {
            return;
        } else {
            Uri uri = Uri.parse(headPic);
            mMtouxiang.setImageURI(uri);
        }
        String nickName = intent.getStringExtra(Constant.NICKNAME);
        mMnicheng.setText(nickName);
        String phone1 = intent.getStringExtra(Constant.PHONE);
        mMshoujihao.setText(phone1);
        String s = intent.getStringExtra("s");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(Long.parseLong(s));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mMriqi.setText(df.format(gc.getTime()));


    }

    @Override
    public int getContent() {
        return R.layout.activity_my_update_info;
    }

    @Override
    public void success(Object data) {
        Toast.makeText(getApplicationContext(), "" + data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void fail(String error) {
        Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.mtouxiang, R.id.mnicheng, R.id.mxingbie, R.id.mriqi, R.id.mshoujihao, R.id.myouxiang, R.id.update_myinfo, R.id.chongzhimima1})
    public void onClick(View v) {
        String nickname = mMnicheng.getText().toString().trim();
        String sex = mMxingbie.getText().toString();
        String email = mMyouxiang.getText().toString();

        switch (v.getId()) {
            case R.id.mtouxiang:
                mMtouxiang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = View.inflate(getApplication(), R.layout.item_head, null);
                        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        popupWindow.showAsDropDown(v, 0, 0);
                        TextView paizhao = view.findViewById(R.id.paizhao);
                        paizhao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //相机的操作
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.addCategory("android.intent.category.DEFAULT");
                                startActivityForResult(intent, 0);
                                popupWindow.dismiss();
                            }
                        });

                        TextView xiangce = view.findViewById(R.id.xiangce);
                        xiangce.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent, 1);
                                popupWindow.dismiss();
                            }
                        });

                        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                });
                break;
            case R.id.mnicheng:
                break;
            case R.id.mxingbie:
                break;
            case R.id.mriqi:
                break;
            case R.id.mshoujihao:
                break;
            case R.id.myouxiang:
                break;

            case R.id.update_myinfo:

                Integer q = -1;
                if ("男".equals(sex)) {
                    q = 1;
                } else if ("女".equals(sex)) {
                    q = 2;
                }
                if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(email)) {
                    Toast.makeText(this, "请输入修改内容", Toast.LENGTH_SHORT).show();
                } else {
                    mMxingbie.setText(q + "");
                    //请求网络
                    Map<String, String> map = new HashMap<>();
                    map.put(Constant.NICKNAME, nickname);
                    map.put(Constant.SEX, String.valueOf(q));
                    map.put(Constant.EMAIL, email);
                    doNetRequestData(MineUrlConstant.UPDATEUSERINFO, map, UpDateUserInfoEntity.class, "post");
                    startActivity(new Intent(this, MyInfoActivity.class));
                    finish();
                }

                break;

            case R.id.chongzhimima1:
                startActivity(new Intent(getApplicationContext(), MyUpdatePwdActivity.class));
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //相机
            case 0:
                if (data == null) {
                    return;
                } else {
                    Bitmap data2 = data.getParcelableExtra("data");

                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getApplication().getContentResolver(), data2, null, null));
                    String data1 = ImageUtil.getPath(getApplicationContext(), uri);
                    File file = new File(data1);
                    getCamera(file);
                }
                break;
            //相册
            case 1:
                if (data == null) {
                    return;
                } else {
                    String path = ImageUtil.getPath(getApplicationContext(), data.getData());
                    LunBanUtil.getImage(MyUpdateInfoActivity.this, path, new LunBanUtil.OnFile() {
                        @Override
                        public void Success(File file) {
                            getCamera(file);
                        }
                    });
                }
                break;
        }
    }

    public void getCamera(File file) {
        Map<String, String> map = new HashMap<>();

        UpdateHeadEntity updateHeadEntity = new UpdateHeadEntity();
        if (!updateHeadEntity.getStatus().equals("0000")) {
            ToastUtil.Toast("上传失败");
        } else {
            String headPath = updateHeadEntity.getHeadPath();
            Uri uri = Uri.parse(headPath);
            mMtouxiang.setImageURI(uri);
        }
    }
}
