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
public class StoryCharacter extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme", nullable = false)
    private Category category;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "storyCharacter", cascade = CascadeType.ALL)
    private List<Mission> missionList = new ArrayList<>();
}
