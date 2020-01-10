/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 8.0.17 : Database - createword
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`createword` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `createword`;

/*Table structure for table `captcha_info` */

DROP TABLE IF EXISTS `captcha_info`;

CREATE TABLE `captcha_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(128) NOT NULL,
  `captcha` varchar(64) NOT NULL COMMENT '验证码',
  `create_time` date NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `captcha_info` */

/*Table structure for table `sys_param` */

DROP TABLE IF EXISTS `sys_param`;

CREATE TABLE `sys_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `p_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'key',
  `p_value` varchar(500) NOT NULL COMMENT 'value',
  `create_user` varchar(100) NOT NULL COMMENT '创建/修改人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `p_describe` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_param` */

insert  into `sys_param`(`id`,`p_key`,`p_value`,`create_user`,`create_time`,`update_time`,`p_describe`) values 
(1,'tempFilePath','D:\\cword\\temp','admin','2019-08-01 22:37:12','2019-08-01 22:37:12','模板文件保存路径'),
(2,'tmpFilePath','d:\\cword\\tmp','admin','2019-08-04 08:00:00','2019-08-04 08:00:00','临时文件保存路径'),
(3,'deleteTmp','1','admin','2019-08-04 08:00:00','2019-08-04 08:00:00','文件保存成功后是否删除临时文件，1是，2否');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `login_name` varchar(200) NOT NULL COMMENT '登录名',
  `pass` varchar(200) NOT NULL COMMENT '密码',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `sex` int(2) NOT NULL DEFAULT '1' COMMENT '性别 1男、2女',
  `email` varbinary(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(40) DEFAULT NULL COMMENT '电话',
  `user_type` int(2) NOT NULL DEFAULT '2' COMMENT '用户类型 1管理、2普通',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态 1正常、2禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `token` varchar(200) DEFAULT NULL COMMENT 'token',
  `last_login` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`login_name`,`pass`,`name`,`sex`,`email`,`phone`,`user_type`,`status`,`create_time`,`update_time`,`token`,`last_login`) values 
(1,'admin','e10adc3949ba59abbe56e057f20f883e','admin',0,NULL,NULL,1,1,'2019-09-09 11:03:00','2019-09-09 11:03:02','eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjYW9ydWkiLCJleHAiOjE1NzA2OTM2NjU3NTksInVzZXJJZCI6MSwiaWF0IjoxNTcwNjkwMDY1NzU5fQ.dzIwEcRrgWr9mnn1F73o3WgEyzxTOVkQCQggTdaXxgc','2019-10-10 14:47:46');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
