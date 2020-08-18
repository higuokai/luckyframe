package com.fantaike.framework.parser.http;

import com.fantaike.framework.lang.Entry;
import com.fantaike.framework.lang.ParamSection;
import com.fantaike.framework.lang.Section;
import com.fantaike.framework.parser.AbstractBodyParser;
import com.fantaike.framework.parser.ParamExpressParser;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("jsonBodyParser")
public class HttpBodyRawParser extends AbstractBodyParser<Section , Entry<Section, List<ParamSection>>> {
    
    @Override
    public Entry<Section, List<ParamSection>> parse(String model) {
        return new ParamExpressParser().parse(model);
    }

    @Override
    public RequestBody getValue(Section model) {
        // 纯文本直接拼装
        StringBuilder sb = new StringBuilder("");
        if (model != null) {
            while(model.hasNext()) {
                model = model.getNext();
                sb.append(model.getValue());
            }
        }
        // 组装FormBody,现在只json格式,后续添加
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"), sb.toString());
        return requestBody;
    }
}
