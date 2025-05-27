package com.example.mova.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Point extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @Column(name = "point", nullable = false)
    private Integer cost;

    @Column(name = "point_message", nullable = false)
    private String message;

    @Column(name = "user_id", nullable = false)
    private Long userId;   // ← 단순 외래키 필드

}
