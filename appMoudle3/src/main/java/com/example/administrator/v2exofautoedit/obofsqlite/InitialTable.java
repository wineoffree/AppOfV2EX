package com.example.administrator.v2exofautoedit.obofsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/7/6.
 */
public class InitialTable {
            private MyDatabaseHelper dbHelper;
            SQLiteDatabase db;

     public InitialTable(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
         dbHelper=new MyDatabaseHelper(context,name, factory, version);
         db=dbHelper.getWritableDatabase();

     }
    public void  Insert(){
        ContentValues values=new ContentValues();
        values.put("id",1);
        values.put("name","李辉");
        db.insert("user",null,values);
        values.clear();
        values.put("id",2);
        values.put("name","吴鹏");
        db.insert("user",null,values);
    }
    public SQLiteDatabase getdb(){
        return  this.db;
    }
}
