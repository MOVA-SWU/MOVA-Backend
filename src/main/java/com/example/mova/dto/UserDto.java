package com.example.mova.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class UserDto {
    @Getter
    public static class UserSignupRequestDto{
        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "유효한 이메일 형식이어야 합니다")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요")
        private String nickname;
    }

    @Getter
    public static class EmailCheckRequestDto{
        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        private String email;
    }

    @Getter
    @AllArgsConstructor
    public static class EmailCheckResponseDto{
        private boolean duplicated;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignupResponseDto{
        private String email;
        private String nickname;
    }

    @Getter
    @Setter
    public static class UserLoginRequestDto{
        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "유효한 이메일 형식이어야 합니다")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLoginResponse{
        private String email;
        private JwtToken token;
    }

    @Getter
    public static class NickNameUpdateDto{
        @NotBlank(message = "닉네임은 비워둘 수 없습니다.")
        private String nickname;
    }

    //마이페이지 조회
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageResponseDto{
        private String profileImage;
        private String nickname;
    }


}
