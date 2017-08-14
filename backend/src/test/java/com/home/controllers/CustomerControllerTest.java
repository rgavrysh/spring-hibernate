package com.home.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.util.OAuthToken;
import hib.Starter;
import hib.config.Config;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Starter.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Config config;

    private final String adminUsername = "admin";
    private final String adminPassword = "qwe123";
    private OAuthToken oAuthToken;

    @Before
    public void getAccessToke() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic cmVzdDpxd2UxMjM=");
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .headers(httpHeaders)
                .content("username=" + adminUsername + "&password=" + adminPassword + "&grant_type=password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        oAuthToken = objectMapper.readValue(response.getContentAsString(), OAuthToken.class);
    }

    @Test
    public void givenToke_whileGetUserInfo_thenRightUserGetting() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", oAuthToken.getTokenType() + " " + oAuthToken.getAccessToke());
        mockMvc.perform(MockMvcRequestBuilders.get("/me").headers(httpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("admin")));

    }
}
