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
        if(!configurations.containsKey(owner) || !configurations.get(owner).containsKey(key))
        {
            logger.info("Could not find the configuration with owner: " + owner + ", and key: " + key + " in the cache.");
            return "";
        }

        return configurations.get(owner).get(key).getValue();
    }

    private Configuration getConfigurationValue(String id)
    {
        return configurations.entrySet().stream()
                .flatMap(owner -> owner.getValue().entrySet().stream())
                .map(key -> key.getValue())
                .filter((configuration) -> configuration.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteConfiguration(String id)
    {
        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("id argument is invalid");

        Configuration configurationToDelete = getConfigurationValue(id);
        logger.info("Deleting configuration: " + configurationToDelete);
        configurationRepository.deleteById(id);

        configurations.get(configurationToDelete.getOwner()).remove(configurationToDelete.getKey());
        if(configurations.get(configurationToDelete.getOwner()).values().size() == 0)
            configurations.remove(configurationToDelete.getOwner());

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
        if(configurations.containsKey(configuration.getOwner()))
        {
            Map<String, Configuration> keyMap = configurations.get(configuration.getOwner());
            keyMap.put(configuration.getKey(), configuration);
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
        configurations = loadedConfigurations.stream().collect(Collectors.groupingBy(Configuration::getOwner, Collectors.toMap(x -> x.getKey(), x -> x)));
        logger.info("Retrieved " + loadedConfigurations.size() + " configurations from the persistence store:\n"
                + loadedConfigurations.stream().map(config -> config + "\n").collect(Collectors.joining()));
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
                .flatMap(owner -> owner.getValue().entrySet().stream())
                .map(key -> key.getValue()).collect(toList());

        logger.info("Returning " + list.size() + " configurations.");

        return list;
    }

    @Override
    public List<Configuration> getConfigurationValues(String owner)
    {
        if(!configurations.containsKey(owner))
            return Collections.emptyList();

        List<Configuration> list = configurations.get(owner).values().stream().collect(Collectors.toList());

        logger.info("Returning {} configurations belonging to owner {}.", list.size(), owner);
        return list;
    }
}