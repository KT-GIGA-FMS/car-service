package com.kt_giga_fms.car.dto;

import com.kt_giga_fms.car.entity.Car;
import com.kt_giga_fms.car.entity.CarModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCarRequest {

    @NotNull(message = "차량 모델 ID는 필수입니다")
    private Long carModelId;

    @NotBlank(message = "차량 번호판은 필수입니다")
    @Size(max = 20, message = "차량 번호판은 20자를 초과할 수 없습니다")
    private String plateNo;

    private String imageUrl;

    @Size(max = 20, message = "연료 구분은 20자를 초과할 수 없습니다")
    private String fuelType;

    @Positive(message = "연비는 양수여야 합니다")
    private BigDecimal efficiencyKmPerL;

    @Size(max = 20, message = "사용 상태는 20자를 초과할 수 없습니다")
    private String status;

    @Size(max = 20, message = "차량 타입은 20자를 초과할 수 없습니다")
    private String carType;

    public Car toCar(CarModel carModel) {
        return Car.builder()
                .carModel(carModel)
                .plateNo(plateNo)
                .imageUrl(imageUrl)
                .fuelType(fuelType)
                .efficiencyKmPerL(efficiencyKmPerL)
                .status(status)
                .carType(carType)
                .build();
    }
}
