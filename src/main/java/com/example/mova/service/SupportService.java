package com.example.mova.service;

import com.example.mova.config.JWTUtil;
import com.example.mova.domain.Company;
import com.example.mova.domain.Support;
import com.example.mova.domain.User;
import com.example.mova.dto.SupportDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.errorhandler.ApiExceptions;
import com.example.mova.repository.CompanyRepository;
import com.example.mova.repository.PointRepository;
import com.example.mova.repository.SupportRepository;
import com.example.mova.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Transactional
    public String  changeStatus(Long companyId){
        String email = JWTUtil.getCurrentUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다. email = " + email));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(()-> new IllegalArgumentException("해당 후원사를 찾을 수 없습니다. id =" + companyId));

        int availablePoints = pointRepository.sumCostByUserIdAndMissionStatus(
                user.getId(), MissionStatus.COMPLETED
        );

        int cost = company.getSupportCost();
        if (availablePoints < cost){
            throw new ApiExceptions.InsufficientPointsException(
                    "포인트가 부족합니다. 보유 포인트 = " + availablePoints +  "필요한 포인트 =" + cost
            );
        }

        Support support = Support.builder()
                .company(company)
                .user(user)
                .supportStatus(true)
                .requestDate(LocalDateTime.now())
                .build();

        supportRepository.save(support);

        return "후원되었습니다.";
    }

}
