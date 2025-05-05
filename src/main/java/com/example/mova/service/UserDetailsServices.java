package com.example.mova.service;


import com.example.mova.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServices implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email)); // IllegalArgumentException → UsernameNotFoundException
    }

    //    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
//    private UserDetails createUserDetails(User user) {
//        return User.builder()
//                .email(user.getEmail())
//                .password(bCryptPasswordEncoder.encode(user.getPassword()))
//                .build();
//    }
}

