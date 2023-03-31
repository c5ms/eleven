package com.demcia.eleven.domain.identity.autoconfigure;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.domain.identity")
public class IdentityProperties {

    private IdGeneratorType idType = IdGeneratorType.UUID;
    private Snowflake snowflake = new Snowflake();
    private Raindrop raindrop = new Raindrop();


    enum IdGeneratorType {
        UUID,
        SNOWFLAKE,
        RAINDROP
    }

    @Getter
    @Setter
    public static class Snowflake {

        /**
         * 如果是雪花算法，需要配置工作ID
         */
        private long wordId = 1;
        /**
         * 如果是雪花算法，需要配置数据中心ID
         */
        private long datacenterId = 1;
    }

    @Getter
    @Setter
    public static class Raindrop {

        /**
         * 如果是雨滴算法，需要配置前缀
         */
        private String prefix = "";
    }


}
