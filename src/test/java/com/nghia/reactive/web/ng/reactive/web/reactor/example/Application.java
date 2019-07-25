//package com.nghia.reactive.web.ng.reactive.web.reactor.example;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import reactor.bus.EventBus;
//import reactor.Environment;
//import reactor.bus.EventBus;
//
//import static reactor.bus.selector.Selectors.$;
//
//@Configuration
////@EnableAutoConfiguration
////@ComponentScan
//public class ReactorApplication implements CommandLineRunner {
//
//    @Autowired
//    private EventBus eventBus;
//
//    @Autowired
//    private NotificationConsumer notificationConsumer;
//
//    @Bean
//    Environment env() {
//        return Environment.initializeIfEmpty().assignErrorJournal();
//    }
//
//    @Bean
//    EventBus createEventBus(Environment env) {
//        return EventBus.create(env, Environment.THREAD_POOL);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        eventBus.on($("notificationConsumer"), notificationConsumer);
//    }
//
//}