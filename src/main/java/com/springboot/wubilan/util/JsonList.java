package com.springboot.wubilan.util;

import java.util.ArrayList;
import java.util.List;

public class JsonList<T> extends JsonResultUtil {
    private List<T> list=new ArrayList<>();

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
