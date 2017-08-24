package com.home.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.util.OAuthToken;
import hib.Starter;
import hib.model.Venue;
import hib.restEntity.CreateAddress;
import hib.restEntity.CreateVenue;
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

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Starter.class)
@AutoConfigureMockMvc
public class BookingsControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private OAuthToken oAuthToken;
    private String adminUsername = "admin";
    private String adminPassword = "qwe123";
    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpHeaders commonHttpHeaders = new HttpHeaders();
    private Venue savedVenue;

    @Before
    public void getAccessToken() throws Exception {
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
        CreateAddress address = new CreateAddress("Barcelona", "park", 1, 2);
        CreateVenue createVenue = new CreateVenue("Grass", address, 1, "08:00:00", "23:00:00");
        String json = objectMapper.writeValueAsString(createVenue);
        MockHttpServletResponse venue = mockMvc.perform(MockMvcRequestBuilders.post("/venue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        savedVenue = objectMapper.readValue(venue.getContentAsString(), Venue.class);
    }

    @Test
    public void givenUserAndVenueId_whenGetBookings_thenRightFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/me/bookings/venue/{venueId}", savedVenue.getId())
                .headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void givenUser_whenGetBookings_thenRightFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/me/bookings/venue")
                .headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    public void givenUserAndVenueIdAndBookingTime_whenPostBooking_thenVenueBooked() throws Exception {
        String json = "{\"start_date_time\": \"2017-08-14 09:00:00\", \"end_date_time\": \"2017-08-14 10:00:00\", \"customer_id\": 1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/bookings/venue/{venueId}/bookTime", savedVenue.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.venue.name").value(savedVenue.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDateTime").value(new Date(1502694000000L).getTime()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDateTime").value(new Date(1502697600000L).getTime()));
    }

    @Test
    public void givenBookingId_whenDeleteBooking_thenRemoved() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/bookings/{id}", 1)
                .headers(commonHttpHeaders))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
