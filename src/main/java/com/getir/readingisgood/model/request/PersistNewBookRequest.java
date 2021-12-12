package com.getir.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
public class PersistNewBookRequest implements Serializable
{
    @NotBlank
    private String name;

    @NotBlank
    private String isbn;

    @NotBlank
    private String author;

    @NotNull
    private LocalDate publishedYear;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;
}
