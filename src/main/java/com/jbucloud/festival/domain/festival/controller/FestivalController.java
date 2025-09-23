package com.jbucloud.festival.domain.festival.controller;

import com.jbucloud.festival.domain.festival.dto.FestivalResponseDto;
import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import com.jbucloud.festival.domain.festival.service.FestivalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "축제 조회", description = "축제 정보 조회를 위한 API")
@RestController
@RequestMapping("/api/festivals")
public class FestivalController {

    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @Operation(summary = "모든 축제 정보 조회", description = "등록된 모든 축제 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<FestivalResponseDto>> getAllFestivals() {
        List<FestivalResponseDto> festivals = festivalService.getAllFestivals();
        return ResponseEntity.ok(festivals);
    }

    @Operation(summary = "제목으로 축제 검색", description = "제목에 키워드가 포함된 축제들을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<FestivalResponseDto>> searchFestivals(
            @Parameter(description = "검색할 키워드", example = "치킨")
            @RequestParam String keyword) {
        List<FestivalResponseDto> festivals = festivalService.searchFestivalsByTitle(keyword);
        return ResponseEntity.ok(festivals);
    }

    @Operation(summary = "필터를 통한 축제 조회", 
              description = "타입과 위치 조건을 조합하여 축제를 조회합니다. 조건이 없으면 전체 축제를 반환합니다.")
    @GetMapping("/filter")
    public ResponseEntity<List<FestivalResponseDto>> getFestivalsByTypeAndLocation(
            @Parameter(description = "축제 정보 타입 (선택사항)", 
                      example = "FOOD_TRUCK",
                      schema = @Schema(allowableValues = {"NOTICE", "BOOTH", "EVENT", "FOOD_TRUCK", "TIMETABLE"}))
            @RequestParam(required = false) InfoType infoType,
            @Parameter(description = "축제 위치 (선택사항)", 
                      example = "FOOD_TRUCK",
                      schema = @Schema(allowableValues = {"BOOTH_ZONE", "STAGE_PHOTO_BOOTH", "DIY_DATE_BOOTH", 
                                                        "COMPANY_BOOTH", "PARTNER_BOOTH", "VIKING_BOOTH", 
                                                        "FOREIGN_STUDENT_BOOTH", "ALCOHOL_BOOTH", "FOOD_TRUCK", 
                                                        "CHAIRS_AND_TABLES", "MAIN_STAGE", "OPERATION_HQ"}))
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

    @Operation(summary = "타입별 축제 정보 조회", description = "지정된 정보 타입에 해당하는 축제들을 조회합니다.")
    @GetMapping("/type/{infoType}")
    public ResponseEntity<List<FestivalResponseDto>> getFestivalsByType(
            @Parameter(description = "조회할 축제 정보 타입", 
                      example = "FOOD_TRUCK",
                      schema = @Schema(allowableValues = {"NOTICE", "BOOTH", "EVENT", "FOOD_TRUCK", "TIMETABLE"}))
            @PathVariable InfoType infoType) {
        List<FestivalResponseDto> festivals = festivalService.getFestivalsByType(infoType);
        return ResponseEntity.ok(festivals);
    }

    @Operation(summary = "위치별 축제 정보 조회", description = "지정된 위치에 해당하는 축제들을 조회합니다.")
    @GetMapping("/location/{locationTag}")
    public ResponseEntity<List<FestivalResponseDto>> getFestivalsByLocation(
            @Parameter(description = "조회할 축제 위치", 
                      example = "FOOD_TRUCK",
                      schema = @Schema(allowableValues = {"BOOTH_ZONE", "STAGE_PHOTO_BOOTH", "DIY_DATE_BOOTH", 
                                                        "COMPANY_BOOTH", "PARTNER_BOOTH", "VIKING_BOOTH", 
                                                        "FOREIGN_STUDENT_BOOTH", "ALCOHOL_BOOTH", "FOOD_TRUCK", 
                                                        "CHAIRS_AND_TABLES", "MAIN_STAGE", "OPERATION_HQ"}))
            @PathVariable LocationTag locationTag) {
        List<FestivalResponseDto> festivals = festivalService.getFestivalsByLocation(locationTag);
        return ResponseEntity.ok(festivals);
    }

    @Operation(summary = "특정 축제 정보 조회", description = "ID를 통해 특정 축제의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<FestivalResponseDto> getFestival(
            @Parameter(description = "조회할 축제의 ID", example = "1")
            @PathVariable Long id) {
        try {
            FestivalResponseDto responseDto = festivalService.getFestival(id);
            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
