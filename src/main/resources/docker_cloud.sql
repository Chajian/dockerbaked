/*
 Navicat Premium Data Transfer

 Source Server         : localhost_330632
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : docker_cloud

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 12/02/2024 18:17:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for container
-- ----------------------------
DROP TABLE IF EXISTS `container`;
CREATE TABLE `container` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name_c` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `descption` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `owner_id` int DEFAULT NULL,
  `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lease_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `lease_end` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of container
-- ----------------------------


-- ----------------------------
-- Table structure for hardware
-- ----------------------------
DROP TABLE IF EXISTS `hardware`;
CREATE TABLE `hardware` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cpu_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cpu_core_number` int DEFAULT NULL,
  `network_speed` int DEFAULT NULL,
  `memory` int DEFAULT NULL,
  `disk` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `money` float(6,2) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of hardware
-- ----------------------------
BEGIN;
INSERT INTO `hardware` VALUES (2, '1', 2, 0, 0, 0, '2023-03-01 14:39:29', '2023-05-16 23:02:57', 0.00);
INSERT INTO `hardware` VALUES (3, '2', 2, 2, 2000, 2, '2023-05-07 22:34:17', '2023-05-16 23:02:59', 0.00);
INSERT INTO `hardware` VALUES (5, '20', 20, 20, 1600, 20, '2023-05-07 22:51:13', '2023-05-16 23:03:02', 0.00);
INSERT INTO `hardware` VALUES (6, '20', 20, 20, 20, 20, '2023-06-12 20:45:21', '2023-06-12 20:45:21', 0.00);
INSERT INTO `hardware` VALUES (7, '20', 20, 20, 20, 20, '2023-09-22 00:04:07', '2023-09-22 00:04:07', 0.00);
INSERT INTO `hardware` VALUES (8, '20', 20, 20, 20, 20, '2023-11-26 16:08:30', '2023-11-26 16:08:30', 0.00);
COMMIT;

CREATE TABLE if not exists `image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `repository` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `image_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `d_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `size` int DEFAULT NULL,
  `unit` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

CREATE TABLE if not exists image_pull_progress (
    id INT AUTO_INCREMENT PRIMARY KEY,
    image_id INT NOT NULL,
    progress INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (image_id) REFERENCES image(id)
);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `container_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name_o` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  `money` float(6,2) DEFAULT NULL,
  `packet_id` int NOT NULL,
  `pay_way` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `state` varchar(16) DEFAULT '未支付',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=111768 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------


-- ----------------------------
-- Table structure for packet
-- ----------------------------
DROP TABLE IF EXISTS `packet`;
CREATE TABLE `packet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name_p` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `hardware_id` int NOT NULL,
  `money` float(6,2) DEFAULT '0.00',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_p_h_hid` (`hardware_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of packet
-- ----------------------------
BEGIN;
INSERT INTO `packet` VALUES (3, '1', '1', '2023-03-01 14:51:55', '2023-03-01 14:51:55', 2, NULL);
INSERT INTO `packet` VALUES (4, '1', NULL, '2023-03-02 14:24:09', '2023-03-02 14:24:09', 2, NULL);
INSERT INTO `packet` VALUES (5, '基本套餐', '基本套餐', '2023-04-25 00:14:20', '2023-05-12 16:02:21', 3, NULL);
INSERT INTO `packet` VALUES (7, '1', '222', '2023-05-07 22:51:13', '2023-05-07 22:51:13', 0, NULL);
INSERT INTO `packet` VALUES (8, '1', NULL, '2023-06-12 20:45:21', '2023-03-02 14:24:09', 6, 0.00);
INSERT INTO `packet` VALUES (9, '1', NULL, '2023-09-22 00:04:08', '2023-03-02 14:24:09', 7, 0.00);
INSERT INTO `packet` VALUES (10, '1', NULL, '2023-11-26 16:08:31', '2023-03-02 14:24:09', 8, 0.00);
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, '权限', '2', '2023-03-01 14:07:15', '2023-03-01 14:07:49');
COMMIT;

-- ----------------------------
-- Table structure for permission_group
-- ----------------------------
DROP TABLE IF EXISTS `permission_group`;
CREATE TABLE `permission_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of permission_group
-- ----------------------------
BEGIN;
INSERT INTO `permission_group` VALUES (1, '111', '1', '2023-03-01 13:34:19', '2023-03-01 13:34:19');
INSERT INTO `permission_group` VALUES (3, '111', '1', '2023-03-01 13:55:35', '2023-03-01 13:55:35');
INSERT INTO `permission_group` VALUES (4, 'admin', '1', '2023-03-01 13:55:42', '2023-03-20 17:50:10');
COMMIT;

-- ----------------------------
-- Table structure for permission_group_permission
-- ----------------------------
DROP TABLE IF EXISTS `permission_group_permission`;
CREATE TABLE `permission_group_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `permission_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_pgp_pg_gid` (`group_id`) USING BTREE,
  KEY `fk_pgp_p_pid` (`permission_id`) USING BTREE,
  CONSTRAINT `fk_pgp_p_pid` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_pgp_pg_gid` FOREIGN KEY (`group_id`) REFERENCES `permission_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of permission_group_permission
-- ----------------------------
BEGIN;
INSERT INTO `permission_group_permission` VALUES (1, 1, 1, '2023-03-01 14:19:59', '2023-03-01 14:19:59');
INSERT INTO `permission_group_permission` VALUES (2, 3, 1, '2023-03-02 14:24:09', '2023-03-02 14:24:09');
COMMIT;

-- ----------------------------
-- Table structure for user_permission_group
-- ----------------------------
DROP TABLE IF EXISTS `user_permission_group`;
CREATE TABLE `user_permission_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_id` int NOT NULL,
  `user_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_ugp_pg_gid` (`group_id`) USING BTREE,
  KEY `fk_ugp_pg_uid` (`user_id`) USING BTREE,
  CONSTRAINT `fk_ugp_pg_gid` FOREIGN KEY (`group_id`) REFERENCES `permission_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_ugp_pg_uid` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_permission_group
-- ----------------------------
BEGIN;
INSERT INTO `user_permission_group` VALUES (1, 3, 1, '2023-03-01 13:56:29', '2023-03-02 14:31:27');
INSERT INTO `user_permission_group` VALUES (4, 4, 4, '2023-03-02 14:24:08', '2023-03-20 17:51:26');
INSERT INTO `user_permission_group` VALUES (5, 4, 1, '2023-06-11 21:17:11', '2023-06-11 21:17:11');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES (1, '1000', '1234', '2023-02-28 23:14:27', '2023-02-28 23:14:27', NULL);
INSERT INTO `users` VALUES (4, '1001', '1234', '2023-02-28 23:58:58', '2023-03-02 16:53:36', NULL);
INSERT INTO `users` VALUES (5, '1002', '1234', '2023-03-02 14:24:09', '2023-03-02 16:53:38', NULL);
INSERT INTO `users` VALUES (7, '10001', '1234', '2023-04-24 15:56:09', '2023-04-24 15:56:09', NULL);
INSERT INTO `users` VALUES (8, '6ae6588', 'a0fe231', '2023-06-12 19:45:00', '2023-06-12 19:45:00', NULL);
INSERT INTO `users` VALUES (9, '450f8b2', 'ee1f055', '2023-06-12 19:45:00', '2023-06-12 19:45:00', NULL);
INSERT INTO `users` VALUES (10, '49907ea', 'ae5c4a5', '2023-06-12 19:45:00', '2023-06-12 19:45:00', NULL);
INSERT INTO `users` VALUES (11, 'a28c945', '3a06a67', '2023-06-12 19:45:00', '2023-06-12 19:45:00', NULL);
INSERT INTO `users` VALUES (12, 'd01cd44', '49ee2df', '2023-06-12 19:45:00', '2023-06-12 19:45:00', NULL);
COMMIT;

-- ----------------------------
-- Table structure for wallet
-- ----------------------------
DROP TABLE IF EXISTS `wallet`;
CREATE TABLE `wallet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `balance` float(6,2) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('0', 13, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('11', 11, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('10', 10, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('9', 9, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('8', 8, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('7', 7, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('6', 6, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('5', 5, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('4', 4, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('3', 3, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('2', 2, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
REPLACE INTO `docker_cloud`.`wallet` (`id`, `user_id`, `created_at`, `updated_at`, `balance`) VALUES ('1', 1, '2024-02-25 08:28:18', '2024-02-25 08:28:18', 0.00);
