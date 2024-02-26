package com.yudiol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yudiol.model.Phrase;
import com.yudiol.service.HelperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class HelperControllerTest {

    @MockBean
    private HelperService helperService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Phrase phrase = null;

    @BeforeEach
    public void setup() {
        phrase = new Phrase("Hello");
    }


    @Test
    public void add_whenInvoked_thenAddPhrase() throws Exception {

        String jsonPhrase = objectMapper.writeValueAsString(phrase);

        mockMvc.perform(post("/help-service/v1/support")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPhrase))
                .andExpect(status().isAccepted());

        verify(helperService, (times(1))).addPhrase(phrase);
    }

    @Test
    public void get_whenInvoked_thenReturnedPhrase() throws Exception {

        when(helperService.getRandomPhrase()).thenReturn(phrase);

        mockMvc.perform(get("/help-service/v1/support")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.text").value("Hello"));

        verify(helperService, times(1)).getRandomPhrase();
    }
}