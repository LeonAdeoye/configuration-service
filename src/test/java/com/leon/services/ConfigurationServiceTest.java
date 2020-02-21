package com.leon.services;

import com.leon.models.Configuration;
import com.leon.repositories.ConfigurationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void whenPassedValidConfiguration_saveConfiguration_shouldCallSaveMethodInRepositoryMock()
    {
        // Arrange
        Configuration configuration = new Configuration("Horatio", "surname", "Adeoye");
        // Act
        configurationService.saveConfiguration(configuration);
        // Assert
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
        // Act
        List<Configuration> result = configurationService.getAllConfigurations();
        // Assert
        assertEquals("list sizes should match", configs.size(), result.size());
    }

    @Test
    public void whenConfigurationExists_getConfigurationValue_shouldReturnValidValue()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye"),
                new Configuration("Horatio", "firstName", "Ethan"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        String surname = configurationService.getConfigurationValue("Horatio", "surname");
        String firstName = configurationService.getConfigurationValue("Horatio", "firstName");
        // Assert
        assertEquals("surname config should be returned", "Adeoye", surname);
        assertEquals("first name config should be returned", "Ethan", firstName);
    }

    @Test
    public void whenConfigurationDoesNotExist_getConfigurationValue_shouldReturnEmptyStringValue()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye"),
                new Configuration("Horatio", "firstName", "Ethan"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        String age = configurationService.getConfigurationValue("Horatio", "age");
        // Assert
        assertEquals("empty string should be returned for non-existent configuration", "", age);
    }

    @Test
    public void whenConfigurationDoesNotExist_deleteConfiguration_shouldNotCallRepositoryDeleteMethod()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye"),
                new Configuration("Horatio", "firstName", "Ethan"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        configurationService.deleteConfiguration("Horatio", "age");
        // Assert
        verify(configurationRepositoryMock, never()).deleteById("");
    }

    @Test
    public void whenConfigurationExists_deleteConfiguration_shouldCallRepositoryDeleteMethod()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye"),
                new Configuration("Horatio", "firstName", "Ethan"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        configurationService.deleteConfiguration("Horatio", "surname");
        // Assert
        verify(configurationRepositoryMock, times(1)).deleteById("");
    }
}
