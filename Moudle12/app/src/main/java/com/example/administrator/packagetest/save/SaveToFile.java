package com.example.administrator.packagetest.save;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.packagetest.MainActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/8.
 */
public class SaveToFile {
    Context context;
    FileOutputStream out=null;
    String filepath;
    String content;
    int index;
    public SaveToFile(int index) {
        this.index=index;
        filepath =Environment.getExternalStorageDirectory().getAbsolutePath()+"/myv2ex";
        makeRootDirectory(filepath);
    }
    public SaveToFile(Context context){
        this.context=context;
    }
    public  void deleteFile(){
          final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                Toast.makeText(context,"删除完毕",Toast.LENGTH_SHORT).show();
            }

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("papapa","hahahah");
                File files = null;
                try {
                    files = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/myv2ex");
                    if (files.exists()) {
                        File Files[] = files.listFiles();
                        if (files != null)
                            for (File f : Files) {
                                f.delete();
                            }
                        Message message = Message.obtain();
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                };

            }
        }).start();

    }

    public void setContent(String content){

        this.content=content;
    }

    // 生成主文件夹
    public  void makeRootDirectory(String filepath) {
        File files = null;
        try {
            files = new File(filepath);
            if (!files.exists()) {
                files.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        };

    }

    public void  saveContent(){

        File file;

        file = new File(filepath+"/save"+String.valueOf(index)+"content.txt");
        BufferedWriter writer=null;
        try {
            if(!file.createNewFile()) {
                //file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("File already exists");
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
        }
        catch (Exception e){e.printStackTrace();}
        try {
            out.flush();
            out.close();
        }
        catch (Exception e){e.printStackTrace();}
    }

}
