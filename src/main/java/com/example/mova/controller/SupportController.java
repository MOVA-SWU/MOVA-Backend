package com.example.mova.controller;

import com.example.mova.dto.SupportDto;
import com.example.mova.service.SupportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/supports")
public class SupportController {

    private final SupportService supportService;

    @GetMapping
    public ResponseEntity<List<SupportDto.CompanyListDto>> getCompanyList(){
        List<SupportDto.CompanyListDto> companyList = supportService.findList();
        return ResponseEntity.ok(companyList);
    }

    @GetMapping("/{supportId}")
    public ResponseEntity<SupportDto.CompanySupportDto> getCompanyDetail(@Valid @PathVariable Long supportId){
        SupportDto.CompanySupportDto supportCompany = supportService.findCompany(supportId);
        return ResponseEntity.ok(supportCompany);
    }


}
