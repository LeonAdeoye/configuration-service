package com.leon.controllers;

import com.leon.services.ConfigurationService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void get_getConfiguration_Should() throws Exception
    {
        mockMVC.perform(get("/configuration")
                .param("owner", "horatio")
                .param("key", "surname"))
                .andExpect(status().isOk());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPassedInvalidOwnerParam_getConfiguration_ShouldThrowIllegalArgumentException() throws Exception
    {
        mockMVC.perform(get("/configuration")
                .param("owner", "")
                .param("key", ""));
    }

    @Test
    public void getRequest_reconfigure_shouldCallConfigurationServiceReconfigureMethod() throws Exception
    {
        mockMVC.perform(get("/reconfigure"));

        verify(configurationServiceMock, times(1)).reconfigure();
    }

    @Test
    public void getRequestWithoutParams_configurations_shouldCallConfigurationServiceGetAllConfigurationsMethod() throws Exception
    {
        mockMVC.perform(get("/configurations"));

        verify(configurationServiceMock, times(1)).getAllConfigurations();
    }
}