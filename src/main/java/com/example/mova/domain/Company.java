package com.example.mova.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Company extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long companyId;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String explainText;

    private String bannerImage;

    @Column(nullable = false)
    @Min(5000)
    @Max(1000000)
    private Integer supportCost;

    @ElementCollection
    @CollectionTable(name = "company_production_images",
            joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "image_url")
    private List<String> productionImages = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Support> supportList = new ArrayList<>();

}
