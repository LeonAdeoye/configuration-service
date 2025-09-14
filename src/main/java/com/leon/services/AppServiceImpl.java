package com.leon.services;

import com.leon.models.App;
import com.leon.repositories.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService
{
    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
    private static Map<String, App> apps;

    @Autowired
    AppRepository appRepository;

    public AppServiceImpl()
    {
    }

    @PostConstruct
    public void initialize()
    {
        loadAllApps();
    }

    @Override
    public List<App> getAllApps()
    {
        List<App> list = apps.values().stream().collect(Collectors.toList());

        logger.info("Returning " + list.size() + " apps.");

        return list;
    }

    @Override
    public App saveApp(App app)
    {
        appRepository.save(app);
        addToCache(app);
        logger.info("Saved app: " + app);
        return app;
    }

    @Override
    public void refreshApps()
    {
        this.loadAllApps();
    }

    private void addToCache(App app)
    {
        apps.put(app.getId(), app);
    }

    private void loadAllApps()
    {
        List<App> loadedApps = appRepository.findAll();
        apps = loadedApps.stream().collect(Collectors.toMap(App::getId, app -> app));
        logger.info("Retrieved " + loadedApps.size() + " apps from the persistence store:\n" + loadedApps.stream().map(app -> app + "\n").collect(Collectors.joining()));
    }
}
