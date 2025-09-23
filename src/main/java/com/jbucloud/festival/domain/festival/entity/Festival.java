package com.jbucloud.festival.domain.festival.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Festival {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
