package com.getir.readingisgood.service;


import com.getir.readingisgood.entity.AddressEntity;
import com.getir.readingisgood.reposityory.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService
{
    private final AddressRepository addressRepository;

    public Optional<AddressEntity> findById(Long id)
    {
        return addressRepository.findById(id);
    }

    public AddressEntity save(AddressEntity entity)
    {
        return addressRepository.save(entity);
    }

}
