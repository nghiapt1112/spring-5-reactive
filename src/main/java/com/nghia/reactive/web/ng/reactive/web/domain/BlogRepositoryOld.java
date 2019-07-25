package com.nghia.reactive.web.ng.reactive.web.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepositoryOld extends MongoRepository<Blog, String> {

}
