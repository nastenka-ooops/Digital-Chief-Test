package com.example.digitalChiefTest.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery.Builder;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.digitalChiefTest.config.SearchConfig;
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
    private final SearchConfig searchConfig;

    @Autowired
    public SearchService(ElasticsearchClient esClient, SearchConfig searchConfig) {
        this.esClient = esClient;
        this.searchConfig = searchConfig;
    }

    public List<ProductDto> search(String query) {
        List<ProductDto> products = new ArrayList<>();

        List<Query> conditions = new ArrayList<>();

        conditions.add(MultiMatchQuery.of(mq -> mq
                        .fields("name", "description", "skus.color", "skus.size")
                        .query(query))
                ._toQuery());

        if (searchConfig.isEnabled()) {
            conditions.add(new Builder()
                    .field("skus.color")
                    .query(searchConfig.getColor()).build()._toQuery());


            conditions.add(new Builder()
                    .field("skus.availability")
                    .query(searchConfig.isAvailability()).build()._toQuery());

        }

        Query q = new Query.Builder().bool(b -> b.should(conditions)).build();

        try {
            SearchResponse<ProductDto> getProduct = esClient.search(ss -> ss
                    .index(PRODUCTS)
                    .query(q), ProductDto.class);

            getProduct.hits().hits().forEach(hit -> products.add(hit.source()));
        } catch (IOException e) {
            throw new RuntimeException("Error while searching products", e);
        }
        return products;
    }
}
