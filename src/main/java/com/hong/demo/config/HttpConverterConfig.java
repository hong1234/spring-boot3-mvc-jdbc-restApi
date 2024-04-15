package com.hong.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.format.DateTimeFormatter;


@Configuration
public class HttpConverterConfig {

    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objMapper = new ObjectMapper();

        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));

        objMapper
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .registerModule(module);

        return objMapper;    
    }

    // @Bean
    // public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
    //     Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    //     builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
    //     builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    //     return builder;
    // }

    // @Bean
    // public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    //     Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    //     builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
    //     builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    //     return new MappingJackson2HttpMessageConverter(builder.build());
    // }
}

