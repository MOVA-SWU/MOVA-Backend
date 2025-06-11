package com.example.mova.domain;

import com.example.mova.enums.MissionStatus;
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
    private String movie;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mission;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String effect;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<MyMission> myMissionList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "point_id")
    private Point point;

    @OneToOne
    @JoinColumn(name = "movie_record_id")
    private MovieRecord movieRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private StoryCharacter storyCharacter;

    public Integer getCost() {
        return this.point.getCost();
    }

    public String getCharacter(){
        return this.storyCharacter.getImageUrl();
    }


}
