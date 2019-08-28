/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : permission

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-08-28 21:45:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_func`;
CREATE TABLE `sys_func` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `funcName` varchar(50) NOT NULL DEFAULT '',
  `funcCode` varchar(20) DEFAULT '',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_func_code` (`funcCode`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='功能';

-- ----------------------------
-- Records of sys_func
-- ----------------------------
INSERT INTO `sys_func` VALUES ('1', '添加', 'add', '2018-06-28 10:00:53', '2018-06-28 10:00:56');
INSERT INTO `sys_func` VALUES ('2', '删除', 'del', '2018-06-28 10:01:20', '2018-06-28 10:01:24');
INSERT INTO `sys_func` VALUES ('3', '修改', 'edit', '2018-06-28 10:01:46', '2019-08-20 10:44:23');
INSERT INTO `sys_func` VALUES ('4', '查询', 'find', '2018-06-28 10:02:12', '2018-06-28 10:02:15');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menuName` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单名',
  `parentMenuId` int(11) NOT NULL DEFAULT '0' COMMENT '父级菜单ID 0：是顶级菜单',
  `component` varchar(255) NOT NULL DEFAULT '',
  `path` varchar(100) DEFAULT NULL COMMENT '菜单地址',
  `orderNo` int(11) DEFAULT NULL COMMENT '排序',
  `icon` varchar(150) DEFAULT NULL COMMENT '图标',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `parentMenuId_orderNo` (`parentMenuId`,`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '权限', '0', 'Layout', '/dashboard', '2', null, '2018-06-28 00:00:00', '2019-08-11 18:38:36');
INSERT INTO `sys_menu` VALUES ('2', '权限管理', '1', 'dashboard/index', 'dashboard', '1', 'dashboard', '2018-06-28 00:00:00', '2019-08-11 18:39:38');

-- ----------------------------
-- Table structure for sys_menu_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_func`;
CREATE TABLE `sys_menu_func` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menuId` int(11) NOT NULL DEFAULT '0',
  `funcCode` varchar(20) NOT NULL DEFAULT '',
  `funcAction` varchar(64) NOT NULL,
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `menuId` (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='菜单 功能 表';

-- ----------------------------
-- Records of sys_menu_func
-- ----------------------------
INSERT INTO `sys_menu_func` VALUES ('1', '2', 'add', 'admin:perms:add', '2018-06-28 10:13:08', '2018-06-28 10:13:12');
INSERT INTO `sys_menu_func` VALUES ('2', '2', 'del', 'admin:perms:del', '2018-06-28 10:15:50', '2018-06-28 10:15:54');
INSERT INTO `sys_menu_func` VALUES ('3', '2', 'edit', 'admin:perms:edit', '2018-06-28 10:16:44', '2019-08-20 10:44:38');
INSERT INTO `sys_menu_func` VALUES ('4', '2', 'find', 'admin:perms:find', '2018-06-28 10:21:10', '2018-06-28 10:21:13');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleName` varchar(20) NOT NULL COMMENT '角色名称',
  `roleInfo` text COMMENT '角色介绍',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '系统管理员', '2018-04-01 15:52:00', '2018-04-01 15:52:38');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleId` int(11) DEFAULT NULL,
  `menuId` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_menu` (`menuId`),
  KEY `FK_role` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1', '2019-08-11 17:01:52', '2019-08-11 17:02:03');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '2', '2018-06-28 10:27:40', '2019-08-11 17:02:09');

-- ----------------------------
-- Table structure for sys_role_menu_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_func`;
CREATE TABLE `sys_role_menu_func` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleId` int(11) NOT NULL DEFAULT '0',
  `menuId` int(11) NOT NULL DEFAULT '0',
  `funcCode` varchar(20) NOT NULL DEFAULT '',
  `funcAction` varchar(100) NOT NULL DEFAULT '' COMMENT '功能的url',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_role` (`roleId`),
  KEY `FK_menu` (`menuId`),
  KEY `FK_func` (`funcCode`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色 菜单 功能 表';

-- ----------------------------
-- Records of sys_role_menu_func
-- ----------------------------
INSERT INTO `sys_role_menu_func` VALUES ('1', '1', '2', 'add', 'admin:perms:add', '2018-06-28 10:33:14', '2018-06-28 10:33:17');

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info`;
CREATE TABLE `sys_user_info` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '自动增长Id',
  `userAccount` varchar(32) NOT NULL COMMENT '用户帐号',
  `userPassword` varchar(64) NOT NULL COMMENT '用户密码',
  `image` varchar(120) NOT NULL DEFAULT '',
  `userName` varchar(64) NOT NULL COMMENT '用户名',
  `token` varchar(64) NOT NULL DEFAULT '' COMMENT '用户token',
  `email` varchar(20) NOT NULL DEFAULT '' COMMENT 'email',
  `createTime` datetime NOT NULL COMMENT '注册时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `userinfo_userName_IDX` (`userName`),
  KEY `userAccount` (`userAccount`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_info
-- ----------------------------
INSERT INTO `sys_user_info` VALUES ('00000000001', 'admin', '111111', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'admin', '1b54a648ae42469cb2d9e29f47306670', 'com', '2018-04-01 14:35:36', '2019-08-05 22:59:31');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `roleId` int(11) NOT NULL COMMENT '角色Id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_bbs_userrole` (`roleId`),
  KEY `FK_bbs_userrole1` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '2018-04-01 16:38:26', '2018-04-01 16:38:30');
