package com.example.jr.okhttp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
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
        ab.setTitle("调用封装后的方法");
        tvConsole = (TextView) findViewById(R.id.tv_console);
        tvConsole.setOnLongClickListener(v -> {
            ((TextView) v).setText("-");
            return true;
        });

        addLisenter();
    }

    private void addLisenter() {
        //get请求
        findViewById(R.id.btn_get).setOnClickListener(v -> {
            String url = "http://apis.baidu.com/apistore/weatherservice/weather";
            RequestParams params = new RequestParams();
            params.addParams("citypinyin", "beijing");
            HttpRequest.get(url, params, new BaseHttpCallBack<String>() {
                @Override
                void onFailure(Call call, Exception e) {
                    runOnUiThread(() -> tvConsole.setText(e.toString()));
                }

                @Override
                void onResponse(String s) {
                    runOnUiThread(() -> tvConsole.setText(s));
                }
            });
        });

        //post请求
        findViewById(R.id.btn_post).setOnClickListener(v -> {
            String url = "http://api.1-blog.com/biz/bizserver/xiaohua/list.do";
            RequestParams params = new RequestParams();
            params.addParams("size", "3");
            params.addParams("hehe", "你好");
            HttpRequest.post(url, params, new BaseHttpCallBack<Joke>() {
                @Override
                void onFailure(Call call, Exception e) {
                    runOnUiThread(() -> tvConsole.setText(e.toString()));
                }

                @Override
                void onResponse(Joke joke) {
                    runOnUiThread(() -> tvConsole.setText(joke.toString()));
                }
            });
        });

        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        String area = getTextFromAssets();
        try {
            JSONArray json = new JSONArray(area);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] strs = new String[]{"a", "b", "c"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("呵呵呵");
        builder.setItems(strs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvConsole.setText("which:" + which);
            }
        });
        builder.show();

    }


    public String getTextFromAssets() {
        InputStream is;
        try {
            is = this.getAssets().open("area.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
