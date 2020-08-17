//package com.fantaike.framework.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class MvcConfigurer  implements WebMvcConfigurer {
//    @Bean
//    public CustomerMessageConverter customerMessageConverter() {
//        CustomerMessageConverter converter=new CustomerMessageConverter();
//        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
//        return converter;
//    }
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(customerMessageConverter());
//    }
//}
