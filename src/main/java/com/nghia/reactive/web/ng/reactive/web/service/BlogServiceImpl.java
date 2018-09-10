package com.nghia.reactive.web.ng.reactive.web.service;

import com.nghia.reactive.web.ng.reactive.web.domain.Blog;
import com.nghia.reactive.web.ng.reactive.web.domain.BlogRepository;
import com.nghia.reactive.web.ng.reactive.web.exception.BlogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Mono<Blog> createBlog(Blog blog) {
        return this.blogRepository.insert(blog);
    }

    public Flux<Blog> createBunk(Blog blog) {
        List<Blog> blogs = IntStream.rangeClosed(1, 1000)
                .mapToObj(el -> {
                    Blog bl = new Blog();
                    bl.setTitle(blog.getTitle().concat(String.valueOf(el)));
                    bl.setContent(blog.getContent().concat(String.valueOf(el)));
                    bl.setAuthor(blog.getAuthor().concat(String.valueOf(el)));
                    return bl;
                })
                .collect(Collectors.toList());
        return this.blogRepository.saveAll(blogs);
    }

    @Override
    public Mono<Blog> updateBlog(Blog blog, String id) {
        return findOne(id)
                .doOnSuccess(foundBlog -> {
                    foundBlog.setContent(blog.getContent());
                    foundBlog.setTitle(blog.getTitle());
                    foundBlog.setAuthor(blog.getAuthor());
                    blogRepository.save(foundBlog).subscribe();
                });
    }

    @Override
    public Flux<Blog> findAll() {
        return this.blogRepository.findAll();
    }

    @Override
    public Mono<Blog> findOne(String id) {
        return this.blogRepository.findByIdAndDeleteIsFalse(id)
                .switchIfEmpty(Mono.error(new BlogException("Cannot find blog")));
    }

    @Override
    public Flux<Blog> findByTitle(String title) {
        return blogRepository.findByAuthorAndDeleteIsFalse(title)
                .switchIfEmpty(Mono.error(new Exception("No Blog found with title Containing : " + title)));
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return findOne(id)
                .doOnSuccess(blog -> {
                    blog.setDelete(true);
                    blogRepository.save(blog).subscribe();
                }).flatMap(blog -> Mono.just(Boolean.TRUE));
    }
}
