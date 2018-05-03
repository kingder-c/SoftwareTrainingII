/*
 Navicat Premium Data Transfer

 Source Server         : 上海
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : snowice.xin
 Source Database       : software_training

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : utf-8

 Date: 05/03/2018 16:40:28 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tbl_monitoring_point_info`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_monitoring_point_info`;
CREATE TABLE `tbl_monitoring_point_info` (
  `ID` int(50) NOT NULL AUTO_INCREMENT COMMENT '监测点ID',
  `MONITORINGNAME` varchar(50) DEFAULT NULL COMMENT '监测点名称',
  `LOCATION` varchar(50) DEFAULT NULL COMMENT '位置',
  `MONITORINGTYPE` varchar(50) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tbl_monitoring_value`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_monitoring_value`;
CREATE TABLE `tbl_monitoring_value` (
  `ID` int(50) NOT NULL AUTO_INCREMENT COMMENT '监测点ID',
  `MONITORINGVALUE` varchar(50) DEFAULT NULL COMMENT '检测值',
  `UPDATETIME` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
