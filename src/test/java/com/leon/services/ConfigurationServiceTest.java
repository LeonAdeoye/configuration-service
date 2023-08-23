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
        Configuration configuration = new Configuration("Horatio", "surname", "Adeoye","papa", "today");
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
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today"),
                new Configuration("Harper", "surname", "Adeoye", "papa", "today"));
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
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today"),
                new Configuration("Horatio", "firstName", "Ethan", "papa", "today"));
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
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today"),
                new Configuration("Horatio", "firstName", "Ethan", "papa", "today"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        String age = configurationService.getConfigurationValue("Horatio", "age");
        // Assert
        assertEquals("empty string should be returned for non-existent configuration", "", age);
    }

    @Test
    public void whenConfigurationDoesNotExist_deleteConfiguration_shouldNotCallRepositoryDeleteMethod() throws Exception
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today", "1"),
                new Configuration("Horatio", "firstName", "Ethan", "papa", "today", "2"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        configurationService.deleteConfiguration("3");
        // Assert
        verify(configurationRepositoryMock, never()).deleteById(any());
    }

    @Test
    public void whenConfigurationExists_deleteConfiguration_shouldCallRepositoryDeleteMethod()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today", "1"),
                new Configuration("Horatio", "firstName","Ethan", "papa", "today", "2"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        configurationService.deleteConfiguration("1");
        // Assert
        verify(configurationRepositoryMock, times(1)).deleteById(any());
    }

    // test getConfigurationValues
    @Test
    public void whenConfigurationExists_getConfigurationValues_shouldReturnValidSizes()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today"),
                new Configuration("Horatio", "firstName", "Ethan", "papa", "today"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        List<Configuration> result = configurationService.getConfigurationValues("Horatio");
        // Assert
        assertEquals("list sizes should match", configs.size(), result.size());
    }

    @Test
    public void whenConfigurationExists_getConfigurationValues_shouldReturnValidValue()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today"),
                new Configuration("Horatio", "firstName", "Ethan", "papa", "today"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        List<Configuration> result = configurationService.getConfigurationValues("Horatio");
        // Assert
        if(result.get(0).getKey().equals("surname"))
            assertEquals("surname config should be returned", "Adeoye", result.get(0).getValue());
        else
            assertEquals("first name config should be returned", "Ethan", result.get(0).getValue());

        if(result.get(1).getKey().equals("surname"))
            assertEquals("surname config should be returned", "Adeoye", result.get(1).getValue());
        else
            assertEquals("first name config should be returned", "Ethan", result.get(1).getValue());
    }

    @Test
    public void whenConfigurationNotExists_getConfigurationValues_shouldReturnEmptyList()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today"),
                new Configuration("Horatio", "firstName", "Ethan", "papa", "today"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        List<Configuration> result = configurationService.getConfigurationValues("Harper");
        // Assert
        assertEquals("empty list should be returned for non-existent configuration", 0, result.size());
    }

    @Test
    public void whenPassedValidOwnerAndKey_deleteConfiguration_shouldDeleteConfiguration()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today", "1"),
                new Configuration("Horatio", "firstName","Ethan", "papa", "today", "2"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        configurationService.deleteConfiguration("Horatio", "surname");
        // Assert
        verify(configurationRepositoryMock, times(1)).deleteById("1");
    }

    @Test
    public void whenPassedNonExistentOwnerAndKey_deleteConfiguration_shouldNotDeleteConfiguration()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today", "1"),
                new Configuration("Horatio", "firstName","Ethan", "papa", "today", "2"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        configurationService.deleteConfiguration("Harper", "surname");
        // Assert
        verify(configurationRepositoryMock, never()).deleteById(any());
    }

    @Test
    public void whenPassedValidOwnerAndNonExistentKey_deleteConfiguration_shouldNotDeleteConfiguration()
    {
        // Arrange
        List<Configuration> configs = Arrays.asList(
                new Configuration("Horatio", "surname", "Adeoye", "papa", "today", "1"),
                new Configuration("Horatio", "firstName","Ethan", "papa", "today", "2"));
        when(configurationRepositoryMock.findAll()).thenReturn(configs);
        configurationService.reconfigure();
        // Act
        configurationService.deleteConfiguration("Horatio", "age");
        // Assert
        verify(configurationRepositoryMock, never()).deleteById(any());
    }

}
