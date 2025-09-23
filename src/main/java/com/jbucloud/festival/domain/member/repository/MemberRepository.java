package com.jbucloud.festival.domain.member.repository;

import com.jbucloud.festival.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndProviderId(String provider, String providerId);

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
