package com.getir.readingisgood.reposityory;

import com.getir.readingisgood.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{
    @Query(value = "select ord from OrderEntity ord left join fetch ord.orderLines orderlines where ord.customer.id = :customerId")
    List<OrderEntity> queryAllOrdersOCustomerId(@Param("customerId") Long customerId, Pageable pageable);

    @Query(value = "select ord from OrderEntity ord where ord.createTime between :startDate and :endDate")
    List<OrderEntity> listOrdersByDateInterval(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT date_trunc('month',o.CREATE_TIME), " +
            "count(distinct o.ID) as total_order, " +
            "sum(t.AMOUNT) as total_book, " +
            "sum(b.PRICE * t.AMOUNT) as TOTAL_AMOUNT " +
            "FROM ORDER_LINE t , PRODUCT_ORDER o , BOOK b " +
            "WHERE o.ID = t.ORDER_ID and t.PRODUCT_ID = b.id and o.CUSTOMER_ID = :customerId " +
            "group by date_trunc('month',o.CREATE_TIME)", nativeQuery = true)
    List<Object[]> listStatistic(@Param("customerId") Long customerId);


}
