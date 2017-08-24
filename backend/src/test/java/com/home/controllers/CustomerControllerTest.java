package com.home.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.util.OAuthToken;
import hib.Starter;
import hib.config.Config;
import hib.restEntity.CreateCustomer;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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
    private HttpHeaders commonHttpHeaders = new HttpHeaders();
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        oAuthToken = objectMapper.readValue(response.getContentAsString(), OAuthToken.class);
        commonHttpHeaders.add("Authorization", oAuthToken.getTokenType() + " " + oAuthToken.getAccessToke());
    }

    @Test
    public void givenToke_whileGetUserInfo_thenRightUserGetting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/me").headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("admin@admin.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role[0].name").value("ROLE_ADMIN"));
    }

    @Test
    public void givenUserId_whileGetUserById_thenRightUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/{id}", 1).headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("admin"));
    }

    @Test
    public void givenCustomer_whileAddingNewUser_thenPersisted() throws Exception {
        final CreateCustomer createCustomer =
                new CreateCustomer(this.getClass().getSimpleName(), 911, "user@user.com");
        String json = objectMapper.writeValueAsString(createCustomer);
        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(this.getClass().getSimpleName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(911))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("user@user.com"))
                .andReturn();
    }

    @Test
    public void givenToken_whileGettingListOfUsers_thenListReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers").headers(commonHttpHeaders))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    public void givenCustomer_whileDeletingUser_thenRemoved() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/{id}/delete", 2)
                .headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}
