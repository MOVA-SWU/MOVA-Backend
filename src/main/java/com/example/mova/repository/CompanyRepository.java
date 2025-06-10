package com.example.mova.repository;

import com.example.mova.domain.Company;
import com.example.mova.domain.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    // CompanyRepository.java
    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.productionImages WHERE c.companyId = :id")
    Optional<Company> findByIdWithImages(@Param("id") Long id);


    Optional<Company> findByCompanyId(Long companyId);
}
