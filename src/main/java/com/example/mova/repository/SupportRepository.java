package com.example.mova.repository;

import com.example.mova.domain.Support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface SupportRepository extends JpaRepository<Support, Long> {

    @Query("""
      select s
        from Support s
        join fetch s.company c
        left join fetch c.productionImages
       where s.supportId = :id
      """)
    Optional<Support> findByIdWithCompany(@Param("id") Long id);
}