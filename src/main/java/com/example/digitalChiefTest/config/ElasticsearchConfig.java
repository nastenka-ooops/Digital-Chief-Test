package com.example.digitalChiefTest.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ElasticsearchConfig extends ElasticsearchRestClientAutoConfiguration {
    String serverUrl = "http://localhost:9200";
    public static final String PRODUCTS = "products";

    @Bean
    RestClient restClient() {
        return RestClient.builder(HttpHost.create(serverUrl)).build();
    }

    @Bean
    ElasticsearchTransport elasticsearchTransport() {
        return new RestClientTransport(restClient(), new JacksonJsonpMapper());
    }

    @Bean
    ElasticsearchClient elasticsearchClient() {
        ElasticsearchClient esClient = new ElasticsearchClient(elasticsearchTransport());

        BooleanResponse indexRes;
        try {
            indexRes = esClient.indices().exists(ex -> ex.index(PRODUCTS));

            if (!indexRes.value()) {
                esClient.indices().create(c -> c
                        .index(PRODUCTS));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return esClient;
    }

}
