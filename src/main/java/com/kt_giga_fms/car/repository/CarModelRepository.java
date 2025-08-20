package com.kt_giga_fms.car.repository;

import com.kt_giga_fms.car.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    
    Optional<CarModel> findById(Long id);
}
