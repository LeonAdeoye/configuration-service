package com.leon.services;

import com.leon.models.Configuration;
import java.util.List;

public interface ConfigurationService
{
    String getConfigurationValue(String owner, String key);
    void saveConfiguration(String owner, String key, String value);
    void reconfigure();
    List<Configuration> getAllConfigurations();
}