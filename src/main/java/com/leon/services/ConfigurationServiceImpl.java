package com.leon.services;

import com.leon.models.Configuration;
import com.leon.repositories.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ConfigurationServiceImpl implements ConfigurationService
{
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
    private static Map<String, Map<String,Configuration>> configurations;

    @Autowired
    ConfigurationRepository configurationRepository;

    public ConfigurationServiceImpl()
    {
    }

    @PostConstruct
    public void initialize()
    {
        loadAllConfigurations();
    }

    @Override
    public String getConfigurationValue(String owner, String key)
    {
        if(!configurations.containsKey(key) || !configurations.get(key).containsKey(owner))
        {
            logger.info("Could not find the configuration with owner: " + owner + ", and key: " + key + " in the cache.");
            return "";
        }

        return configurations.get(key).get(owner).getValue();
    }

    @Override
    public void deleteConfiguration(String owner, String key)
    {
        if(!configurations.containsKey(key) || !configurations.get(key).containsKey(owner))
        {
            logger.info("Could not delete the configuration with owner: " + owner + ", and key: " + key + ".");
            return;
        }

        Configuration configurationToDelete = configurations.get(key).get(owner);
        logger.info("Deleting configuration: " + configurationToDelete);
        configurationRepository.deleteById(configurationToDelete.getId());
        configurations.get(key).remove(configurationToDelete);
        if(configurations.get(key).values().size() == 0)
            configurations.remove(key);

        logger.info("Deleted configuration: " + configurationToDelete);
    }

    @Override
    public void saveConfiguration(Configuration configuration)
    {
        configurationRepository.save(configuration);
        addToCache(configuration);

        logger.info("Saved configuration: " + configuration);
    }

    private void addToCache(Configuration configuration)
    {
        if(configurations.containsKey(configuration.getKey()))
        {
            Map<String, Configuration> keyMap = configurations.get(configuration.getKey());
            keyMap.put(configuration.getOwner(), configuration);
        }
        else
        {
            Map<String, Configuration> ownerMap = new HashMap<>();
            ownerMap.put(configuration.getOwner(), configuration);
            configurations.put(configuration.getKey(), ownerMap);
        }
    }

    private void loadAllConfigurations()
    {
        List<Configuration> loadedConfigurations = configurationRepository.findAll();
        configurations = loadedConfigurations.stream().collect(Collectors.groupingBy(Configuration::getKey, Collectors.toMap(x -> x.getOwner(), x -> x)));

        logger.info("Retrieved configurations from persistence store: " + configurations);
    }

    @Override
    public void reconfigure()
    {
        this.loadAllConfigurations();
    }

    @Override
    public List<Configuration> getAllConfigurations()
    {
        List<Configuration> list = configurations.entrySet().stream()
                .flatMap(key -> key.getValue().entrySet().stream())
                .map(owner -> owner.getValue()).collect(toList());

        logger.info("Returning " + list.size() + " configurations.");

        return list;
    }
}