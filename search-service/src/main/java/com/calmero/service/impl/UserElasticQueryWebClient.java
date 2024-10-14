package com.calmero.service.impl;

import com.calmero.config.ChatLogger;
import com.calmero.model.UserElasticEntity;
import com.calmero.model.request.SearchRequest;
import com.calmero.service.ElasticQueryWebClient;
import com.calmero.util.ElasticQueryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.suggest.response.Suggest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserElasticQueryWebClient implements ElasticQueryWebClient {
    private final ElasticQueryUtil<String, UserElasticEntity> elasticQueryUtil;
    private final ReactiveElasticsearchOperations reactiveElasticsearchOperations;
    private final ChatLogger LOG;

    @Override
    public Flux<SearchHit<UserElasticEntity>> searchByName(Mono<SearchRequest> searchRequest) {
        return searchRequest.doOnNext(s -> {
                    LOG.search.info("Querying by name {}", s.getKeyword());
                })
                .map(s -> elasticQueryUtil.getSearchQueryByFieldTextAndShould("name", s.getKeyword(), s))
                .flatMapMany(q -> reactiveElasticsearchOperations.search(q, UserElasticEntity.class));
    }

    @Override
    public Mono<Suggest> searchSuggest(Mono<SearchRequest> searchRequest) {
        return searchRequest.doOnNext(s -> {
                    LOG.search.info("Querying by name {}", s.getKeyword());
                })
                .map(s -> elasticQueryUtil.getSuggesstionQuery("name", s.getKeyword(), s))
                .flatMap(suggestion -> reactiveElasticsearchOperations.suggest(suggestion, UserElasticEntity.class));
    }
}
