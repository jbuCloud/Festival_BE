package com.jbucloud.festival.domain.festival.repository;

import com.jbucloud.festival.domain.festival.entity.Festival;
import com.jbucloud.festival.domain.festival.entity.InfoType;
import com.jbucloud.festival.domain.festival.entity.LocationTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {
    List<Festival> findByInfoType(InfoType infoType);
    List<Festival> findByLocationTag(LocationTag locationTag);
    List<Festival> findByInfoTypeAndLocationTag(InfoType infoType, LocationTag locationTag);
    List<Festival> findByTitleContaining(String keyword);
}
