package com.example.demo.dao;
import com.example.demo.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

    List<Translation> findByTranslationKey(String translationKey);

    List<Translation> findByLocale(String locale);

    List<Translation> findByContentContaining(String content);

    List<Translation> findByTags_Name(String tagName);

    List<Translation> findByTranslationKeyAndLocale(String translationKey, String locale);
}

