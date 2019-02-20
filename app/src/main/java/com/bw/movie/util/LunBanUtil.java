package com.bw.movie.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 郭佳兴
 * 图片压缩完成
 */
public class LunBanUtil {
    public static void getImage(Context context, String image, final OnFile onFile) {
        Luban.with(context)
                .load(image)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        ToastUtil.Toast("正在压缩");
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        onFile.Success(file);
                        ToastUtil.Toast("压缩成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.Toast("压缩失败");
                    }
                }).launch();
    }

    private static String getPath() {
        String path =Environment.getExternalStorageDirectory().getAbsolutePath()+"/luban/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public interface OnFile{
        void Success(File file);
    }

}
