package com.jbucloud.festival.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter // Added @Setter to allow updating fields
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email; // 이메일 (로그인 ID)

    private String nickname; // 닉네임

    private String major; // 전공

    private String password; // 비밀번호 (암호화)

    private String provider; // 소셜 로그인 제공자 (e.g., kakao)

    private String providerId; // 소셜 로그인 제공자 ID

}
