package com.hong.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
// import org.springframework.http.converter.HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;


@Configuration
public class HttpConverterConfig {

    // public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";

    // private Jackson2ObjectMapperBuilder getObjectMapperBuilder(){
    //     Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    //     builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
    //     builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    //     return builder;
    // }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objMapper.registerModule(module);

        return objMapper; 
        // return getObjectMapperBuilder().build();
    }

    // @Bean
    // public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
    //     return getObjectMapperBuilder();
    // }

    // @Bean
    // public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    //     ObjectMapper objMapper = getObjectMapperBuilder().build();
    //     return new MappingJackson2HttpMessageConverter(objMapper);
    // }

    // @Bean
    // public HttpMessageConverters customConverters() {
    //     ObjectMapper objMapper = getObjectMapperBuilder().build();
    //     return new HttpMessageConverters(new MappingJackson2HttpMessageConverter(objMapper));
    // }

}

