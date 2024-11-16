package com.eleven.core.configure;

import com.eleven.core.interfaces.rest.ElevenExceptionAdvice;
import com.eleven.core.interfaces.rest.ElevenResponseAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@RequiredArgsConstructor
class ElevenRestConfiguration {


    @Bean
    ElevenExceptionAdvice elevenExceptionAdvice() {
        return new ElevenExceptionAdvice();
    }

    @Order(1)
    @Bean
    ElevenResponseAdvice elevenResponseAdvice() {
        return new ElevenResponseAdvice();
    }


}
