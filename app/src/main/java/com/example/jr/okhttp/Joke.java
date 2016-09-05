package com.example.jr.okhttp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Joke {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("desc")
    @Expose
    private Object desc;
    @SerializedName("detail")
    @Expose
    private List<Detail> detail = new ArrayList<Detail>();

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

    /**
     * @return The desc
     */
    public Object getDesc() {
        return desc;
    }

    /**
     * @param desc The desc
     */
    public void setDesc(Object desc) {
        this.desc = desc;
    }

    /**
     * @return The detail
     */
    public List<Detail> getDetail() {
        return detail;
    }

    /**
     * @param detail The detail
     */
    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "detail=" + detail +
                '}';
    }
}