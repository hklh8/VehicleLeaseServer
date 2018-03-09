package com.hklh8;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

public class BaseResponse {

    private static final String emObject = "{}";
    private static final String ER = "er";
    private static final String OK = "ok";

    private String stat;
    private String fun;
    private String cbk;

    public BaseResponse() {
        this.cbk = emObject;
    }

    public <T> T getCbkObj(Class<T> clz) {
        if (StringUtils.isBlank(stat) || ER.equals(stat)) {
            return null;
        }
        return JSON.parseObject(cbk, clz);
    }

    public boolean isStat() {
        return OK.equals(stat);
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getFun() {
        return fun;
    }

    public void setFun(String fun) {
        this.fun = fun;
    }

    public String getCbk() {
        return cbk;
    }

    public void setCbk(String cbk) {
        this.cbk = cbk;
    }

}
