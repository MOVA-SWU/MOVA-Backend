package com.example.mova.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "이메일은 필수 입력사항입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Column(length = 40, nullable = false, unique = true)
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    @Column(length = 64, nullable = false)
    private String password;

    private String nickname;


    private String refreshToken;

    @Column(nullable = false)
    private Integer totalPoints = 0;

    public void addPoints(int cost){
        this.totalPoints += cost;
    }

    public void subtract(int sponsorCost){
        this.totalPoints -= sponsorCost;
    }

    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken = updateRefreshToken;
    }
    //사용자 이름 변경
    public User update(String nickname){
        this.nickname = nickname;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // 계정 만료 여부

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // 계정 잠김 여부

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // 비밀번호 만료 여부

    @Override
    public boolean isEnabled() {
        return true;
    } // 계정 활성화 여부

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MovieRecord> movieRecordList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyMission> myMissionList = new ArrayList<>();

}
