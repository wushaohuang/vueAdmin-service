# vueAdmin-service
init

# Database

```sql
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
`id`        bigint(20)  NOT NULL AUTO_INCREMENT,
`parent_id` bigint(20)   DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
`name`      varchar(64) NOT NULL,
`path`      varchar(255) DEFAULT NULL COMMENT '菜单URL',
`perms`     varchar(255) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
`component` varchar(255) DEFAULT NULL,
`type`      int(5)      NOT NULL COMMENT '类型 -> 0：目录; 1：菜单; 2：按钮',
`icon`      varchar(32)  DEFAULT NULL COMMENT '菜单图标',
`orderNum`  int(11)      DEFAULT NULL COMMENT '排序',
`created`   datetime    NOT NULL,
`updated`   datetime     DEFAULT NULL,
`statu`     int(5)      NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
`id`      bigint(20)  NOT NULL AUTO_INCREMENT,
`name`    varchar(64) NOT NULL,
`code`    varchar(64) NOT NULL,
`remark`  varchar(64) DEFAULT NULL COMMENT '备注',
`created` datetime    DEFAULT NULL,
`updated` datetime    DEFAULT NULL,
`statu`   int(5)      NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `name` (`name`) USING BTREE,
UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
`id`      bigint(20) NOT NULL AUTO_INCREMENT,
`role_id` bigint(20) NOT NULL,
`menu_id` bigint(20) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE = InnoDB
AUTO_INCREMENT = 102
DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
`id`         bigint(20) NOT NULL AUTO_INCREMENT,
`username`   varchar(64)  DEFAULT NULL,
`password`   varchar(64)  DEFAULT NULL,
`avatar`     varchar(255) DEFAULT NULL,
`email`      varchar(64)  DEFAULT NULL,
`city`       varchar(64)  DEFAULT NULL,
`created`    datetime     DEFAULT NULL,
`updated`    datetime     DEFAULT NULL,
`last_login` datetime     DEFAULT NULL,
`statu`      int(5)     NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UK_USERNAME` (`username`) USING BTREE
) ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
`id`      bigint(20) NOT NULL AUTO_INCREMENT,
`user_id` bigint(20) NOT NULL,
`role_id` bigint(20) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARSET = utf8mb4;

SELECT *
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = (SELECT DATABASE());

SELECT *
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = (SELECT DATABASE())
AND upper(TABLE_NAME) = 'SYS_USER';
```


