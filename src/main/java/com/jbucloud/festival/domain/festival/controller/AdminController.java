package com.jbucloud.festival.domain.festival.controller;

import com.jbucloud.festival.domain.festival.dto.FestivalRequestDto;
import com.jbucloud.festival.domain.festival.dto.FestivalResponseDto;
import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import com.jbucloud.festival.domain.festival.service.FestivalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Tag(name = "관리자 페이지", description = "관리자 페이지")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final FestivalService festivalService;

    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> test()  {
        return ResponseEntity.ok("hello world");
    }

    // 축제 정보 생성
    @PostMapping
    @ResponseBody
    public ResponseEntity<FestivalResponseDto> createFestival(@Valid @RequestBody FestivalRequestDto requestDto) {
        try {
            FestivalResponseDto responseDto = festivalService.createFestival(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 축제 정보 수정
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<FestivalResponseDto> updateFestival(@PathVariable Long id,
                                                              @Valid @RequestBody FestivalRequestDto requestDto) {
        try {
            FestivalResponseDto responseDto = festivalService.updateFestival(id, requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 축제 정보 삭제
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFestival(@PathVariable Long id) {
        try {
            festivalService.deleteFestival(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
