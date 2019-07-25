package com.nghia.reactive.web.ng.reactive.web.reactor.example;

public interface NotificationService {
 
    void initiateNotification(NotificationData notificationData) 
      throws InterruptedException;
 
}