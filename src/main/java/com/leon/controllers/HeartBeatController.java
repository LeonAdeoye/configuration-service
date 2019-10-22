package com.leon.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HeartBeatController
{
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatController.class);

    @RequestMapping("/heartbeat")
    public String heartBeat()
    {
        logger.debug("Received heartbeat request and will respond.");

        return "I am still here!";
    }
}