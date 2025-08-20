package com.kt_giga_fms.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "차량 목록 조회 응답")
public class ShowCarsResponse {

    @Schema(description = "차량 목록")
    private List<CarListItem> cars;
    
    @Schema(description = "페이지네이션 정보")
    private PageInfo pageInfo;
    
    @Schema(description = "전체 차량 수", example = "25")
    private long totalCount;
    
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "차량 목록 항목")
    public static class CarListItem {
        @Schema(description = "차량 ID", example = "1")
        private Long id;
        
        @Schema(description = "차량번호", example = "12가3456")
        private String plateNo;
        
        @Schema(description = "차량 모델명", example = "현대 아반떼")
        private String carModelName;
        
        @Schema(description = "차량 이미지 URL", example = "https://example.com/car1.jpg")
        private String imageUrl;
        
        @Schema(description = "상태 (사용가능, 사용불가 등)", example = "사용가능")
        private String status;
        
        @Schema(description = "법인/개인 구분", example = "법인", allowableValues = {"법인", "개인"})
        private String carType;
        
        @Schema(description = "연료 구분", example = "휘발유")
        private String fuelType;
        
        @Schema(description = "연비 (km/l)", example = "15.5")
        private String efficiencyKmPerL;
        
        @Schema(description = "전체 누적거리 (km)", example = "10,000km")
        private String totalDistance;
        
        @Schema(description = "이번달 운행거리 (km)", example = "110km")
        private String monthlyDistance;
        
        @Schema(description = "등록일", example = "2025-08-20")
        private String createdAt;
        
        @Schema(description = "특이사항", example = "정기점검")
        private String remarks;
    }
    
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "페이지네이션 정보")
    public static class PageInfo {
        @Schema(description = "현재 페이지 (1부터 시작)", example = "1")
        private int currentPage;
        
        @Schema(description = "페이지 크기", example = "10")
        private int pageSize;
        
        @Schema(description = "전체 페이지 수", example = "3")
        private int totalPages;
        
        @Schema(description = "다음 페이지 존재 여부", example = "true")
        private boolean hasNext;
        
        @Schema(description = "이전 페이지 존재 여부", example = "false")
        private boolean hasPrevious;
        
        @Schema(description = "전체 요소 수", example = "25")
        private long totalElements;
    }
}
