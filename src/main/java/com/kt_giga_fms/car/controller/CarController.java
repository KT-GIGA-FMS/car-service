package com.kt_giga_fms.car.controller;

import com.kt_giga_fms.car.dto.CarResponse;
import com.kt_giga_fms.car.dto.RegisterCarRequest;
import com.kt_giga_fms.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Car Management", description = "차량 관리 API")
public class CarController {

    private final CarService carService;

    @PostMapping
    @Operation(summary = "차량 등록", description = "새로운 차량을 시스템에 등록합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "차량 등록 성공",
            content = @Content(schema = @Schema(implementation = CarResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
        @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<CarResponse> registerCar(
            @Parameter(description = "차량 등록 요청 정보", required = true)
            @Valid @RequestBody RegisterCarRequest request) {
        log.info("차량 등록 API 호출: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.registerCar(request));
    }
}
