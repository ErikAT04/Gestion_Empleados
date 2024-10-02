DROP DATABASE IF EXISTS GEST_EMPLE;
CREATE DATABASE GEST_EMPLE;
USE GEST_EMPLE;
CREATE TABLE ENTERPRISE(
id int PRIMARY KEY AUTO_INCREMENT,
enter_name VARCHAR(255) unique,
enter_passwd VARCHAR(255)
);

CREATE TABLE DEPT(
id int PRIMARY KEY AUTO_INCREMENT,
enterprise int REFERENCES ENTERPRISE(id),
dept_name VARCHAR(255),
dept_location VARCHAR(255)
);

CREATE TABLE EMPLE(
dni VARCHAR(9) PRIMARY KEY,
working_dept int REFERENCES DEPT(id),
emp_name VARCHAR(255),
emp_surname VARCHAR(255),
emp_salary DOUBLE,
emp_date_join DATE
);

INSERT INTO ENTERPRISE(enter_name, enter_passwd) VALUES ("FiberTec", SHA("FibTec1234", 256))
INSERT INTO DEPT(enterprise, dept_name, dept_location) VALUES ((SELECT id from ENTERPRISE where enter_name like "FiberTec"), "Ventas", "Madrid")
INSERT INTO DEPT(enterprise, dept_name, dept_location) VALUES ((SELECT id from ENTERPRISE where enter_name like "FiberTec"), "Comercio", "Madrid")
INSERT INTO DEPT(enterprise, dept_name, dept_location) VALUES ((SELECT id from ENTERPRISE where enter_name like "FiberTec"), "Comercio", "Salamanca")
INSERT INTO DEPT(enterprise, dept_name, dept_location) VALUES ((SELECT id from ENTERPRISE where enter_name like "FiberTec"), "Saldos", "Murcia")

INSERT INTO EMPLE(dni, working_dept, emp_name, emp_surname, emp_salary, emp_date_join) VALUES ("12345678K", (SELECT ID FROM DEPT WHERE DEPT_LOCATION LIKE "Murcia" AND DEPT_NAME LIKE "Saldos" AND ENTERPRISE = (SELECT id from ENTERPRISE where enter_name like "FiberTec")))

