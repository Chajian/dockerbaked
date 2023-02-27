/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : dockercloud

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 27/08/2022 17:17:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for container
-- ----------------------------
DROP TABLE IF EXISTS `container`;
CREATE TABLE `container`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of container
-- ----------------------------
INSERT INTO `container` VALUES ('6ed1c71a85ff025f533d4a896b905e63043712c58d675e64ad494751f5589f68', '1', '1', '2022-08-21 13:40:51');
INSERT INTO `container` VALUES ('821e720e4d60631f504e890d220a70d6f43a3b5af4d7f61462fe27d1022c2872', '1', '1', '2022-08-21 13:42:09');

-- ----------------------------
-- Table structure for containerorder
-- ----------------------------
DROP TABLE IF EXISTS `containerorder`;
CREATE TABLE `containerorder`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `start_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `end_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `container_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cpu` int(11) NOT NULL,
  `memory` int(11) NOT NULL,
  `network` int(11) NOT NULL,
  `disk` int(11) NOT NULL,
  `cost` float NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of containerorder
-- ----------------------------
INSERT INTO `containerorder` VALUES (1, 1, '2022-08-17 14:17:13', '0000-00-00 00:00:00', '123asd', 1, 0, 1, 1, 1);

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `author` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `craeted` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for imageorder
-- ----------------------------
DROP TABLE IF EXISTS `imageorder`;
CREATE TABLE `imageorder`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `image_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `dockerfile` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rose
-- ----------------------------
DROP TABLE IF EXISTS `rose`;
CREATE TABLE `rose`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rose
-- ----------------------------
INSERT INTO `rose` VALUES (1, '游客');
INSERT INTO `rose` VALUES (2, '用户');
INSERT INTO `rose` VALUES (3, '管理员');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pass` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` int(11) NULL DEFAULT 0,
  `created` timestamp(6) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '19240112', '123456', 99, NULL, 2);

-- ----------------------------
-- Table structure for userrose
-- ----------------------------
DROP TABLE IF EXISTS `userrose`;
CREATE TABLE `userrose`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `rose_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
