package com.hexl.imageloder;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.hexl.imageloder.image_cache.DiskCache;
import com.hexl.imageloder.image_cache.DoubleCache;
import com.hexl.imageloder.image_cache.ImageCache;
import com.hexl.imageloder.image_cache.ImageLoader;
import com.hexl.imageloder.image_cache.MemoryCache;

public class MainActivity extends AppCompatActivity {

    private String url = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1461001497,2601559119&fm=175&s=E0728A6767A195471A5448130100E0C0&w=640&h=383&img.JPEG";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.imageView);
        final ImageLoader imageLoader = new ImageLoader(url, iv);

        //开闭原则 code

        //硬盘缓存
        imageLoader.setImageCache(new DiskCache());
        //内存缓存
        imageLoader.setImageCache(new MemoryCache());
        //双缓存
        imageLoader.setImageCache(new DoubleCache());

        imageLoader.setImageCache(new ImageCache() {
            @Override
            public void put(String url, Bitmap bitmap) {
                //缓存图片
            }

            @Override
            public Bitmap get(String url) {
                //从缓存中取图片
                return null;
            }
        });








        //单一原则 code
        //加载图片并将其加入到内存缓存中
        //imageLoader.setDoubleCache(true);
        //从内存缓存中取出图片
        //Bitmap bitmap = new ImageCache().get(url);
    }
}
