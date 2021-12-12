package com.getir.readingisgood.model.response;

import com.getir.readingisgood.model.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class PersistNewCustomerResponse implements Serializable
{
    private Customer customer;
}
