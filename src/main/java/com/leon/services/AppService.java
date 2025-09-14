package com.leon.services;

import com.leon.models.App;
import java.util.List;

public interface AppService
{
    List<App> getAllApps();
    App saveApp(App app);
    void refreshApps();
}
