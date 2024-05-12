INSERT INTO roles(description, is_deleted) VALUES ('Admin', false);
INSERT INTO roles(description, is_deleted) VALUES ('Manager', false);
INSERT INTO roles(description, is_deleted) VALUES ('Employee', false);

INSERT INTO users(first_name, last_name, user_name, pass_word, enabled, phone, role_id, gender, is_deleted)
VALUES ('John', 'Smith', 'jsmith', 'arr', true, '212-593-0233', 1, 'MALE', false),
       ('Suzan', 'Smith', 'ssmith', 'orr', true, '212-593-0234', 2, 'FEMALE', false),
       ('Jonny', 'Smith', 'jsmithjr', 'irr', true, '212-593-0235', 3, 'MALE', false);