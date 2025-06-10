package com.example.mova.service;

import com.example.mova.domain.Company;
import com.example.mova.domain.Support;
import com.example.mova.dto.SupportDto;
import com.example.mova.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public List<SupportDto.CompanyListDto> findList(){

        return companyRepository.findAll().stream()
                .map(entity -> new SupportDto.CompanyListDto(
                        entity.getCompanyId(),
                        entity.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public SupportDto.CompanySupportDto findCompany(Long companyId){
        Company company = companyRepository.findByIdWithImages(companyId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 아이디를 찾을 수 없습니다. id=" + companyId));

        return SupportDto.CompanySupportDto.builder()
                .bannerImage(company.getBannerImage())
                .explainText(company.getExplainText())
                .productionImages(company.getProductionImages())
                .supportCost(company.getSupportCost())
                .build();
    }
}
