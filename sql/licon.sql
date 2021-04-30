/*
 Navicat Premium Data Transfer

 Source Server         : licon
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : licon

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 30/04/2021 16:51:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorities
-- ----------------------------
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `authority_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ix_auth_username`(`username`, `authority_id`) USING BTREE,
  INDEX `fk_authorities_authority`(`authority_id`) USING BTREE,
  CONSTRAINT `fk_authorities_authority` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authorities
-- ----------------------------
INSERT INTO `authorities` VALUES (1, 'zhangsan', 1);
INSERT INTO `authorities` VALUES (2, 'zhangsan', 2);
INSERT INTO `authorities` VALUES (3, 'zhangsan', 3);

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `id` int(11) NOT NULL,
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `auth_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'R 标识角色，U标识url操作权限',
  `p_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父级角色名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES (1, 'admin', '管理员', 'R', NULL);
INSERT INTO `authority` VALUES (2, 'user', '用户', 'R', 'admin');
INSERT INTO `authority` VALUES (3, 'user_info', '用户信息接口权限', 'U', 'user');
INSERT INTO `authority` VALUES (4, 'address_info', '定制信息接口', 'U', 'user');
INSERT INTO `authority` VALUES (5, 'multi_address', '多地址接口', 'U', 'address_info');

-- ----------------------------
-- Table structure for url
-- ----------------------------
DROP TABLE IF EXISTS `url`;
CREATE TABLE `url`  (
  `id` int(11) NOT NULL,
  `url_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `vote_type` int(2) DEFAULT NULL COMMENT '1拥有符合的一个角色即有权限 2多数或一半统一则有权限 3必须全部符合才有权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of url
-- ----------------------------
INSERT INTO `url` VALUES (1, '/all-user', '获取所有用户', 1);

-- ----------------------------
-- Table structure for url_authority
-- ----------------------------
DROP TABLE IF EXISTS `url_authority`;
CREATE TABLE `url_authority`  (
  `id` int(11) NOT NULL,
  `url_id` int(11) DEFAULT NULL,
  `authority_id` int(11) DEFAULT NULL,
  `req_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求类型',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_urlauthority_url`(`url_id`) USING BTREE,
  INDEX `fk_urlauthority_authority`(`authority_id`) USING BTREE,
  CONSTRAINT `fk_urlauthority_authority` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_urlauthority_url` FOREIGN KEY (`url_id`) REFERENCES `url` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of url_authority
-- ----------------------------
INSERT INTO `url_authority` VALUES (1, 1, 3, 'GET');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('zhangsan', '$2a$10$uMpXoFHemhCERRB.sSnZZOJdOFH3hQQKPjIrjRoXAZKzL7Sa8gvDe', 1);

SET FOREIGN_KEY_CHECKS = 1;
