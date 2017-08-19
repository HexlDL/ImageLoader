package com.hexl.imageloder.image_cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by hexl on 2017/8/19.
 * <p>
 * 图片缓存
 */

class ImageCache {

    //imageCache
    private LruCache<String, Bitmap> mLruCache;

    ImageCache() {
        initImageCache();
    }

    private void initImageCache() {

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        int maxSize = maxMemory / 4;

        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {

                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public void put(String url, Bitmap bitmap) {
        mLruCache.put(url, bitmap);
    }

    public Bitmap get(String url) {
        return mLruCache.get(url);
    }
}




















