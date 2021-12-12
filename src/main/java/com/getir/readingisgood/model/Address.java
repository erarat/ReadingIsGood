package com.getir.readingisgood.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class Address implements Serializable
{
    @NotNull
    private Long addressId;

    private String country;

    private String city;

    private String description;
}
