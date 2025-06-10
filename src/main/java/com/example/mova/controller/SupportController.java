package com.example.mova.controller;

import com.example.mova.dto.SupportDto;
import com.example.mova.service.SupportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/companies/{companyId}/sponsor")
    public ResponseEntity<String > getStatusChange(@PathVariable Long companyId){
        String message =supportService.changeStatus(companyId);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

}
