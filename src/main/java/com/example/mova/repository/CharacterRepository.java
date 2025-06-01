package com.example.mova.repository;

import com.example.mova.domain.StoryCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<StoryCharacter, Long> {
}
