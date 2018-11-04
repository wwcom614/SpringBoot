USE springbootdb;
DROP TABLE IF EXISTS users;
CREATE TABLE users(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT '系统分配ID，主键且唯一', 
	username VARCHAR(50) NOT NULL COMMENT '用户名，全局唯一', 
	PASSWORD VARCHAR(50) NOT NULL COMMENT '用户密码', 
	PRIMARY KEY pk_user_id(id),
	UNIQUE INDEX idx_username(username)
) ENGINE=INNODB COMMENT '用户表';
INSERT INTO users(username,PASSWORD) VALUES ('ww','123456') ;

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT '系统分配ID，主键且唯一', 
	username VARCHAR(50) NOT NULL COMMENT '用户名', 
	role_name VARCHAR(50) NOT NULL COMMENT '角色名', 
	PRIMARY KEY pk_user_roles_id(id),
	UNIQUE INDEX idx_user_role(username,role_name)
) ENGINE=INNODB COMMENT '用户-角色表';
INSERT INTO user_roles(username,role_name) VALUES ('ww','admin');
INSERT INTO user_roles(username,role_name) VALUES ('ww','user');

DROP TABLE IF EXISTS roles_permissions;
CREATE TABLE roles_permissions(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT '系统分配ID，主键且唯一', 
	role_name VARCHAR(50) NOT NULL COMMENT '角色名', 
	permission VARCHAR(50) NOT NULL COMMENT '权限名', 
	PRIMARY KEY pk_role_permission_id(id),
	UNIQUE INDEX idx_role_permission(role_name,permission)
) ENGINE=INNODB COMMENT '角色-权限表';
INSERT INTO roles_permissions(role_name,permission) VALUES ('admin','admin:add');
INSERT INTO roles_permissions(role_name,permission) VALUES ('admin','admin:delete');
INSERT INTO roles_permissions(role_name,permission) VALUES ('user','user:select');
