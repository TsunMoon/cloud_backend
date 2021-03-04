package com.example.demo.repository;

import com.example.demo.entity.PriceByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceByDateRepository extends JpaRepository<PriceByDate, Integer> {
    List<PriceByDate> findByPrice(float price);

    @Query(value = "SELECT pd FROM PriceByDate pd WHERE pd.product.id = :id")
    List<PriceByDate> findByProductId(@Param("id") int id);

}
