package com.getir.readingisgood.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.model.OrderLine;
import com.getir.readingisgood.model.request.ListOrdersByDateIntervalRequest;
import com.getir.readingisgood.model.request.PersistNewOrderRequest;
import com.getir.readingisgood.model.request.QueryByIdOrderRequest;
import com.getir.readingisgood.model.response.ListOrdersByDateIntervalResponse;
import com.getir.readingisgood.model.response.PersistNewOrderResponse;
import com.getir.readingisgood.model.response.QueryByIdOrderResponse;
import com.getir.readingisgood.core.config.JwtTokenProvider;
import com.getir.readingisgood.model.Address;
import com.getir.readingisgood.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class OrderControllerTest
{

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void testPersistNewOrderSuccessFully() throws Exception
    {
        var res = mockMvc
                .perform(post("/api/v1/orders")
                        .header("Authorization", "Bearer " + JwtTokenProvider.createToken("test@gmail.com"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(PersistNewOrderRequest.builder()
                                .order(Order.builder()
                                        .orderLines(Arrays.asList(OrderLine.builder()
                                                .productId(12L)
                                                .quantity(1)
                                                .build()))
                                        .address(Address.builder()
                                                .addressId(10L)
                                                .build())
                                        .build())
                                .build())))
                .andDo(print()).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        PersistNewOrderResponse saved = objectMapper.readValue(res, PersistNewOrderResponse.class);
        assertTrue(saved.getOrder().getOrderLines().size() > 0);
    }

    @Test
    void testQueryOrderByIdSuccessFully() throws Exception
    {
        var res = mockMvc
                .perform(post("/api/v1/get-order-by-id")
                        .header("Authorization", "Bearer " + JwtTokenProvider.createToken("test@gmail.com"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(QueryByIdOrderRequest.builder()
                                .orderId(14L)
                                .build())))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        QueryByIdOrderResponse saved = objectMapper.readValue(res, QueryByIdOrderResponse.class);
        assertTrue(saved.getOrder().getOrderLines().size() > 0);
    }

    @Test
    void testListOrdersByDateIntervalSuccessFully() throws Exception
    {
        var res = mockMvc
                .perform(post("/api/v1/get-orders-by-date-interval")
                        .header("Authorization", "Bearer " + JwtTokenProvider.createToken("test@gmail.com"))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(ListOrdersByDateIntervalRequest.builder()
                                .startDate(LocalDateTime.of(2020, Month.APRIL, 20, 20, 20))
                                .endDate(LocalDateTime.of(2022, Month.APRIL, 20, 20, 20))
                                .build())))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ListOrdersByDateIntervalResponse saved = objectMapper.readValue(res, ListOrdersByDateIntervalResponse.class);
        assertTrue(saved.getOrder().size() > 0);
    }
}
