package com.kt_giga_fms.car.dto;

import com.kt_giga_fms.car.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {

    private Long id;
    private Long carModelId;
    private String carModelName;
    private String plateNo;
    private String imageUrl;
    private String fuelType;
    private BigDecimal efficiencyKmPerL;
    private String status;
    private String carType;
    private LocalDateTime createdAt;

    public static CarResponse from(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .carModelId(car.getCarModel().getId())
                .carModelName(car.getCarModel().getName())
                .plateNo(car.getPlateNo())
                .imageUrl(car.getImageUrl())
                .fuelType(car.getFuelType())
                .efficiencyKmPerL(car.getEfficiencyKmPerL())
                .status(car.getStatus())
                .carType(car.getCarType())
                .createdAt(car.getCreatedAt())
                .build();
    }
}
