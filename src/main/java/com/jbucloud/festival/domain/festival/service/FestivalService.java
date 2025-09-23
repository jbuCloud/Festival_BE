package com.jbucloud.festival.domain.festival.service;

import com.jbucloud.festival.domain.festival.dto.FestivalRequestDto;
import com.jbucloud.festival.domain.festival.dto.FestivalResponseDto;
import com.jbucloud.festival.domain.festival.entity.Festival;
import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import com.jbucloud.festival.domain.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    // 축제 정보 생성
    public FestivalResponseDto createFestival(FestivalRequestDto requestDto) {
        Festival festival = new Festival(
                requestDto.getInfoType(),
                requestDto.getTitle(),
                requestDto.getPreview(),
                requestDto.getStartTime(),
                requestDto.getEndTime(),
                requestDto.getDetailedDescription(),
                requestDto.getLocationTag()
        );

        // 상시 운영 설정
        if (requestDto.getIsAlwaysOpen() != null && requestDto.getIsAlwaysOpen()) {
            festival.setAlwaysOpen(true);
        }

        // 푸드트럭 메뉴 추가
        if (requestDto.getInfoType() == InfoType.FOOD_TRUCK &&
                requestDto.getFoodTruckMenus() != null) {
            for (FestivalRequestDto.FoodTruckMenuDto menuDto : requestDto.getFoodTruckMenus()) {
                festival.addFoodTruckMenu(menuDto.getMenuName(), menuDto.getPrice());
            }
        }

        Festival savedFestival = festivalRepository.save(festival);
        return new FestivalResponseDto(savedFestival);
    }

    // 축제 정보 수정
    public FestivalResponseDto updateFestival(Long id, FestivalRequestDto requestDto) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Festival not found with id: " + id));

        // 필드 업데이트
        festival.setInfoType(requestDto.getInfoType());
        festival.setTitle(requestDto.getTitle());
        festival.setPreview(requestDto.getPreview());
        festival.setDetailedDescription(requestDto.getDetailedDescription());
        festival.setLocationTag(requestDto.getLocationTag());

        // 시간 설정
        if (requestDto.getIsAlwaysOpen() != null && requestDto.getIsAlwaysOpen()) {
            festival.setAlwaysOpen(true);
        } else {
            festival.setIsAlwaysOpen(false);
            festival.setStartTime(requestDto.getStartTime());
            festival.setEndTime(requestDto.getEndTime());
        }

        // 푸드트럭 메뉴 업데이트
        if (requestDto.getInfoType() == InfoType.FOOD_TRUCK) {
            festival.getFoodTruckMenus().clear();
            if (requestDto.getFoodTruckMenus() != null) {
                for (FestivalRequestDto.FoodTruckMenuDto menuDto : requestDto.getFoodTruckMenus()) {
                    festival.addFoodTruckMenu(menuDto.getMenuName(), menuDto.getPrice());
                }
            }
        }

        Festival updatedFestival = festivalRepository.save(festival);
        return new FestivalResponseDto(updatedFestival);
    }

    // 축제 정보 삭제
    public void deleteFestival(Long id) {
        if (!festivalRepository.existsById(id)) {
            throw new RuntimeException("Festival not found with id: " + id);
        }
        festivalRepository.deleteById(id);
    }

    // 축제 정보 조회 (ID로)
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public FestivalResponseDto getFestival(Long id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Festival not found with id: " + id));
        return new FestivalResponseDto(festival);
    }

    // 모든 축제 정보 조회
    @Transactional(readOnly = true)
    public List<FestivalResponseDto> getAllFestivals() {
        return festivalRepository.findAll().stream()
                .map(FestivalResponseDto::new)
                .collect(Collectors.toList());
    }

    // 타입별 축제 정보 조회
    @Transactional(readOnly = true)
    public List<FestivalResponseDto> getFestivalsByType(InfoType infoType) {
        return festivalRepository.findByInfoType(infoType).stream()
                .map(FestivalResponseDto::new)
                .collect(Collectors.toList());
    }

    // 위치별 축제 정보 조회
    @Transactional(readOnly = true)
    public List<FestivalResponseDto> getFestivalsByLocation(LocationTag locationTag) {
        return festivalRepository.findByLocationTag(locationTag).stream()
                .map(FestivalResponseDto::new)
                .collect(Collectors.toList());
    }

    // 제목 검색
    @Transactional(readOnly = true)
    public List<FestivalResponseDto> searchFestivalsByTitle(String keyword) {
        return festivalRepository.findByTitleContaining(keyword).stream()
                .map(FestivalResponseDto::new)
                .collect(Collectors.toList());
    }
}
