package com.kt_giga_fms.car.repository;

import com.kt_giga_fms.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
    Optional<Car> findByPlateNo(String plateNo);
    
    boolean existsByPlateNo(String plateNo);
}
