package com.kt_giga_fms.car.service;

import com.kt_giga_fms.car.dto.CarResponse;
import com.kt_giga_fms.car.dto.RegisterCarRequest;
import com.kt_giga_fms.car.entity.Car;
import com.kt_giga_fms.car.entity.CarModel;
import com.kt_giga_fms.car.repository.CarModelRepository;
import com.kt_giga_fms.car.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;

    @Transactional
    public CarResponse registerCar(RegisterCarRequest request) {
        log.info("차량 등록 요청: {}", request);

        // 차량 모델 존재 여부 확인
        CarModel carModel = findCarModelById(request.getCarModelId());
        // 차량 번호판 중복 확인
        validateDuplicatedPlateNo(request.getPlateNo());

        // 차량 저장
        Car car = carRepository.save(request.toCar(carModel));
        log.info("차량 등록 완료: {}", car.getId());

        // 응답 DTO 생성
        return CarResponse.from(car);
    }

    private void validateDuplicatedPlateNo(String plateNo) {
        if (carRepository.existsByPlateNo(plateNo)) {
            throw new IllegalArgumentException("이미 등록된 차량 번호판입니다: " + plateNo);
        }
    }

    private CarModel findCarModelById(Long carModelId) {
        CarModel carModel = carModelRepository.findById(carModelId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 차량 모델입니다: " + carModelId));
        return carModel;
    }
}
