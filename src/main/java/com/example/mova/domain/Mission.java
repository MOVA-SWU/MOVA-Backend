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
public class Mission extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long missionId;

    @Column(nullable = false)
    private String mission;

    @Column(nullable = false)
    private String effect;

    @Column(nullable = false)
    private String image_url;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<MyMisssion> myMisssionList = new ArrayList<>();




}
