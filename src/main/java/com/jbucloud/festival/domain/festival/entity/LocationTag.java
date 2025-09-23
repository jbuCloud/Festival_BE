package com.jbucloud.festival.domain.festival.entity;

import lombok.Getter;

@Getter
public enum LocationTag {
    BOOTH_ZONE("운영본부(QR)"),
    STAGE_PHOTO_BOOTH("포토부스"),
    DIY_DATE_BOOTH("DIY 소개팅"),
    COMPANY_BOOTH("업체부스"),
    PARTNER_BOOTH("제휴부스"),
    VIKING_BOOTH("바이킹"),
    FOREIGN_STUDENT_BOOTH("재학생 외국인 부스"),
    ALCOHOL_BOOTH("주류"),
    FOOD_TRUCK("푸드트럭"),
    CHAIRS_AND_TABLES("의자 및 테이블"),
    MAIN_STAGE("무대"),
    OPERATION_HQ("운영본부");

    private final String displayName;

    LocationTag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
