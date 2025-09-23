package com.jbucloud.festival.domain.festival.controller;

import com.jbucloud.festival.domain.festival.dto.FestivalResponseDto;
import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import com.jbucloud.festival.domain.festival.service.FestivalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Festival Controller (조회)
@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    // 특정 축제 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<FestivalResponseDto> getFestival(@PathVariable Long id) {
        try {
            FestivalResponseDto responseDto = festivalService.getFestival(id);
            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 모든 축제 정보 조회
    @GetMapping
    public ResponseEntity<List<FestivalResponseDto>> getAllFestivals() {
        List<FestivalResponseDto> festivals = festivalService.getAllFestivals();
        return ResponseEntity.ok(festivals);
    }

    // 타입별 축제 정보 조회
    @GetMapping("/type/{infoType}")
    public ResponseEntity<List<FestivalResponseDto>> getFestivalsByType(@PathVariable InfoType infoType) {
        List<FestivalResponseDto> festivals = festivalService.getFestivalsByType(infoType);
        return ResponseEntity.ok(festivals);
    }

    // 위치별 축제 정보 조회
    @GetMapping("/location/{locationTag}")
    public ResponseEntity<List<FestivalResponseDto>> getFestivalsByLocation(@PathVariable LocationTag locationTag) {
        List<FestivalResponseDto> festivals = festivalService.getFestivalsByLocation(locationTag);
        return ResponseEntity.ok(festivals);
    }

    // 제목으로 검색
    @GetMapping("/search")
    public ResponseEntity<List<FestivalResponseDto>> searchFestivals(@RequestParam String keyword) {
        List<FestivalResponseDto> festivals = festivalService.searchFestivalsByTitle(keyword);
        return ResponseEntity.ok(festivals);
    }

    // 타입과 위치 조합으로 조회
    @GetMapping("/filter")
    public ResponseEntity<List<FestivalResponseDto>> getFestivalsByTypeAndLocation(
            @RequestParam(required = false) InfoType infoType,
            @RequestParam(required = false) LocationTag locationTag) {

        if (infoType != null && locationTag != null) {
            // 둘 다 있을 때는 Repository에 메서드 추가 필요
            List<FestivalResponseDto> festivals = festivalService.getAllFestivals().stream()
                    .filter(f -> f.getInfoType() == infoType && f.getLocationTag() == locationTag)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(festivals);
        } else if (infoType != null) {
            return getFestivalsByType(infoType);
        } else if (locationTag != null) {
            return getFestivalsByLocation(locationTag);
        } else {
            return getAllFestivals();
        }
    }

}