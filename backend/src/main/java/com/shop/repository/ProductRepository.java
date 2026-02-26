package com.shop.repository;

import com.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;




@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    

    @Procedure(procedureName ="sp_add_product" )
    Void addProductBySp(
        @Param("p_id") String id,
        @Param("p_name") String name,
        @Param("p_price") BigDecimal price,
        @Param("p_stock") Integer stock,
        @Param("p_image") String image
    );

    @Procedure(procedureName = "sp_get_available_products")
    List<Product> spGetAvailableProducts();
    
}
