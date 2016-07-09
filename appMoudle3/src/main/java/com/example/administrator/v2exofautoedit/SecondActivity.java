package com.example.administrator.v2exofautoedit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.v2exofautoedit.obofsqlite.InitialTable;

public class SecondActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Cursor cursor;
    TextView id,name;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        InitialTable initialTable=new InitialTable(this,"UserStore.db",null,2);
         db=initialTable.getdb();
         editText=(EditText) findViewById(R.id.infomation);
        TextView ifsure =(TextView)findViewById(R.id.ifsure);
        id=(TextView)findViewById(R.id.id);
        name=(TextView)findViewById(R.id.name);
        ifsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor=db.query("user",null,"id like'"+editText.getText().toString()+"'",null,null,null,null);
                if(cursor.moveToFirst()){
                id.setText(cursor.getString(cursor.getColumnIndex("id")));
                name.setText(cursor.getString(cursor.getColumnIndex("name")));}
                else {
                    Toast.makeText(SecondActivity.this,"unfind",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
