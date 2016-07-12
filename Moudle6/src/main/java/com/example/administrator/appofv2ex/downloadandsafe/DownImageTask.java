package com.example.administrator.appofv2ex.downloadandsafe;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/12.
 */
public class DownImageTask extends AsyncTask<String, Integer, Bitmap> {
    private String imageUrl;
    private ImageView imageView;
   private boolean ifAvoid;//判断是否需要防止错位
    public DownImageTask(ImageView imageView,boolean ifAvoid) {
        this.imageView = imageView;
        this.ifAvoid=ifAvoid;
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
        if(ifAvoid){
        if (result != null) {
            // 通过 tag 来防止图片错位
            if (imageView.getTag() != null && imageView.getTag().equals(imageUrl)) {
                imageView.setImageBitmap(result);
            }
        }}
        else {
            imageView.setImageBitmap(result);
        }
}
}
