package com.example.demo.service;
import com.example.demo.dao.TranslationRepository;
import com.example.demo.dao.TagRepository;
import com.example.demo.entity.Translation;
import com.example.demo.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private TagRepository tagRepository;

    public Translation createTranslation(Translation translation, Set<String> tagNames) {
        setTags(translation,tagNames);
        return translationRepository.save(translation);
    }

    public Translation updateTranslation(Long id, Translation updatedTranslation, Set<String> tagNames) {
        Translation existingTranslation = translationRepository.findById(id).orElse(null);
        if (existingTranslation != null) {
            existingTranslation.setTranslationKey(updatedTranslation.getTranslationKey());
            existingTranslation.setLocale(updatedTranslation.getLocale());
            existingTranslation.setContent(updatedTranslation.getContent());
            setTags(existingTranslation,tagNames);
            return translationRepository.save(existingTranslation);
        }
        return null;
    }

    public Translation getTranslationById(Long id) {
        return translationRepository.findById(id).orElse(null);
    }

    public List<Translation> searchTranslations(String key, String locale, String content, String tag) {
        if (key != null) {
            if(locale != null){
                return translationRepository.findByTranslationKeyAndLocale(key, locale);
            }
            return translationRepository.findByTranslationKey(key);
        } else if (locale != null) {
            return translationRepository.findByLocale(locale);
        } else if (content != null) {
            return translationRepository.findByContentContaining(content);
        } else if (tag != null) {
            return translationRepository.findByTags_Name(tag);
        }
        return translationRepository.findAll();
    }

    public Map<String, Map<String, String>> exportTranslations(String locale) {
        List<Translation> translations = translationRepository.findByLocale(locale);
        return translations.stream().collect(Collectors.groupingBy(Translation::getTranslationKey,
                Collectors.toMap(Translation::getLocale, Translation::getContent)));
    }

    private void setTags(Translation translation, Set<String> tagNames){
        if(tagNames != null){
            translation.getTags().clear();
            for(String tagName: tagNames){
                Tag tag = tagRepository.findByName(tagName);
                if(tag == null){
                    tag = new Tag();
                    tag.setName(tagName);
                    tagRepository.save(tag);
                }
                translation.getTags().add(tag);
            }
        }
    }
}