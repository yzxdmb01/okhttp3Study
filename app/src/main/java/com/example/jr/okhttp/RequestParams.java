package com.example.jr.okhttp;

import android.net.Uri;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 请求参数
 * Created by Administrator on 2016-09-05.
 */

public class RequestParams {
    LinkedHashMap<String, String> params = new LinkedHashMap<>();
    List<FileWrapper> files = new ArrayList<>();

    public RequestParams() {
    }

    public void addParams(String key, String value) {
        params.put(key, value);
    }

    public void addParams(String key, String fileName, File file) {
        files.add(new FileWrapper(key, fileName, file));
    }

    public RequestBody getRequestBody() {
        RequestBody body;
        //应该判断参数类型为string还是包含文件的
        if (files.size() <= 0) {
            //说明没有上传file
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            body = builder.build();
            return body;
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            for (FileWrapper file : files) {
                RequestBody fileBody = RequestBody.create(MediaType.parse(getContentType(file.getFile().getPath())), file.getFile());
                builder.addFormDataPart(file.getKey(), file.getFileName(), fileBody);
            }
            return builder.build();
        }

    }

    public String append2Url(String url) {
        if (params == null || params.size() == 0) {
            return url;
        }
        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return uriBuilder.build().toString();
    }

    public String getContentType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = null;
        try {
            contentType = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentType == null) contentType = "application/octet-stream";
        return contentType;
    }

    class FileWrapper {
        String key;
        String fileName;
        File file;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public FileWrapper(String key, String fileName, File file) {
            this.key = key;
            this.fileName = fileName;
            this.file = file;
        }
    }
}
