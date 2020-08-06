package com.fantaike.framework.asserts.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.framework.asserts.AbstractAssertParser;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component("httpAssertParser")
public class HttpAssertParser extends AbstractAssertParser<String> {

    private static final Logger logger = LoggerFactory.getLogger(HttpAssertParser.class);
    
    /**
     * 断言解析
     * @param model 断言参数
     * @return Result
     * 
     * 1.0包括 Header.contains("xx")  Body.contains("xx") Body.xx.xx.equals("xx")
     * {
     *     location:"Header",
     *     express:"",
     *     type:"contains",
     *     value:"xxx"
     * }
     */
    @Override
    public Result parse(String model) {
        if (StringUtils.isEmpty(model)) {
            return null;
        }
        List<HttpAssert> list = new ArrayList<>();
        try {
            JSONArray array = JSON.parseArray(model);
            for(int i=0; i<array.size(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String location = jsonObject.getString("location");
                String express = jsonObject.getString("express");
                String type = jsonObject.getString("type");
                String value = jsonObject.getString("value");
                HttpAssert httpAssert = new HttpAssert();
                httpAssert.setExpress(express);
                httpAssert.setLocation(location);
                httpAssert.setType(type);
                httpAssert.setValue(value);
                list.add(httpAssert);
            }
        } catch (Exception e) {
            logger.error("解析断言失败", e);
            return new Result(Constant.fail, "解析断言失败");
        }

        return new Result(Constant.success, "", list);
    }
    
}
