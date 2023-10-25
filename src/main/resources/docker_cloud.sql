/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : docker_cloud

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 25/10/2023 20:28:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for container
-- ----------------------------
DROP TABLE IF EXISTS `container`;
CREATE TABLE `container`  (
  `id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `image_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `name_c` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `descption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `owner_id` int NULL DEFAULT NULL,
  `state` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lease_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lease_end` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_c_u_oid`(`owner_id` ASC) USING BTREE,
  CONSTRAINT `fk_c_u_oid` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of container
-- ----------------------------
INSERT INTO `container` VALUES ('0', '1234', '1234', 'hhhh', 1, '1', '2023-02-28 23:51:06', '2023-03-02 16:53:21', '2023-02-28 23:51:06', NULL);
INSERT INTO `container` VALUES ('1647929547603173377', '1234', 'dockerSql', NULL, 1, NULL, '2023-04-17 19:46:36', '2023-04-17 19:46:35', '2023-04-17 19:46:35', NULL);
INSERT INTO `container` VALUES ('1647935730338545665', '1234', 'test1', NULL, 1, NULL, '2023-04-17 20:11:10', '2023-04-17 20:11:09', '2023-04-17 20:11:09', NULL);
INSERT INTO `container` VALUES ('1648216619106713601', '1234', 'XylTest', NULL, 1, NULL, '2023-04-18 14:47:19', '2023-04-18 14:47:19', '2023-04-18 14:47:19', NULL);

-- ----------------------------
-- Table structure for hardware
-- ----------------------------
DROP TABLE IF EXISTS `hardware`;
CREATE TABLE `hardware`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `cpu_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `cpu_core_number` int NULL DEFAULT NULL,
  `network_speed` int NULL DEFAULT NULL,
  `memory` int NULL DEFAULT NULL,
  `disk` int NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `money` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hardware
-- ----------------------------
INSERT INTO `hardware` VALUES (2, '1', 2, 0, 0, 0, '2023-03-01 14:39:29', '2023-10-25 20:05:05', 0);
INSERT INTO `hardware` VALUES (3, '2', 2, 2, 2000, 2, '2023-05-07 22:34:17', '2023-10-25 20:05:06', 0);
INSERT INTO `hardware` VALUES (5, '20', 20, 20, 1600, 20, '2023-05-07 22:51:13', '2023-10-25 20:05:07', 0);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `container_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `name_o` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  `money` float(6, 2) NULL DEFAULT NULL,
  `packet_id` int NOT NULL,
  `pay_way` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `state` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '未支付',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_o_c_cid`(`container_id` ASC) USING BTREE,
  INDEX `fk_o_u_uid`(`user_id` ASC) USING BTREE,
  INDEX `fk_o_p_pid`(`packet_id` ASC) USING BTREE,
  CONSTRAINT `fk_o_c_cid` FOREIGN KEY (`container_id`) REFERENCES `container` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_o_p_pid` FOREIGN KEY (`packet_id`) REFERENCES `packet` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_o_u_uid` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '0', NULL, '1', '2023-03-01 15:00:03', '2023-03-01 15:00:03', 1, 1000.00, 3, '1', '未支付');
