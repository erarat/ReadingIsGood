package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.CustomerEntity;
import com.getir.readingisgood.model.Statistic;
import com.getir.readingisgood.reposityory.CustomerRepository;
import lombok.RequiredArgsConstructor;
import com.getir.readingisgood.core.config.SecurityFilter;
import com.getir.readingisgood.reposityory.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService
{
    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final CustomerRepository customerRepository;

    public List<Statistic> listStatistic()
    {
        List<Statistic> result = new ArrayList<>();

        String email = SecurityFilter.USER_EMAIL_TL.get();

        CustomerEntity customerEntity = customerRepository.findByEmail(email);

        List<Object[]> statistics = orderRepository.listStatistic(customerEntity.getId());
        for (Object[] item : statistics)
        {
            Statistic statistic = convertStatisticObjectsToStatisticDto(item);
            result.add(statistic);
        }
        return result;
    }

    private Statistic convertStatisticObjectsToStatisticDto(Object[] item)
    {
        Statistic statistic = new Statistic();
        if (null != item[0])
        {
            LocalDateTime month = ((Timestamp) item[0]).toLocalDateTime();
            statistic.setMonth(month.getMonth().name());
        }
        if (null != item[1])
        {
            BigInteger totalOrderCount = (BigInteger) item[1];
            statistic.setTotalOrderCount(totalOrderCount);
        }
        if (null != item[2])
        {
            BigInteger totalBookCount = (BigInteger) item[2];
            statistic.setTotalBookCount(totalBookCount);
        }
        if (null != item[3])
        {
            BigDecimal totalPurchasedAmount = (BigDecimal) item[3];
            statistic.setTotalPurchasedAmount(totalPurchasedAmount);
        }
        return statistic;
    }
}
