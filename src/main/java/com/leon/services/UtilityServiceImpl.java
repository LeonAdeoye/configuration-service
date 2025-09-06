
package com.leon.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UtilityServiceImpl
{
    @Value("${server.hostname}")
    private String hostname;

    public String transformHostnameInUrl(String url)
    {
        if(url != null && !url.isEmpty() && url.indexOf("#hostname#") != -1 && this.hostname != null && !this.hostname.trim().isEmpty())
            return url.replace("#hostname#", this.hostname);
        else
            return url;
    }

}
