//package com.nghia.reactive.web.ng.reactive.web.reactor.example;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import reactor.bus.Event;
//
//import java.util.function.Consumer;
//
//@Service
//public class NotificationConsumer implements
//        Consumer<Event<NotificationData>> {
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @Override
//    public void accept(Event<NotificationData> notificationDataEvent) {
//        NotificationData notificationData = notificationDataEvent.getData();
//
//        try {
//            notificationService.initiateNotification(notificationData);
//        } catch (InterruptedException e) {
//            // ignore
//        }
//    }
//}