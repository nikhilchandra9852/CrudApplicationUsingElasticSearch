package com.example.elasticSearch.elasticSearch.controller;


import com.example.elasticSearch.elasticSearch.dtos.Product;
import com.example.elasticSearch.elasticSearch.search.ElasticSearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ElasticSearchController {

    @Autowired
    private ElasticSearchQuery elasticSearchQuery;

    // creating or updating api

    @PostMapping("/createOrUpdateDocument")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody Product product) throws IOException {
        String response  = elasticSearchQuery.createOrUpdateDocument(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get the Document by Id
    @GetMapping("/getDocument")
    public ResponseEntity<Object> getDocumentById(@RequestParam String productId) throws IOException {
        Product product = elasticSearchQuery.getProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }


    // delete the product by product Id
    @DeleteMapping("/deleteDocument")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String productId) throws IOException {
        String response = elasticSearchQuery.deleteProductById(productId);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    // retrieve all the documents.
    @GetMapping("/searchDocument")
    public ResponseEntity<Object> searchAllDocument() throws IOException {
        List<Product> products = elasticSearchQuery.searchAllDocument();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
