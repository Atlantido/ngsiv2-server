/*
 * Copyright (C) 2015 Orange
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hopu.client;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;

import javax.annotation.Resource;

/**
 * Configure HTTP connections
 */
@Configuration
public class HttpConfiguration {

    @Value("${ngsi.http.maxTotalConnections:20}")
    private int maxTotalConnections;

    @Value("${ngsi.http.maxConnectionsPerRoute:2}")
    private int maxConnectionsPerRoute;

    @Value("${ngsi.http.requestTimeout:2000}")
    private int requestTimeout;

    @Bean
    @Resource(name = "jsonV1Converter")
    public AsyncRestTemplate asyncRestTemplate(AsyncClientHttpRequestFactory asyncClientHttpRequestFactory,
            MappingJackson2HttpMessageConverter jsonConverter) {
        AsyncRestTemplate restTemplate = new AsyncRestTemplate(asyncClientHttpRequestFactory);

        // Replace the default json converter by our converter
        // Remove
        for(HttpMessageConverter httpMessageConverter : restTemplate.getMessageConverters()) {
            if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                restTemplate.getMessageConverters().remove(httpMessageConverter);
                break;
            }
        }
        // Add
        restTemplate.getMessageConverters().add(jsonConverter);

        return restTemplate;
    }

    @Bean
    public AsyncClientHttpRequestFactory clientHttpRequestFactory(CloseableHttpAsyncClient closeableHttpAsyncClient) {
        return new HttpComponentsAsyncClientHttpRequestFactory(closeableHttpAsyncClient);
    }

    @Bean
    public CloseableHttpAsyncClient asyncHttpClient(PoolingNHttpClientConnectionManager poolingNHttpClientConnectionManager) {

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(requestTimeout)
                .setSocketTimeout(requestTimeout)
                .setConnectionRequestTimeout(requestTimeout)
                .build();

        return HttpAsyncClientBuilder
                .create().setConnectionManager(poolingNHttpClientConnectionManager)
                .setDefaultRequestConfig(config).build();
    }

    @Bean
    PoolingNHttpClientConnectionManager poolingNHttpClientConnectionManager() throws IOReactorException {
        PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(
                new DefaultConnectingIOReactor(IOReactorConfig.DEFAULT));
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
        return connectionManager;
    }
}
