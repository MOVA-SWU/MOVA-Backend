package com.example.mova.service;

import com.example.mova.config.TokenProvider;
import com.example.mova.domain.User;
import com.example.mova.dto.JwtToken;
import com.example.mova.dto.UserDto;
import com.example.mova.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public UserDto.UserSignupResponseDto signup(UserDto.UserSignupRequestDto request){

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User newUser = User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();
        userRepository.save(newUser);

        return UserDto.UserSignupResponseDto.builder()
                .email(newUser.getEmail())
                .nickname(newUser.getNickname())
                .build();
    }

    public boolean checkEmailDuplicated(String email) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        return true;
    }

    @Transactional
    public UserDto.UserLoginResponse signIn(UserDto.UserLoginRequestDto request){
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 UserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = tokenProvider.generateToken(authentication);

        // 리프레시 토큰 DB 저장
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        user.updateRefreshToken(jwtToken.getRefreshToken());

        return UserDto.UserLoginResponse.builder()
                .email(request.getEmail())
                .token(jwtToken)
                .build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 이메일입니다."));
    }

    @Transactional
    public void updateNickname(Long userId, String newNickname){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("유저가 존재하지 않습니다."));
        user.update(newNickname);
    }

}
