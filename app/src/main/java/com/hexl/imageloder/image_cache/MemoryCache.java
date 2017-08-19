package com.hexl.imageloder.image_cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by hexl on 2017/8/19.
 * <p>
 * 内存缓存
 * <p>
 * 将图片缓存到 SD卡中
 */

public class MemoryCache implements ImageCache {

    private LruCache<String, Bitmap> mLruCache;

    public MemoryCache() {
        //计算可使用的最大缓存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取4分之1的可用内存作为缓存
        int cacheSize = maxMemory / 4;


        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mLruCache.put(url,bitmap);
    }

    @Override
    public Bitmap get(String url) {
        return mLruCache.get(url);
    }
}
