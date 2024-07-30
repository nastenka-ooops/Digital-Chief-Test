package com.example.digitalChiefTest.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.digitalChiefTest.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.digitalChiefTest.config.ElasticsearchConfig.PRODUCTS;

@Service
public class SearchService {
    private final ElasticsearchClient esClient;

    @Autowired
    public SearchService(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    public List<ProductDto> search(String query) {
        List<ProductDto> products;

        try {
            SearchResponse<ProductDto> getProduct = esClient.search(ss -> ss
                            .index(PRODUCTS)
                            .query(q -> q
                                    .multiMatch(t -> t
                                            .fields("name", "description", "skus.color", "skus.size")
                                            .query(query))
                            ), ProductDto.class);
            products = new ArrayList<>();
            getProduct.hits().hits().forEach(hit -> products.add(hit.source()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
