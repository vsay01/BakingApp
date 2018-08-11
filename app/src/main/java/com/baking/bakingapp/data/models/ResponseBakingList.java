package com.baking.bakingapp.data.models;

import java.util.List;

public class ResponseBakingList {
    private List<BakingWS> list;
    private Throwable throwable;

    public ResponseBakingList(List<BakingWS> list) {
        this.list = list;
    }

    public ResponseBakingList(Throwable throwable) {
        this.throwable = throwable;
    }

    public List<BakingWS> getList() {
        return list;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
