package com.getir.readingisgood.reposityory;

import com.getir.readingisgood.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long>
{
    @Query("select stockEntity from StockEntity stockEntity where stockEntity.product.id = :product" )
    public StockEntity findByProductId(@Param("product") Long product);
}
