package com.getir.readingisgood.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.getir.readingisgood.entity.AddressEntity;
import com.getir.readingisgood.reposityory.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {

    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave(){
        AddressEntity addressEntity = AddressEntity.builder().country("").city("").description("").build();
        AddressEntity addressEntityResult = AddressEntity.builder().id(1l).country("").city("").description("").build();

        when(addressRepository.save(addressEntity)).thenReturn(addressEntityResult);
        assertEquals(1l,addressService.save(addressEntity).getId().longValue());
    }

    @Test
    public void testFindById(){
        AddressEntity addressEntityResult = AddressEntity.builder().id(1l).country("").city("").description("").build();

        when(addressRepository.findById(1l)).thenReturn(Optional.ofNullable(addressEntityResult));
        assertEquals(1l,addressService.findById(1l).get().getId().longValue());
    }
}