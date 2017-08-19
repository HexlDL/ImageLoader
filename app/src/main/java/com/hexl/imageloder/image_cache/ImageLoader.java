package com.hexl.imageloder.image_cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hexl on 2017/8/19.
 * <p>
 * 图片加载
 */

public class ImageLoader {

    //图片缓存
    //private LruCache<String, Bitmap> mLruCache;

    //内存缓存
    private final ImageCache mImageCache;
    //硬盘缓存
    private final DiskCache mDiskCache;
    //双缓存
    private final DoubleCache mDoubleCache;

    //是否使用 SD卡缓存
    private boolean isUseSDKCache;
    //是否使用双缓存
    private boolean isDoubleCache;

    //线程池,线程数量为 CPU 的数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public ImageLoader(String url, ImageView iv) {
        //initImageCache();
        mImageCache = new ImageCache();
        mDiskCache = new DiskCache();
        mDoubleCache = new DoubleCache();

        displayImage(url, iv);
    }

    public void setDoubleCache(boolean doubleCache) {
        isDoubleCache = doubleCache;
    }

    public void setUseSDKCache(boolean useSDKCache) {
        isUseSDKCache = useSDKCache;
    }

    /* private void initImageCache() {
        //计算可使用的最大缓存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取4分之1的可用内存作为缓存
        int cacheSize = maxMemory / 4;

        *//*
         * 不太理解
         *//*
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

    }
*/

    /**
     * 下载成功后将图片显示到 image 上
     *
     * @param url       图片 url
     * @param imageView 控件
     */
    void displayImage(final String url, final ImageView imageView) {
        imageView.setTag(url);


        //不理解
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                //如果开启了硬盘缓存就从硬盘缓存中取,否则从内存中取
                //Bitmap bitmap = isUseSDKCache ? mDiskCache.get(url) : mImageCache.get(url);
                Bitmap bitmap = null;
                bitmap = getCache(url);

                //没有图片就从网络下载
                if (bitmap == null) {
                    return;
                }
                bitmap = downloadImage(url);


                if (imageView.getTag().equals(url)) {
                    imageView.setImageBitmap(bitmap);
                }

                isCache(url, bitmap);
                //mImageCache.put(url, bitmap);
                //mDiskCache.put(url, bitmap);
            }
        });
    }

    /**
     * 从缓存中取图片
     *
     * @param url
     * @return bitmap
     */
    private Bitmap getCache(String url) {
        Bitmap bitmap = null;
        if (isDoubleCache) {
            bitmap = mDoubleCache.get(url);
        } else if (isUseSDKCache) {
            bitmap = mDiskCache.get(url);
        } else {
            bitmap = mImageCache.get(url);
        }
        return bitmap;
    }


    /**
     * 判断使用哪一种缓存策略
     *
     * @param url
     * @param bitmap
     * @return
     */
    private void isCache(String url, Bitmap bitmap) {
        if (isDoubleCache) {
            mDoubleCache.put(url, bitmap);
        } else if (isUseSDKCache) {
            mDiskCache.put(url, bitmap);
        } else {
            mImageCache.put(url, bitmap);
        }
    }

    /**
     * 下载图片
     *
     * @param imageUrl 图片地址
     * @return bitmap
     */
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
