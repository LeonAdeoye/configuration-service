package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("Configuration")
public class Configuration
{
    @Id
    private String id;
    private String key;
    private String value;
    private String owner;
    private String lastUpdatedBy;
    private String lastUpdatedOn;

    public Configuration()
    {
        this.key = "";
        this.value = "";
        this.owner = "";
        this.lastUpdatedBy = "";
        this.lastUpdatedOn = "";
    }

    public Configuration(String owner, String key, String value, String lastUpdatedBy, String lastUpdatedOn)
    {
        this.key = key;
        this.value = value;
        this.owner = owner;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Configuration(String owner, String key, String value, String lastUpdatedBy, String lastUpdatedOn, String id)
    {
        this.key = key;
        this.value = value;
        this.owner = owner;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedOn = lastUpdatedOn;
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getLastUpdatedBy()
    {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedOn()
    {
        return this.lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn)
    {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    @Override
    public String toString()
    {
        return "Configuration { \"Id\": \"" + id + "\"" +
                ", \"last updated by\": \"" + lastUpdatedBy + "\"" +
                ", \"last updated on\": \"" + lastUpdatedOn + "\"" +
                ", \"key\": \"" + key + "\"" +
                ", \"value\": \"" + value + "\"" +
                ", \"owner\": \"" + owner + "\" }";
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return getKey().equals(that.getKey()) &&
                getValue().equals(that.getValue()) &&
                getLastUpdatedOn().equals(that.getLastUpdatedOn()) &&
                getLastUpdatedBy().equals(that.getLastUpdatedBy()) &&
                getId().equals(that.getId()) &&
                getOwner().equals(that.getOwner());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getKey(), getValue(), getOwner(), getLastUpdatedBy(), getLastUpdatedOn());
    }
}
