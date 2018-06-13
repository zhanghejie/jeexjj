-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.10


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema xjj
--

CREATE DATABASE IF NOT EXISTS xjj;
USE xjj;

--
-- Definition of table `t_sec_menu`
--

DROP TABLE IF EXISTS `t_sec_menu`;
CREATE TABLE `t_sec_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `PARENT_ID` bigint(20) DEFAULT NULL,
  `PRIVILEGE_CODE` varchar(200) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `order_sn` int(11) DEFAULT NULL,
  `ICON` varchar(100) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `code` varchar(16) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique_code` (`code`),
  KEY `FK_psv09uu72jp7gwbch9sgm0pm6` (`PARENT_ID`),
  CONSTRAINT `FK_psv09uu72jp7gwbch9sgm0pm6` FOREIGN KEY (`PARENT_ID`) REFERENCES `t_sec_menu` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8 COMMENT='菜单';

--
-- Dumping data for table `t_sec_menu`
--

/*!40000 ALTER TABLE `t_sec_menu` DISABLE KEYS */;
INSERT INTO `t_sec_menu` (`ID`,`TITLE`,`DESCRIPTION`,`PARENT_ID`,`PRIVILEGE_CODE`,`URL`,`order_sn`,`ICON`,`status`,`code`) VALUES 
 (153,'权限管理','权限管理',NULL,'',NULL,2,'users','valid','02'),
 (155,'系统管理','系统管理',NULL,'',NULL,1,'desktop','valid','01'),
 (156,'字典管理','字典管理',155,'sys_dict','/sys/dict/index',1,NULL,'valid','0101'),
 (157,'代码生成','代码生成',155,'sys_code','/sys/code/index',2,NULL,'valid','0102'),
 (158,'管理员管理','管理员管理',153,'sec_manager','/sec/manager/index',NULL,NULL,'valid','0202'),
 (159,'用户管理','用户管理',153,'sec_user','/sec/user/index',NULL,NULL,'valid','0203'),
 (161,'菜单管理','菜单管理',153,'sec_menu','/sec/menu/index',NULL,NULL,'valid','0204'),
 (162,'角色管理','菜单管理',153,'sec_role','/sec/role/index',NULL,NULL,'valid','0201'),
 (163,'文件管理','文件管理',155,'sys_xfile','/sys/xfile/index',NULL,NULL,'valid','0103');
/*!40000 ALTER TABLE `t_sec_menu` ENABLE KEYS */;


--
-- Definition of table `t_sec_role`
--

