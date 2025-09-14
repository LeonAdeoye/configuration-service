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
        // TODO: Remove this temporary method call once all apps are loaded in MongoDB
        populateInitialAppsIfEmpty();
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
        logger.info("Retrieved " + loadedApps.size() + " apps from the persistence store:\n"
                + loadedApps.stream().map(app -> app + "\n").collect(Collectors.joining()));
    }

    // TODO: Remove this temporary method once all apps are loaded in MongoDB
    private void populateInitialAppsIfEmpty()
    {
        if(apps.isEmpty())
        {
            logger.info("App collection is empty. Populating with initial app data...");
            
            List<App> initialApps = Arrays.asList(
                new App("New Basket", "file:///assets/new basket.png", "http://loalhost:3000/new-basket"),
                new App("Child Orders", "file:///assets/child orders.png", "http://localhost:3000/child-orders"),
                new App("New Order", "file:///assets/new order.png", "http://localhost:3000/new-order"),
                new App("Client Interests", "file:///assets/client interests.png", "http://localhost:3000/client-interests"),
                new App("Baskets", "file:///assets/baskets.png", "http://localhost:3000/baskets"),
                new App("Basket Chart", "file:///assets/basket chart.png", "http://localhost:3000/basket-chart"),
                new App("Orders", "file:///assets/orders.png", "http://localhost:3000/orders"),
                new App("Limits", "file:///assets/limits.png", "http://localhost:3000/limits"),
                new App("Reference Data", "file:///assets/reference data.png", "http://localhost:3000/reference-data"),
                new App("Fx Rates", "file:///assets/fx rates.png", "http://localhost:3000/fx-rates"),
                new App("Holdings", "file:///assets/holdings.png", "http://localhost:3000/holdings"),
                new App("Crypto Ticker", "file:///assets/ticker.png", "http://localhost:3000/crypto-ticker"),
                new App("Crypto Chart", "file:///assets/chart.png", "http://localhost:3000/crypto-chart"),
                new App("Stock Ticker", "file:///assets/ticker.png", "http://localhost:3000/stock-ticker"),
                new App("Stock Chart", "file:///assets/chart.png", "http://localhost:3000/stock-chart"),
                new App("Configurations", "file:///assets/configurations.png", "http://localhost:3000/configs"),
                new App("Users", "file:///assets/users.png", "http://localhost:3000/users"),
                new App("Dashboard", "file:///assets/dashboard.png", "http://localhost:3000/dashboard"),
                new App("Trade History", "file:///assets/trade history.png", "http://localhost:3000/trade-history"),
                new App("Alerts", "file:///assets/alerts.png", "http://localhost:3000/alerts"),
                new App("Alert Configurations", "file:///assets/alert configurations.png", "http://localhost:3000/alert-configurations"),
                new App("Tasks", "file:///assets/tasks.png", "http://localhost:3000/tasks"),
                new App("Client Blasts", "file:///assets/client blasts.png", "http://localhost:3000/blasts"),
                new App("Crosses", "file:///assets/crosses.png", "http://localhost:3000/crosses"),
                new App("News", "file:///assets/news.png", "http://localhost:3000/news"),
                new App("IOIs", "file:///assets/iois.png", "http://localhost:3000/ioi"),
                new App("TCA", "file:///assets/tca.png", "http://localhost:3000/tca"),
                new App("Workflow", "file:///assets/workflow.png", "http://localhost:3000/workflow"),
                new App("Insights", "file:///assets/insights.png", "http://localhost:3000/insights"),
                new App("Reports", "file:///assets/reports.png", "http://localhost:3000/reports"),
                new App("Parametrics", "file:///assets/parametrics.png", "http://localhost:3000/parametrics"),
                new App("Request For Quote", "file:///assets/request for quote.png", "http://localhost:3000/rfq"),
                new App("Index Pricing", "file:///assets/index pricing.png", "http://localhost:3000/index-pricing"),
                new App("Position Keeping", "file:///assets/position keeping.png", "http://localhost:3000/positions"),
                new App("Services", "file:///assets/services.png", "http://localhost:3000/services")
            );

            for(App app : initialApps)
            {
                appRepository.save(app);
                addToCache(app);
            }

            logger.info("Successfully populated " + initialApps.size() + " initial apps into MongoDB and cache.");
        }
        else
        {
            logger.info("App collection already contains " + apps.size() + " apps. Skipping initial population.");
        }
    }
}
