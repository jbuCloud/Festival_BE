package com.jbucloud.festival.domain.festival.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 페이지", description = "관리자 페이지")
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {


    @GetMapping
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> test()  {
        return ResponseEntity.ok("hello world");
    }

}
