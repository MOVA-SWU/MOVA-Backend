package com.example.mova.service;

import com.example.mova.domain.Support;
import com.example.mova.dto.SupportDto;
import com.example.mova.repository.CompanyRepository;
import com.example.mova.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;
    private final CompanyRepository companyRepository;

    public List<SupportDto.CompanyListDto> findList(){

        return supportRepository.findAll().stream()
                .map(entity -> new SupportDto.CompanyListDto(
                        entity.getSupportId(),
                        entity.getCompanyName()))
                .collect(Collectors.toList());
    }

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
}
