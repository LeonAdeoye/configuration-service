package com.leon.repositories;

import com.leon.models.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends MongoRepository<Configuration, String>
{
    public Configuration findByKey(String key);
    public List<Configuration> findByOwner(String owner);
}
