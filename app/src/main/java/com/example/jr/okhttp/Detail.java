package com.example.jr.okhttp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("xhid")
    @Expose
    private Integer xhid;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("picUrl")
    @Expose
    private String picUrl;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The xhid
     */
    public Integer getXhid() {
        return xhid;
    }

    /**
     * @param xhid The xhid
     */
    public void setXhid(Integer xhid) {
        this.xhid = xhid;
    }

    /**
     * @return The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return The picUrl
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * @param picUrl The picUrl
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", xhid=" + xhid +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}