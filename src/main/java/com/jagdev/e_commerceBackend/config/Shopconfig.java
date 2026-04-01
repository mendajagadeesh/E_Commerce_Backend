package com.jagdev.e_commerceBackend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Shopconfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
