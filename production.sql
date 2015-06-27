# Inital db/user creation
create USER Sylvain WITH password 'dubai';
create database production;
GRANT ALL PRIVILEGES ON DATABASE "production" to Sylvain;
ALTER role Sylvain WITH LOGIN;

# chkpass is a new field that automatically encrypts the field
CREATE EXTENSION chkpass;

## Login as Sylvain user and run the following


drop table task;
drop table project;
drop table category;
drop table employee;



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
   done			  BOOLEAN DEFAULT FALSE NOT NULL,
   CONSTRAINT u_project_id_name UNIQUE (project_id, name)
);

insert into employee (first_name, last_name, email, password) VALUES ('Magali', 'Mouquet', 'mm@emiratesreit.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('Sylvain', 'Vieujot', 'sv@emiratesreit.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('Hannah', 'Jeffrey', 'hj@emiratesreit.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('James', 'Anderson', 'ja@emiratesreit.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('Arsheen', 'Saulat', 'as@emiratesreit.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('Croydon', 'Dias', 'croydondias@gmail.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('Sergio', 'Artalejo', 'sa@gmail.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('Harry', 'Potter', 'wonderboy@gmail.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('Hermione', 'Granger', 'muggle@hpworld.com', 'abc123');
insert into employee (first_name, last_name, email, password) VALUES ('Ron', 'Burgundy', 'ron.burgundy@news.com', 'abc123');

insert into category (name) VALUES('Buildings');
insert into category (name) VALUES('Work departments');

insert into project (category_id, name, description) VALUES (1, 'The Grande', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'Building 24', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'The Loft Offices', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'Office Park', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'Indigo 7', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'GEMS World Academy', 'xxx');
insert into project (category_id, name, description) VALUES (1, 'Index Tower', 'xxx');

insert into task (project_id, employee_id, name, description) VALUES (1, 1, 'Paint walls', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (1, 2, 'Lay carpets', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (1, 3, 'Put furniture', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (1, null, 'Finish marketing', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (1, 4, 'Hire cleaners', 'xxx', true);
insert into task (project_id, employee_id, name, description) VALUES (2, 1, 'Paint walls', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (2, 2, 'Lay carpets', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (2, 3, 'Put furniture', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (2, null, 'Finish marketing', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (2, 4, 'Hire cleaners', 'xxx', true);
insert into task (project_id, employee_id, name, description) VALUES (3, 1, 'Paint walls', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (3, 2, 'Lay carpets', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (3, 3, 'Put furniture', 'xxx');
insert into task (project_id, employee_id, name, description) VALUES (3, null, 'Finish marketing', 'xxx');
insert into task (project_id, employee_id, name, description, done) VALUES (3, 4, 'Hire cleaners', 'xxx', true);