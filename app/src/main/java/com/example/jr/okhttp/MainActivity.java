package com.example.jr.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private TextView tvConsole;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setListener();
    }

    private void init() {
        okHttpClient = new OkHttpClient();
        tvConsole = (TextView) findViewById(R.id.tv_console);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    private void setListener() {
        tvConsole.setOnLongClickListener(view -> {
            tvConsole.setText("-");
            return false;
        });

        /**
         * 同步GET方法,Android 4.0之后，要求网络请求必须在工作线程中，
         * 不再允许在主线程中运行，所以要新起一个工作线程
         */
        findViewById(R.id.btn_get_sync).setOnClickListener(v -> {
                    showProgressBar();
                    new Thread(() -> {
                        Request request = new Request.Builder().url("https://api.github.com/").build();
                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            String resString = response.body().string();
                            runOnUiThread(() -> {
                                hideProgressBar();
                                tvConsole.setText("Get同步请求:\n" + resString);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();

                }

        );

        /**
         * 异步get方法
         */
        findViewById(R.id.btn_get_async).setOnClickListener(view -> {
            showProgressBar();
            Request request = new Request.Builder().url("https://api.github.com").build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        hideProgressBar();
                        tvConsole.setText("GET异步请求:onFailure\n" + e.toString());
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String resString = response.body().string();
                    Thread thread = Thread.currentThread();
                    runOnUiThread(() -> {
                        hideProgressBar();
                        tvConsole.setText("GET异步请求:\nThread:+" + thread + "\nstring:" + resString);
                    });
                }
            });
        });

        /**
         * 异步post请求
         */
        findViewById(R.id.btn_post_async).setOnClickListener(view -> {
            showProgressBar();
            RequestBody requestBody = new FormBody.Builder().add("size", "1").build();
            Request request = new Request.Builder()
                    .url("http://api.1-blog.com/biz/bizserver/xiaohua/list.do")
                    .post(requestBody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        hideProgressBar();
                        tvConsole.setText("异步POST请求:onFailure\n" + e.toString());
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resString = response.body().string();
                    runOnUiThread(() -> {
                        hideProgressBar();
                        tvConsole.setText("异步POST请求:\n" + resString);
                    });
                }
            });
        });

        /**
         * 文件的下载
         */
        findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
