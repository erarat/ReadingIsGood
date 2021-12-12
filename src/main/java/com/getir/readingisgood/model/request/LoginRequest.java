package com.getir.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginRequest implements Serializable
{
    @NotNull
    private String email;

    @NotNull
    private String password;
}
