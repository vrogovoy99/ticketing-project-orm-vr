INSERT INTO roles(description, is_deleted, insert_date_time, insert_user_id) VALUES ('Admin', false, '2024-05-01 00:00:00.000000', 1);
INSERT INTO roles(description, is_deleted, insert_date_time, insert_user_id) VALUES ('Manager', false, '2024-05-01 00:00:00.000000', 1);
INSERT INTO roles(description, is_deleted, insert_date_time, insert_user_id) VALUES ('Employee', false, '2024-05-01 00:00:00.000000', 1);

INSERT INTO users(first_name, last_name, user_name, pass_word, enabled, phone, role_id, gender, is_deleted, insert_date_time, insert_user_id)
VALUES ('John', 'Smith', 'jsmith@cydeo.com', 'arr', true, '2125930233', 1, 'MALE', false, '2024-05-01 00:00:00.000000', 1),
       ('Suzan', 'Smith', 'ssmith@cydeo.com', 'orr', true, '2125930234', 2, 'FEMALE', false, '2024-05-01 00:00:00.000000', 1),
       ('Jonny', 'Smith', 'jsmithjr@cydeo.com', 'irr', true, '2125930235', 3, 'MALE', false, '2024-05-01 00:00:00.000000', 1),
       ('Suzan2', 'Smith', 's2smith@cydeo.com', 'orr', true, '2125930234', 2, 'FEMALE', false, '2024-05-01 00:00:00.000000', 1),
       ('Jonny2', 'Smith', 'j2smithjr@cydeo.com', 'irr', true, '2125930235', 3, 'MALE', false, '2024-05-01 00:00:00.000000', 1);

insert into projects(project_name, project_code, project_detail, project_status,
                     start_date, end_date, assigned_manager_id, complete_task_counts, unfinished_task_counts, is_deleted,
                     insert_date_time, insert_user_id)
select 'Project One', 'P01', 'First Project', 'OPEN',
                    '2004-01-06', '2024-05-12', id, 0, 0, false,
                    '2024-05-01 00:00:00.000000', 1 from users where first_name='Suzan';

insert into projects(project_name, project_code, project_detail, project_status, start_date, end_date, assigned_manager_id, complete_task_counts, unfinished_task_counts, is_deleted,
                     insert_date_time, insert_user_id)
select 'Project Two', 'P02', 'Second Project', 'OPEN', '2007-05-08', '2024-05-12', id, 0, 0, false,
                    '2024-05-01 00:00:00.000000', 1 from users where first_name='Suzan';


insert into tasks (assigned_date, task_detail, task_status, task_subject,  assigned_employee_id, project_id, is_deleted,
                   insert_date_time, insert_user_id)
values (now(), 'Task One Detail', 'OPEN', 'Task One', 3, 2, false, '2024-05-01 00:00:00.000000', 1),
       (now(), 'Task Two Detail', 'OPEN', 'Task Two', 3, 2, false, '2024-05-01 00:00:00.000000', 1);
-- insert into tasks (assigned_date, task_detail, task_status, task_subject, tid, assigned_employee_id, project_id, is_deleted,
--                    insert_date_time, insert_user_id)
-- values (now(), 'Task One Detail', 'OPEN', 'Task One', 1, 3, 2, false, '2024-05-01 00:00:00.000000', 1),
--        (now(), 'Task Two Detail', 'OPEN', 'Task Two', 2, 3, 2, false, '2024-05-01 00:00:00.000000', 1);