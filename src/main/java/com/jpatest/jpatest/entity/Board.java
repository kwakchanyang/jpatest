package com.jpatest.jpatest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="board") // 데이터베이스의 실제 테이블 이름 - 클래스명과 다르게 하려면
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String memberId;
    private String content;
    private String fileName;
    private int hit;
    private LocalDate writeDate;
}

// @Column(unique = true)
