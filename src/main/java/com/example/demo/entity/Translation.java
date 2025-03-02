package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String translationKey;

    private String locale;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime lastUpdated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "translation_tag",
            joinColumns = @JoinColumn(name = "translation_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @PreUpdate
    @PrePersist
    public void preUpdatePersist() {
        lastUpdated = LocalDateTime.now();
    }
};