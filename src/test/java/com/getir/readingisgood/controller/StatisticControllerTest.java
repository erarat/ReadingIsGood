package com.getir.readingisgood.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.model.Statistic;
import com.getir.readingisgood.core.config.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class StatisticControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;


    @Test
    void testBadRequestListStatistic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/monthly-statistics")
                .header("Authorization", "Bearer " + JwtTokenProvider.createToken("test@gmail.com")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUnauthorizedListStatistic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/monthly-statistics"))
                .andExpect(status().isUnauthorized());
    }
}
