package com.nghia.reactive.web.ng.reactive.web.exception;

public class BlogException extends DomainException {
    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }
}
