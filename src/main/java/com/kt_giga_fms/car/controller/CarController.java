package com.kt_giga_fms.car.controller;

import com.kt_giga_fms.car.dto.CarResponse;
import com.kt_giga_fms.car.dto.RegisterCarRequest;
import com.kt_giga_fms.car.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponse> registerCar(@Valid @RequestBody RegisterCarRequest request) {
        log.info("차량 등록 API 호출: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.registerCar(request));
    }
}
