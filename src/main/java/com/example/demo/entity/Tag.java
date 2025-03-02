package com.example.demo.entity;
import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}