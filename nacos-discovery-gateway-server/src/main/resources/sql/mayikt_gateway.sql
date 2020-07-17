/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 17/07/2020 11:15:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mayikt_gateway
-- ----------------------------
DROP TABLE IF EXISTS `mayikt_gateway`;
CREATE TABLE `mayikt_gateway`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `route_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `route_pattern` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `route_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `route_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mayikt_gateway
-- ----------------------------
INSERT INTO `mayikt_gateway` VALUES (1, 'test1', 'nacos-discovery-provider', '/provider/**', '0', 'nacos-discovery-provider');

SET FOREIGN_KEY_CHECKS = 1;
