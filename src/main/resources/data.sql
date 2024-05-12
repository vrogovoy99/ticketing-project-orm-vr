INSERT INTO roles(description) VALUES ('Admin');
INSERT INTO roles(description) VALUES ('Manager');
INSERT INTO roles(description) VALUES ('Employee');

INSERT INTO users(first_name, last_name, user_name, pass_word, enabled, phone, role_id, gender)
VALUES ('John', 'Smith', 'jsmith', 'arr', true, '212-593-0233', 1, 'MALE'),
       ('Suzan', 'Smith', 'ssmith', 'orr', true, '212-593-0234', 2, 'FEMALE'),
       ('Jonny', 'Smith', 'jsmithjr', 'irr', true, '212-593-0235', 3, 'MALE');