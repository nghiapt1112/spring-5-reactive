package com.nghia.reactive.web.ng.reactive.web.service;

import com.nghia.reactive.web.ng.reactive.web.domain.Blog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BlogService {

    Mono<Blog> createBlog(Blog blog);

    Mono<Blog> updateBlog(Blog blog, String id);

    Flux<Blog> findAll();

    Mono<Blog> findOne(String id);

    Flux<Blog> findByTitle(String title);

    Mono<Boolean> delete(String id);

    Flux<Blog> createBunk(Blog blog);

    List<Blog> createOldBunk(Blog blog);
}
