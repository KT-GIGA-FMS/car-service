package com.kt_giga_fms.car.repository;

import com.kt_giga_fms.car.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
    Optional<Car> findByPlateNo(String plateNo);
    
    boolean existsByPlateNo(String plateNo);
    
    // 차량 목록 검색 (페이지네이션 포함)
    @Query("SELECT c FROM Car c " +
           "JOIN c.carModel cm " +
           "WHERE (:searchKeyword IS NULL OR " +
           "       c.plateNo LIKE %:searchKeyword% OR " +
           "       cm.name LIKE %:searchKeyword%) " +
           "AND (:status IS NULL OR c.status = :status) " +
           "AND (:carType IS NULL OR c.carType = :carType)")
    Page<Car> findCarsWithFilters(
            @Param("searchKeyword") String searchKeyword,
            @Param("status") String status,
            @Param("carType") String carType,
            Pageable pageable
    );
}
