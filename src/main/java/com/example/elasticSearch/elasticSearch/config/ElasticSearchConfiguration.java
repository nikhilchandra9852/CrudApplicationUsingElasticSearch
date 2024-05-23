package com.example.elasticSearch.elasticSearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {


    // create a RestClient bean on port 9200
    // return the url and port which elastic search is running.
    @Bean
    public RestClient getRestClient(){
        return  RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();
    }

    // create one more bean for elasticSearchTransport
    // transport object helps to automatically map our Model class
    // to Json and integrated with Api Client.
    @Bean
    public ElasticsearchTransport getElasticSearchTransport(){
        return new RestClientTransport(
                getRestClient(),new JacksonJsonpMapper()
        );
    }

    // create bean for elasticSearchClient
    // it returns the client object where we can use for queries with ElasticSearch.
    @Bean
    public ElasticsearchClient getElasticSearchClient(){
        return  new ElasticsearchClient(getElasticSearchTransport());
    }

}
