package com.calmero.repository;

import com.calmero.model.UserElasticEntity;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserElasticsearchQueryRepository extends ReactiveElasticsearchRepository<UserElasticEntity, String> {
    Flux<UserElasticEntity> findByName(String name);
}