INSERT INTO `orders` VALUES (2, '0', NULL, NULL, '2023-03-02 14:24:09', '2023-03-02 14:24:09', 4, 0.00, 3, NULL, '未支付');
INSERT INTO `orders` VALUES (3, '0', NULL, '1', '2023-04-06 14:52:36', '2023-04-06 14:52:36', 1, 1000.00, 3, '1', '未支付');
INSERT INTO `orders` VALUES (4, '0', NULL, '1', '2023-04-06 14:53:02', '2023-04-06 14:53:02', 1, 1000.00, 3, '1', '未支付');
INSERT INTO `orders` VALUES (5, '0', NULL, '1', '2023-04-06 14:53:48', '2023-04-06 14:53:48', 1, 1000.00, 3, '1', '未支付');
INSERT INTO `orders` VALUES (6, '0', NULL, 'order', '2023-04-15 23:48:35', '2023-04-15 23:48:35', 1, 0.00, 3, NULL, '未支付');
INSERT INTO `orders` VALUES (7, '0', NULL, 'order', '2023-04-15 23:51:03', '2023-04-15 23:51:03', 1, 0.00, 3, NULL, '未支付');
INSERT INTO `orders` VALUES (8, '0', NULL, 'order', '2023-04-15 23:52:25', '2023-04-15 23:52:25', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (9, '0', NULL, 'order', '2023-04-15 23:56:41', '2023-04-15 23:56:41', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (10, '0', NULL, 'order', '2023-04-16 00:31:55', '2023-04-16 00:31:55', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (11, '0', NULL, 'order', '2023-04-16 00:45:05', '2023-04-16 00:45:05', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (12, '0', NULL, 'order', '2023-04-16 01:17:57', '2023-04-16 01:17:57', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (13, '0', NULL, 'order', '2023-04-16 01:21:30', '2023-04-16 01:21:30', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (14, '0', NULL, 'order', '2023-04-17 19:38:19', '2023-04-17 19:38:19', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (15, '0', NULL, 'order', '2023-04-17 19:41:05', '2023-04-17 19:41:05', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (16, '0', NULL, 'order', '2023-04-17 19:43:58', '2023-04-17 19:43:58', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (17, '0', NULL, 'order', '2023-04-17 19:46:23', '2023-04-17 19:46:23', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (18, '0', NULL, 'order', '2023-04-17 20:08:22', '2023-04-17 20:08:22', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (19, '0', NULL, 'order', '2023-04-17 20:09:48', '2023-04-17 20:09:48', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (20, '0', NULL, 'order', '2023-04-17 20:10:59', '2023-04-17 20:10:59', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (21, '0', NULL, 'order', '2023-04-18 14:31:45', '2023-04-18 14:31:45', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (22, '0', NULL, 'order', '2023-04-18 14:39:21', '2023-04-18 14:39:21', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (23, '0', NULL, 'order', '2023-04-18 14:47:07', '2023-04-18 14:47:07', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (24, '0', NULL, 'order', '2023-04-26 00:00:43', '2023-04-26 00:00:43', 1, 0.00, 3, NULL, '初始化');
INSERT INTO `orders` VALUES (25, NULL, NULL, 'order', '2023-10-25 20:14:31', '2023-10-25 20:14:31', 1, 0.00, 5, NULL, '未支付');
INSERT INTO `orders` VALUES (26, NULL, NULL, 'order', '2023-10-25 20:14:31', '2023-10-25 20:14:31', 1, 0.00, 5, NULL, '未支付');
INSERT INTO `orders` VALUES (27, NULL, NULL, 'order', '2023-10-25 20:14:31', '2023-10-25 20:14:31', 1, 0.00, 5, NULL, '未支付');
INSERT INTO `orders` VALUES (28, NULL, NULL, 'order', '2023-10-25 20:15:16', '2023-10-25 20:15:16', 1, 0.00, 5, NULL, '未支付');
INSERT INTO `orders` VALUES (29, NULL, NULL, 'order', '2023-10-25 20:17:37', '2023-10-25 20:17:37', 1, 0.00, 5, NULL, '未支付');
INSERT INTO `orders` VALUES (30, NULL, NULL, 'order', '2023-10-25 20:25:46', '2023-10-25 20:25:46', 1, 0.00, 5, NULL, '未支付');
INSERT INTO `orders` VALUES (31, NULL, NULL, 'order', '2023-10-25 20:27:29', '2023-10-25 20:27:29', 1, 0.00, 5, NULL, '未支付');

-- ----------------------------
-- Table structure for packet
-- ----------------------------
DROP TABLE IF EXISTS `packet`;
CREATE TABLE `packet`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `name_p` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `hardware_id` int NOT NULL,
  `money` float(6, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_p_h_hid`(`hardware_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of packet
-- ----------------------------
INSERT INTO `packet` VALUES (3, '1', '1', '2023-03-01 14:51:55', '2023-10-25 19:56:34', 2, 1.00);
INSERT INTO `packet` VALUES (4, '1', NULL, '2023-03-02 14:24:09', '2023-10-25 19:56:35', 2, 1.00);
INSERT INTO `packet` VALUES (5, '基本套餐', '基本套餐', '2023-04-25 00:14:20', '2023-10-25 19:56:36', 2, 1.00);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '权限', '2', '2023-03-01 14:07:15', '2023-03-01 14:07:49');

-- ----------------------------
-- Table structure for permission_group
-- ----------------------------
DROP TABLE IF EXISTS `permission_group`;
CREATE TABLE `permission_group`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission_group
-- ----------------------------
INSERT INTO `permission_group` VALUES (1, '111', '1', '2023-03-01 13:34:19', '2023-03-01 13:34:19');
INSERT INTO `permission_group` VALUES (3, '111', '1', '2023-03-01 13:55:35', '2023-03-01 13:55:35');
INSERT INTO `permission_group` VALUES (4, 'admin', '1', '2023-03-01 13:55:42', '2023-03-20 17:50:10');

-- ----------------------------
-- Table structure for permission_group_permission
-- ----------------------------
DROP TABLE IF EXISTS `permission_group_permission`;
CREATE TABLE `permission_group_permission`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `permission_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pgp_pg_gid`(`group_id` ASC) USING BTREE,
  INDEX `fk_pgp_p_pid`(`permission_id` ASC) USING BTREE,
  CONSTRAINT `fk_pgp_p_pid` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_pgp_pg_gid` FOREIGN KEY (`group_id`) REFERENCES `permission_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission_group_permission
-- ----------------------------
INSERT INTO `permission_group_permission` VALUES (1, 1, 1, '2023-03-01 14:19:59', '2023-03-01 14:19:59');
INSERT INTO `permission_group_permission` VALUES (2, 3, 1, '2023-03-02 14:24:09', '2023-03-02 14:24:09');

-- ----------------------------
-- Table structure for user_permission_group
-- ----------------------------
DROP TABLE IF EXISTS `user_permission_group`;
CREATE TABLE `user_permission_group`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `user_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_ugp_pg_gid`(`group_id` ASC) USING BTREE,
  INDEX `fk_ugp_pg_uid`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_ugp_pg_gid` FOREIGN KEY (`group_id`) REFERENCES `permission_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ugp_pg_uid` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_permission_group
-- ----------------------------
INSERT INTO `user_permission_group` VALUES (1, 3, 1, '2023-03-01 13:56:29', '2023-03-02 14:31:27');
INSERT INTO `user_permission_group` VALUES (4, 4, 4, '2023-03-02 14:24:08', '2023-03-20 17:51:26');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `pwd` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '1000', '1234', '2023-02-28 23:14:27', '2023-02-28 23:14:27');
INSERT INTO `users` VALUES (4, '1001', '1234', '2023-02-28 23:58:58', '2023-03-02 16:53:36');
INSERT INTO `users` VALUES (5, '1002', '1234', '2023-03-02 14:24:09', '2023-03-02 16:53:38');
INSERT INTO `users` VALUES (7, '10001', '1234', '2023-04-24 15:56:09', '2023-04-24 15:56:09');

SET FOREIGN_KEY_CHECKS = 1;
