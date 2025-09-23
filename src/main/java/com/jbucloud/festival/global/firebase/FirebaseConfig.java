package com.jbucloud.festival.global.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${firebase.service-account-file}")
    private String serviceAccountFile;

    private final ResourceLoader resourceLoader;

    public FirebaseConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() {
        try {
            if (FirebaseApp.getApps().isEmpty()) { // FirebaseApp이 이미 초기화되었는지 확인
                Resource resource = resourceLoader.getResource("classpath:" + serviceAccountFile);
                String jsonContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

                InputStream serviceAccount = new ByteArrayInputStream(jsonContent.getBytes(StandardCharsets.UTF_8));

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                FirebaseApp.initializeApp(options);
                log.info("Firebase Admin SDK initialized successfully.");
            } else {
                log.info("Firebase Admin SDK already initialized.");
            }
        } catch (IOException e) {
            log.error("Error initializing Firebase Admin SDK: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize Firebase Admin SDK", e);
        }
    }
}
