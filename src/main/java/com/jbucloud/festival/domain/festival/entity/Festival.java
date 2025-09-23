package com.jbucloud.festival.domain.festival.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "festivals")
@Getter @Setter
public class Festival {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "info_type", nullable = false)
    private InfoType infoType;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "preview", columnDefinition = "TEXT")
    private String preview;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_always_open")
    private Boolean isAlwaysOpen = false;

    @Column(name = "detailed_description", columnDefinition = "TEXT")
    private String detailedDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_tag")
    private LocationTag locationTag;

    // 푸드트럭 메뉴 리스트 (푸드트럭 타입일 때만 사용)
    @ElementCollection
    @CollectionTable(
            name = "festival_food_truck_menus",
            joinColumns = @JoinColumn(name = "festival_id")
    )

    private List<FoodTruckMenu> foodTruckMenus = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 기본 생성자
    public Festival() {}

    // 생성자
    public Festival(InfoType infoType, String title, String preview,
                    LocalDateTime startTime, LocalDateTime endTime,
                    String detailedDescription, LocationTag locationTag) {
        this.infoType = infoType;
        this.title = title;
        this.preview = preview;
        this.startTime = startTime;
        this.endTime = endTime;
        this.detailedDescription = detailedDescription;
        this.locationTag = locationTag;
    }

    // 상시 운영 설정 메서드
    public void setAlwaysOpen(boolean isAlwaysOpen) {
        this.isAlwaysOpen = isAlwaysOpen;
        if (isAlwaysOpen) {
            this.startTime = null;
            this.endTime = null;
        }
    }

    // 푸드트럭 메뉴 추가
    public void addFoodTruckMenu(String menuName, Integer price) {
        if (this.infoType == InfoType.FOOD_TRUCK) {
            this.foodTruckMenus.add(new FoodTruckMenu(menuName, price));
        }
    }

    // 푸드트럭 메뉴 제거
    public void removeFoodTruckMenu(String menuName) {
        this.foodTruckMenus.removeIf(menu -> menu.getMenuName().equals(menuName));
    }



    @Embeddable
    @Getter
    @Setter
    public class FoodTruckMenu {
        @Column(name = "menu_name")
        private String menuName;

        @Column(name = "price")
        private Integer price;

        // 기본 생성자
        public FoodTruckMenu() {}

        public FoodTruckMenu(String menuName, Integer price) {
            this.menuName = menuName;
            this.price = price;
        }
    }
}