DROP TABLE IF EXISTS `t_sec_role`;
CREATE TABLE `t_sec_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(200) DEFAULT NULL COMMENT '名称',
  `CODE` varchar(200) DEFAULT NULL COMMENT '编码',
  `DESCRIPTION` varchar(2000) DEFAULT NULL COMMENT '描述',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uni_code` (`CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色';

--
-- Dumping data for table `t_sec_role`
--

/*!40000 ALTER TABLE `t_sec_role` DISABLE KEYS */;
INSERT INTO `t_sec_role` (`ID`,`TITLE`,`CODE`,`DESCRIPTION`,`status`) VALUES 
 (4,'管理员','admin','管理员','valid'),
 (5,'录入员','lulu','录入员','valid'),
 (6,'教师','teacher','教师','valid'),
 (7,'班主任','banzhren','班主任','valid');
/*!40000 ALTER TABLE `t_sec_role` ENABLE KEYS */;


--
-- Definition of table `t_sec_role_privilege`
--

DROP TABLE IF EXISTS `t_sec_role_privilege`;
CREATE TABLE `t_sec_role_privilege` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  `PRIVILEGE_TITLE` varchar(200) DEFAULT NULL,
  `PRIVILEGE_CODE` varchar(100) DEFAULT NULL,
  `FUNCTION_LIST` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_37slprxb3e2ygav598e3yeawt` (`ROLE_ID`),
  CONSTRAINT `FK_37slprxb3e2ygav598e3yeawt` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_sec_role` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_sec_role_privilege`
--

/*!40000 ALTER TABLE `t_sec_role_privilege` DISABLE KEYS */;
INSERT INTO `t_sec_role_privilege` (`ID`,`ROLE_ID`,`PRIVILEGE_TITLE`,`PRIVILEGE_CODE`,`FUNCTION_LIST`) VALUES 
 (14,4,'代码生成管理','sys_code','generate'),
 (15,4,'菜单管理','sec_menu','edit|delete|create|list'),
 (20,4,'字典管理','sys_dict','edit|delete|create|list'),
 (26,4,'管理员管理','sec_manager','delete|create|list|edit'),
 (27,4,'角色管理','sec_role','edit|delete|create|list'),
 (28,4,'用户管理','sec_user','edit|delete|create|list'),
 (29,5,'菜单管理','sec_menu','edit|delete|create|list'),
 (30,6,'角色管理','sec_role','edit|delete|create|list'),
 (31,6,'文件管理','sys_xfile','edit|delete|create|list'),
 (32,6,'用户管理','sec_user','edit|deletelist');
/*!40000 ALTER TABLE `t_sec_role_privilege` ENABLE KEYS */;


--
-- Definition of table `t_sec_user`
--

DROP TABLE IF EXISTS `t_sec_user`;
CREATE TABLE `t_sec_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `user_type` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `birthday` datetime DEFAULT NULL,
  `province` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_login_name` (`login_name`) USING BTREE,
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='用户';

--
-- Dumping data for table `t_sec_user`
--

/*!40000 ALTER TABLE `t_sec_user` DISABLE KEYS */;
INSERT INTO `t_sec_user` (`id`,`login_name`,`password`,`user_name`,`user_type`,`email`,`mobile`,`address`,`create_date`,`status`,`birthday`,`province`) VALUES 
 (52,'admin','e10adc3949ba59abbe56e057f20f883e','系统管理员','admin','status','12345678911',NULL,'2018-04-19 13:30:57','valid','2018-04-19 00:00:00',NULL),
 (54,'zhanghejie','e10adc3949ba59abbe56e057f20f883e','张合杰','user','','13366442927',NULL,'2018-04-27 16:16:38','valid','2013-03-07 00:00:00',NULL),
 (58,'tutor','e10adc3949ba59abbe56e057f20f883e','教师','admin','jlsdzhj@126.com','13366442928',NULL,'2018-05-03 17:40:45','valid','2018-05-03 00:00:00',NULL),
 (74,'xjjv92s2018-05-07 14:42:55','e10adc3949ba59abbe56e057f20f883e','张三1','user','zhangsan@126.com','13966442927',NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (75,'xjj7w7R2018-05-07 14:42:55','e10adc3949ba59abbe56e057f20f883e','张三2','user','zhangsan@127.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (76,'test3','e10adc3949ba59abbe56e057f20f883e','张三3','user','zhangsan@128.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (77,'test4','e10adc3949ba59abbe56e057f20f883e','张三4','user','zhangsan@129.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (78,'test5','e10adc3949ba59abbe56e057f20f883e','张三5','user','zhangsan@130.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (79,'test6','e10adc3949ba59abbe56e057f20f883e','张三6','user','zhangsan@131.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (80,'test7','e10adc3949ba59abbe56e057f20f883e','张三7','user','zhangsan@132.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (81,'test8','e10adc3949ba59abbe56e057f20f883e','张三8','user','zhangsan@133.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (82,'test9','e10adc3949ba59abbe56e057f20f883e','张三9','user','zhangsan@134.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (83,'test10','e10adc3949ba59abbe56e057f20f883e','张三10','user','zhangsan@135.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (84,'test11','e10adc3949ba59abbe56e057f20f883e','张三11','user','zhangsan@136.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (85,'test12','e10adc3949ba59abbe56e057f20f883e','张三12','user','zhangsan@137.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (86,'test13','e10adc3949ba59abbe56e057f20f883e','张三13','user','zhangsan@138.com',NULL,NULL,'2018-05-07 14:42:55','valid',NULL,NULL),
 (87,'bbt','e10adc3949ba59abbe56e057f20f883e','bbt','user','bbt','bbt',NULL,'2018-05-08 14:29:20','valid','2018-05-08 00:00:00',NULL),
 (88,'shandongren','e10adc3949ba59abbe56e057f20f883e','shandongren','user','shandongren','shandongren',NULL,'2018-05-08 14:50:41','valid','2018-05-08 00:00:00','henan');
/*!40000 ALTER TABLE `t_sec_user` ENABLE KEYS */;


--
-- Definition of table `t_sec_user_role`
--

DROP TABLE IF EXISTS `t_sec_user_role`;
CREATE TABLE `t_sec_user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色';

--
-- Dumping data for table `t_sec_user_role`
--

/*!40000 ALTER TABLE `t_sec_user_role` DISABLE KEYS */;
INSERT INTO `t_sec_user_role` (`ID`,`USER_ID`,`ROLE_ID`) VALUES 
 (1,58,6);
/*!40000 ALTER TABLE `t_sec_user_role` ENABLE KEYS */;


--
-- Definition of table `t_sys_dict`
--

DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `group_code` varchar(16) NOT NULL,
  `name` varchar(45) NOT NULL,
  `code` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `detail` varchar(512) DEFAULT NULL,
  `sn` int(8) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='字典';

--
-- Dumping data for table `t_sys_dict`
--

/*!40000 ALTER TABLE `t_sys_dict` DISABLE KEYS */;
INSERT INTO `t_sys_dict` (`id`,`group_code`,`name`,`code`,`status`,`detail`,`sn`) VALUES 
 (5,'gender','男','man','invalid',NULL,NULL),
 (6,'gender','女','woman','valid',NULL,NULL),
 (7,'province','山东','shandong','valid',NULL,1),
 (8,'province','河南','henan','valid',NULL,2),
 (9,'province','河北','hebei','valid',NULL,3);
/*!40000 ALTER TABLE `t_sys_dict` ENABLE KEYS */;


--
-- Definition of table `t_sys_xfile`
--

DROP TABLE IF EXISTS `t_sys_xfile`;
CREATE TABLE `t_sys_xfile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_realname` varchar(200) NOT NULL,
  `file_path` varchar(200) NOT NULL,
  `file_title` varchar(200) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `file_size` bigint(16) DEFAULT NULL,
  `user_id` bigint(16) DEFAULT NULL,
  `extension_name` varchar(16) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `is_deleted` char(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `t_sys_xfile`
--

/*!40000 ALTER TABLE `t_sys_xfile` DISABLE KEYS */;
INSERT INTO `t_sys_xfile` (`id`,`file_realname`,`file_path`,`file_title`,`url`,`file_size`,`user_id`,`extension_name`,`create_date`,`is_deleted`) VALUES 
/*!40000 ALTER TABLE `t_sys_xfile` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
