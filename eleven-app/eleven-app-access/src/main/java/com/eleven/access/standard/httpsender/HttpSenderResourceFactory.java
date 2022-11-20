package com.eleven.access.standard.httpsender;

import com.cnetong.access.core.*;
import com.cnetong.access.core.openapi.MessageRequest;
import com.cnetong.access.core.openapi.MessageResponse;
import com.cnetong.common.json.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.cnetong.access.core.ComponentSpecification.PropertyType.number;
import static com.cnetong.access.core.ComponentSpecification.PropertyType.string;
import static com.cnetong.access.core.ComponentSpecification.property;

@Service
public class HttpSenderResourceFactory implements ResourceFactory {
    @Override
    public ComponentSpecification getSpecification() {

        return ComponentSpecification.of(Resource.class, "httpSender")
            .label("httpSender")
            .describe("将消息转发到另一个access或兼容的HTTP接口中")
            .property(
                property("uri", string).withLabel("URI").required(true).withPlaceholder("http://localhost:9000/messages"),
                property("headers", string).withLabel("HTTP请求头").required(false).withPlaceholder("token=abc"),
                property("poolSize", number).withLabel("最大连接数").required(false).withDefault("6"),
                property("connectTimeout", number).withLabel("连接超时时长").required(false).withDefault("60000"),
                property("readTimeout", number).withLabel("读取超时时长").required(false).withDefault("60000"),

                // 生产者属性
                property("producer", "topic", string).withLabel("topic").required(true).withDefault("")
            )

            .runtime();
    }

    @Override
    public Resource<?> create(Map<String, String> config) throws ComponentConfigException {
        return new HttpSenderResource(config);
    }

    public static class HttpSenderResource implements Resource<CloseableHttpClient> {

        private String uri;
        private String headers;
        private int poolSize = 6;
        private int connectTimeout = 60000;
        private int readTimeout = 60000;

        private PoolingHttpClientConnectionManager connManager;
        private CloseableHttpClient httpClient;

        public HttpSenderResource(Map<String, String> config) {
            this.update(config);
        }

        @Override
        public void check() throws Exception {

        }

        @Override
        public CloseableHttpClient getActual() {
            return httpClient;
        }

        @Override
        public void update(Map<String, String> config) {
            this.uri = config.get("uri");
            this.headers = config.get("headers");
            if (StringUtils.isNotBlank(config.get("poolSize"))) {
                this.poolSize = Integer.parseInt(config.get("poolSize"));
            }
            if (StringUtils.isNotBlank(config.get("connectTimeout"))) {
                this.connectTimeout = Integer.parseInt(config.get("connectTimeout"));
            }
            if (StringUtils.isNotBlank(config.get("readTimeout"))) {
                this.readTimeout = Integer.parseInt(config.get("readTimeout"));
            }

            destroy();

            connManager = new PoolingHttpClientConnectionManager();
            connManager.setMaxTotal(poolSize);
            connManager.setDefaultMaxPerRoute(20);
            RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(readTimeout)
                .build();
            httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connManager)
                .build();
        }

        @Override
        public void destroy() {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException ignored) {
                }
            }
            if (connManager != null) {
                connManager.shutdown();
            }
        }

        @Override
        public ResourceProducer createProducer(Map<String, String> config) {
            return new ResourceProducer() {
                @Override
                public void produce(Message message) throws Exception {
                    HttpPost httpPost = new HttpPost(uri);
                    httpPost.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
                    MessageRequest request = new MessageRequest();
                    request.setTopic(config.get("topic"));
                    request.setBody(message.asString());
                    Map<String, String> headerMap = new HashMap<>();
                    if (StringUtils.isNotBlank(headers)) {
                        for (String kvStr : StringUtils.split(StringUtils.trim(headers).replace(',', ';'), ";")) {
                            String[] groups = StringUtils.split(kvStr, "=");
                            if (null != groups && groups.length == 2) {
                                headerMap.put(StringUtils.trim(groups[0]), StringUtils.trim(groups[1]));
                            }
                        }
                    }
                    request.setHeaders(headerMap);
                    for (String key : headerMap.keySet()) {
                        httpPost.setHeader(key, headerMap.get(key));
                    }
                    httpPost.setEntity(new StringEntity(JsonUtil.toJson(request), message.getCharset()));
                    CloseableHttpResponse response = httpClient.execute(httpPost);
                    if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                        throw new IOException("接收端返回异常：" + response.getStatusLine().toString());
                    }
                    String resStr = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
                    MessageResponse resObject = JsonUtil.toObject(resStr, MessageResponse.class);
                    if (!Boolean.TRUE.equals(resObject.isSuccess())) {
                        throw new IOException("接收端返回异常：" + resStr);
                    }
                }

                @Override
                public void close() {
                }

                @Override
                public boolean isClosed() {
                    return false;
                }

                @Override
                public void check() throws Exception {
                }
            };
        }

        @Override
        public ResourceConsumer createConsumer(Map<String, String> config) {
            return null;
        }
    }
}
