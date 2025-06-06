package com.example.mova.service;

import com.example.mova.repository.CompanyRepository;
import com.example.mova.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;
    private final CompanyRepository companyRepository;


}
