package com.example.digitalChiefTest.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.digitalChiefTest.config.SearchConfig;
import com.example.digitalChiefTest.dto.ProductDto;
import com.example.digitalChiefTest.dto.SkuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        // General product search query
        Query generalQuery = MultiMatchQuery.of(mq -> mq
                        .fields("name", "description")
                        .query(query))
                ._toQuery();

        // SKU level conditions
        List<Query> skuConditions = new ArrayList<>();
        if (searchConfig.getEnabled()) {
            if (searchConfig.getColor() != null) {
                skuConditions.add(MatchQuery.of(m -> m
                                .field("skus.color")
                                .query(searchConfig.getColor()))
                        ._toQuery());
            }
            skuConditions.add(MatchQuery.of(m -> m
                            .field("skus.availability")
                            .query(searchConfig.getAvailability()))
                    ._toQuery());

        }

        // Combine SKU conditions into a nested query
        Query skuQuery = BoolQuery.of(bq -> bq.must(skuConditions))._toQuery();

        // Combine general product query and SKU query
        Query combinedQuery = BoolQuery.of(b -> b
                        .must(generalQuery)
                        .filter(skuQuery))
                ._toQuery();

        try {
            SearchResponse<ProductDto> getProduct = esClient.search(ss -> ss
                    .index(PRODUCTS)
                    .query(combinedQuery), ProductDto.class);

            List<ProductDto> allProducts = getProduct.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

            return filterProductsBySku(allProducts, searchConfig);
        } catch (IOException e) {
            throw new RuntimeException("Error while searching products", e);
        }
    }

    public List<ProductDto> filterProductsBySku(List<ProductDto> products, SearchConfig searchConfig) {
        List<ProductDto> filteredProducts = new ArrayList<>();

        for (ProductDto product : products) {
            List<SkuDto> filteredSkus = product.skus().stream()
                    .filter(sku -> {
                        if (searchConfig.getEnabled()){
                            return (searchConfig.getColor() != null && searchConfig.getColor().equals(sku.color())) ||
                                    (searchConfig.getAvailability() != null && searchConfig.getAvailability().equals(sku.availability()));
                        }
                        return true;
                    })
                    .collect(Collectors.toList());

            if (!filteredSkus.isEmpty()) {
                // Update product with only the filtered SKUs
                ProductDto filteredProduct = new ProductDto(
                        product.id(),
                        product.name(),
                        product.description(),
                        product.price(),
                        product.active(),
                        product.startDate(),
                        filteredSkus
                );
                filteredProducts.add(filteredProduct);
            }
        }

        return filteredProducts;
    }


        /*List<ProductDto> products = new ArrayList<>();

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
        return products;*/
}
