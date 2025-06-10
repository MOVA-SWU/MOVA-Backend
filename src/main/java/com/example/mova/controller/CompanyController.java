package com.example.mova.controller;

import com.example.mova.dto.SupportDto;
import com.example.mova.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<SupportDto.CompanyListDto>> getCompanyList(){
        List<SupportDto.CompanyListDto> companyList = companyService.findList();
        return ResponseEntity.ok(companyList);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<SupportDto.CompanySupportDto> getCompanyDetail(@Valid @PathVariable Long companyId){
        SupportDto.CompanySupportDto supportCompany = companyService.findCompany(companyId);
        return ResponseEntity.ok(supportCompany);
    }
}
