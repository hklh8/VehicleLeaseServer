package com.hklh8.controller.support;

/**
 * Created by GouBo on 2018/1/2.
 */
public class SimpleResponse {

    public SimpleResponse(Object result) {
        this.result = result;
    }

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
