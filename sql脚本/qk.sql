-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: qk
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS qk;

USE qk;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id, 主键',
  `channel` tinyint unsigned NOT NULL COMMENT '渠道来源, 1:线上活动, 2:推广介绍',
  `name` varchar(20) NOT NULL COMMENT '活动名称',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `description` varchar(100) NOT NULL COMMENT '活动简介',
  `type` tinyint unsigned NOT NULL COMMENT '活动类型, 1:课程折扣, 2:代金券',
  `discount` double(3,1) DEFAULT NULL COMMENT '课程折扣',
  `voucher` int unsigned DEFAULT NULL COMMENT '代金券金额（元）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COMMENT='活动表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,1,'618-AI训练营折扣','2025-05-31 16:00:00','2025-06-30 15:59:59','618-AI训练营折扣',1,8.5,NULL,'2025-05-14 17:49:58','2025-05-14 17:49:58'),(2,2,'B站推广介绍','2025-05-19 16:00:00','2025-06-05 15:59:59','B站推广介绍-全新课程升级',2,NULL,300,'2025-05-15 10:51:23','2025-05-15 10:51:23'),(3,1,'测试活动1','2025-06-01 00:00:00','2025-06-30 23:59:59','描述1',1,7.5,200,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(4,2,'测试活动2','2025-07-01 00:00:00','2025-07-31 23:59:59','描述2',2,NULL,250,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(5,1,'测试活动3','2025-08-01 00:00:00','2025-08-31 23:59:59','描述3',1,9.0,NULL,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(6,2,'测试活动4','2025-09-01 00:00:00','2025-09-30 23:59:59','描述4',2,6.5,150,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(7,1,'测试活动5','2025-10-01 00:00:00','2025-10-31 23:59:59','描述5',1,8.0,300,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(8,2,'测试活动6','2025-11-01 00:00:00','2025-11-30 23:59:59','描述6',2,NULL,100,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(9,1,'测试活动7','2025-12-01 00:00:00','2025-12-31 23:59:59','描述7',1,7.0,NULL,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(10,2,'测试活动8','2026-01-01 00:00:00','2026-01-31 23:59:59','描述8',2,8.5,200,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(11,1,'测试活动9','2026-02-01 00:00:00','2026-02-28 23:59:59','描述9',1,9.5,250,'2025-05-15 11:09:04','2025-05-15 11:09:04'),(12,2,'暑期超值优惠活动','2025-06-24 16:00:00','2025-07-25 15:59:59','暑期超值优惠活动, 主要是针对于暑期大学生',2,NULL,800,'2025-06-22 14:47:34','2025-07-20 17:58:18');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business`
--

DROP TABLE IF EXISTS `business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '线索id, 主键',
  `name` varchar(20) DEFAULT NULL COMMENT '客户姓名',
  `phone` char(11) NOT NULL COMMENT '手机号',
  `gender` tinyint unsigned DEFAULT NULL COMMENT '性别，1:男, 2:女',
  `age` tinyint unsigned DEFAULT NULL COMMENT '年龄',
  `wechat` varchar(30) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(15) DEFAULT NULL COMMENT 'qq号',
  `subject` tinyint unsigned DEFAULT NULL COMMENT '意向学科，1:ai智能应用开发(java), 2:ai大模型开发(python)，3:ai鸿蒙开发，4:ai大数据，5:ai嵌入式，6:ai测试，7:ai运维',
  `course_id` int unsigned DEFAULT NULL COMMENT '意向课程, 课程id',
  `degree` tinyint unsigned DEFAULT NULL COMMENT '学历, 1:高中、2:中专、3:大专、4:本科、5:硕士、6:博士、7:其他',
  `job_status` tinyint unsigned DEFAULT NULL COMMENT '在职情况, 1: 在职, 0: 离职',
  `channel` tinyint unsigned NOT NULL COMMENT '渠道来源，1:线上活动, 2:推广介绍',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '商机状态，1:待分配, 2:待跟进, 3:跟进中, 4:回收, 5:转客户',
  `user_id` int unsigned DEFAULT NULL COMMENT '归属人id，关联用户id',
  `clue_id` int unsigned DEFAULT NULL COMMENT '归属线索id',
  `next_time` datetime DEFAULT NULL COMMENT '下次跟进时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb3 COMMENT='商机表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business`
--

LOCK TABLES `business` WRITE;
/*!40000 ALTER TABLE `business` DISABLE KEYS */;
INSERT INTO `business` VALUES (1,'张岱','13398980102',1,22,'wx13728785434','234567543',1,NULL,NULL,NULL,1,NULL,2,8,10,NULL,'2026-02-03 18:25:26','2026-02-04 15:49:46'),(27,'小五','17713492901',1,22,'jsdhfsf2324','3456789432',1,2,4,2,1,'无',3,1,NULL,'2025-03-20 10:10:10','2025-06-10 21:04:18','2025-06-21 17:41:48'),(28,'小六子','17723692901',1,22,'jsdhfsf2324','3456789432',1,3,4,2,1,'无',2,12,NULL,NULL,'2025-06-10 21:04:18','2026-02-04 15:50:35'),(29,'伍六一','17763292901',1,22,'jsdhfsf2324','3456789432',1,4,4,2,1,'无',2,12,NULL,NULL,'2025-06-10 21:04:18','2026-02-04 15:50:38'),(30,'张思','17738992901',2,19,'jsdhfsf2324','3456789432',1,8,4,2,1,'无',2,12,NULL,NULL,'2025-06-10 21:04:18','2026-02-04 15:50:40'),(31,'齐白','17751092901',1,22,'jsdhfsf2324','3456789432',1,9,4,2,1,'无',2,8,NULL,NULL,'2025-06-10 21:04:18','2026-02-04 15:50:45'),(32,'奚梦','17709092901',1,22,'jsdhfsf2324','3456789432',1,10,4,2,1,'无',2,12,NULL,NULL,'2025-06-10 21:04:18','2026-02-04 15:50:48'),(33,'钱四','13688889991',1,21,'wxdjjd92922','2345643236',2,1,4,2,1,'B站关注的小伙伴儿',2,12,4,NULL,'2025-06-10 21:15:03','2026-02-04 15:50:22'),(34,'王原大','13390901245',1,23,'wx237847834','234567865',1,11,4,2,1,'',2,8,NULL,NULL,'2025-06-21 17:26:34','2026-02-04 15:50:16'),(35,'林婉儿','15816382891',2,20,'linwaner112','1234567543',1,12,4,1,2,'学习RAG相关技术，转型AI',2,8,NULL,NULL,'2025-06-21 17:27:41','2026-02-04 15:50:53'),(36,'吴十','13800138008',2,21,'wushi1010','123789456',3,6,4,0,1,'产品经理课程',5,8,NULL,NULL,'2025-03-26 11:05:33','2026-02-04 17:45:36'),(37,'郑十一','13800138009',1,29,'zhengsy11','456789123',2,5,3,1,2,'UI设计学习',2,12,NULL,NULL,'2025-03-21 15:20:10','2026-02-04 15:50:25'),(38,'王芳','13800138010',2,25,'wangfang12','789123456',1,2,4,1,1,'Java进阶课程',2,12,NULL,NULL,'2025-03-20 10:10:10','2026-02-04 15:50:29'),(39,'李明','13800138011',1,24,'liming123','321456789',2,7,3,0,1,'前端框架学习',2,12,NULL,NULL,'2025-03-19 14:25:40','2026-02-04 15:50:32'),(40,'张伟','13800138012',1,26,'zhangwei456','654321987',1,4,4,1,2,'机器学习方向',2,8,NULL,NULL,'2025-03-18 16:30:15','2026-02-04 15:49:57'),(41,'刘洋','13800138013',2,23,'liuyang789','987654123',2,1,4,1,1,'数据分析师方向',2,12,NULL,NULL,'2025-03-25 13:20:45','2026-02-04 15:55:52'),(42,'陈晨','13800138014',1,27,'chenchen14','123456987',1,4,3,2,2,'Python自动化',2,8,NULL,NULL,'2025-03-17 11:15:30','2026-02-04 15:50:04'),(43,'杨光','13800138015',2,22,'yangguang15','456987321',3,6,2,1,1,'软件测试入门',2,8,NULL,NULL,'2025-03-16 09:40:25','2026-02-04 15:50:09'),(44,'周杰','13800138016',1,28,'zhoujie16','789321654',1,13,4,2,2,'短视频运营',2,8,NULL,NULL,'2025-03-15 14:50:10','2026-02-04 15:50:01'),(45,'吴倩','13800138017',2,24,'wuqian17','321789456',3,6,4,2,1,'产品设计咨询',4,8,NULL,'2026-02-12 00:00:00','2025-03-24 15:10:20','2026-02-04 17:37:34'),(46,'郑凯','13800138018',1,26,'zhengkai18','654123987',2,7,3,1,2,'UI交互设计',2,8,NULL,NULL,'2025-03-14 10:30:45','2026-02-04 15:50:06'),(47,'王磊','13800138019',1,25,'wanglei19','987123654',1,2,4,1,1,'Java架构师',2,8,NULL,NULL,'2025-03-13 13:20:15','2026-02-04 15:49:51'),(48,'李娜','13800138020',2,23,'lina20','123987456',2,2,3,0,2,'前端全栈开发',2,8,NULL,NULL,'2025-03-12 16:40:30','2026-02-04 15:49:54'),(49,'承娟','13909018929',27,19,'cj2839232323','2595964758',1,3,4,2,2,'无',2,12,NULL,NULL,'2025-06-21 20:07:27','2026-02-04 15:50:20'),(50,'齐欧式','13511110000',1,30,'wxqi299232','2435676543',2,7,NULL,NULL,2,NULL,2,8,NULL,NULL,'2025-06-22 14:52:08','2026-02-04 15:50:12'),(51,'张岱山','13398980103',1,22,'wx13728785434','234567543',1,2,NULL,NULL,1,NULL,2,8,NULL,NULL,'2025-07-20 20:59:46','2026-02-04 15:49:49'),(52,'zhangyida','13812341234',1,22,'z123456','123456',1,2,4,2,1,'想要技术提升',1,NULL,NULL,NULL,'2026-02-04 16:03:55','2026-02-04 16:03:55');
/*!40000 ALTER TABLE `business` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business_track_record`
--

DROP TABLE IF EXISTS `business_track_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_track_record` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '跟进记录id, 主键',
  `business_id` int unsigned NOT NULL COMMENT '商机id，关联商机id',
  `user_id` int unsigned NOT NULL COMMENT '跟进人id，关联用户id',
  `track_status` tinyint unsigned NOT NULL COMMENT '跟进状态, 1:接通, 2:拒绝, 3:无人接听',
  `key_items` varchar(50) DEFAULT NULL COMMENT '沟通重点',
  `next_time` datetime DEFAULT NULL COMMENT '下次跟进时间',
  `record` varchar(50) DEFAULT NULL COMMENT '沟通纪要',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='商机跟进记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business_track_record`
--

LOCK TABLES `business_track_record` WRITE;
/*!40000 ALTER TABLE `business_track_record` DISABLE KEYS */;
INSERT INTO `business_track_record` VALUES (1,45,8,1,'[位置, 价格, 时间]','2026-02-12 00:00:00','关心开班时间','2026-02-04 16:51:01'),(3,45,8,2,NULL,NULL,NULL,'2026-02-04 17:37:34');
/*!40000 ALTER TABLE `business_track_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clue`
--

DROP TABLE IF EXISTS `clue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clue` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '线索ID, 主键',
  `phone` char(11) NOT NULL COMMENT '手机号',
  `channel` tinyint unsigned NOT NULL COMMENT '渠道来源，1:线上活动, 2:推广介绍',
  `activity_id` int unsigned DEFAULT NULL COMMENT '活动信息，关联活动的ID',
  `name` varchar(20) DEFAULT NULL COMMENT '客户姓名',
  `gender` tinyint unsigned DEFAULT NULL COMMENT '性别，1:男, 2:女',
  `age` tinyint unsigned DEFAULT NULL COMMENT '年龄',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ号',
  `user_id` int unsigned DEFAULT NULL COMMENT '归属人ID，关联用户ID',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '线索状态，1:待分配, 2:待跟进, 3:跟进中, 4:伪线索, 5:转为商机',
  `subject` tinyint unsigned DEFAULT NULL COMMENT '意向学科，1:AI智能应用开发(Java), 2:AI大模型开发(Python)，3:AI鸿蒙开发，4:AI大数据，5:AI嵌入式，6:AI测试，7:AI运维',
  `level` tinyint unsigned DEFAULT NULL COMMENT '意向等级, 1:近期学习、2:打算学习(考虑中)、3:进行了解、4:打酱油',
  `next_time` datetime DEFAULT NULL COMMENT '下次跟进时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COMMENT='线索表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clue`
--

LOCK TABLES `clue` WRITE;
/*!40000 ALTER TABLE `clue` DISABLE KEYS */;
INSERT INTO `clue` VALUES (1,'14700000001',1,3,'张思',1,23,'wx2324342323','3434343423',2,2,NULL,4,'2025-06-25 10:00:00','2025-05-24 17:13:50','2026-02-04 10:44:10'),(2,'13589890912',2,2,'李久阶',1,22,'wx28392839','2323232323',9,2,1,NULL,NULL,'2025-05-24 17:16:03','2026-02-04 18:30:03'),(3,'13909120913',1,3,'李迪',1,22,'wx17327323','2456754323',4,2,1,NULL,NULL,'2025-05-28 11:54:02','2026-02-03 18:14:33'),(4,'13688889991',2,2,'钱四',1,21,'wxdjjd92922','2345643236',7,2,1,NULL,NULL,'2025-05-28 14:16:55','2026-02-04 18:30:07'),(5,'15509091231',2,4,'欧斯卡',2,22,'wx283232423','23456789657',2,2,NULL,NULL,NULL,'2025-05-28 19:41:46','2026-02-04 18:30:21'),(6,'17709092901',2,10,'奚梦',1,22,'jsdhfsf2324','3456789432',11,2,1,NULL,NULL,'2025-05-28 19:44:15','2026-02-04 18:30:13'),(7,'13589898881',1,11,'伊斯科',1,19,'wx8943895345','34343232536',13,2,2,NULL,NULL,'2025-05-28 19:45:06','2026-02-04 18:30:18'),(8,'13511110000',1,13,'齐欧式',1,30,'wxqi299232','2435676543',6,2,2,NULL,NULL,'2025-05-29 10:45:07','2026-02-04 18:29:56'),(9,'13511110001',2,2,'李秋菊',2,22,'liqiuju23874','234567865',6,2,1,NULL,NULL,'2025-05-29 10:47:22','2026-02-04 18:30:00'),(10,'13398980102',2,2,'张岱',1,22,'wx13728785434','234567543',2,5,1,1,'2026-02-12 00:00:00','2025-06-16 15:31:22','2026-02-03 18:25:26'),(11,'15508761231',1,1,'卫丹',2,24,'wxweidan1212','8450313640',2,2,NULL,NULL,NULL,'2025-07-16 18:46:10','2026-02-04 09:56:21'),(12,'17792098192',1,3,'张吉',1,21,'wx289483544','245676856',4,2,NULL,NULL,NULL,'2025-07-20 15:17:56','2026-02-04 18:29:52'),(13,'13612341234',1,3,'aaa',1,12,'zxy1231123234','123456',2,4,NULL,4,NULL,'2026-02-03 19:34:12','2026-02-04 09:56:51'),(14,'13512341234',1,3,'张伟',1,22,'a1234567','1234567',2,2,NULL,NULL,NULL,'2026-02-04 10:48:35','2026-02-04 18:29:49');
/*!40000 ALTER TABLE `clue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clue_track_record`
--

DROP TABLE IF EXISTS `clue_track_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clue_track_record` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '跟进记录ID, 主键',
  `clue_id` int unsigned NOT NULL COMMENT '线索ID，关联线索ID',
  `user_id` int unsigned NOT NULL COMMENT '跟进人ID，关联用户ID',
  `subject` tinyint unsigned DEFAULT NULL COMMENT '意向学科，1:AI智能应用开发(Java), 2:AI大模型开发(Python)，3:AI鸿蒙开发，4:AI大数据，5:AI嵌入式，6:AI测试，7:AI运维',
  `level` tinyint unsigned DEFAULT NULL COMMENT '意向等级, 1:近期学习、2:打算学习(考虑中)、3:进行了解、4:打酱油',
  `record` varchar(100) DEFAULT NULL COMMENT '跟进记录',
  `next_time` datetime DEFAULT NULL COMMENT '下次跟进时间',
  `type` tinyint unsigned DEFAULT NULL COMMENT '跟进类型, 1:正常跟进、0:伪线索',
  `false_reason` tinyint unsigned DEFAULT NULL COMMENT '伪线索原因, 1:空号、2:停机、3:竞品、4:无法联系、5:其他',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='线索跟进记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clue_track_record`
--

LOCK TABLES `clue_track_record` WRITE;
/*!40000 ALTER TABLE `clue_track_record` DISABLE KEYS */;
INSERT INTO `clue_track_record` VALUES (1,1,2,1,1,'无','2025-05-29 10:00:00',1,NULL,'2025-05-29 10:25:00'),(2,1,2,1,1,'有意向,目前大三','2025-05-30 10:00:00',1,NULL,'2025-05-29 10:25:00'),(3,10,1,1,1,'对AI智能应用开发不了解，已解释，能正常参与线下学习','2026-02-12 00:00:00',1,NULL,'2026-02-03 17:23:20'),(6,10,2,1,1,'确定能来','2026-02-12 00:00:00',1,NULL,'2026-02-03 17:51:03'),(7,13,2,NULL,4,'联系不上',NULL,0,4,'2026-02-04 09:56:51');
/*!40000 ALTER TABLE `clue_track_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '课程id, 主键',
  `subject` tinyint unsigned NOT NULL COMMENT '课程学科，1:AI智能应用开发(Java), 2:AI大模型开发(Python)，3:AI鸿蒙开发，4:AI大数据，5:AI嵌入式，6:AI测试，7:AI运维',
  `name` varchar(20) NOT NULL COMMENT '课程名称',
  `price` int unsigned NOT NULL COMMENT '课程价格（元）',
  `target` tinyint unsigned NOT NULL COMMENT '适用人群, 1:小白学员, 2:中级程序员',
  `description` varchar(100) DEFAULT NULL COMMENT '课程介绍',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COMMENT='课程表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,2,'Python核心与AI开发基础',299,1,'主要讲解Python核心与AI开发基础','2025-05-09 21:30:00','2025-07-16 15:04:07'),(2,1,'AI驱动Web开发',299,2,'AI驱动Web开发， 主要讲解Web开发的核心知识及Web项目的设计、开发、测试、部署','2025-05-22 11:01:40','2025-07-16 15:04:07'),(3,1,'企业级物联网项目',4599,2,'企业级物联网项目，主要讲解物联网项目的设计、开发、测试、部署全流程交付','2025-05-22 11:02:44','2025-05-22 11:02:44'),(4,1,'SpringAI大模型应用开发',2089,3,'SpringAI大模型应用开发','2025-05-22 11:08:26','2025-05-22 11:08:26'),(5,2,'数据分析',2000,2,'基于Python数据分析的','2025-05-22 21:44:49','2025-05-22 21:45:50'),(6,3,'前端基础',28,1,'前端基础，主要讲解HTML、CSS、JS等前端开发基础知识','2025-05-23 20:07:47','2025-05-23 20:07:47'),(7,2,'LangChain入门',1800,2,'LangChain入门，学习该框架如何操作AI大模型','2025-05-26 21:43:57','2025-05-26 21:44:26'),(8,1,'LangChain4j',299,2,'LangChain4j从入门到进阶, 适合AI初学者','2025-06-19 19:03:17','2025-06-19 19:03:17'),(9,1,'RAG增强检索',399,2,'RAG','2025-06-19 19:04:24','2025-06-19 19:04:24'),(10,1,'SpringCloud微服务框架',1200,2,'SpringCloud微服务框架及分布式解决方案','2025-06-22 14:41:16','2025-06-22 14:41:16'),(11,1,'微服务智能项目集',3899,2,'微服务智能项目集','2025-06-22 14:44:06','2025-06-22 14:44:06'),(12,1,'AI智能体项目-天机AI助理',4999,2,'AI智能体项目-天机AI助理','2025-06-22 14:44:31','2025-06-22 14:44:31'),(13,1,'Tool Calling实战',199,2,'Tool Calling实战讲解','2025-07-16 11:38:33','2025-07-16 11:38:33');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id, 主键',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `name` varchar(20) NOT NULL COMMENT '客户姓名',
  `channel` tinyint unsigned DEFAULT NULL COMMENT '渠道来源，1:线上活动, 2:推广介绍',
  `gender` tinyint unsigned DEFAULT NULL COMMENT '性别，1:男, 2:女',
  `age` tinyint unsigned DEFAULT NULL COMMENT '年龄',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq号',
  `degree` tinyint unsigned DEFAULT NULL COMMENT '学历, 1:高中、2:中专、3:大专、4:本科、5:硕士、6:博士、7:其他',
  `job_status` tinyint unsigned DEFAULT NULL COMMENT '在职情况, 1: 在职, 0: 离职',
  `subject` tinyint unsigned DEFAULT NULL COMMENT '意向学科，1:ai智能应用开发(java), 2:ai大模型开发(python)，3:ai鸿蒙开发，4:ai大数据，5:ai嵌入式，6:ai测试，7:ai运维',
  `course_id` int unsigned DEFAULT NULL COMMENT '课程id',
  `business_id` int unsigned DEFAULT NULL COMMENT '商机id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COMMENT='客户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'13688889991','钱四',1,1,21,'wxdjjd92922','2345643236',4,2,2,9,7,'2025-06-21 18:16:35','2025-06-21 18:16:35'),(2,'13800138002','李四',2,2,22,'lisi456','987654321',3,0,2,2,11,'2025-06-21 20:19:59','2025-06-21 20:19:59'),(3,'13800138005','钱七',2,1,26,'qianqi777','321654987',3,1,2,2,14,'2025-06-21 21:27:25','2025-06-21 21:27:25'),(4,'13800138010','王芳',1,2,25,'wangfang12','789123456',4,1,1,1,19,'2025-06-21 18:06:45','2025-06-21 18:31:20'),(5,'15501101213','邓光明',1,1,22,'wx28392483','4532456',4,2,1,17,NULL,'2025-06-21 18:17:50','2025-06-21 18:31:25'),(6,'13567210012','库明',1,1,22,'kujiaming1122','3353439142',4,2,1,1,NULL,'2025-06-21 21:55:50','2025-06-21 22:01:21'),(7,'13800138012','张伟',2,1,26,'zhangwei456','654321987',4,1,1,4,21,'2025-06-22 15:18:04','2025-06-22 15:18:04'),(8,'13800138008','吴十',1,2,21,'wushi1010','123789456',4,0,3,6,36,'2026-02-04 17:45:36','2026-02-04 17:45:36'),(9,'13612341236','张益达',2,1,22,'z1231236','1231231236',4,2,1,2,NULL,'2026-02-04 18:12:40','2026-02-04 18:13:31'),(10,'13411001010','刘杰',1,1,NULL,'','',6,NULL,1,NULL,NULL,'2026-02-06 08:46:28','2026-02-06 08:46:28');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dept` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id，主键',
  `name` varchar(10) NOT NULL COMMENT '部门名称',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '状态：0-停用，1-正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3 COMMENT='部门信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES (1,'研发部',1,'2025-04-26 15:45:31','2025-07-26 15:27:40'),(2,'市场部',1,'2025-04-26 15:45:31','2025-07-26 15:27:40'),(3,'销售一部',1,'2025-04-26 15:45:31','2025-07-26 15:27:46'),(4,'人力资源部',1,'2025-04-26 15:45:31','2025-07-26 15:27:46'),(5,'财务部',1,'2025-04-26 15:45:31','2025-07-26 15:28:04'),(6,'客服部',0,'2025-04-26 15:45:31','2025-07-26 15:28:04'),(7,'技术支持部',1,'2025-04-26 15:45:31','2025-07-26 15:28:08'),(8,'产品部',1,'2025-04-26 15:45:31','2025-07-26 15:28:08'),(9,'运营部',0,'2025-04-26 15:45:31','2025-07-26 15:28:13'),(10,'采购部',1,'2025-04-26 15:45:31','2025-07-26 15:28:13'),(11,'法务部',0,'2025-04-26 15:45:31','2025-07-26 15:28:18'),(12,'设计部',1,'2025-04-26 15:45:31','2025-07-26 15:28:18'),(13,'公关部',1,'2025-04-26 15:45:31','2026-02-05 09:53:43'),(14,'质量部',1,'2025-04-26 15:45:31','2025-07-26 15:28:22'),(15,'战略二部',1,'2025-04-26 15:45:31','2026-02-05 09:35:57'),(16,'市场一部',1,'2025-07-09 11:32:42','2025-07-26 15:28:25'),(19,'市场二部',1,'2026-02-05 09:30:27','2026-02-05 09:30:27');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operate_log`
--

DROP TABLE IF EXISTS `operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operate_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operate_user_id` int unsigned DEFAULT NULL COMMENT '操作用户ID',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `class_name` varchar(100) DEFAULT NULL COMMENT '操作的类名',
  `method_name` varchar(100) DEFAULT NULL COMMENT '操作的方法名',
  `method_params` varchar(1000) DEFAULT NULL COMMENT '方法参数',
  `return_value` varchar(2000) DEFAULT NULL COMMENT '返回值',
  `cost_time` bigint DEFAULT NULL COMMENT '方法执行耗时, 单位:ms',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operate_log`
--

LOCK TABLES `operate_log` WRITE;
/*!40000 ALTER TABLE `operate_log` DISABLE KEYS */;
INSERT INTO `operate_log` VALUES (2,2,'2026-02-05 09:35:57','com.itheima.qk.controller.DeptController','update','[Dept(id=15, name=战略二部, status=1, createTime=2025-04-26T15:45:31, updateTime=2025-07-26T15:28:25)]','Result(code=1, msg=success, data=null)',26),(3,2,'2026-02-05 09:53:43','com.itheima.qk.controller.DeptController','update','[Dept(id=13, name=公关部, status=1, createTime=2025-04-26T15:45:31, updateTime=2025-07-26T15:28:22)]','Result(code=1, msg=success, data=null)',11),(4,1,'2026-02-06 08:46:28','com.itheima.qk.controller.CustomerController','add','[Customer(id=null, phone=13411001010, channel=1, name=刘杰, gender=1, age=null, wechat=, qq=, degree=6, jobStatus=null, subject=1, courseId=null, businessId=null, createTime=null, updateTime=null, courseName=null)]','Result(code=1, msg=success, data=null)',40);
/*!40000 ALTER TABLE `operate_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '角色id, 主键',
  `name` varchar(10) NOT NULL COMMENT '角色名称',
  `label` varchar(20) NOT NULL COMMENT '角色标识',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `label` (`label`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'管理员','admin','管理员, 用于管理整个系统数据','2025-05-09 19:47:28','2025-05-09 19:47:28'),(2,'线索专员','clue_operator','线索专员','2025-05-09 19:48:00','2025-05-09 19:48:00'),(3,'商机专员','business_operator','商机专员','2025-05-09 20:03:04','2025-05-09 20:03:04'),(4,'普通用户','common_user','普通公司员工','2025-06-21 15:25:30','2025-07-26 15:25:56'),(5,'测试角色','role_test','测试时使用的角色666','2025-06-27 15:57:57','2025-07-26 15:25:56');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id, 主键',
  `username` varchar(20) NOT NULL COMMENT '用户名，唯一',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `phone` char(11) NOT NULL COMMENT '手机号，唯一',
  `email` varchar(50) NOT NULL COMMENT '邮箱，唯一',
  `gender` tinyint unsigned NOT NULL COMMENT '性别，1: 男，2: 女',
  `status` tinyint unsigned NOT NULL COMMENT '状态，1: 正常，0: 停用',
  `dept_id` int unsigned DEFAULT NULL COMMENT '部门id，关联部门表主键',
  `role_id` int unsigned DEFAULT NULL COMMENT '角色id，关联角色表主键',
  `image` varchar(255) DEFAULT NULL COMMENT '头像url',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注，50字以内',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'zhangsan','4e7bdb88640b376ac6646b8f1ecfb558','张三','13800138001','admin@example.com',1,1,6,1,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/1.png','系统管理员','2025-05-12 11:39:03','2025-06-21 15:25:55'),(2,'lisi','c3cb6d12c40908943b64bc0681af47db','李四','13800138002','editor1@example.com',1,1,4,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/2.png','内容编辑','2025-05-12 11:39:03','2025-06-21 15:26:00'),(3,'sunwuji','368a7fccd730d807f41e2161798e13ca','孙无忌','18809091111','sunwuji@163.com',1,1,5,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/3.png','孙无忌刚入职，先在销售部磨炼磨炼','2025-05-12 16:46:21','2025-07-14 18:13:45'),(4,'songjiang','a91b1cfe31e6b534537ab992e06380f8','宋江','13800000001','songjiang@example.com',1,1,5,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/4.png','Remark 1','2025-05-14 15:49:31','2025-07-14 18:13:45'),(5,'lujunyi','ef73dd11149e096116f755a171eb3fec','卢俊义','13800000002','lujunyi@example.com',1,1,3,7,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/1.png','Remark 2','2025-05-14 15:49:31','2025-07-14 18:13:45'),(6,'wuyong','d74bcf7b7f805922e4ad42315f3a8cde','吴用','13800000003','wuyong@example.com',1,0,7,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/2.png','Remark 3','2025-05-14 15:49:31','2025-07-14 18:13:45'),(7,'gongsunsheng','ee3a229f2f3d2bd7c9dd3eeb4eda02b3','公孙胜','13800000004','gongsunsheng@example.com',1,1,5,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/3.png','Remark 4','2025-05-14 15:49:31','2025-07-14 18:13:45'),(8,'linchong','786271307576f1ee762341173a2caa48','林冲','13800000006','linchong@example.com',1,1,4,3,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/4.png','Remark 6','2025-05-14 15:49:31','2025-07-14 18:13:45'),(9,'qinming','23b9c4ea1f4d1844acd20a5314da8553','秦明','13800000007','qinming@example.com',1,1,5,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/1.png','Remark 7','2025-05-14 15:49:31','2025-07-14 18:13:45'),(10,'huarong','19d59a4e1e63552228b73b2cb2b5fcdd','花荣','13800000008','huayong@example.com',1,1,6,1,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/2.png','Remark 8','2025-05-14 15:49:31','2025-07-14 18:13:45'),(11,'huyan','fbb39f3019cc4f5444dbd6b623f71a1a','呼延灼','13800000009','huyan@example.com',1,0,8,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/3.png','Remark 9','2025-05-14 15:49:31','2025-07-14 18:13:45'),(12,'huaqian','1007f13423a57bc0390d9510f96d0322','花千朵','13800000010','huaqian@example.com',2,1,10,3,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/4.png','Remark 10','2025-05-14 15:49:31','2025-07-14 18:13:45'),(13,'shaqianmo','29d6fb85ac49d6d82b9b6dfd5f62939a','杀阡陌','13509091456','shaqianmo@163.com',0,1,5,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/1.png','','2025-06-19 17:48:39','2025-07-14 18:13:45'),(14,'baizihua','1e9ade0bae727ccd71591d1c375f625c','白子画','13609091206','baizihua@163.com',1,1,3,4,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/2.png','系统研发','2025-06-22 14:33:49','2026-02-05 09:12:35'),(15,'huaqiangu','d73c787ef92d527b3a946afe223c5138','花千骨','15809092819','huaqiangu@163.com',0,1,15,2,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/3.png','花千骨是一个普通用户','2025-06-24 10:40:20','2026-02-05 09:12:30'),(16,'kuangyetian','6d36072b523e38209510faeb4c3e9b80','旷野天','13689281921','kuangyetian@163.com',1,1,15,4,'https://zxy-data.oss-cn-hangzhou.aliyuncs.com/icon/4.png','新入职的技术员旷野天','2025-06-27 19:38:34','2026-02-05 09:12:14');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-07 18:01:29
