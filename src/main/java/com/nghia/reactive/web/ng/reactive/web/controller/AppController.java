package com.nghia.reactive.web.ng.reactive.web.controller;

import com.nghia.reactive.web.ng.reactive.web.domain.Blog;
import com.nghia.reactive.web.ng.reactive.web.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.teeing;

@RestController
@RequestMapping("/api/blog")
public class AppController extends BaseController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private BlogService blogService;

    @GetMapping
    public Flux<Blog> findAll() {
        log.info("findAll Blog");
        long start = System.currentTimeMillis();
        Flux<Blog> res = blogService.findAll();
        log.info("find all  Blogs with : {} second.", (System.currentTimeMillis() - start));
        return res;
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
    public Flux<Blog> createBunk() {
        log.info("==================\n\n");
        long start = System.currentTimeMillis();
        Blog blog = new Blog();
        blog.setAuthor("Author");
        blog.setContent("Content");
        blog.setTitle("Title");
        Flux<Blog> blogFlux = blogService.createBunk(blog);
        log.info("created bunk Blog with : {} second.", (System.currentTimeMillis() - start));
        return blogFlux;
    }

    @PostMapping("/old/create-bunk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Blog> oldcreateBunk() {
//            int day = 0;
//        int numLetters = switch (day) {
//            case 1, 2, 3 -> 6;
//            case 4 -> 7;
//            case 5, 6 -> 8;
//            case 7 -> 9;
//            default -> throw new IllegalStateException("Huh? " + day);
//        };
        Flux<Long> squares = Flux.generate(
                AtomicLong::new, //1
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next(i * i); //2
                    if (i == 10) sink.complete(); //3
                    return state;
                });
        var x = new ArrayList<String>();
        double average = Stream.of(1, 4, 2, 7, 4, 6, 5)
                .collect(teeing(
                        summingDouble(i -> i),
                        counting(),
                        (sum, n) -> sum / n));

        log.info("==================\n\n");
        long start = System.currentTimeMillis();
        Blog blog = new Blog();
        blog.setAuthor("Author");
        blog.setContent("Content");
        blog.setTitle("Title");
        List<Blog> blogFlux = blogService.createOldBunk(blog);
        log.info("created bunk Blog with : {} second.", (System.currentTimeMillis() - start));
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
