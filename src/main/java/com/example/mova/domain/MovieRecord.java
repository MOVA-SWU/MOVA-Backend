package com.example.mova.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    private Double rating;

    @Column(nullable = false)
    private LocalDate dateTime;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private String checkedUrl; // 검증할 사진 URL 저장

    public void update(Double rating, LocalDate dateTime, String content){
        this.rating = rating;
        this.dateTime = dateTime;
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
