package com.leon.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UtilityServiceTest
{
    @Autowired
    private UtilityServiceImpl utilityService;

    @Test
    public void whenPassedUrlWithReplaceableHostname_transformHostnameInUrl_shouldReplaceHostname()
    {
        // Act
        String result = utilityService.transformHostnameInUrl("http://#hostname#:20003/usage");
        // Assert
        assertEquals("#hostname# should be replaced", result, "http://localhost:20003/usage");
    }

    @Test
    public void whenPassedUrlWithoutReplaceableHostname_transformHostnameInUrl_shouldHaveNoEffect()
    {
        // Act
        String result = utilityService.transformHostnameInUrl("http://#hostname:20003/usage");
        // Assert
        assertEquals("#hostname should not be replaced", result, "http://#hostname:20003/usage");
    }

    @Test
    public void whenPassedEmptyString_transformHostnameInUrl_shouldHaveNoEffect()
    {
        // Act
        String result = utilityService.transformHostnameInUrl("");
        // Assert
        assertEquals("empty string should have not effect", result, "");
    }

    @Test
    public void whenPassedNull_transformHostnameInUrl_shouldHaveNoEffect()
    {
        // Act
        String result = utilityService.transformHostnameInUrl(null);
        // Assert
        assertEquals("null should have not effect", result, null);
    }
}
