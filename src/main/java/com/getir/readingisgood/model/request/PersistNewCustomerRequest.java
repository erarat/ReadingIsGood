package com.getir.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.getir.readingisgood.model.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class PersistNewCustomerRequest implements Serializable
{
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Address address;

}
