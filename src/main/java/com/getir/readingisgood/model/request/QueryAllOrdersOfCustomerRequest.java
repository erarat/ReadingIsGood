package com.getir.readingisgood.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.getir.readingisgood.model.Pagination;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class QueryAllOrdersOfCustomerRequest implements Serializable
{
    @NotNull
    private Pagination pagination;
}
