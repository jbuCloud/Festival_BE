package com.jbucloud.festival.domain.member.service;

import com.jbucloud.festival.domain.auth.dto.TokenDto;
import com.jbucloud.festival.domain.member.dto.LoginRequestDto;
import com.jbucloud.festival.domain.member.dto.SignUpRequestDto;
import com.jbucloud.festival.domain.member.entity.Member;
import com.jbucloud.festival.domain.member.repository.MemberRepository;
import com.jbucloud.festival.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignUpRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Member newMember = Member.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .major(requestDto.getMajor())
                .provider("local") // 로컬 가입자
                .build();

        memberRepository.save(newMember);
    }

    @Transactional(readOnly = true)
    public TokenDto login(LoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (member.getPassword() == null) {
            throw new IllegalArgumentException("소셜 로그인으로 가입된 회원입니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateTokens(member.getId());
    }

    public void updateMajor(Long memberId, String major) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        member.setMajor(major);
        memberRepository.save(member);
    }
}
