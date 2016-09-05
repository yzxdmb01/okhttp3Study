package com.example.jr.okhttp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
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
        findViewById(R.id.btn_download).setOnClickListener(view -> {
            //先要检测一下有没有读取sd卡的权限
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                //没有sd卡权限，得申请一下
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return;
            }


            //http://isujin.com/wp-content/uploads/2016/08/wallhaven-407775.png
            showProgressBar();
            Request request = new Request.Builder()
                    .url("http://isujin.com/wp-content/uploads/2016/08/wallhaven-407775.png")
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        tvConsole.setText("文件下载失败~");
                        hideProgressBar();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream inputStream = response.body().byteStream();
                    FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/logo.png"));
                    byte[] buffer = new byte[2048];
                    int len;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    runOnUiThread(() -> {
                        hideProgressBar();
                        tvConsole.setText("文件下载完成");
                    });
                }
            });

        });


        findViewById(R.id.btn_star_intent).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Main2Activity.class)));
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
