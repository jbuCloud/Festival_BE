package com.jbucloud.festival.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class MajorValidator implements ConstraintValidator<ValidMajor, String> {

    private static final List<String> ALLOWED_MAJORS = Arrays.asList(
            "경영학전공",
            "국제통상학전공",
            "미디어커뮤니케이션학전공",
            "문헌정보학전공",
            "공공안전학전공",
            "항공서비스학전공",
            "유아교육과",
            "유아특수교육과",
            "초등특수교육과",
            "중등특수교육과",
            "특수체육교육과",
            "반려동물보건학과",
            "건축학전공",
            "토목공학전공",
            "게임소프트웨어전공",
            "정보보호학전공",
            "인공지능전공",
            "전기전자공학전공",
            "스마트모빌리티공학전공",
            "산업디자인학전공",
            "만화애니메이션학전공",
            "사진영상학전공",
            "연극영화학전공",
            "실용음악학전공",
            "엔터테인먼트학전공",
            "뷰티·패션비즈니스학전공",
            "자율설계전공학부"
    );

    @Override
    public boolean isValid(String major, ConstraintValidatorContext context) {
        if (major == null || major.trim().isEmpty()) {
            return false; // 또는 true로 설정하여 @NotNull 또는 @NotEmpty로 처리하도록 할 수 있습니다.
        }
        return ALLOWED_MAJORS.contains(major);
    }
}
