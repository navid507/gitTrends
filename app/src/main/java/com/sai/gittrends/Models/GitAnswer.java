package com.sai.gittrends.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SAI_U2 on 16/08/2018.
 */

public class GitAnswer {
    @SerializedName("items")
    @Expose
    private List<Repository> items = null;

    @SerializedName("total_count")
    @Expose
    private Integer total_count;

    @SerializedName("incomplete_results")
    @Expose
    private Boolean incomplete_results;

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public Boolean getIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(Boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }
}
