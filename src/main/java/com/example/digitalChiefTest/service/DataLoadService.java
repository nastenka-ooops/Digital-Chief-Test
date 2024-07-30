package com.example.digitalChiefTest.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.digitalChiefTest.dto.ProductDto;
import com.example.digitalChiefTest.entity.Product;
import com.example.digitalChiefTest.repository.ProductRepository;
import com.example.digitalChiefTest.util.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.example.digitalChiefTest.config.ElasticsearchConfig.PRODUCTS;

@Service
public class DataLoadService {
    private final ProductRepository productRepository;
    private final ElasticsearchClient esClient;

    @Autowired
    public DataLoadService(ProductRepository productRepository, ElasticsearchClient esClient) {
        this.productRepository = productRepository;
        this.esClient = esClient;
    }

    public void loadData() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            ProductDto productDto = ProductMapper.mapToProductDto(product);
            try {
                IndexResponse response = esClient.index(i -> i
                        .index(PRODUCTS)
                        .id(productDto.id().toString())
                        .document(productDto)
                );
                System.out.println("Document indexed" + response);
            } catch (IOException e) {
                throw new RuntimeException("error while indexing product", e);
            }
        }
    }
}
