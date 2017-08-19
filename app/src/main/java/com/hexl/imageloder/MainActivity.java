package com.hexl.imageloder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.hexl.imageloder.image_cache.ImageLoader;

public class MainActivity extends AppCompatActivity {

    private String url = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1461001497,2601559119&fm=175&s=E0728A6767A195471A5448130100E0C0&w=640&h=383&img.JPEG";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.imageView);

        //加载图片并将其加入到内存缓存中
        ImageLoader imageLoader = new ImageLoader(url, iv);
        imageLoader.setDoubleCache(true);
        //从内存缓存中取出图片
        //Bitmap bitmap = new ImageCache().get(url);
    }
}
