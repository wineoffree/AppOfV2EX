package com.example.administrator.v2exofautoedit.saveordownload;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Administrator on 2016/7/8.
 */
public class CacheImage {
    private Context context;
    // 内存缓存默认大小 5M
    static final int MEM_CACHE_DEFAULT_SIZE = 5 * 1024 * 1024;
    // 一级内存缓存基于 LruCache
    private static LruCache<String, Bitmap> memCache;
    public CacheImage(Context context) {
        this.context = context;
        initMemCache();
    }
               //初始化内存缓存
    private static void initMemCache() {
        memCache = new LruCache<String, Bitmap>(MEM_CACHE_DEFAULT_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    //从内存缓存中拿
    public static Bitmap getBitmapFromMem(String url) {
        return memCache.get(url);
    }

    //加入到内存缓存中
    public static void putBitmapToMem(String url, Bitmap bitmap) {
        memCache.put(url, bitmap);
    }

}
