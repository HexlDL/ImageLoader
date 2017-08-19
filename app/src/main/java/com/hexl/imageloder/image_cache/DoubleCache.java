package com.hexl.imageloder.image_cache;

import android.graphics.Bitmap;

/**
 * Created by hexl on 2017/8/19.
 * <p>
 * 双缓存策略,
 * <p>
 * 遵循先从内存中取,如果内存中不存在,再从硬盘中获取
 */

public class DoubleCache {

    private DiskCache mDiskCache;
    private ImageCache mImageCache;

    public DoubleCache() {
        mDiskCache = new DiskCache();
        mImageCache = new ImageCache();
    }

    /**
     * 从缓存中获取图片
     *
     * @param url key
     * @return Bitmap
     */
    public Bitmap get(String url) {
        //从内存中获取
        Bitmap bitmap = mImageCache.get(url);
        if (bitmap == null) {
            //从硬盘获取
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    /**
     * 加入到缓存中
     *
     * @param url    key
     * @param bitmap value
     */
    public void put(String url, Bitmap bitmap) {
        mDiskCache.put(url, bitmap);
        mImageCache.put(url, bitmap);
    }

}
