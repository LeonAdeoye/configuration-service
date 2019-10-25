package com.leon.controllers;

import com.leon.models.Configuration;
import com.leon.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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
        configurationService.reconfigure();
    }

    @RequestMapping(value = "/configuration", method=GET)
    public String getConfiguration(@RequestParam String owner, @RequestParam String key)
    {
        if(owner == null)
        {
            logger.error("The owner request param cannot be null.");
            throw new NullPointerException("owner");
        }

        if(key == null)
        {
            logger.error("The key request param cannot be null.");
            throw new NullPointerException("key");
        }

        logger.info("Received request for configuration value with owner: " + owner + ", and key: " + key);
        return configurationService.getConfigurationValue(owner, key);
    }

    @RequestMapping(value = "/configurations", method=GET)
    public List<Configuration> getConfigurations()
    {
        logger.info("Received request for all configuration values");
        return configurationService.getAllConfigurations();
    }

    @RequestMapping(value = "/configuration", method=POST)
    public void saveConfiguration(@RequestBody Configuration configuration)
    {
        if(configuration == null)
        {
            logger.error("The configuration request body cannot be null");
            throw new NullPointerException("configuration");
        }

        logger.info("Received request to save a configuration: " + configuration);
        configurationService.saveConfiguration(configuration);
    }

    @RequestMapping(value = "/configuration", method=DELETE)
    public void deleteConfiguration(@RequestParam String owner, @RequestParam String key)
    {
        if(owner == null)
        {
            logger.error("The owner request param cannot be null.");
            throw new NullPointerException("owner");
        }

        if(key == null)
        {
            logger.error("The key request param cannot be null.");
            throw new NullPointerException("key");
        }

        logger.info("Received request to delete configuration value with owner: " + owner + ", and key: " + key);
        configurationService.deleteConfiguration(owner, key);
    }


}