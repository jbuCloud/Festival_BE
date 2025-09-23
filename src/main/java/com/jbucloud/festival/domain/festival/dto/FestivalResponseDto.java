package com.jbucloud.festival.domain.festival.dto;

import com.jbucloud.festival.domain.festival.entity.Festival;
import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "축제 정보 응답 DTO")
@Getter
public class FestivalResponseDto {
    
    @Schema(description = "축제 정보 고유 ID 상세 조회에 사용",
            example = "1")
    private Long id;
    
    @Schema(description = "축제 정보 타입", 
            example = "FOOD_TRUCK",
            allowableValues = {"NOTICE", "BOOTH", "EVENT", "FOOD_TRUCK", "TIMETABLE"})
    private InfoType infoType;
    
    @Schema(description = "축제 제목", 
            example = "공지사항은 테이블 배부 안내, 부스는 부스제목 등..")
    private String title;
    
    @Schema(description = "정보 미리보기 설명",
            example = "미리보일 정보 (비워도됨)")
    private String preview;
    
    @Schema(description = "축제 시작 시간", 
            example = "2024-05-15T10:00:00")
    private LocalDateTime startTime;
    
    @Schema(description = "축제 종료 시간", 
            example = "2024-05-15T18:00:00")
    private LocalDateTime endTime;
    
    @Schema(description = "상시 운영 여부", 
            example = "false")
    private Boolean alwaysOpen;
    
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
    
    @Schema(description = "푸드트럭 메뉴 리스트")
    private List<FoodTruckMenuResponseDto> foodTruckMenus;
    
    @Schema(description = "축제 생성 시간", 
            example = "2024-05-10T09:30:00")
    private LocalDateTime createdAt;

    // 푸드트럭 메뉴 응답 DTO
    @Schema(description = "푸드트럭 메뉴 정보")
    @Getter
    public static class FoodTruckMenuResponseDto {
        @Schema(description = "메뉴명",
                example = "양념치킨")
        private String menuName;
        
        @Schema(description = "메뉴 가격 (원)", 
                example = "15000")
        private Integer price;

        public FoodTruckMenuResponseDto(Festival.FoodTruckMenu menu) {
            this.menuName = menu.getMenuName();
            this.price = menu.getPrice();
        }
    }

    // Entity to DTO 변환 생성자
    public FestivalResponseDto(Festival festival) {
        this.id = festival.getId();
        this.infoType = festival.getInfoType();
        this.title = festival.getTitle();
        this.preview = festival.getPreview();
        this.startTime = festival.getStartTime();
        this.endTime = festival.getEndTime();
        this.alwaysOpen = festival.getIsAlwaysOpen();
        this.detailedDescription = festival.getDetailedDescription();
        this.locationTag = festival.getLocationTag();
        this.foodTruckMenus = festival.getFoodTruckMenus() != null ? 
            festival.getFoodTruckMenus().stream()
                .map(FoodTruckMenuResponseDto::new)
                .toList() : null;
        this.createdAt = festival.getCreatedAt();
    }
}
