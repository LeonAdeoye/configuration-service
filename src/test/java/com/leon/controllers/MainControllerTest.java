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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
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
    public void configurationGet_whenPassedValidParams_shouldCallConfigurationServiceGetConfigurationValueMethod() throws Exception
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
    public void configurationGet_whenPassedInvalidOwnerParam_shouldThrowIllegalArgumentException() throws Throwable
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
    public void configurationGet_whenPassedInvalidKeyParam_shouldThrowIllegalArgumentException() throws Throwable
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
    public void reconfigure_shouldCallConfigurationServiceReconfigureMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/reconfigure"));
        // Assert
        verify(configurationServiceMock, times(1)).reconfigure();
    }

    @Test
    public void configurationGetRequest_withoutParams_shouldCallConfigurationServiceGetAllConfigurationsMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/configurations"));
        // Assert
        verify(configurationServiceMock, times(1)).getAllConfigurations();
    }

    @Test
    public void configurationPost_whenPassedValidRequestBody_shouldCallConfigurationServiceSaveConfigurationMethod() throws Exception
    {
        // Arrange
        Configuration configuration = new Configuration("Horatio", "surname", "Adeoye", "papa", "today");
        // Act
        mockMVC.perform(post("/configuration")
                .content(asJsonString(configuration))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Assert
        verify(configurationServiceMock, times(1)).saveConfiguration(any(Configuration.class));
    }

    @Test()
    public void configurationPost_withoutRequestBody_shouldNeverCallConfigurationServiceSaveConfigurationMethod() throws Exception
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
    public void configurationDelete_whenPassedValidRequestBody_shouldCallConfigurationServiceDeleteConfigurationMethod() throws Exception
    {
        // Act
        mockMVC.perform(delete("/configuration")
                .param("id", "horatio"))
                .andExpect(status().isOk());
        // Assert
        verify(configurationServiceMock, times(1)).deleteConfiguration("horatio");
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationDelete_whenPassedInvalidIdParam_shouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(delete("/configuration")
                    .param("id", ""));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).deleteConfiguration("");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test
    public void configurationByOwner_whenPassedValidParams_shouldCallConfigurationServiceGetConfigurationValueMethod() throws Exception
    {
        // Act
        mockMVC.perform(get("/configurationByOwner")
                .param("owner", "horatio"))
                .andExpect(status().isOk());
        // Assert
        verify(configurationServiceMock, times(1)).getConfigurationValues("horatio");
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationByOwner_whenPassedInvalidOwnerParam_shouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/configurationByOwner")
                    .param("owner", ""));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).getConfigurationValues("");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void configurationByOwner_whenPassedNullOwnerParam_shouldThrowIllegalArgumentException() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(get("/configurationByOwner")
                    .param("owner", null));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).getConfigurationValues(null);
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test
    public void configurationByOwner_whenPassedNonExistentOwnerParam_shouldReturnEmptyList() throws Exception
    {
        // Arrange
        when(configurationServiceMock.getConfigurationValues("horatio")).thenReturn(Collections.emptyList());
        // Act
        MvcResult result = mockMVC.perform(get("/configurationByOwner")
                .param("owner", "horatio"))
                .andExpect(status().isOk())
                .andReturn();
        // Assert
        verify(configurationServiceMock, times(1)).getConfigurationValues("horatio");
        assertEquals("[]", result.getResponse().getContentAsString());
    }

    @Test
    public void deleteConfigurationByOwnerAndKey_whenPassedValidOwnerAndKeyParams_shouldCallConfigurationServiceDeleteConfigurationMethod() throws Exception
    {
        // Act
        mockMVC.perform(delete("/configurationByOwnerAndKey")
                .param("owner", "horatio")
                .param("key", "surname"))
                .andExpect(status().isOk());
        // Assert
        verify(configurationServiceMock, times(1)).deleteConfiguration("horatio", "surname");
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteConfigurationByOwnerAndKey_whenPassedInvalidOwnerParam_shouldNotCallConfigurationServiceDeleteConfigurationMethod() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(delete("/configurationByOwnerAndKey")
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
    public void deleteConfigurationByOwnerAndKey_whenPassedValidOwnerButInvalidKeyParam_shouldNotCallConfigurationServiceDeleteConfigurationMethod() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(delete("/configurationByOwnerAndKey")
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

    @Test(expected = IllegalArgumentException.class)
    public void deleteConfigurationByOwnerAndKey_whenPassedNullOwnerParam_shouldNotCallConfigurationServiceDeleteConfigurationMethod() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(delete("/configurationByOwnerAndKey")
                    .param("owner", null)
                    .param("key", "surname"));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).deleteConfiguration(null, "surname");
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteConfigurationByOwnerAndKey_whenPassedValidOwnerButNullKeyParam_shouldNotCallConfigurationServiceDeleteConfigurationMethod() throws Throwable
    {
        try
        {
            // Act
            mockMVC.perform(delete("/configurationByOwnerAndKey")
                    .param("owner", "horatio")
                    .param("key", null));
        }
        catch(NestedServletException e)
        {
            // Assert
            verify(configurationServiceMock, never()).deleteConfiguration("horatio", null);
            assertNotNull( e );
            assertNotNull( e.getCause() );
            assertTrue( e.getCause() instanceof IllegalArgumentException );
            throw e.getCause();
        }
    }
}