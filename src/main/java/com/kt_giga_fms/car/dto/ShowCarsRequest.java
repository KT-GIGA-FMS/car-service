package com.kt_giga_fms.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "차량 목록 조회 요청")
public class ShowCarsRequest {

    @Schema(description = "검색 키워드 (차량번호, 차량명 검색)", example = "아반떼")
    private String searchKeyword;
    
    @Schema(description = "상태 필터 (사용가능, 사용불가 등)", example = "사용가능")
    private String status;
    
    @Schema(description = "법인/개인 구분", example = "법인", allowableValues = {"법인", "개인"})
    private String carType;
    
    @Schema(description = "페이지 번호 (0부터 시작)", example = "0", defaultValue = "0")
    private Integer page = 0;
    
    @Schema(description = "페이지 크기 (기본값: 10개씩)", example = "10", defaultValue = "10")
    private Integer size = 10;
    
    @Schema(description = "정렬 기준", example = "createdAt", defaultValue = "createdAt")
    private String sortBy = "createdAt";
    
    @Schema(description = "정렬 방향 (ASC, DESC)", example = "DESC", defaultValue = "DESC", allowableValues = {"ASC", "DESC"})
    private String sortDirection = "DESC";
}
