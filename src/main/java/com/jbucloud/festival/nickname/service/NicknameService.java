package com.jbucloud.festival.nickname.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class NicknameService {

    private static final List<String> ADJECTIVES = Arrays.asList(
            "행복한", "즐거운", "빛나는", "음흉한", "주량이 약한", "바보",
            "낮잠자는", "개강한", "슬픈", "그냥", "생기잃은", "주량이 센",
            "변태", "유치한", "바쁜", "졸린", "배부른", "귀여운", "깜찍한"
    );

    private static final List<String> NOUNS = Arrays.asList(
            "공돌이", "미대생", "대학생", "대동제", "교수님", "대학원생",
            "술찌", "돼지", "멍뭉이", "경재", "세종이", "인농이", "문돌이"
    );

    private final Random random = new Random();

    public String generateNickname() {
        String adjective = ADJECTIVES.get(random.nextInt(ADJECTIVES.size()));
        String noun = NOUNS.get(random.nextInt(NOUNS.size()));
        return adjective + " " + noun;
    }
}