USE springbootdb;
DROP TABLE IF EXISTS t_users;
CREATE TABLE t_users(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT '系统分配ID，主键且唯一',
	user_name VARCHAR(50) NOT NULL COMMENT '用户名，全局唯一',
	user_password VARCHAR(50) NOT NULL COMMENT '用户密码',
	PRIMARY KEY pk_user_id(id),
	UNIQUE INDEX idx_username(user_name)
) ENGINE=INNODB COMMENT '用户表ww';
INSERT INTO t_users(user_name,user_PASSWORD) VALUES ('userww','654321') ;

DROP TABLE IF EXISTS t_user_roles;
CREATE TABLE t_user_roles(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT '系统分配ID，主键且唯一', 
	user_name VARCHAR(50) NOT NULL COMMENT '用户名',
	role_name VARCHAR(50) NOT NULL COMMENT '角色名', 
	PRIMARY KEY pk_user_roles_id(id),
	UNIQUE INDEX idx_user_role(user_name,role_name)
) ENGINE=INNODB COMMENT '用户-角色表';
INSERT INTO t_user_roles(user_name,role_name) VALUES ('userww','admin');
INSERT INTO t_user_roles(user_name,role_name) VALUES ('userww','user');

DROP TABLE IF EXISTS t_roles_permissions;
CREATE TABLE t_roles_permissions(
	id BIGINT NOT NULL AUTO_INCREMENT COMMENT '系统分配ID，主键且唯一', 
	role_name VARCHAR(50) NOT NULL COMMENT '角色名', 
	perm_name VARCHAR(50) NOT NULL COMMENT '权限名',
	PRIMARY KEY pk_role_perm_id(id),
	UNIQUE INDEX idx_role_permission(role_name,perm_name)
) ENGINE=INNODB COMMENT '角色-权限表';
INSERT INTO t_roles_permissions(role_name,perm_name) VALUES ('admin','admin:add');
INSERT INTO t_roles_permissions(role_name,perm_name) VALUES ('admin','admin:delete');
INSERT INTO t_roles_permissions(role_name,perm_name) VALUES ('user','user:select');


