package com.example.mova.service;

import com.example.mova.config.JWTUtil;
import com.example.mova.domain.Support;
import com.example.mova.domain.User;
import com.example.mova.dto.SupportDto;
import com.example.mova.enums.MissionStatus;
import com.example.mova.errorhandler.ApiExceptions;
import com.example.mova.repository.PointRepository;
import com.example.mova.repository.SupportRepository;
import com.example.mova.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;
    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Transactional
    public List<SupportDto.CompanyListDto> findList(){

        return supportRepository.findAll().stream()
                .map(entity -> new SupportDto.CompanyListDto(
                        entity.getSupportId(),
                        entity.getCompanyName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public SupportDto.CompanySupportDto findCompany(Long supportId){
        Support support = supportRepository.findById(supportId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 아이디를 찾을 수 없습니다. id=" + supportId));

        return SupportDto.CompanySupportDto.builder()
                .bannerImage(support.getBannerImage())
                .explainText(support.getExplain())
                .productionImages(support.getImages())
                .supportCost(support.getSupportCost())
                .build();
    }

    @Transactional
    public String  changeStatus(Long supportId){
        String email = JWTUtil.getCurrentUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다. email = " + email));

        Support support = supportRepository.findById(supportId)
                .orElseThrow(()-> new IllegalArgumentException("해당 후원 정보를 찾을 수 없습니다. id =" + supportId));

        if (!support.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("본인의 후원 내역만 신청할 수 있습니다.");
        }

        int availablePoints = pointRepository.sumCostByUserIdAndMissionStatus(
                user.getId(), MissionStatus.COMPLETED
        );

        int cost = support.getSupportCost();
        if (availablePoints < cost){
            throw new ApiExceptions.InsufficientPointsException(
                    "포인트가 부족합니다. 현재 포인트 = " + availablePoints + "필요한 포인트 =" + cost
            );
        }

        support.setSupportStatus(true);
        support.setRequestDate(LocalDateTime.now());
        supportRepository.save(support);

        return "후원되었습니다.";
    }

}
