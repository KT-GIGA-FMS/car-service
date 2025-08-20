package com.kt_giga_fms.car.service;

import com.kt_giga_fms.car.dto.AnalyticsApiResponse;
import com.kt_giga_fms.car.dto.VehicleStatisticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsClientService {
    
    private final RestTemplate restTemplate;
    
    @Value("${analytics.service.url}")
    private String analyticsServiceUrl;
    
    /**
     * 특정 차량의 통계 정보를 Analytics 서비스에서 가져옵니다.
     */
    public VehicleStatisticsDto getVehicleStatistics(String vehicleId) {
        try {
            String url = analyticsServiceUrl + "/api/v1/analytics/vehicles/" + vehicleId + "/statistics";
            log.info("Analytics API 호출: {}", url);
            
            // ParameterizedTypeReference를 사용하여 Generic 타입 정보 전달
            ParameterizedTypeReference<AnalyticsApiResponse<VehicleStatisticsDto>> typeRef = 
                new ParameterizedTypeReference<AnalyticsApiResponse<VehicleStatisticsDto>>() {};
            
            ResponseEntity<AnalyticsApiResponse<VehicleStatisticsDto>> responseEntity = 
                restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
            
            AnalyticsApiResponse<VehicleStatisticsDto> response = responseEntity.getBody();
            
            if (response != null && response.isSuccess() && response.getData() != null) {
                log.info("차량 {} 통계 조회 성공", vehicleId);
                return response.getData();
            } else {
                log.warn("차량 {} 통계 조회 실패: {}", vehicleId, 
                        response != null ? response.getMessage() : "응답 없음");
                return createDefaultStatistics(vehicleId);
            }
            
        } catch (ResourceAccessException e) {
            log.error("Analytics 서비스 연결 실패: {}", e.getMessage());
            return createDefaultStatistics(vehicleId);
        } catch (Exception e) {
            log.error("차량 {} 통계 조회 중 오류 발생: {}", vehicleId, e.getMessage(), e);
            return createDefaultStatistics(vehicleId);
        }
    }
    
    /**
     * 여러 차량의 통계 정보를 Analytics 서비스에서 일괄로 가져옵니다.
     */
    public List<VehicleStatisticsDto> getVehicleStatisticsBatch(List<String> vehicleIds) {
        try {
            String url = analyticsServiceUrl + "/api/v1/analytics/vehicles/statistics/batch";
            log.info("Analytics 일괄 API 호출: {}건", vehicleIds.size());
            
            // ParameterizedTypeReference를 사용하여 Generic 타입 정보 전달
            ParameterizedTypeReference<AnalyticsApiResponse<List<VehicleStatisticsDto>>> typeRef = 
                new ParameterizedTypeReference<AnalyticsApiResponse<List<VehicleStatisticsDto>>>() {};
            
            ResponseEntity<AnalyticsApiResponse<List<VehicleStatisticsDto>>> responseEntity = 
                restTemplate.exchange(url, HttpMethod.POST, 
                    new org.springframework.http.HttpEntity<>(vehicleIds), typeRef);
            
            AnalyticsApiResponse<List<VehicleStatisticsDto>> response = responseEntity.getBody();
            
            if (response != null && response.isSuccess() && response.getData() != null) {
                log.info("차량 {}건 통계 일괄 조회 성공", vehicleIds.size());
                return response.getData();
            } else {
                log.warn("차량 통계 일괄 조회 실패: {}", 
                        response != null ? response.getMessage() : "응답 없음");
                return createDefaultStatisticsBatch(vehicleIds);
            }
            
        } catch (ResourceAccessException e) {
            log.error("Analytics 서비스 연결 실패: {}", e.getMessage());
            return createDefaultStatisticsBatch(vehicleIds);
        } catch (Exception e) {
            log.error("차량 통계 일괄 조회 중 오류 발생: {}", e.getMessage(), e);
            return createDefaultStatisticsBatch(vehicleIds);
        }
    }
    
    /**
     * 기본 통계 정보를 생성합니다 (Analytics 서비스 연결 실패 시).
     */
    private VehicleStatisticsDto createDefaultStatistics(String vehicleId) {
        return new VehicleStatisticsDto(
            vehicleId, 
            "", 
            BigDecimal.ZERO, 
            BigDecimal.ZERO, 
            "", 
            0, 
            0
        );
    }
    
    /**
     * 여러 차량의 기본 통계 정보를 생성합니다 (Analytics 서비스 연결 실패 시).
     */
    private List<VehicleStatisticsDto> createDefaultStatisticsBatch(List<String> vehicleIds) {
        return vehicleIds.stream()
            .map(this::createDefaultStatistics)
            .toList();
    }
}
