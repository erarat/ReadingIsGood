package com.getir.readingisgood.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class Customer implements Serializable
{
    private Long customerId;

    private String name;

    private String email;

}
