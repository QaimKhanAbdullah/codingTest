package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.TranslationService;
import com.example.demo.entity.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation, @RequestParam(required = false) Set<String> tags) {
        return ResponseEntity.ok(translationService.createTranslation(translation,tags));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Translation> updateTranslation(@PathVariable Long id, @RequestBody Translation translation, @RequestParam(required = false) Set<String> tags) {
        Translation updated = translationService.updateTranslation(id, translation, tags);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslationById(@PathVariable Long id) {
        Translation translation = translationService.getTranslationById(id);
        if (translation != null) {
            return ResponseEntity.ok(translation);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Translation>> searchTranslations(
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String tag) {
        return ResponseEntity.ok(translationService.searchTranslations(key, locale, content, tag));
    }

    @GetMapping("/export/{locale}")
    public ResponseEntity<Map<String, Map<String, String>>> exportTranslations(@PathVariable String locale) {
        return ResponseEntity.ok(translationService.exportTranslations(locale));
    }
}
