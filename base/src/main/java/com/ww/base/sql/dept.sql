DROP DATABASE IF EXISTS springbootdb;
CREATE DATABASE springbootdb CHARACTER SET UTF8;
USE springbootdb;
CREATE TABLE dept (
	dept_no BIGINT AUTO_INCREMENT,
	dept_name VARCHAR(50),
	CONSTRAINT pk_dept_no PRIMARY KEY(dept_no)
);

INSERT INTO dept(dept_name) VALUES ('开发部');
INSERT INTO dept(dept_name) VALUES ('测试部');
INSERT INTO dept(dept_name) VALUES ('市场部');
INSERT INTO dept(dept_name) VALUES ('销售部');
INSERT INTO dept(dept_name) VALUES ('服务部');