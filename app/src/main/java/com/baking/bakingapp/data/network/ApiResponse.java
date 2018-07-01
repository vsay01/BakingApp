package com.baking.bakingapp.data.network;

import com.baking.bakingapp.data.models.BakingWS;

public class ApiResponse {
    private BakingWS bakingResponses;
    private Throwable error;

    public ApiResponse(BakingWS bakingWS) {
        this.bakingResponses = bakingWS;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.bakingResponses = null;
    }

    public Throwable getError() {
        return error;
    }

    public BakingWS getBakingResponses() {
        return bakingResponses;
    }
}