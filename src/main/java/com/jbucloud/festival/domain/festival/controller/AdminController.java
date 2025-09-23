package com.jbucloud.festival.domain.festival.controller;

import com.jbucloud.festival.domain.festival.dto.FestivalRequestDto;
import com.jbucloud.festival.domain.festival.dto.FestivalResponseDto;
import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import com.jbucloud.festival.domain.festival.service.FestivalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Tag(name = "관리자 페이지", description = "축제 정보 관리를 위한 관리자 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final FestivalService festivalService;

    @Operation(summary = "관리자 페이지", description = "축제 관리 웹 페이지를 반환합니다.")
    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @Operation(summary = "연결 테스트", description = "API 연결 상태를 확인합니다.")
    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> test()  {
        return ResponseEntity.ok("hello world");
    }

    @Operation(summary = "축제 정보 생성", description = "새로운 축제 정보를 등록합니다.")
    @PostMapping
    @ResponseBody
    public ResponseEntity<FestivalResponseDto> createFestival(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "축제 생성 요청 정보", 
                required = true,
                content = @Content(schema = @Schema(implementation = FestivalRequestDto.class))
            )
            @Valid @RequestBody FestivalRequestDto requestDto) {
        try {
            FestivalResponseDto responseDto = festivalService.createFestival(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            log.error("축제 생성 실패", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "축제 정보 수정", description = "기존 축제 정보를 수정합니다.")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<FestivalResponseDto> updateFestival(
            @Parameter(description = "수정할 축제의 ID", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "축제 수정 요청 정보", 
                required = true,
                content = @Content(schema = @Schema(implementation = FestivalRequestDto.class))
            )
            @Valid @RequestBody FestivalRequestDto requestDto) {
        try {
            FestivalResponseDto responseDto = festivalService.updateFestival(id, requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            log.error("축제 수정 실패 - 축제를 찾을 수 없음: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("축제 수정 실패", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "축제 정보 삭제", description = "축제 정보를 삭제합니다.")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteFestival(
            @Parameter(description = "삭제할 축제의 ID", example = "1")
            @PathVariable Long id) {
        try {
            festivalService.deleteFestival(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("축제 삭제 실패 - 축제를 찾을 수 없음: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
