package com.bw.movie.movie.frescotobitmap;

import android.net.Uri;

/**
 * Author: 王帅
 * Date: 2019/1/28 9:33
 * Description: ${DESCRIPTION}
 */
public interface FrescoBitmapCallback<T> {
    void onSuccess(Uri uri, T result);

    void onFailure(Uri uri, Throwable throwable);

    void onCancel(Uri uri);

}
