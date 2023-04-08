package com.eleven.core.generate.autoconfigure;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.domain.identity")
public class IdentityProperties {

    private IdGeneratorType idType = IdGeneratorType.RAINDROP;
    /**
     * 数据中心ID
     */
    private long datacenterId = 1;

    /**
     * 最大的机器号 ID
     */
    private long maxWorkerId = 1024;


    enum IdGeneratorType {
        UUID,
        SNOWFLAKE,
        RAINDROP,
        NANOID,
        OBJECT
    }

}
