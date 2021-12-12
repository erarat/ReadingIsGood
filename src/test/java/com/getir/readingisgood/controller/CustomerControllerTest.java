package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.model.request.PersistNewCustomerRequest;
import com.getir.readingisgood.model.Address;
import com.getir.readingisgood.model.response.PersistNewCustomerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CustomerControllerTest
{
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void testPersistNewCustomerSuccessFully() throws Exception
    {
        var res = mockMvc
                .perform(post("/api/v1/customers").contentType("application/json")
                        .content(objectMapper.writeValueAsString(PersistNewCustomerRequest.builder()
                                .name("Eray")
                                .email("test@gmail.com")
                                .password("$!Fr3")
                                .address(Address.builder()
                                        .city("Ankara")
                                        .country("Turkey")
                                        .description("Business Address")
                                        .build())
                                .build())))
                .andDo(print()).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        PersistNewCustomerResponse saved = objectMapper.readValue(res, PersistNewCustomerResponse.class);
        assertTrue(saved.getCustomer().getCustomerId() > 0);
        assertEquals("Eray", saved.getCustomer().getName());
        assertEquals("test@gmail.com", saved.getCustomer().getEmail());

        mockMvc
                .perform(post("/api/v1/customers").contentType("application/json")
                        .content(objectMapper.writeValueAsString(PersistNewCustomerRequest.builder()
                                .name("Kaya")
                                .password("P455w0rD")
                                .address(Address.builder()
                                        .city("Ankara")
                                        .country("Turkey")
                                        .description("Home Address")
                                        .build())
                                .build())))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        mockMvc
                .perform(post("/api/v1/customers").contentType("application/json")
                        .content(objectMapper.writeValueAsString(PersistNewCustomerRequest.builder()
                                .name("Erarat")
                                .password("+%rreF3")
                                .address(Address.builder()
                                        .city("Ankara")
                                        .country("Turkey")
                                        .description("Business Address")
                                        .build())
                                .build())))
                .andDo(print()).andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();


    }

}
