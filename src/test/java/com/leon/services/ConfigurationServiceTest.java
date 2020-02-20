package com.leon.services;

import com.leon.models.Configuration;
import com.leon.repositories.ConfigurationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import org.mockito.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConfigurationServiceTest
{
    @MockBean
    private ConfigurationRepository configurationRepositoryMock;

    @Autowired
    private ConfigurationService configurationService;

    @Test
    public void whenPassedNonExistentConfiguration_getConfigurationValue_shouldReturnEmptyString()
    {
        // Arrange
        //ConfigurationService configurationServiceMock = Mockito.mock(ConfigurationService.class);
        //when(configurationServiceMock.getConfigurationValue())
        // Act
        //String result = configurationServiceMock.getConfigurationValue("horatio", "surname");
        //Assert
        //assertEquals("should return empty string", "", result);
    }

    @Test
    public void whenPassedValidConfiguration_saveConfiguration_shouldCallSaveMethodInRepositoryMock()
    {
        // Arrange
        Configuration configuration = new Configuration("Horatio", "surname", "Adeoye");
        // Act
        configurationService.saveConfiguration(configuration);
        //Assert
        verify(configurationRepositoryMock, times(1)).save(configuration);
    }

    @Test
    public void whenPassedValidConfiguration_reconfigure_shouldCallFindAllMethodInRepositoryMock()
    {
        // Act
        configurationService.reconfigure();
        //Assert
        verify(configurationRepositoryMock, times(1)).findAll();
    }

    @Test
    public void whenConfigurationsExist_getAllConfigurations_shouldReturnAllConfigurations()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye"),
                new Configuration("Harper", "surname", "Adeoye"));

        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();

        //Act
        List<Configuration> result = configurationService.getAllConfigurations();

        assertEquals(configs.size(), result.size());
    }
}
