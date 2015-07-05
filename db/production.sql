# Inital db/user creation
create USER Sylvain WITH password 'dubai';
create database production;
GRANT ALL PRIVILEGES ON DATABASE "production" to Sylvain;
ALTER role Sylvain WITH LOGIN;

## Login as Sylvain user and run the following

CREATE TABLE employee (
   id   BIGSERIAL PRIMARY KEY,
   first_name    VARCHAR(128) NOT NULL,
   last_name     VARCHAR(128) NOT NULL,
   email         VARCHAR(128) UNIQUE NOT NULL,
   password      VARCHAR(128) NOT NULL
);

CREATE TABLE category (
   id   BIGSERIAL PRIMARY KEY,
   name    VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE project (
   id   BIGSERIAL PRIMARY KEY,
   category_id BIGINT REFERENCES category(id) NOT NULL,
   name    VARCHAR(128) NOT NULL,
   description    VARCHAR(128) NOT NULL,
   CONSTRAINT u_cateory_id_name UNIQUE (category_id, name)
);

CREATE TABLE task (
   id   BIGSERIAL PRIMARY KEY,
   project_id BIGINT REFERENCES project(id) NOT NULL,
   employee_id BIGINT REFERENCES employee(id),
   name    VARCHAR(128) NOT NULL,
   description    VARCHAR(128) NOT NULL,
   done			  BOOLEAN DEFAULT FALSE NOT NULL
);

## Insert some dummy data to populate the db
# password is set to 'abc123'
insert into employee (first_name, last_name, email, password) VALUES ('Magali', 'Mouquet', 'mm@emiratesreit.com', '$2a$10$QhT1U93tK4BT6e3XXGPOJeGqmQSphfdP4ARZfPCsLCJUPpenkrNIe');
insert into employee (first_name, last_name, email, password) VALUES ('Sylvain', 'Vieujot', 'sv@emiratesreit.com', '$2a$10$QhT1U93tK4BT6e3XXGPOJeGqmQSphfdP4ARZfPCsLCJUPpenkrNIe');
insert into employee (first_name, last_name, email, password) VALUES ('Hannah', 'Jeffrey', 'hj@emiratesreit.com', '$2a$10$QhT1U93tK4BT6e3XXGPOJeGqmQSphfdP4ARZfPCsLCJUPpenkrNIe');
insert into employee (first_name, last_name, email, password) VALUES ('James', 'Anderson', 'ja@emiratesreit.com', '$2a$10$QhT1U93tK4BT6e3XXGPOJeGqmQSphfdP4ARZfPCsLCJUPpenkrNIe');
insert into employee (first_name, last_name, email, password) VALUES ('Arsheen', 'Saulat', 'as@emiratesreit.com', '$2a$10$QhT1U93tK4BT6e3XXGPOJeGqmQSphfdP4ARZfPCsLCJUPpenkrNIe');
insert into employee (first_name, last_name, email, password) VALUES ('Croydon', 'Dias', 'croydondias@gmail.com', '$2a$10$QhT1U93tK4BT6e3XXGPOJeGqmQSphfdP4ARZfPCsLCJUPpenkrNIe');

insert into category (name) VALUES('Buildings');
insert into category (name) VALUES('Work departments');

insert into project (category_id, name, description) VALUES (1, 'The Grande', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'Building 24', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'The Loft Offices', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'Office Park', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'Indigo 7', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'GEMS World Academy', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'Index Tower', 'xxx');
insert into project (category_id, name, description) VALUES (2, 'IT', 'xxx');
insert into project (category_id, name, description) VALUES (2, 'Accounting', 'xxx');
insert into project (category_id, name, description) VALUES (2, 'Marketing', 'xxx');
insert into project (category_id, name, description) VALUES (2, 'Legal', 'xxx');

insert into task (project_id, employee_id, name, description) VALUES (1, 1, 'Paint walls', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (1, 2, 'Lay Indian carpets', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (1, 3, 'Put furniture', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (1, null, 'Finish marketing', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (1, 4, 'Hire cleaners', 'xxx', true);

insert into task (project_id, employee_id, name, description) VALUES (2, 1, 'Paint walls Red', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (2, 2, 'Lay Egyptian carpets', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (2, 3, 'Put furniture', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (2, null, 'Finish marketing', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (2, 4, 'Hire cleaners', 'xxx', true);

insert into task (project_id, employee_id, name, description) VALUES (3, 1, 'Paint walls', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (3, 3, 'Check light fixtures', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (3, null, 'Finish marketing', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (3, 4, 'Hire cleaners', 'xxx', true);
insert into task (project_id, employee_id, name, description) VALUES (3, 2, 'Adjust airconditioning', 'xxx');

insert into task (project_id, employee_id, name, description, done) VALUES (4, 4, 'Hire cleaners', 'xxx', true);
insert into task (project_id, employee_id, name, description) VALUES (4, 1, 'Install curtain rods', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (4, 3, 'Check light fixtures', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (4, null, 'Finish marketing', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (4, 2, 'Adjust aiconditioning', 'xxx');

insert into task (project_id, employee_id, name, description) VALUES (5, 1, 'Install curtain rods', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (5, 3, 'Check light fixtures', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (5, 2, 'Adjust aiconditioning', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (5, null, 'Advertise vacancies', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (5, 4, 'Hire cleaners', 'xxx', true);

insert into task (project_id, employee_id, name, description) VALUES (6, 2, 'Adjust aiconditioning', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (6, 3, 'Check light fixtures', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (6, null, 'Advertise vacancies', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (6, 4, 'Hire cleaners', 'xxx', true);
insert into task (project_id, employee_id, name, description) VALUES (6, 1, 'Install curtain rods', 'xxx');

insert into task (project_id, employee_id, name, description) VALUES (7, 4, 'Hire security', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (7, 2, 'Interview for new building manager', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (7, 3, 'Advertise vacancies', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (7, null, 'Finish marketing', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (7, 4, 'Hire cleaners', 'xxx', true);

insert into task (project_id, employee_id, name, description) VALUES (8, 6, 'Develop new website', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (8, 6, 'Run batch jobs', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (8, 6, 'Develop iOS app', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (8, 6, 'Submit project', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (8, 6, 'Check bugs', 'xxx');

insert into task (project_id, employee_id, name, description) VALUES (9, 2, 'Request a shipping order', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (9, 3, 'Pay salaries', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (9, 2, 'Caclculate net profit', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (9, 3, 'Write adjust for split shares', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (9, 1, 'Consult with legal department regarding an acquisition', 'xxx');

insert into task (project_id, employee_id, name, description) VALUES (10, 1, 'Hire 3 new employees', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (10, 3, 'Put out an ad in a magazine', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (10, 4, 'Check ratings for previous magazine issue', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (10, 4, 'Investigate company logo re-redign', 'xxx');

insert into task (project_id, employee_id, name, description) VALUES (11, 5, 'Check new laws for 2015', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (11, 5, 'Do a presentation', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (11, 5, 'Sign papers from hedge fund', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (11, 5, 'Sign investor relations documents', 'xxx');