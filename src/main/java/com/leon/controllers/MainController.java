package com.leon.controllers;

import com.leon.models.Configuration;
import com.leon.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @CrossOrigin
    @RequestMapping("/reconfigure")
    public void reconfigure()
    {
        logger.info("Received request to reconfigure.");
        configurationService.reconfigure();
    }

    @CrossOrigin
    @RequestMapping(value = "/configuration", method=GET)
    public String getConfiguration(@RequestParam String owner, @RequestParam String key) throws IllegalArgumentException
    {
        if(owner == null || owner.isEmpty())
        {
            logger.error("The owner request param cannot be null or empty.");
            throw new IllegalArgumentException("owner argument is invalid");
        }

        if(key == null || key.isEmpty())
        {
            logger.error("The key request param cannot be null or empty");
            throw new IllegalArgumentException("key argument is invalid");
        }

        logger.info("Received request for configuration value with owner: " + owner + ", and key: " + key);
        return configurationService.getConfigurationValue(owner, key);
    }

    @CrossOrigin
    @RequestMapping(value = "/configurationByOwner", method=GET)
    public List<Configuration> getConfiguration(@RequestParam String owner) throws IllegalArgumentException
    {
        if(owner == null || owner.isEmpty())
        {
            logger.error("The owner request param cannot be null or empty.");
            throw new IllegalArgumentException("owner argument is invalid");
        }

        logger.info("Received request for configuration values belonging to owner: " + owner);
        return configurationService.getConfigurationValues(owner);
    }

    @CrossOrigin
    @RequestMapping(value = "/configurations", produces=MediaType.APPLICATION_JSON_VALUE, method=GET)
    public List<Configuration> getAllConfigurations()
    {
        logger.info("Received request for all configuration values.");
        return configurationService.getAllConfigurations();
    }

    @CrossOrigin
    @RequestMapping(value = "/configuration", method={PUT, POST}, consumes=MediaType.APPLICATION_JSON_VALUE)
    public String saveConfiguration(@RequestBody Configuration configuration)
    {
        if(configuration == null)
        {
            logger.error("The configuration request body cannot be null.");
            throw new NullPointerException("configuration");
        }

        logger.info("Received request to save a configuration: " + configuration);
        return configurationService.saveConfiguration(configuration);
    }

    @CrossOrigin
    @RequestMapping(value = "/configuration", method=DELETE)
    public void deleteConfiguration(@RequestParam String id)
    {
        if(id == null || id.isEmpty())
        {
            logger.error("The id request param cannot be null or empty.");
            throw new IllegalArgumentException("id argument is invalid");
        }

        logger.info("Received request to delete configuration value with id: " + id);
        configurationService.deleteConfiguration(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/configurationByOwnerAndKey", method=DELETE)
    public void deleteConfiguration(@RequestParam String owner, @RequestParam String key)
    {
        if(owner == null || owner.isEmpty())
        {
            logger.error("The owner request param cannot be null or empty.");
            throw new IllegalArgumentException("owner argument is invalid");
        }

        if(key == null || key.isEmpty())
        {
            logger.error("The key request param cannot be null or empty");
            throw new IllegalArgumentException("key argument is invalid");
        }

        logger.info("Received request to delete configuration value with owner: " + owner + ", and key: " + key);
        configurationService.deleteConfiguration(owner, key);
    }
}