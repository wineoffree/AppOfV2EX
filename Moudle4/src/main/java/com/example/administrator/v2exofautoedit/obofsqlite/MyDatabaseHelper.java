package com.example.administrator.v2exofautoedit.obofsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/6.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{
     public static final String CREATE_USER ="create table user("
             +"id integer primary key ,"
             +"url text)";


    public  MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);

    }
    @Override
    public  void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_USER);
    }
    public void Insert(){

    }
    public void Find(){

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                 sqLiteDatabase.execSQL("drop table if exists user");
        onCreate(sqLiteDatabase);
    }
}
