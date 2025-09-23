package com.jbucloud.festival.domain.festival.entity;

import lombok.Getter;

@Getter
public enum InfoType {
    NOTICE("공지사항"),
    BOOTH("부스"),
    EVENT("이벤트"),
    FOOD_TRUCK("푸드트럭"),
    TIMETABLE("타임테이블");

    private final String displayName;

    InfoType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
