package com.example.demo.repository;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT new com.example.demo.dto.ProductDTO(p.id, p.name, p.quantity, pd.price , c.name , p.image, p.isUsed)   from Product p " +
            "JOIN PriceByDate pd on p.id = pd.product.id " +
            "JOIN Category c on c.id = p.category.id " +
            "WHERE p.name LIKE CONCAT('%',:keyword,'%') and pd.isUsed = true and p.category.id = :categoryId and p.isUsed = true",
        countQuery = "SELECT new com.example.demo.dto.ProductDTO(p.id, p.name, p.quantity, pd.price , c.name , p.image, p.isUsed)  from Product p " +
                "JOIN PriceByDate pd on p.id = pd.product.id " +
                "JOIN Category c on c.id = p.category.id " +
                "WHERE p.name LIKE CONCAT('%',:keyword,'%') and pd.isUsed = true and p.category.id = :categoryId and p.isUsed = true"
    )
    List<ProductDTO> getAllProductDTO(@Param("keyword") String keyword, @Param("categoryId") int categoryId, Pageable pageable);

    List<Product> findByNameContaining(String name);

    @Query(value = "SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.isUsed = true")
    List<Product> findProductByCategoryId (@Param("categoryId") int categoryId);

    @Query(value = "SELECT new com.example.demo.dto.ProductDTO(p.id, p.name, p.quantity, pd.price, c.name , p.image, p.isUsed )  FROM Product p JOIN Category c on c.id = p.category.id " +
            "JOIN PriceByDate pd on pd.product.id = p.id WHERE p.id = :id AND pd.isUsed = true ")
    ProductDTO findProductById(@Param("id") int id);



}
