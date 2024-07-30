package com.example.digitalChiefTest.controller;

import com.example.digitalChiefTest.service.DataLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/data")
public class DataLoadController {
    private final DataLoadService dataLoadService;

    @Autowired
    public DataLoadController(DataLoadService dataLoadService) {
        this.dataLoadService = dataLoadService;
    }

    @PostMapping("/load")
    public ResponseEntity<String> loadData() {
        dataLoadService.loadData();
        return ResponseEntity.ok("Load completed successfully");
    }
}
