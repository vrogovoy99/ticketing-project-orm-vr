INSERT INTO roles(description, is_deleted) VALUES ('Admin', false);
INSERT INTO roles(description, is_deleted) VALUES ('Manager', false);
INSERT INTO roles(description, is_deleted) VALUES ('Employee', false);

INSERT INTO users(first_name, last_name, user_name, pass_word, enabled, phone, role_id, gender, is_deleted)
VALUES ('John', 'Smith', 'jsmith', 'arr', true, '212-593-0233', 1, 'MALE', false),
       ('Suzan', 'Smith', 'ssmith', 'orr', true, '212-593-0234', 2, 'FEMALE', false),
       ('Jonny', 'Smith', 'jsmithjr', 'irr', true, '212-593-0235', 3, 'MALE', false),
       ('Suzan2', 'Smith', 's2smith', 'orr', true, '212-593-0234', 2, 'FEMALE', false),
       ('Jonny2', 'Smith', 'j2smithjr', 'irr', true, '212-593-0235', 3, 'MALE', false);

insert into projects(project_name, project_code, project_detail, project_status, start_date, end_date, assigned_manager_id, complete_task_counts, unfinished_task_counts, is_deleted)
select 'Project One', 'P01', 'First Project', 'OPEN', '2004-01-06', '2024-05-12', id, 0, 0, false from users where first_name='Suzan';

insert into projects(project_name, project_code, project_detail, project_status, start_date, end_date, assigned_manager_id, complete_task_counts, unfinished_task_counts, is_deleted)
select 'Project Two', 'P02', 'Second Project', 'OPEN', '2007-05-08', '2024-05-12', id, 0, 0, false from users where first_name='Suzan';