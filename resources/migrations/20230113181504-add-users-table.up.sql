CREATE TABLE skills(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    skill_name VARCHAR(25) NOT NULL UNIQUE,
    total_xp INTEGER,
    skill_level INTEGER,
    timestamp TIMESTAMP(7)
);
