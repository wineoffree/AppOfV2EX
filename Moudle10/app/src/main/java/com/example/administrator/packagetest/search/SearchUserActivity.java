package com.example.administrator.packagetest.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.packagetest.R;

public class SearchUserActivity extends AppCompatActivity {
    EditText editText;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        editText=(EditText)findViewById(R.id.username);
        webView=(WebView)findViewById(R.id.userresult);
        ImageButton imageButton=(ImageButton)findViewById(R.id.sure);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=editText.getText().toString();
                String newUrl="http://www.v2ex.com/member/"+url;
                webView.loadUrl(newUrl);
            }
        });
    }
}
