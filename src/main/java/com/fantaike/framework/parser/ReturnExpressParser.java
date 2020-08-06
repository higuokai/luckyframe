package com.fantaike.framework.parser;

import com.fantaike.common.entity.Result;
import com.fantaike.framework.lang.ReturnSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ReturnExpressParser implements Parser<String>{
    
    private static final Logger logger = LoggerFactory.getLogger(ReturnExpressParser.class);
    
    public List<ReturnSection> parse(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }

        // data=result   name=result.aa
        String[] split = string.split(";");
        logger.info("定义的返回结果是:{}", printfInfo(split));

        List<ReturnSection> list = new ArrayList<>();

        for (String s : split) {
            ReturnSection section = new ReturnSection();
            // name=result
            String[] kv = s.split("=");
            String key = kv[0];
            // 表达式
            String express = kv[1];
            section.setName(key);
            section.setExpress(express);
            list.add(section);
        }
        return list;
    }

    private String printfInfo(String ...msg) {
        StringBuilder sb = new StringBuilder();
        for (String s : msg) {
            sb.append(s).append("\r\n");
        }
        return sb.toString();
    }
}
