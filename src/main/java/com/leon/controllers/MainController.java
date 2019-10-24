package com.leon.controllers;

import com.leon.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MainController
{
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping("/reconfigure")
    public void reconfigure()
    {
        logger.info("Received request to reconfigure.");
        this.configurationService.reconfigure();
    }

    @RequestMapping(value = "/configuration", method=GET)
    public String getConfiguration(@RequestParam String owner, @RequestParam String key)
    {
        logger.info("Received request for configuration value with owner: " + owner + ", and key: " + key);
        return this.configurationService.getConfigurationValue(owner, key);
    }

    @RequestMapping(value = "/configurations", method=GET)
    public void getConfigurations()
    {
        logger.info("Received request for all configuration values");
        this.configurationService.getAllConfigurations();
    }


}