package com.example.elasticSearch.elasticSearch.search;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import com.example.elasticSearch.elasticSearch.dtos.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



// repository for searching the queries from elastic search
// using the elastic Search Client.
@Repository
@Slf4j
public class ElasticSearchQuery {

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    private final String indexName = "products";


    // creating the document or updating the document.
    public  String createOrUpdateDocument(Product product) throws IOException {

        IndexResponse response = elasticsearchClient.index(i->i
                .index(indexName)
                .id(product.getId())
                .document(product)
        );

        if(response.result().name().equals("Created")){
            return "Document has been Created Successfully";
        }else if(response.result().name().equals("Updated")){
            return "Document has been Updated Successfully";
        }
        return  "Error while perfoming the action";
    }


    // get the product by productId
    public Product getProductById(String productId) throws IOException {
        Product product = null;

        GetResponse<Product> response = elasticsearchClient.get(g->g
                .index(indexName)
                .id(productId),
                Product.class
        );

        if(response.found()){
            product = response.source();
        }else{
            log.error("Product is not found");
        }
        return product;
    }

    // delete the product by Id

    public String deleteProductById(String productId) throws IOException {

        DeleteRequest deleteRequest = DeleteRequest.of(d->
                d.index(indexName).id(productId));

        DeleteResponse deleteResponse = elasticsearchClient.delete(deleteRequest);


        if(deleteResponse.result()!=null && !deleteResponse.result().name().equals("NotFound")){
            log.error(productId + " is found and deleted");
            return "Product with id "+ productId +" is Found and deleted Successfully";
        }
        log.error(productId +" is not found");
        return "Product with id " + deleteResponse.id()+" does not exist.";
    }


    // return the all the products
    // here hit is no of Doucments are there in the products index.
    public List<Product> searchAllDocument() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(d->
                d.index(indexName));
        SearchResponse<Product> searchResponse = elasticsearchClient.search(searchRequest, Product.class);

        List<Hit<Product>> hits = searchResponse.hits().hits();
        List<Product> products = new ArrayList<>();
        for(Hit<Product> object: hits){
            System.out.println(object.toString());

            products.add(object.source());
        }
        return  products;

    }
}
