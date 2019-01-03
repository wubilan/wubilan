package com.springboot.wubilan.util;

import com.github.pagehelper.PageInfo;

public class JsonPageInfo<T> extends JsonResultUtil {
    private PageInfo<T>pageInfo;

    public PageInfo<T> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<T> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
