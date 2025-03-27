package com.spring_boot.spring_boot_rest_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    // This class is used to configure ModelMapper, a library for object mapping in Java.
    // It is currently empty but can be used to define custom mappings or settings for ModelMapper.
    // For example, you can create a ModelMapper bean and customize its properties here.

    // Example:
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
