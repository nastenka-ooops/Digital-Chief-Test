package com.example.digitalChiefTest.controller;

import com.example.digitalChiefTest.dto.ProductDto;
import com.example.digitalChiefTest.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> search(@RequestParam(name = "query") String query) {
        return ResponseEntity.ok(searchService.search(query));
    }
}
