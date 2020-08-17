//package com.fantaike.framework.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpInputMessage;
//import org.springframework.http.HttpOutputMessage;
//import org.springframework.http.converter.AbstractHttpMessageConverter;
//import org.springframework.http.converter.FormHttpMessageConverter;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class CustomerMessageConverter<T> extends AbstractHttpMessageConverter<T> {
//    private static final FormHttpMessageConverter formHttpMessageConverter=new FormHttpMessageConverter();
//    private static final ObjectMapper mapper=new ObjectMapper();
//
//    @Override
//    protected boolean supports(Class<?> aClass) {
//        return T.class==aClass;
//    }
//
//    @Override
//    protected T readInternal(Class<? extends T> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
//        Map<String,String> map=formHttpMessageConverter.read(null,httpInputMessage).toSingleValueMap();
//        return mapper.convertValue(map,aClass);
//    }
//
//    @Override
//    protected void writeInternal(T t, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
//
//    }
//}
//
//
