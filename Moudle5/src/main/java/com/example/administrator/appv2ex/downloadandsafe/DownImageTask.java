package com.example.administrator.appv2ex.downloadandsafe;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.jsoup.select.Elements;

import java.io.OutputStream;

/**
 * Created by Administrator on 2016/7/12.
 */
public class DownImageTask extends AsyncTask<String, Integer, Bitmap> {
    private String imageUrl;
    private ImageView imageView;

    public DownImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap=null;
        imageUrl = params[0];
        try {
           DownImage downImage=new DownImage(imageUrl);
            bitmap=downImage.loadImage();
        }
        catch (Exception e){e.printStackTrace();
        }
        return bitmap;
    }



    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        if (result != null) {
            // 通过 tag 来防止图片错位
            if (imageView.getTag() != null && imageView.getTag().equals(imageUrl)) {
                imageView.setImageBitmap(result);
            }
        }
}
}
