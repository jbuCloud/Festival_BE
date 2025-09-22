package com.jbucloud.festival.global.firebase.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.jbucloud.festival.global.firebase.FirebaseService;
import com.jbucloud.festival.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Firebase", description = "Firebase 관련 API")
@RestController
@RequestMapping("/api/firebase")
@RequiredArgsConstructor
public class FirebaseController {

    private final FirebaseService firebaseService;

    @Operation(summary = "Firebase Custom Token 생성", description = "인증된 사용자를 위한 Firebase Custom Token을 생성합니다.")
    @PostMapping("/custom-token")
    public ResponseEntity<String> createCustomToken(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            String uid = userDetails.getMember().getId().toString();
            String customToken = firebaseService.createCustomToken(uid);
            return ResponseEntity.ok(customToken);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(500).body("Failed to create Firebase custom token: " + e.getMessage());
        }
    }
}
