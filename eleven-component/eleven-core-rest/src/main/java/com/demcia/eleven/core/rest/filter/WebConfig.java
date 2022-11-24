package com.demcia.eleven.core.rest.filter;

import com.demcia.eleven.core.rest.autoconfigure.ElevenRestProperties;
import com.demcia.eleven.core.rest.support.RestApiPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebFluxConfigurer {
    private final ElevenRestProperties elevenRestProperties;

    @Override
    public void configurePathMatching(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(elevenRestProperties.getPrefix(), new RestApiPredicate(
                elevenRestProperties.getPackages(),
                elevenRestProperties.getAnnotations()
        ));
    }
}