package com.example.bimosektiw.searchnews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SourceRespModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sources")
    @Expose
    private List<SourceListModel> sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SourceListModel> getSources() {
        return sources;
    }

    public void setSources(List<SourceListModel> sources) {
        this.sources = sources;
    }
}
