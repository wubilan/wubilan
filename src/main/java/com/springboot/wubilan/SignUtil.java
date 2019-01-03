package com.springboot.wubilan;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtil {

    public static String getSigature(String prefix, Map<String, String> param) {
        if (StringUtils.isBlank(prefix) || MapUtils.isEmpty(param)) {
            return null;
        }
        List<String> keyList = new ArrayList<String>(param.keySet());
        Collections.sort(keyList, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder(prefix);
        for (String key : keyList) {
            if (param.get(key) != null) {
                if (!key.equals("sign")) {
                    sb.append("&");
                    sb.append(key);
                    sb.append("=");
                    sb.append(param.get(key));
                }
            }
        }
        String md5Str = DigestUtils.md5Hex(sb.toString());
        return md5Str;
    }

}
