package com.leon.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document("App")
public class App
{
    @Id
    private String id;
    private String title;
    private String icon;
    private String url;

    public App()
    {
        this.title = "";
        this.icon = "";
        this.url = "";
    }

    public App(String title, String icon, String url)
    {
        this.title = title;
        this.icon = icon;
        this.url = url;
    }

    public App(String title, String icon, String url, String id)
    {
        this.title = title;
        this.icon = icon;
        this.url = url;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }


    @Override
    public String toString()
    {
        return "App { \"Id\": \"" + id + "\"" +
                ", \"title\": \"" + title + "\"" +
                ", \"icon\": \"" + icon + "\"" +
                ", \"url\": \"" + url + "\" }";
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        App that = (App) o;
        return getTitle().equals(that.getTitle()) &&
                getIcon().equals(that.getIcon()) &&
                getUrl().equals(that.getUrl()) &&
                getId().equals(that.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getTitle(), getIcon(), getUrl());
    }
}
