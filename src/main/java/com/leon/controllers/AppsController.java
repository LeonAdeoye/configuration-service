package com.leon.controllers;

import com.leon.models.App;
import com.leon.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class AppsController
{
    private static final Logger logger = LoggerFactory.getLogger(AppsController.class);
    @Autowired
    private AppService appService;

    @CrossOrigin
    @RequestMapping(value = "/apps", produces=MediaType.APPLICATION_JSON_VALUE, method=GET)
    public List<App> getAllApps()
    {
        logger.info("Received request for all apps.");
        return appService.getAllApps();
    }

    @CrossOrigin
    @RequestMapping(value = "/app", method={PUT, POST}, consumes=MediaType.APPLICATION_JSON_VALUE)
    public App saveApp(@RequestBody App app)
    {
        if(app == null)
        {
            logger.error("The app request body cannot be null.");
            throw new NullPointerException("app");
        }

        logger.info("Received request to save an app: " + app);
        return appService.saveApp(app);
    }

    @CrossOrigin
    @RequestMapping("/refreshApps")
    public void refreshApps()
    {
        logger.info("Received request to refresh apps.");
        appService.refreshApps();
    }
}
