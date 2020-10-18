package com.samboy.dmcc.home.model;

import java.util.List;

public class AreaResponse<T> {

    private List<T> data;

    public AreaResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
