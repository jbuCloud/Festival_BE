package com.jbucloud.festival.domain.festival.dto;


import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
public class FestivalRequestDto {
    @NotNull(message = "정보 타입은 필수입니다.")
    private InfoType infoType;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String preview;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAlwaysOpen = false;

    private String detailedDescription;

    private LocationTag locationTag;

    // 푸드트럭 메뉴 리스트
    private List<FoodTruckMenuDto> foodTruckMenus = new ArrayList<>();

    // 메뉴 Dto
    @Getter
    @Setter
    public static class FoodTruckMenuDto {
        @NotBlank(message = "메뉴명은 필수입니다.")
        private String menuName;

        @NotNull(message = "가격은 필수입니다.")
        private Integer price;

        // 생성자, getters, setters
        public FoodTruckMenuDto() {}

        public FoodTruckMenuDto(String menuName, Integer price) {
            this.menuName = menuName;
            this.price = price;
        }

    }
}
