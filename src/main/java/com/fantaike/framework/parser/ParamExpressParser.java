package com.fantaike.framework.parser;

import com.fantaike.common.constant.Constant;
import com.fantaike.common.entity.Result;
import com.fantaike.framework.lang.DefaultSection;
import com.fantaike.framework.lang.Entry;
import com.fantaike.framework.lang.ParamSection;
import com.fantaike.framework.lang.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数解析
 */
public class ParamExpressParser extends AbstractParser<String, Entry<Section, List<ParamSection>>>{
    
    private static final Logger logger = LoggerFactory.getLogger(ParamExpressParser.class);

    /** 参数前缀 **/
    private static final String PARAM_PREFIX = "{{$";
    /** 参数后缀 **/
    private static final String PARAM_SUFFIX = "}}";
    /** 参数符号 **/
    private static final Character PARAM_SYMBOL = '$';
    /** 参数分隔符 **/
    private static final String PARAM_SPLIT = ",";
    private static final char PARAM_SPLIT_CHAR = ',';
    /** 不填参数默认值 **/
    private static final String DEFAULT_EMPTY_VALUE = "";
    /** 参数正则 **/
    private static final String regex = "^\\{\\{\\$[a-zA-Z0-9_.]*,[a-zA-Z0-9_]*}}";
    
    /**
     * 参数字符串解析,返回section链和参数集合
     * @param input 要解析的字符串
     * @return Entry
     */
    @Override
    public Entry<Section, List<ParamSection>> parse(String input) {
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        // 头部节点
        Section header = Section.emptyHeader();
        Section cn = header;

        // 参数节点
        List<ParamSection> paramSections = new ArrayList<>();

        //xxx{{$name,aaa}}xxx
        char[] chars = input.toCharArray();
        int left, right = 0, c = 0;
        int length = chars.length;
        while (right < length) {
            int first = input.indexOf(PARAM_PREFIX, right);
            if (first < 0) {
                // 没有剩余,全部添加
                DefaultSection section = new DefaultSection(input.substring(c, length));
                cn.setNext(section);
                break;
            }
            // 移动left
            left = first;
            // 移动rigth
            right = left + PARAM_PREFIX.length() - 1;
            if (right == length) {
                // 剩余{{,添加为默认节点
                DefaultSection section = new DefaultSection(input.substring(c, length));
                cn.setNext(section);
                break;
            } else if (right > length) {
                // end
                break;
            }
            // 匹配参数前置,判断参数后置
            if (chars[right] == PARAM_SYMBOL) {
                int suffixIndex = input.indexOf(PARAM_SUFFIX, right);
                if (suffixIndex > 0) {
                    if (suffixIndex == (right + 1)) {
                        // 参数无内容
                        continue;
                    }
                    //记录参数之前的内容
                    if (c != left) {
                        DefaultSection section = new DefaultSection(input.substring(c, left));
                        cn.setNext(section);
                        cn = section;
                    }
                    // 记录参数
                    ParamSection paramSection = new ParamSection();
                    String innerParams = input.substring(right + 1, suffixIndex);
                    //默认第一个 ',' 之前为key
                    int spitIndex = innerParams.indexOf(PARAM_SPLIT_CHAR);
                    if (spitIndex > 0) {
                        // 有默认值
                        paramSection.setKey(innerParams.substring(0, spitIndex));
                        paramSection.setDefaultValue(innerParams.substring(spitIndex + 1));
                    } else {
                        // 无默认值,使用默认值 ""
                        paramSection.setKey(innerParams);
                        paramSection.setDefaultValue(Constant.DEFAULT_VALUE);
                    }
                    // 设置表达式
                    String key = paramSection.getKey();
                    int index = key.indexOf(".");
                    if (index > 0) {
                        // 取值表达式
                        String firstKey = key.substring(0, index);
                        paramSection.setKey(firstKey);
                        if ((index+1) < key.length()) {
                            // data.xx  
                            String sKey = key.substring(index+1);
                            paramSection.setExpress(sKey);
                        }
                    }
                    cn.setNext(paramSection);
                    cn = paramSection;
                    // 记录参数节点
                    paramSections.add(paramSection);
                    // move index
                    c = right = suffixIndex + PARAM_SUFFIX.length();
                } else {
                    // append all
                    DefaultSection section = new DefaultSection(input.substring(c, length));
                    cn.setNext(section);
                    break;
                }
            }
        }

        return new Entry<>(header, paramSections);
    }
}
