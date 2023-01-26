-- CREATE

-- :name create-skill! :! :n
-- :doc creates a new skill record
INSERT INTO skills
(skill_name, skill_level, total_xp, timestamp)
VALUES (:skill-name, :skill-level, :total-xp, :timestamp)

-- READ

-- :name get-skills :? :*
-- :doc retrieves a skill record given the id
SELECT * FROM skills

-- :name get-skill :? :1
-- :doc retrieves a skill record given the id
SELECT * FROM skills
WHERE id = :id

-- UPDATE

-- :name update-skill! :! :n
-- :doc updates a given skill
UPDATE skills
SET skill_name = :skill-name,
    skill_level = :skill-level,
    total_xp = :total-xp
WHERE id = :id

-- DELETE

-- :name delete-skill! :! :n
-- :doc deletes a skill record given the id
DELETE FROM skills
WHERE id = :id

-- :name show-schemas
-- :doc shows the schema for all tables
SHOW COLUMNS FROM skills