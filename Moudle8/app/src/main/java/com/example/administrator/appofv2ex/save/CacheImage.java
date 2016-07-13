package com.example.administrator.appofv2ex.save;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Administrator on 2016/7/13.
 */
public class CacheImage {
    // 内存缓存默认大小 5M
    static final int IMA_CACHE_DEFAULT_SIZE = 5 * 1024 * 1024;
    // 一级内存缓存基于 LruCache
    private static LruCache<String, Bitmap> imaCache;
    public CacheImage() {
        initImaCache();
    }
    //初始化内存缓存
    private static void initImaCache() {
        imaCache = new LruCache<String, Bitmap>(IMA_CACHE_DEFAULT_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    //从内存缓存中拿
    public static Bitmap getBitmap(String url) {
        return imaCache.get(url);
    }

    //加入到内存缓存中
    public static void putBitmap(String url, Bitmap bitmap) {
        try {
            imaCache.put(url, bitmap);
        }
      catch (Exception e){e.printStackTrace();}
    }

}