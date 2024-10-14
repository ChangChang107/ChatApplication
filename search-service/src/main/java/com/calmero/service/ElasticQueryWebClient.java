package com.calmero.service;

import com.calmero.model.UserElasticEntity;
import com.calmero.model.request.SearchRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.suggest.response.Suggest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ElasticQueryWebClient {

    Flux<SearchHit<UserElasticEntity>> searchByName(Mono<SearchRequest> request);

    Mono<Suggest> searchSuggest(Mono<SearchRequest> searchRequest);
}
