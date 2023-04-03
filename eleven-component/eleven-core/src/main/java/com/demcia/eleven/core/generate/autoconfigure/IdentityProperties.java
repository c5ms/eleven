package com.demcia.eleven.core.generate.autoconfigure;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.domain.identity")
public class IdentityProperties {

    private IdGeneratorType idType = IdGeneratorType.RAINDROP;
    private Snowflake snowflake = new Snowflake();
    private Raindrop raindrop = new Raindrop();

    enum IdGeneratorType {
        UUID,
        SNOWFLAKE,
        RAINDROP,
        NANOID,
        OBJECT
    }

    @Getter
    @Setter
    public static class Snowflake {
        /**
         * 如果是雪花算法，需要配置数据中心ID
         */
        private long datacenterId = 1;
    }

    @Getter
    @Setter
    public static class Raindrop {
        /**
         * 如果是雨滴算法，需要配置数据中心ID
         */
        private long datacenterId = 1;
    }


}
