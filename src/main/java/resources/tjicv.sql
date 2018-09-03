/*
Navicat MySQL Data Transfer

Source Server         : hkx
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : tjicv

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2018-09-03 16:57:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_carhistory
-- ----------------------------
DROP TABLE IF EXISTS `tb_carhistory`;
CREATE TABLE `tb_carhistory` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `devPhone` varchar(255) NOT NULL,
  `locateState` varchar(255) DEFAULT NULL,
  `gpsPosX` float(255,0) DEFAULT NULL,
  `gpsPosY` float(255,0) DEFAULT NULL,
  `gpsSpeed` double(255,0) DEFAULT NULL,
  `gpsDirect` int(255) DEFAULT NULL,
  `gpsMileage` float(255,0) DEFAULT NULL,
  `canSpeed` double(255,0) DEFAULT NULL,
  `Height` float(255,0) DEFAULT NULL,
  `drivemode` int(2) DEFAULT NULL,
  `engine` int(255) DEFAULT NULL,
  `acceleratorPedal` int(255) DEFAULT NULL,
  `atmosphericPressure` int(255) DEFAULT NULL,
  `brakePedal` int(255) DEFAULT NULL,
  `tempreture` int(255) DEFAULT NULL,
  `steeringwheelAngle` int(255) DEFAULT NULL,
  `videostate` int(2) DEFAULT NULL,
  `reviceDatetime` datetime DEFAULT NULL,
  `sendDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_carhistory
-- ----------------------------

-- ----------------------------
-- Table structure for tb_carrunntime
-- ----------------------------
DROP TABLE IF EXISTS `tb_carrunntime`;
CREATE TABLE `tb_carrunntime` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `devPhone` varchar(255) NOT NULL,
  `locateState` varchar(255) DEFAULT NULL,
  `gpsPosX` float(255,0) DEFAULT NULL,
  `gpsPosY` float(255,0) DEFAULT NULL,
  `gpsSpeed` double(255,0) DEFAULT NULL,
  `gpsDirect` int(255) DEFAULT NULL,
  `gpsMileage` float(255,0) DEFAULT NULL,
  `canSpeed` double(255,0) DEFAULT NULL,
  `Height` float(255,0) DEFAULT NULL,
  `drivemode` int(2) DEFAULT NULL,
  `engine` int(255) DEFAULT NULL,
  `acceleratorPedal` int(255) DEFAULT NULL,
  `atmosphericPressure` int(255) DEFAULT NULL,
  `brakePedal` int(255) DEFAULT NULL,
  `tempreture` int(255) DEFAULT NULL,
  `steeringwheelAngle` int(255) DEFAULT NULL,
  `videostate` int(2) DEFAULT NULL,
  `reviceDatetime` datetime DEFAULT NULL,
  `sendDatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_carrunntime
-- ----------------------------
