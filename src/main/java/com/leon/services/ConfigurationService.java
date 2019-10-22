package com.leon.services;

import com.leon.models.Configuration;
import java.util.List;

public interface ConfigurationService
{
    public String getConfigurationValue(String owner, String key);
    public void saveConfiguration(String owner, String key, String value);
    public void loadAllConfigurations();
    public void reconfigure();
}