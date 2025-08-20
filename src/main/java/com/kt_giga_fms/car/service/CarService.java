package com.kt_giga_fms.car.service;

import com.kt_giga_fms.car.dto.ShowCarsRequest;
import com.kt_giga_fms.car.dto.ShowCarsResponse;
import com.kt_giga_fms.car.dto.CarResponse;
import com.kt_giga_fms.car.dto.RegisterCarRequest;
import com.kt_giga_fms.car.dto.VehicleStatisticsDto;
import com.kt_giga_fms.car.entity.Car;
import com.kt_giga_fms.car.entity.CarModel;
import com.kt_giga_fms.car.repository.CarModelRepository;
import com.kt_giga_fms.car.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final AnalyticsClientService analyticsClientService;

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

    public ShowCarsResponse getCarList(ShowCarsRequest request) {
        log.info("차량 목록 조회 요청: {}", request);

        // 페이지네이션 및 정렬 설정
        Sort sort = Sort.by(
            Sort.Direction.fromString(request.getSortDirection()),
            request.getSortBy()
        );
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        // 차량 목록 조회
        Page<Car> carPage = carRepository.findCarsWithFilters(
            request.getSearchKeyword(),
            request.getStatus(),
            request.getCarType(),
            pageable
        );

        // 페이지에 해당하는 차량들의 통계 정보를 일괄로 조회
        List<String> vehicleIds = carPage.getContent().stream()
            .map(car -> car.getId().toString())
            .toList();
        
        List<VehicleStatisticsDto> statistics = analyticsClientService.getVehicleStatisticsBatch(vehicleIds);
        
        // 차량 ID를 키로 하는 Map 생성
        Map<String, VehicleStatisticsDto> statisticsMap = statistics.stream()
            .collect(Collectors.toMap(VehicleStatisticsDto::getVehicleId, stats -> stats));

        // 응답 DTO 변환
        return ShowCarsResponse.builder()
            .cars(carPage.getContent().stream()
                .map(car -> convertToCarListItem(car, statisticsMap.get(car.getId().toString())))
                .toList())
            .pageInfo(buildPageInfo(carPage))
            .totalCount(carPage.getTotalElements())
            .build();
    }

    private ShowCarsResponse.CarListItem convertToCarListItem(Car car, VehicleStatisticsDto statistics) {
        // 통계 정보가 없는 경우 기본값 사용
        if (statistics == null) {
            statistics = new VehicleStatisticsDto(
                car.getId().toString(), "", BigDecimal.ZERO, BigDecimal.ZERO, "", 0, 0
            );
        }
        
        return ShowCarsResponse.CarListItem.builder()
            .id(car.getId())
            .plateNo(car.getPlateNo())
            .carModelName(car.getCarModel().getName())
            .imageUrl(car.getImageUrl())
            .status(car.getStatus())
            .carType(car.getCarType())
            .fuelType(car.getFuelType())
            .efficiencyKmPerL(car.getEfficiencyKmPerL() != null ? 
                car.getEfficiencyKmPerL().toString() : null)
            .totalDistance(statistics.getTotalDistanceFormatted())
            .monthlyDistance(statistics.getMonthlyDistanceFormatted())
            .createdAt(car.getCreatedAt().toString())
            .remarks("") // TODO: 특이사항 데이터 연동 필요
            .build();
    }

    private ShowCarsResponse.PageInfo buildPageInfo(Page<Car> carPage) {
        return ShowCarsResponse.PageInfo.builder()
            .currentPage(carPage.getNumber() + 1) // 0부터 시작하므로 +1
            .totalPages(carPage.getTotalPages())
            .pageSize(carPage.getSize())
            .hasNext(carPage.hasNext())
            .hasPrevious(carPage.hasPrevious())
            .totalElements(carPage.getTotalElements())
            .build();
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
