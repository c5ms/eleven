package com.eleven.domain.product;

import com.eleven.domain.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<Product, ProductKey> {

    Collection<Product> findByPlan(@Param("key") Plan plan);

}
