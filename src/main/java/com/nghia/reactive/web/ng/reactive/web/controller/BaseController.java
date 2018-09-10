package com.nghia.reactive.web.ng.reactive.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public abstract class BaseController {
    protected final Logger CONTROLLER_LOGGER = LoggerFactory.getLogger(getClass());


}
