package com.example.mova.domain;

import com.example.mova.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Character extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String imageUrl;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private List<Mission> missionList = new ArrayList<>();
}
