package com.getir.readingisgood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Statistic implements Serializable
{
    private String month;

    private BigInteger totalOrderCount;

    private BigInteger totalBookCount;

    private BigDecimal totalPurchasedAmount;
}
