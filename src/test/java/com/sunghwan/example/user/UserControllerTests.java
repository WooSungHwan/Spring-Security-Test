package com.sunghwan.example.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunghwan.example.ExampleApplication;
import com.sunghwan.example.user.domain.AuthenticationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
@WebAppConfiguration
public class UserControllerTests {

    @Autowired
    private WebApplicationContext cx;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(cx).build();
    }

    @Test
    public void loginTest() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("admin");
        request.setPassword("admin");

        ObjectMapper om = new ObjectMapper();

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.username", is(request.getUsername().toUpperCase())))
                //.andReturn(jsonPath("$"), );
                .andExpect(jsonPath("$.authorities[*].authority", hasItem("USER")))
        ;
    }
}