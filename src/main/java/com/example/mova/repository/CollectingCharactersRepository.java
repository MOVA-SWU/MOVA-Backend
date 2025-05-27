package com.example.mova.repository;

import com.example.mova.domain.CollectingCharacters;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectingCharactersRepository extends JpaRepository<CollectingCharacters, Long> {
    List<CollectingCharacters> findByUserId(Long userId);
}
