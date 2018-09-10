package com.nghia.reactive.web.ng.reactive.web.controller;

import com.nghia.reactive.web.ng.reactive.web.domain.Blog;
import com.nghia.reactive.web.ng.reactive.web.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class AppController extends BaseController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private BlogService blogService;

    @GetMapping
    public Flux<Blog> findAll() {
        log.info("findAll Blog");
        return blogService.findAll();
    }

    @GetMapping("/find")
    public Flux<Blog> findByTitle(@RequestParam String blogTitle) {
        log.info("findByTitle Blog with blogTitle : {}", blogTitle);
        return blogService.findByTitle(blogTitle);
    }

    @GetMapping("/{id}")
    public Mono<Blog> findOne(@PathVariable String id) {
        log.info("findOne Blog with id : {}", id);
        return blogService.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Blog> create(@RequestBody Blog blog) {
        log.info("create Blog with blog : {}", blog);
        return blogService.createBlog(blog);
    }

    @PostMapping("/create-bunk")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Blog> createBunk(@RequestBody Blog blog) {
        log.info("==================\n\n");
        long start = System.currentTimeMillis();

        Flux<Blog> blogFlux = blogService.createBunk(blog);
        log.info("created bunk Blog with : {}" + (System.currentTimeMillis() - start));
        return blogFlux;
    }
    @GetMapping("/getBlocking")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Blog> findByTitleBlocking(@RequestParam String blogTitle) {
        log.info("findByTitle Blog with blogTitle : {}", blogTitle);
        return blogService.findByTitle(blogTitle)
                .collectList().block();
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> delete(@PathVariable String id) {
        log.info("delete Blog with id : {}", id);
        return blogService.delete(id);
    }

    @PutMapping("/{id}")
    public Mono<Blog> updateBlog(@RequestBody Blog blog, @PathVariable String id) {
        log.info("updateBlog Blog with id : {} and blog : {}", id, blog);
        return blogService.updateBlog(blog, id);
    }
}
