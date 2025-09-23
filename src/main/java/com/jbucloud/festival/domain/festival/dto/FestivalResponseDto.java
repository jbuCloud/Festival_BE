package com.jbucloud.festival.domain.festival.dto;

import com.jbucloud.festival.domain.festival.entity.Festival;
import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FestivalResponseDto {
    private Long id;
    private InfoType infoType;
    private String title;
    private String preview;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAlwaysOpen;
    private String detailedDescription;
    private LocationTag locationTag;
    private List<Festival.FoodTruckMenu> foodTruckMenus;
    private LocalDateTime createdAt;

    // Entity to DTO 변환 생성자
    public FestivalResponseDto(Festival festival) {
        this.id = festival.getId();
        this.infoType = festival.getInfoType();
        this.title = festival.getTitle();
        this.preview = festival.getPreview();
        this.startTime = festival.getStartTime();
        this.endTime = festival.getEndTime();
        this.isAlwaysOpen = festival.getIsAlwaysOpen();
        this.detailedDescription = festival.getDetailedDescription();
        this.locationTag = festival.getLocationTag();
        this.foodTruckMenus = festival.getFoodTruckMenus();
        this.createdAt = festival.getCreatedAt();
    }
}
