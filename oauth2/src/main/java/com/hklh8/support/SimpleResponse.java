package com.hklh8.support;

/**
 * Created by GouBo on 2017/12/26.
 */
public class SimpleResponse {

    private Object result;

    public SimpleResponse(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
