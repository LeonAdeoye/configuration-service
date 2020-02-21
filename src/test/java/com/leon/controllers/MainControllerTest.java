package com.leon.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leon.models.Configuration;
import com.leon.services.ConfigurationService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest
{
    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private ConfigurationService configurationServiceMock;

    private static String asJsonString(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void configurationGetRequestWhenPassedValidParams_ShouldCallConfigurationServiceGetConfigurationValueMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/configuration")
                .param("owner", "horatio")
                .param("key", "surname"))
                .andExpect(status().isOk());
        // Assert
        verify(configurationServiceMock, times(1)).getConfigurationValue("horatio", "surname");
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationGetRequestWhenPassedInvalidOwnerParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/configuration")
                    .param("owner", "")
                    .param("key", "surname"));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).getConfigurationValue("", "surname");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationGetRequestWhenPassedInvalidKeyParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/configuration")
                    .param("owner", "horatio")
                    .param("key", ""));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).getConfigurationValue("horatio", "");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test
    public void reconfigureGetRequest_shouldCallConfigurationServiceReconfigureMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/reconfigure"));
        // Assert
        verify(configurationServiceMock, times(1)).reconfigure();
    }

    @Test
    public void configurationGetRequestWithoutParams_shouldCallConfigurationServiceGetAllConfigurationsMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/configurations"));
        // Assert
        verify(configurationServiceMock, times(1)).getAllConfigurations();
    }

    @Test
    public void configurationPostRequestWhenPassedValidRequestBody_ShouldCallConfigurationServiceSaveConfigurationMethod() throws Exception
    {
        // Arrange
        Configuration configuration = new Configuration("Horatio", "surname", "Adeoye");
        // Act
        mockMVC.perform(post("/configuration")
                .content(asJsonString(configuration))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Assert
        verify(configurationServiceMock, times(1)).saveConfiguration(configuration);
    }

    @Test()
    public void configurationPostRequestWithoutRequestBody_ShouldNeverCallConfigurationServiceSaveConfigurationMethod() throws Exception
    {
        // Act
        mockMVC.perform(post("/configuration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        // Assert
        verify(configurationServiceMock, never()).saveConfiguration(null);
    }

    @Test
    public void configurationDeleteRequestWhenPassedValidRequestBody_ShouldCallConfigurationServiceDeleteConfigurationMethod() throws Exception
    {
        // Act
        mockMVC.perform(delete("/configuration")
                .param("owner", "horatio")
                .param("key", "surname"))
                .andExpect(status().isOk());
        // Assert
        verify(configurationServiceMock, times(1)).deleteConfiguration("horatio", "surname");
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationDeleteRequestWhenPassedInvalidOwnerParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(delete("/configuration")
                    .param("owner", "")
                    .param("key", "surname"));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).deleteConfiguration("", "surname");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationDeleteRequestWhenPassedInvalidKeyParam_ShouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(delete("/configuration")
                    .param("owner", "horatio")
                    .param("key", ""));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).deleteConfiguration("horatio", "");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }
}