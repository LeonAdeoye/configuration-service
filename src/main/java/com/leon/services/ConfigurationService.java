package com.leon.services;

import com.leon.models.Configuration;
import java.util.List;

public interface ConfigurationService
{
    String getConfigurationValue(String owner, String key);
    void deleteConfiguration(String owner, String key);
    void saveConfiguration(Configuration configuration);
    void reconfigure();
    List<Configuration> getAllConfigurations();
}