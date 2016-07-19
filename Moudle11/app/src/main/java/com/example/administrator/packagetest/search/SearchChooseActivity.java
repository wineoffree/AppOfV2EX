package com.example.administrator.packagetest.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.packagetest.R;

public class SearchChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchchoose);
        Button chooseUser=(Button)findViewById(R.id.chooseuser);
        chooseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchChooseActivity.this,SearchUserActivity.class);
                startActivity(intent);
            }
        });
         Button chooseitem=(Button)findViewById(R.id.chooseitem);
        chooseitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchChooseActivity.this,SearchItemActivity.class);
                startActivity(intent);
            }
        });

    }
}
