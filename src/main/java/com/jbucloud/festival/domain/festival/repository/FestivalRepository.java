package com.jbucloud.festival.domain.festival.repository;

import com.jbucloud.festival.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {

}
