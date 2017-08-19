package com.hexl.imageloder.image_cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hexl on 2017/8/19.
 * <p>
 * 硬盘缓存
 * <p>
 * 将图片缓存到 SD卡中
 */
public class DiskCache implements ImageCache{

    private String cacheDir = "sdcard/cache";

    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(cacheDir + url);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(cacheDir + url);
    }
}


/*
//单一原则 code
public class DiskCache {

    private String cacheDir = "sdcard/cache";

    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(cacheDir + url);
    }

    public void put(String url, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(cacheDir + url);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
*/
