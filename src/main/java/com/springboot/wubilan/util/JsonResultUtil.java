package com.springboot.wubilan.util;

import java.util.HashMap;
import java.util.Map;

public class JsonResultUtil {
    private String  message;
    private String code;
    private Boolean success=true;
    private Map<Object,Object> data=new HashMap<>();

    public JsonResultUtil(){
        super();
    }

    public JsonResultUtil(Boolean success){
        super();
        this.success=success;
    }

    public JsonResultUtil(Boolean success,String code,String message){
        super();
        this.success=success;
        this.code=code;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Map<Object, Object> getData() {
        return data;
    }

    public void setData(Map<Object, Object> data) {
        this.data = data;
    }
}
