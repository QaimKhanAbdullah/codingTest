DROP SCHEMA IF EXISTS `demoapp`;

CREATE SCHEMA `demoapp`;
USE `demoapp` ;

CREATE TABLE Tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE Translation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    translationKey VARCHAR(255) UNIQUE,
    locale VARCHAR(255),
    content TEXT,
    lastUpdated TIMESTAMP
);

CREATE TABLE Translation_Tag (
    translation_id BIGINT,
    tag_id BIGINT,
    PRIMARY KEY (translation_id, tag_id),
    FOREIGN KEY (translation_id) REFERENCES Translation(id),
    FOREIGN KEY (tag_id) REFERENCES Tag(id)
);

INSERT INTO Tag (name) VALUES
('mobile'),
('desktop'),
('web'),
('common');

INSERT INTO Translation (translationKey, locale, content, lastUpdated) VALUES
('greeting', 'en', 'Hello', NOW()),
('greeting', 'fr', 'Bonjour', NOW()),
('greeting', 'es', 'Hola', NOW()),
('welcome_message', 'en', 'Welcome to our application', NOW()),
('welcome_message', 'fr', 'Bienvenue dans notre application', NOW()),
('button_label', 'en', 'Submit', NOW()),
('button_label', 'es', 'Enviar', NOW());

INSERT INTO Translation_Tag (translation_id, tag_id) VALUES
(1, 4),
(2, 4),
(3, 4),
(4, 1),
(4, 3),
(5, 1),
(5, 3),
(6, 3),
(7, 3);

