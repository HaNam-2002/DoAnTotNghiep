CREATE DATABASE HMR_data ;
USE HMR_data;
CREATE TABLE department (
    department_manager_id INT,
    id INT NOT NULL AUTO_INCREMENT,
    department_name NVARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_department_manager FOREIGN KEY (department_manager_id) REFERENCES employees (id)
);

CREATE TABLE employees (
    department_id INT,
    id INT NOT NULL AUTO_INCREMENT,
    phone INT,
    position_id INT,
    user_id INT,
    date_of_birth DATE,
    email NVARCHAR(50),
    full_name NVARCHAR(50),
    gender NVARCHAR(50),
    home_town NVARCHAR(100),
    nation NVARCHAR(50),
    PRIMARY KEY (id),
    CONSTRAINT FK_department_id FOREIGN KEY (department_id) REFERENCES department (id),
    CONSTRAINT FK_position_id FOREIGN KEY (position_id) REFERENCES position (id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES user_entity (id)
);

CREATE TABLE leave_request (
    employe_id INT,
    id INT NOT NULL AUTO_INCREMENT,
    end_date DATE NOT NULL,
    start_date DATE NOT NULL,
    description NVARCHAR(50) NOT NULL,
    leave_status NVARCHAR(50) NOT NULL,
    type_application NVARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_employe_id FOREIGN KEY (employe_id) REFERENCES employees (id)
);

CREATE TABLE position (
    id INT NOT NULL AUTO_INCREMENT,
    salary DECIMAL,
    position_name NVARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE project (
    id INT NOT NULL AUTO_INCREMENT,
    teamid INT,
    end_date DATE NOT NULL,
    start_date DATE NOT NULL,
    description NVARCHAR(50) NOT NULL,
    project_name NVARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_teamid FOREIGN KEY (teamid) REFERENCES team (id)
);

CREATE TABLE role (
    id INT NOT NULL AUTO_INCREMENT,
    role_name NVARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE task (
    id INT NOT NULL AUTO_INCREMENT,
    receiverid INT,
    teamid INT,
    end_date DATE NOT NULL,
    start_date DATE NOT NULL,
    task_description NVARCHAR(100) NOT NULL,
    task_name NVARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_receiverid FOREIGN KEY (receiverid) REFERENCES employees (id),
    CONSTRAINT FK_teamid_task FOREIGN KEY (teamid) REFERENCES team (id)
);

CREATE TABLE team (
    departmentid INT,
    id INT NOT NULL AUTO_INCREMENT,
    leaderid INT,
    purpose NVARCHAR(50) NOT NULL,
    team_name INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_departmentid_team FOREIGN KEY (departmentid) REFERENCES department (id),
    CONSTRAINT FK_leaderid FOREIGN KEY (leaderid) REFERENCES employees (id)
);

CREATE TABLE user_roles (
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (role_id, user_id),
    CONSTRAINT FK_role_id FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT FK_user_id_roles FOREIGN KEY (user_id) REFERENCES user_entity (id)
);

CREATE TABLE user_entity (
    id INT NOT NULL AUTO_INCREMENT,
    pass_word NVARCHAR(255) NOT NULL,
    user_name NVARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
