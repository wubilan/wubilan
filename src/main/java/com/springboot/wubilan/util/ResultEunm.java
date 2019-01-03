package com.springboot.wubilan.util;

import java.util.ArrayList;
import java.util.List;

public enum  ResultEunm {
    SUCCESS("SUCCESS","成功"),
    FAIL("FAIL","失败"),;
    private final String code;
    private final   String  message;

    ResultEunm(String code, String message) {
        this.code = code;
        this.message = message;
        Object object;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     * @param code
     * @return
     */
    public ResultEunm getByCode(String code){
        for (ResultEunm resultEunm : values()){
            if (resultEunm.getCode().equals(code)){
                return resultEunm;
            }
        }
        return null;
    }

    public List<ResultEunm> getAllEunm(){
        List<ResultEunm> resultEunmList=new ArrayList<>();
        for (ResultEunm resultEunm : values()){
            resultEunmList.add(resultEunm);
        }
        return resultEunmList;
    }
}
