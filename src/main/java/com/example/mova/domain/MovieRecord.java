package com.example.mova.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MovieRecord extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieRecord_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    private String imageUrl;

    @Column(nullable = false)
    private Integer rating;

}
