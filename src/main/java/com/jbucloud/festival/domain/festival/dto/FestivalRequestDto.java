package com.jbucloud.festival.domain.festival.dto;


import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Schema(description = "축제 정보 등록/수정 요청 DTO")
@Getter
@Setter
public class FestivalRequestDto {
    
    @Schema(description = "축제 정보 타입", 
            example = "FOOD_TRUCK",
            allowableValues = {"NOTICE", "BOOTH", "EVENT", "FOOD_TRUCK", "TIMETABLE"})
    @NotNull(message = "정보 타입은 필수입니다.")
    private InfoType infoType;

    @Schema(description = "정보 제목",
            example = "공지사항은 테이블 배부 안내, 부스는 부스제목 등..")
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @Schema(description = "정보 미리보기 설명",
            example = "미리보일 정보 비워도됨")
    private String preview;

    @Schema(description = "축제 시작 시간", 
            example = "2024-05-15T10:00:00")
    private LocalDateTime startTime;
    
    @Schema(description = "축제 종료 시간", 
            example = "2024-05-15T18:00:00")
    private LocalDateTime endTime;
    
    @Schema(description = "상시 운영 여부", 
            example = "false")
    private Boolean isAlwaysOpen = false;

    @Schema(description = "축제 상세 설명", 
            example = "당일 강수량에 따라 일부 프로그램이....")
    private String detailedDescription;

    @Schema(description = "축제 위치", 
            example = "FOOD_TRUCK",
            allowableValues = {"BOOTH_ZONE", "STAGE_PHOTO_BOOTH", "DIY_DATE_BOOTH", 
                             "COMPANY_BOOTH", "PARTNER_BOOTH", "VIKING_BOOTH", 
                             "FOREIGN_STUDENT_BOOTH", "ALCOHOL_BOOTH", "FOOD_TRUCK", 
                             "CHAIRS_AND_TABLES", "MAIN_STAGE", "OPERATION_HQ"})
    private LocationTag locationTag;

    @Schema(description = "푸드트럭 메뉴 리스트 (FOOD_TRUCK 타입일 때만 사용)")
    @Valid
    private List<FoodTruckMenuDto> foodTruckMenus = new ArrayList<>();

    @Schema(description = "푸드트럭 메뉴 정보")
    @Getter
    @Setter
    public static class FoodTruckMenuDto {
        
        @Schema(description = "메뉴명", 
                example = "양념치킨")
        @NotBlank(message = "메뉴명은 필수입니다.")
        private String menuName;

        @Schema(description = "메뉴 가격 (원)", 
                example = "15000",
                minimum = "0")
        @NotNull(message = "가격은 필수입니다.")
        private Integer price;

        // 기본 생성자
        public FoodTruckMenuDto() {}

        // 매개변수 생성자
        public FoodTruckMenuDto(String menuName, Integer price) {
            this.menuName = menuName;
            this.price = price;
        }
    }
}
