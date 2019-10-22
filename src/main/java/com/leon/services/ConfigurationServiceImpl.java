package com.leon.services;

import com.leon.models.Configuration;
import com.leon.repositories.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConfigurationServiceImpl implements ConfigurationService
{
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
    private static Map<String, Map<String,List<Configuration>>> configurations;

    @Autowired
    ConfigurationRepository configurationRepository;


    public ConfigurationServiceImpl()
    {
        loadAllConfigurations();
    }

    @PostConstruct
    public void initialize()
    {
        List<Configuration> configurations = configurationRepository.findAll();
    }

    @Override
    public String getConfigurationValue(String owner, String key)
    {
        if(!configurations.containsKey(key) || !configurations.get(key).containsKey(owner) || configurations.get(key).get(owner).size() == 0)
            return "";

        return this.configurations.get(key).get(owner).get(0).getValue();
    }

    @Override
    public void saveConfiguration(String owner, String key, String value)
    {
        Configuration configuration = new Configuration(owner, key, value);
        logger.info("Saving configuration: ", configuration);
        configurationRepository.save(configuration);
    }

    @Override
    public void loadAllConfigurations()
    {
        //List<Configuration> configurations = configurationRepository.findAll();
        logger.info("Retrieved configurations: " + configurations);
    }

    @Override
    public void reconfigure()
    {
        logger.info("Reconfiguring...");
        this.loadAllConfigurations();
    }
}