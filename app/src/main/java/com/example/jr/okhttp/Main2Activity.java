package com.example.jr.okhttp;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import okhttp3.Call;

/**
 * 用于测试封装后的okhttp方法调用
 * <p>
 * HttpRequest.post(url,params,new HttpRequestCallBack(){
 * <p>
 * });
 */
public class Main2Activity extends AppCompatActivity {
    private TextView tvConsole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("简单的封装测试");
        tvConsole = (TextView) findViewById(R.id.tv_console);
        findViewById(R.id.btn_post).setOnClickListener(view -> post());
    }

    private void post() {
        RequestParams params = new RequestParams();
        params.addParams("size", "1");
        HttpRequest.post("http://api.1-blog.com/biz/bizserver/xiaohua/list.do", params, new BaseHttpCallBack<Joke>() {

            @Override
            void onFailure(Call call, Exception e) {

            }

            @Override
            void onResponse(Joke joke) {
                runOnUiThread(() -> tvConsole.setText(joke.toString()));
            }
        });
    }

}
