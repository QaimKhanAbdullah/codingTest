package com.example.demo.populator;

/*public class DatabasePopulator {
}*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.TranslationRepository;
import com.example.demo.dao.TagRepository;
import com.example.demo.entity.Translation;
import com.example.demo.entity.Tag;
import com.example.demo.service.TranslationService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DatabasePopulator implements CommandLineRunner {

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TranslationService translationService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        populateDatabase(100000); // Populate with 100,000 records
    }

    private void populateDatabase(int numberOfRecords) {
        Random random = new Random();
        List<Translation> translations = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        // Create a few tags
        String[] tagNames = {"mobile", "desktop", "web", "common", "api", "ui", "backend", "frontend"};
        for (String tagName : tagNames) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tags.add(tag);
            tagRepository.save(tag);
        }

        for (int i = 0; i < numberOfRecords; i++) {
            Translation translation = new Translation();
            translation.setTranslationKey("key_" + UUID.randomUUID().toString());
            translation.setLocale(random.nextBoolean() ? "en" : (random.nextBoolean() ? "fr" : "es"));
            translation.setContent("Content for " + translation.getTranslationKey());
            translation.setLastUpdated(LocalDateTime.now());

            // Assign random tags
            int numberOfTags = random.nextInt(3) + 1; // 1 to 3 tags
            for (int j = 0; j < numberOfTags; j++) {
                translation.getTags().add(tags.get(random.nextInt(tags.size())));
            }

            translations.add(translation);
        }

        translationRepository.saveAll(translations);
    }
}



