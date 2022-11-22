package com.demcia.eleven.core.rest.autoconfigure;

import com.demcia.eleven.core.rest.annonation.RestResource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.lang.annotation.Annotation;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "eleven.rest")
public class ElevenRestProperties {

    /**
     * 统一 API 路径，通常我们可以直接全局指定 context-path ，但是那样会造成静态文件、动态 HTML 都归属到这个路径下，如果只希望 rest 接口全局修改一个路径，则使用这个配置会很有效，
     * 比如你在 cms 系统中，首页肯定是要部署到根路径（“/ ”）的，但是后台接口可能需要部署到 /api 路径下。
     * 还有比如在本地开发的时候，这个可能很有效，你需要将前后台的接口地址统一，然后快速调用测试。
     */
    private String prefix = "/";
    /**
     * 指定这些包下的接口修改路径
     */
    private List<String> packages = List.of();
    /**
     * 指定包含哪些注解的接口修改路径
     */
    private List<Class<? extends Annotation>> annotations = List.of(RestResource.class);

}
