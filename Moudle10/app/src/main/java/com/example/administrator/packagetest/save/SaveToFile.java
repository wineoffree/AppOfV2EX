package com.example.administrator.packagetest.save;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/8.
 */
public class SaveToFile {

    FileOutputStream out=null;
    String filepath;
    String content;
    int index;
    public SaveToFile(int index) {
        this.index=index;
        filepath =Environment.getExternalStorageDirectory().getAbsolutePath()+"/myv2ex";
        makeRootDirectory(filepath);
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
