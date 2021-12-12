package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.model.request.UpdateBookStockRequest;
import com.getir.readingisgood.core.config.JwtTokenProvider;
import com.getir.readingisgood.model.request.PersistNewBookRequest;
import com.getir.readingisgood.model.response.PersistNewBookResponse;
import com.getir.readingisgood.model.response.UpdateBookStockResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class BookControllerTest
{
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void testPersistNewBookSuccessFully() throws Exception
    {
        var res = mockMvc
                .perform(post("/api/v1/books").contentType("application/json")
                        .header("Authorization", "Bearer " + JwtTokenProvider.createToken("test@gmail.com"))
                        .content(objectMapper.writeValueAsString(PersistNewBookRequest.builder()
                                .name("Martin Eden")
                                .author("Jack Londan")
                                .isbn("12345")
                                .publishedYear(LocalDate.now())
                                .price(BigDecimal.valueOf(90))
                                .build())))
                .andDo(print()).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        PersistNewBookResponse saved = objectMapper.readValue(res, PersistNewBookResponse.class);
        assertTrue(saved.getBook().getId() > 0);
    }

    @Test
    void testUpdateBookStockSuccessFully() throws Exception
    {
        var res = mockMvc
                .perform(put("/api/v1/update-stock").contentType("application/json")
                        .header("Authorization", "Bearer " + JwtTokenProvider.createToken("test@gmail.com"))
                        .content(objectMapper.writeValueAsString(UpdateBookStockRequest.builder()
                                .bookId(12L)
                                .quantity(1)
                                .build())))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        UpdateBookStockResponse saved = objectMapper.readValue(res, UpdateBookStockResponse.class);
        assertEquals(1,saved.getQuantity());
    }

}
