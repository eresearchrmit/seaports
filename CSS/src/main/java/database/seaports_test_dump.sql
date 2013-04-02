CREATE DATABASE  IF NOT EXISTS `seaports_test` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `seaports_test`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: seaports_test
-- ------------------------------------------------------
-- Server version	5.5.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `climate_emission_scenario`
--

DROP TABLE IF EXISTS `climate_emission_scenario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `climate_emission_scenario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `climate_emission_scenario`
--

LOCK TABLES `climate_emission_scenario` WRITE;
/*!40000 ALTER TABLE `climate_emission_scenario` DISABLE KEYS */;
INSERT INTO `climate_emission_scenario` VALUES (1,'No CO2 emissions increase','Base'),(2,'medium','A1B'),(3,'high','A1FI');
/*!40000 ALTER TABLE `climate_emission_scenario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `climate_model`
--

DROP TABLE IF EXISTS `climate_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `climate_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `climate_model`
--

LOCK TABLES `climate_model` WRITE;
/*!40000 ALTER TABLE `climate_model` DISABLE KEYS */;
INSERT INTO `climate_model` VALUES (1,'reference','Reference climate model: considering there is no change','(Reference)'),(2,'csiro_mk3_5','The CSIRO MK 3.5 climate model','CSIRO Mk3.5'),(3,'mri_cgcm2_3_2','The MRI-CGCM 2.3.2 climate model','MRI-CGCM2.3.2'),(4,'ipsl_cm4','The IPSL CM4 climate model','IPSL CM4'),(5,'miroc_3_2_medres','The Miroc 3.2 MedRes climate model','MIROC-Medres');
/*!40000 ALTER TABLE `climate_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `climate_params`
--

DROP TABLE IF EXISTS `climate_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `climate_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_name` varchar(255) DEFAULT NULL,
  `climate_emission_scenario_id` int(11) DEFAULT NULL,
  `climate_model_id` int(11) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA6107AE0F00AAA5A` (`climate_model_id`),
  KEY `FKA6107AE01E25895D` (`climate_emission_scenario_id`),
  KEY `FKA6107AE03365D05` (`region_id`),
  CONSTRAINT `FKA6107AE03365D05` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `FKA6107AE01E25895D` FOREIGN KEY (`climate_emission_scenario_id`) REFERENCES `climate_emission_scenario` (`id`),
  CONSTRAINT `FKA6107AE0F00AAA5A` FOREIGN KEY (`climate_model_id`) REFERENCES `climate_model` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `climate_params`
--

LOCK TABLES `climate_params` WRITE;
/*!40000 ALTER TABLE `climate_params` DISABLE KEYS */;
INSERT INTO `climate_params` VALUES (1,'Hotter and Drier',2,2,1),(2,'Hotter and Drier',3,2,1),(3,'Most Likely',2,4,1),(4,'Most Likely',3,4,1),(5,'Colder and Wetter',2,5,1),(6,'Colder and Wetter',3,5,1),(7,'Hotter and Drier',2,5,2),(8,'Hotter and Drier',3,5,2),(9,'Most Likely',2,2,2),(10,'Most Likely',3,2,2),(11,'Colder and Wetter',2,4,2),(12,'Colder and Wetter',3,4,2),(13,'Hotter and Drier',2,5,3),(14,'Hotter and Drier',3,5,3),(15,'Most Likely',2,2,3),(16,'Most Likely',3,2,3),(17,'Colder and Wetter',2,4,3),(18,'Colder and Wetter',3,4,3);
/*!40000 ALTER TABLE `climate_params` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmar_data`
--

DROP TABLE IF EXISTS `cmar_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmar_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `picture` longblob,
  `value` longtext,
  `year` int(11) DEFAULT NULL,
  `climate_params_id` int(11) DEFAULT NULL,
  `climate_variable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK64859B4E201EEBA` (`climate_params_id`),
  KEY `FK64859B4EB7D2D10B` (`climate_variable_id`),
  CONSTRAINT `FK64859B4EB7D2D10B` FOREIGN KEY (`climate_variable_id`) REFERENCES `csiro_variable` (`id`),
  CONSTRAINT `FK64859B4E201EEBA` FOREIGN KEY (`climate_params_id`) REFERENCES `climate_params` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmar_data`
--

LOCK TABLES `cmar_data` WRITE;
/*!40000 ALTER TABLE `cmar_data` DISABLE KEYS */;
INSERT INTO `cmar_data` VALUES (1,'2013-04-03 10:09:51',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,3,5),(2,'2013-04-03 10:09:51',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,3,5),(3,'2013-04-03 10:09:51',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,9,5),(4,'2013-04-03 10:09:51',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,9,5),(5,'2013-04-03 10:09:51',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,15,5),(6,'2013-04-03 10:09:51',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,15,5);
/*!40000 ALTER TABLE `cmar_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `csiro_data`
--

DROP TABLE IF EXISTS `csiro_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `csiro_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `picture` longblob,
  `value` double DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `climate_params_id` int(11) DEFAULT NULL,
  `climate_variable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKFFD9FC53201EEBA` (`climate_params_id`),
  KEY `FKFFD9FC53B7D2D10B` (`climate_variable_id`),
  CONSTRAINT `FKFFD9FC53B7D2D10B` FOREIGN KEY (`climate_variable_id`) REFERENCES `csiro_variable` (`id`),
  CONSTRAINT `FKFFD9FC53201EEBA` FOREIGN KEY (`climate_params_id`) REFERENCES `climate_params` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `csiro_data`
--

LOCK TABLES `csiro_data` WRITE;
/*!40000 ALTER TABLE `csiro_data` DISABLE KEYS */;
INSERT INTO `csiro_data` VALUES (1,'2013-04-03 10:09:51',NULL,0.45078652746433523,2030,1,1),(2,'2013-04-03 10:09:51',NULL,0.6017291060388469,2055,1,1),(3,'2013-04-03 10:09:51',NULL,0.573700673434284,2070,1,1),(4,'2013-04-03 10:09:51',NULL,0.813837367277336,2030,1,2),(5,'2013-04-03 10:09:51',NULL,0.03219364665659663,2055,1,2),(6,'2013-04-03 10:09:51',NULL,0.24774199739177172,2070,1,2),(7,'2013-04-03 10:09:51',NULL,0.24322985983207357,2030,1,3),(8,'2013-04-03 10:09:51',NULL,0.09632366963354544,2055,1,3),(9,'2013-04-03 10:09:51',NULL,0.34377762724899963,2070,1,3),(10,'2013-04-03 10:09:51',NULL,0.30816228843229,2030,1,4),(11,'2013-04-03 10:09:51',NULL,0.3416446283920648,2055,1,4),(12,'2013-04-03 10:09:51',NULL,0.7180940846587823,2070,1,4),(13,'2013-04-03 10:09:51',NULL,0.2514857279994558,2030,2,1),(14,'2013-04-03 10:09:51',NULL,0.6314569195114261,2055,2,1),(15,'2013-04-03 10:09:51',NULL,0.028347551545488958,2070,2,1),(16,'2013-04-03 10:09:51',NULL,0.311718489940484,2030,2,2),(17,'2013-04-03 10:09:51',NULL,0.23558277037543063,2055,2,2),(18,'2013-04-03 10:09:51',NULL,0.5330850065636632,2070,2,2),(19,'2013-04-03 10:09:51',NULL,0.6044465358160157,2030,2,3),(20,'2013-04-03 10:09:51',NULL,0.9337121767629338,2055,2,3),(21,'2013-04-03 10:09:51',NULL,0.41718002818585276,2070,2,3),(22,'2013-04-03 10:09:51',NULL,0.9321057605460651,2030,2,4),(23,'2013-04-03 10:09:51',NULL,0.5100310228285656,2055,2,4),(24,'2013-04-03 10:09:51',NULL,0.24315359991528795,2070,2,4),(25,'2013-04-03 10:09:51',NULL,0.5117094809296777,2030,3,1),(26,'2013-04-03 10:09:51',NULL,0.7980402405427558,2055,3,1),(27,'2013-04-03 10:09:51',NULL,0.14861666377882543,2070,3,1),(28,'2013-04-03 10:09:51',NULL,0.08426022232556896,2030,3,2),(29,'2013-04-03 10:09:51',NULL,0.3313228150607824,2055,3,2),(30,'2013-04-03 10:09:51',NULL,0.4813821925301619,2070,3,2),(31,'2013-04-03 10:09:51',NULL,0.6358348601437747,2030,3,3),(32,'2013-04-03 10:09:51',NULL,0.18157150089818586,2055,3,3),(33,'2013-04-03 10:09:51',NULL,0.38495130468322514,2070,3,3),(34,'2013-04-03 10:09:51',NULL,0.3107228341166973,2030,3,4),(35,'2013-04-03 10:09:51',NULL,0.0743264583501313,2055,3,4),(36,'2013-04-03 10:09:51',NULL,0.16908284170963772,2070,3,4),(37,'2013-04-03 10:09:51',NULL,0.4577710491204585,2030,4,1),(38,'2013-04-03 10:09:51',NULL,0.3052748068631381,2055,4,1),(39,'2013-04-03 10:09:51',NULL,0.367931584079503,2070,4,1),(40,'2013-04-03 10:09:51',NULL,0.8436198353143983,2030,4,2),(41,'2013-04-03 10:09:51',NULL,0.7910759776900822,2055,4,2),(42,'2013-04-03 10:09:51',NULL,0.9736514234405539,2070,4,2),(43,'2013-04-03 10:09:51',NULL,0.9056504008994003,2030,4,3),(44,'2013-04-03 10:09:51',NULL,0.35793740206320246,2055,4,3),(45,'2013-04-03 10:09:51',NULL,0.629068238937766,2070,4,3),(46,'2013-04-03 10:09:51',NULL,0.9480769242461744,2030,4,4),(47,'2013-04-03 10:09:51',NULL,0.669482731287886,2055,4,4),(48,'2013-04-03 10:09:51',NULL,0.05047388635880801,2070,4,4),(49,'2013-04-03 10:09:51',NULL,0.45083421830728954,2030,5,1),(50,'2013-04-03 10:09:51',NULL,0.63777414116209,2055,5,1),(51,'2013-04-03 10:09:51',NULL,0.4501365553550123,2070,5,1),(52,'2013-04-03 10:09:51',NULL,0.7463851161291492,2030,5,2),(53,'2013-04-03 10:09:51',NULL,0.16378155412062734,2055,5,2),(54,'2013-04-03 10:09:51',NULL,0.1760962994945795,2070,5,2),(55,'2013-04-03 10:09:51',NULL,0.8907291901734857,2030,5,3),(56,'2013-04-03 10:09:51',NULL,0.6584291964786804,2055,5,3),(57,'2013-04-03 10:09:51',NULL,0.8990706502077421,2070,5,3),(58,'2013-04-03 10:09:51',NULL,0.6830398366859022,2030,5,4),(59,'2013-04-03 10:09:51',NULL,0.020986904137831552,2055,5,4),(60,'2013-04-03 10:09:51',NULL,0.4664677996660337,2070,5,4),(61,'2013-04-03 10:09:51',NULL,0.8200819138510874,2030,6,1),(62,'2013-04-03 10:09:51',NULL,0.26809558478505413,2055,6,1),(63,'2013-04-03 10:09:51',NULL,0.793081950518393,2070,6,1),(64,'2013-04-03 10:09:51',NULL,0.7607952436509002,2030,6,2),(65,'2013-04-03 10:09:51',NULL,0.5317179802493269,2055,6,2),(66,'2013-04-03 10:09:51',NULL,0.25796584195167993,2070,6,2),(67,'2013-04-03 10:09:51',NULL,0.8851288583349115,2030,6,3),(68,'2013-04-03 10:09:51',NULL,0.7972015710658886,2055,6,3),(69,'2013-04-03 10:09:51',NULL,0.2613475955864717,2070,6,3),(70,'2013-04-03 10:09:51',NULL,0.8494951934828648,2030,6,4),(71,'2013-04-03 10:09:51',NULL,0.6994945054604307,2055,6,4),(72,'2013-04-03 10:09:51',NULL,0.43062187813470476,2070,6,4),(73,'2013-04-03 10:09:51',NULL,0.9714558419117276,2030,7,1),(74,'2013-04-03 10:09:51',NULL,0.7602335095017388,2055,7,1),(75,'2013-04-03 10:09:51',NULL,0.05045909298568574,2070,7,1),(76,'2013-04-03 10:09:51',NULL,0.1694186165673227,2030,7,2),(77,'2013-04-03 10:09:51',NULL,0.9595734470488697,2055,7,2),(78,'2013-04-03 10:09:51',NULL,0.4773694057565211,2070,7,2),(79,'2013-04-03 10:09:51',NULL,0.5093985055942902,2030,7,3),(80,'2013-04-03 10:09:51',NULL,0.5237894065423156,2055,7,3),(81,'2013-04-03 10:09:51',NULL,0.3028152419003799,2070,7,3),(82,'2013-04-03 10:09:51',NULL,0.3903430066579988,2030,7,4),(83,'2013-04-03 10:09:51',NULL,0.7555409663864165,2055,7,4),(84,'2013-04-03 10:09:51',NULL,0.20948840150728598,2070,7,4),(85,'2013-04-03 10:09:51',NULL,0.7123661194244314,2030,8,1),(86,'2013-04-03 10:09:51',NULL,0.4332674102790356,2055,8,1),(87,'2013-04-03 10:09:51',NULL,0.5664834044233794,2070,8,1),(88,'2013-04-03 10:09:51',NULL,0.311993660830001,2030,8,2),(89,'2013-04-03 10:09:51',NULL,0.9775102664980895,2055,8,2),(90,'2013-04-03 10:09:51',NULL,0.8603581231969732,2070,8,2),(91,'2013-04-03 10:09:51',NULL,0.27503686054976906,2030,8,3),(92,'2013-04-03 10:09:51',NULL,0.21644954786738213,2055,8,3),(93,'2013-04-03 10:09:51',NULL,0.1993074123493067,2070,8,3),(94,'2013-04-03 10:09:51',NULL,0.4308901306739422,2030,8,4),(95,'2013-04-03 10:09:51',NULL,0.6176016278769882,2055,8,4),(96,'2013-04-03 10:09:51',NULL,0.9651573786362241,2070,8,4),(97,'2013-04-03 10:09:51',NULL,0.44077796291205307,2030,9,1),(98,'2013-04-03 10:09:51',NULL,0.5897730457295213,2055,9,1),(99,'2013-04-03 10:09:51',NULL,0.9041566942747051,2070,9,1),(100,'2013-04-03 10:09:51',NULL,0.7170835275901003,2030,9,2),(101,'2013-04-03 10:09:51',NULL,0.25346603684500635,2055,9,2),(102,'2013-04-03 10:09:51',NULL,0.19565435964179556,2070,9,2),(103,'2013-04-03 10:09:51',NULL,0.3510806553210616,2030,9,3),(104,'2013-04-03 10:09:51',NULL,0.15279918041534635,2055,9,3),(105,'2013-04-03 10:09:51',NULL,0.7795077765253142,2070,9,3),(106,'2013-04-03 10:09:51',NULL,0.9705316997117354,2030,9,4),(107,'2013-04-03 10:09:51',NULL,0.23540358088511215,2055,9,4),(108,'2013-04-03 10:09:51',NULL,0.9934615632842029,2070,9,4),(109,'2013-04-03 10:09:51',NULL,0.7738399985387262,2030,10,1),(110,'2013-04-03 10:09:51',NULL,0.12317520588752284,2055,10,1),(111,'2013-04-03 10:09:51',NULL,0.2527228518731921,2070,10,1),(112,'2013-04-03 10:09:51',NULL,0.283891179220899,2030,10,2),(113,'2013-04-03 10:09:51',NULL,0.9429418541811665,2055,10,2),(114,'2013-04-03 10:09:51',NULL,0.5332311578082997,2070,10,2),(115,'2013-04-03 10:09:51',NULL,0.0908982769731338,2030,10,3),(116,'2013-04-03 10:09:51',NULL,0.7274958783440612,2055,10,3),(117,'2013-04-03 10:09:51',NULL,0.45451021280521453,2070,10,3),(118,'2013-04-03 10:09:51',NULL,0.4736985481477022,2030,10,4),(119,'2013-04-03 10:09:51',NULL,0.7677747598250857,2055,10,4),(120,'2013-04-03 10:09:51',NULL,0.154736665049082,2070,10,4),(121,'2013-04-03 10:09:51',NULL,0.3367273336392116,2030,11,1),(122,'2013-04-03 10:09:51',NULL,0.0965406766595901,2055,11,1),(123,'2013-04-03 10:09:51',NULL,0.7235635953712949,2070,11,1),(124,'2013-04-03 10:09:51',NULL,0.15702666575043334,2030,11,2),(125,'2013-04-03 10:09:51',NULL,0.7562867569536023,2055,11,2),(126,'2013-04-03 10:09:51',NULL,0.8095140469527232,2070,11,2),(127,'2013-04-03 10:09:51',NULL,0.007146651730210785,2030,11,3),(128,'2013-04-03 10:09:51',NULL,0.02439911566334163,2055,11,3),(129,'2013-04-03 10:09:51',NULL,0.8411019665844909,2070,11,3),(130,'2013-04-03 10:09:51',NULL,0.23186691455011366,2030,11,4),(131,'2013-04-03 10:09:51',NULL,0.150449556971456,2055,11,4),(132,'2013-04-03 10:09:51',NULL,0.38797591012689503,2070,11,4),(133,'2013-04-03 10:09:51',NULL,0.7966291385055775,2030,12,1),(134,'2013-04-03 10:09:51',NULL,0.6323806840943871,2055,12,1),(135,'2013-04-03 10:09:51',NULL,0.48616404550930437,2070,12,1),(136,'2013-04-03 10:09:51',NULL,0.3872136703368827,2030,12,2),(137,'2013-04-03 10:09:51',NULL,0.5539942772847843,2055,12,2),(138,'2013-04-03 10:09:51',NULL,0.6252972571080081,2070,12,2),(139,'2013-04-03 10:09:51',NULL,0.7314813412337822,2030,12,3),(140,'2013-04-03 10:09:51',NULL,0.757099260956065,2055,12,3),(141,'2013-04-03 10:09:51',NULL,0.08362170901683974,2070,12,3),(142,'2013-04-03 10:09:51',NULL,0.4279956263030592,2030,12,4),(143,'2013-04-03 10:09:51',NULL,0.6341320660878303,2055,12,4),(144,'2013-04-03 10:09:51',NULL,0.9619123407857075,2070,12,4),(145,'2013-04-03 10:09:51',NULL,0.809819567024591,2030,13,1),(146,'2013-04-03 10:09:51',NULL,0.16885213880932737,2055,13,1),(147,'2013-04-03 10:09:51',NULL,0.35691050452995654,2070,13,1),(148,'2013-04-03 10:09:51',NULL,0.38986748641060665,2030,13,2),(149,'2013-04-03 10:09:51',NULL,0.2697854609271876,2055,13,2),(150,'2013-04-03 10:09:51',NULL,0.5745377945090667,2070,13,2),(151,'2013-04-03 10:09:51',NULL,0.0629940335430984,2030,13,3),(152,'2013-04-03 10:09:51',NULL,0.666894311795875,2055,13,3),(153,'2013-04-03 10:09:51',NULL,0.27475288445449886,2070,13,3),(154,'2013-04-03 10:09:51',NULL,0.8630777584256096,2030,13,4),(155,'2013-04-03 10:09:51',NULL,0.8567513398993084,2055,13,4),(156,'2013-04-03 10:09:51',NULL,0.3480084449328271,2070,13,4),(157,'2013-04-03 10:09:51',NULL,0.3515076125626537,2030,14,1),(158,'2013-04-03 10:09:51',NULL,0.822317389677869,2055,14,1),(159,'2013-04-03 10:09:51',NULL,0.8333657359698462,2070,14,1),(160,'2013-04-03 10:09:51',NULL,0.6011725089600235,2030,14,2),(161,'2013-04-03 10:09:51',NULL,0.16926033903968452,2055,14,2),(162,'2013-04-03 10:09:51',NULL,0.8630101581448408,2070,14,2),(163,'2013-04-03 10:09:51',NULL,0.4954830932312452,2030,14,3),(164,'2013-04-03 10:09:51',NULL,0.011240902852179047,2055,14,3),(165,'2013-04-03 10:09:51',NULL,0.9898699878070452,2070,14,3),(166,'2013-04-03 10:09:51',NULL,0.3015626746373079,2030,14,4),(167,'2013-04-03 10:09:51',NULL,0.20622607413799576,2055,14,4),(168,'2013-04-03 10:09:51',NULL,0.30052761466349853,2070,14,4),(169,'2013-04-03 10:09:51',NULL,0.9186700342084418,2030,15,1),(170,'2013-04-03 10:09:51',NULL,0.24954253389088865,2055,15,1),(171,'2013-04-03 10:09:51',NULL,0.6652997529904678,2070,15,1),(172,'2013-04-03 10:09:51',NULL,0.5877317237703531,2030,15,2),(173,'2013-04-03 10:09:51',NULL,0.8786576626215267,2055,15,2),(174,'2013-04-03 10:09:51',NULL,0.2653752107324009,2070,15,2),(175,'2013-04-03 10:09:51',NULL,0.6185461247472546,2030,15,3),(176,'2013-04-03 10:09:51',NULL,0.7884008661973207,2055,15,3),(177,'2013-04-03 10:09:51',NULL,0.7825636919106763,2070,15,3),(178,'2013-04-03 10:09:51',NULL,0.3582261281733299,2030,15,4),(179,'2013-04-03 10:09:51',NULL,0.1411882849605487,2055,15,4),(180,'2013-04-03 10:09:51',NULL,0.8978071694073999,2070,15,4),(181,'2013-04-03 10:09:51',NULL,0.33532893708366884,2030,16,1),(182,'2013-04-03 10:09:51',NULL,0.024884677362629892,2055,16,1),(183,'2013-04-03 10:09:51',NULL,0.8711371081865971,2070,16,1),(184,'2013-04-03 10:09:51',NULL,0.19105703320517142,2030,16,2),(185,'2013-04-03 10:09:51',NULL,0.2637703271436508,2055,16,2),(186,'2013-04-03 10:09:51',NULL,0.7475369529723555,2070,16,2),(187,'2013-04-03 10:09:51',NULL,0.5111169173636437,2030,16,3),(188,'2013-04-03 10:09:51',NULL,0.8254500239030852,2055,16,3),(189,'2013-04-03 10:09:51',NULL,0.5407281572786654,2070,16,3),(190,'2013-04-03 10:09:51',NULL,0.7836348532168009,2030,16,4),(191,'2013-04-03 10:09:51',NULL,0.7669989872283294,2055,16,4),(192,'2013-04-03 10:09:51',NULL,0.936548409152734,2070,16,4),(193,'2013-04-03 10:09:51',NULL,0.3477434092333678,2030,17,1),(194,'2013-04-03 10:09:51',NULL,0.13863870144125867,2055,17,1),(195,'2013-04-03 10:09:51',NULL,0.6446143254801778,2070,17,1),(196,'2013-04-03 10:09:51',NULL,0.08211358674071834,2030,17,2),(197,'2013-04-03 10:09:51',NULL,0.10332228925194564,2055,17,2),(198,'2013-04-03 10:09:51',NULL,0.32823161158894365,2070,17,2),(199,'2013-04-03 10:09:51',NULL,0.6884508158641263,2030,17,3),(200,'2013-04-03 10:09:51',NULL,0.8070114747968864,2055,17,3),(201,'2013-04-03 10:09:51',NULL,0.8731022798330084,2070,17,3),(202,'2013-04-03 10:09:51',NULL,0.9086736861492571,2030,17,4),(203,'2013-04-03 10:09:51',NULL,0.3130213881814974,2055,17,4),(204,'2013-04-03 10:09:51',NULL,0.17738374709876115,2070,17,4),(205,'2013-04-03 10:09:51',NULL,0.8737427023006635,2030,18,1),(206,'2013-04-03 10:09:51',NULL,0.7822845011397456,2055,18,1),(207,'2013-04-03 10:09:51',NULL,0.8513240655238417,2070,18,1),(208,'2013-04-03 10:09:51',NULL,0.3638274036192116,2030,18,2),(209,'2013-04-03 10:09:51',NULL,0.5841196146040991,2055,18,2),(210,'2013-04-03 10:09:51',NULL,0.9696128853534709,2070,18,2),(211,'2013-04-03 10:09:51',NULL,0.32469634314758933,2030,18,3),(212,'2013-04-03 10:09:51',NULL,0.7410205498759057,2055,18,3),(213,'2013-04-03 10:09:51',NULL,0.18541353319986587,2070,18,3),(214,'2013-04-03 10:09:51',NULL,0.880672980553828,2030,18,4),(215,'2013-04-03 10:09:51',NULL,0.2245000553350608,2055,18,4),(216,'2013-04-03 10:09:51',NULL,0.5151773146926799,2070,18,4);
/*!40000 ALTER TABLE `csiro_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `csiro_data_baseline`
--

DROP TABLE IF EXISTS `csiro_data_baseline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `csiro_data_baseline` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `value` double DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  `climate_variable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE24548B1B7D2D10B` (`climate_variable_id`),
  KEY `FKE24548B13365D05` (`region_id`),
  CONSTRAINT `FKE24548B13365D05` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `FKE24548B1B7D2D10B` FOREIGN KEY (`climate_variable_id`) REFERENCES `csiro_variable` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `csiro_data_baseline`
--

LOCK TABLES `csiro_data_baseline` WRITE;
/*!40000 ALTER TABLE `csiro_data_baseline` DISABLE KEYS */;
INSERT INTO `csiro_data_baseline` VALUES (1,'2013-04-03 10:09:51',16.1,1,1),(2,'2013-04-03 10:09:51',1,1,2),(3,'2013-04-03 10:09:51',1029.8,1,3),(4,'2013-04-03 10:09:51',74.8,1,4);
/*!40000 ALTER TABLE `csiro_data_baseline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `csiro_variable`
--

DROP TABLE IF EXISTS `csiro_variable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `csiro_variable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `uom` varchar(255) DEFAULT NULL,
  `uom_variation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `csiro_variable`
--

LOCK TABLES `csiro_variable` WRITE;
/*!40000 ALTER TABLE `csiro_variable` DISABLE KEYS */;
INSERT INTO `csiro_variable` VALUES (1,'Forecasted hange of temperature from now','Temperature','T','&#8451;','&#8451;'),(2,'Forecasted wind speed','Wind speed','WS','km/h','%'),(3,'Forecasted rain fall','Rainfall','RF','mm/y','%'),(4,'Forecasted relative humidity','Relative humidity','RH','%','%'),(5,'Forecasted sea level rise','Sea Level Rise','SLR','mm','%');
/*!40000 ALTER TABLE `csiro_variable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_element`
--

DROP TABLE IF EXISTS `data_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_element` (
  `type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creation_date` datetime DEFAULT NULL,
  `included` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `content` longblob,
  `filetype` varchar(255) DEFAULT NULL,
  `pictures_included` bit(1) DEFAULT NULL,
  `text` longtext,
  `user_story_id` int(11) DEFAULT NULL,
  `weather_event` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK74B46B672F7FE520` (`weather_event`),
  KEY `FK74B46B67994A1ED8` (`user_story_id`),
  CONSTRAINT `FK74B46B67994A1ED8` FOREIGN KEY (`user_story_id`) REFERENCES `user_story` (`id`),
  CONSTRAINT `FK74B46B672F7FE520` FOREIGN KEY (`weather_event`) REFERENCES `weather_event` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_element`
--

LOCK TABLES `data_element` WRITE;
/*!40000 ALTER TABLE `data_element` DISABLE KEYS */;
INSERT INTO `data_element` VALUES ('File',1,'2013-04-03 10:09:51','','Test 1',0,'This is a test for Data Element','csv',NULL,NULL,1,NULL),('File',2,'2013-04-03 10:09:51','','Test 2',0,'This is the second test','xml',NULL,NULL,1,NULL),('File',3,'2013-04-03 10:09:51','','Test 3',0,'This is the third test','txt',NULL,NULL,1,NULL),('File',4,'2013-04-03 10:09:51','','Test 4',1,'User Story\'s Data Element content test','txt',NULL,NULL,2,NULL),('Text',5,'2013-04-03 10:09:51','','Comment text 1',2,NULL,NULL,NULL,'This is a text comment',2,NULL),('File',6,'2013-04-03 10:09:51','','Test 5',3,'User Story\'s Data Element content test','txt',NULL,NULL,2,NULL),('Text',7,'2013-04-03 10:09:51','','Comment text 2',4,NULL,NULL,NULL,'This is a second text comment',2,NULL),('File',8,'2013-04-03 10:09:51','','Test 6',1,'User Story\'s Data Element content test','txt',NULL,NULL,3,NULL),('Csiro',9,'2013-04-03 10:09:51','','CSIRO Data Element Test',0,NULL,NULL,'',NULL,1,NULL),('EngineeringModel',10,'2013-04-03 10:09:51','','Data Element for Pint ()',0,NULL,NULL,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `data_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_element_cmar_data`
--

DROP TABLE IF EXISTS `data_element_cmar_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_element_cmar_data` (
  `data_element_id` int(11) NOT NULL,
  `cmar_data_id` int(11) NOT NULL,
  KEY `FKAE352C76719E52B5` (`data_element_id`),
  KEY `FKAE352C76CB8CE91C` (`cmar_data_id`),
  CONSTRAINT `FKAE352C76CB8CE91C` FOREIGN KEY (`cmar_data_id`) REFERENCES `cmar_data` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKAE352C76719E52B5` FOREIGN KEY (`data_element_id`) REFERENCES `data_element` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_element_cmar_data`
--

LOCK TABLES `data_element_cmar_data` WRITE;
/*!40000 ALTER TABLE `data_element_cmar_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_element_cmar_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_element_csiro_data`
--

DROP TABLE IF EXISTS `data_element_csiro_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_element_csiro_data` (
  `data_element_id` int(11) NOT NULL,
  `csiro_data_id` int(11) NOT NULL,
  KEY `FKEC1C902BED6FB022` (`data_element_id`),
  KEY `FKEC1C902B505D479C` (`csiro_data_id`),
  CONSTRAINT `FKEC1C902B505D479C` FOREIGN KEY (`csiro_data_id`) REFERENCES `csiro_data` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKEC1C902BED6FB022` FOREIGN KEY (`data_element_id`) REFERENCES `data_element` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_element_csiro_data`
--

LOCK TABLES `data_element_csiro_data` WRITE;
/*!40000 ALTER TABLE `data_element_csiro_data` DISABLE KEYS */;
INSERT INTO `data_element_csiro_data` VALUES (9,1),(9,2),(9,3),(9,4);
/*!40000 ALTER TABLE `data_element_csiro_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_element_engineering_model_data`
--

DROP TABLE IF EXISTS `data_element_engineering_model_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_element_engineering_model_data` (
  `data_element_id` int(11) NOT NULL,
  `engineering_model_data_id` int(11) NOT NULL,
  KEY `FK610A3B8437160FB9` (`engineering_model_data_id`),
  KEY `FK610A3B84354219F0` (`data_element_id`),
  CONSTRAINT `FK610A3B84354219F0` FOREIGN KEY (`data_element_id`) REFERENCES `data_element` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK610A3B8437160FB9` FOREIGN KEY (`engineering_model_data_id`) REFERENCES `engineering_model_data` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_element_engineering_model_data`
--

LOCK TABLES `data_element_engineering_model_data` WRITE;
/*!40000 ALTER TABLE `data_element_engineering_model_data` DISABLE KEYS */;
INSERT INTO `data_element_engineering_model_data` VALUES (10,1),(10,2),(10,3),(10,4),(10,5),(10,6);
/*!40000 ALTER TABLE `data_element_engineering_model_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `engineering_model_asset`
--

DROP TABLE IF EXISTS `engineering_model_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `engineering_model_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_code` varchar(255) DEFAULT NULL,
  `carbonation_class` varchar(255) DEFAULT NULL,
  `ce` double DEFAULT NULL,
  `chloride_class` varchar(255) DEFAULT NULL,
  `cover` double DEFAULT NULL,
  `dbar` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `distance_from_coast` double DEFAULT NULL,
  `dmember` double DEFAULT NULL,
  `exposure_class` varchar(255) DEFAULT NULL,
  `fprimec` double DEFAULT NULL,
  `wc` double DEFAULT NULL,
  `year_built` int(11) NOT NULL,
  `zone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engineering_model_asset`
--

LOCK TABLES `engineering_model_asset` WRITE;
/*!40000 ALTER TABLE `engineering_model_asset` DISABLE KEYS */;
INSERT INTO `engineering_model_asset` VALUES (1,'006B','CB3',300,'CL1',60,13,'Pile No 6 @ Berth 08',2,1300,'C2',60,0.37,1991,'atmospheric');
/*!40000 ALTER TABLE `engineering_model_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `engineering_model_data`
--

DROP TABLE IF EXISTS `engineering_model_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `engineering_model_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` text,
  `engineering_model_asset_id` int(11) DEFAULT NULL,
  `climate_params_id` int(11) DEFAULT NULL,
  `engineering_model_variable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4C7EABAC201EEBA` (`climate_params_id`),
  KEY `FK4C7EABACD1BF0EF9` (`engineering_model_variable_id`),
  KEY `FK4C7EABAC2AA35D5B` (`engineering_model_asset_id`),
  CONSTRAINT `FK4C7EABAC2AA35D5B` FOREIGN KEY (`engineering_model_asset_id`) REFERENCES `engineering_model_asset` (`id`),
  CONSTRAINT `FK4C7EABAC201EEBA` FOREIGN KEY (`climate_params_id`) REFERENCES `climate_params` (`id`),
  CONSTRAINT `FK4C7EABACD1BF0EF9` FOREIGN KEY (`engineering_model_variable_id`) REFERENCES `engineering_model_variable` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engineering_model_data`
--

LOCK TABLES `engineering_model_data` WRITE;
/*!40000 ALTER TABLE `engineering_model_data` DISABLE KEYS */;
INSERT INTO `engineering_model_data` VALUES (1,'2065,8.0;2032,7.0;2064,3.0;2033,0.0;2067,0.0;2034,0.0;2066,8.0;2035,2.0;2069,8.0;2036,8.0;2068,7.0;2037,7.0;2038,6.0;2070,3.0;2039,1.0;2040,3.0;2041,9.0;2042,8.0;2043,3.0;2044,4.0;2045,8.0;2046,1.0;2047,2.0;2048,4.0;2017,8.0;2049,2.0;2016,9.0;2050,0.0;2019,2.0;2051,7.0;2018,9.0;2052,5.0;2021,1.0;2053,7.0;2020,0.0;2054,9.0;2023,0.0;2055,4.0;2022,0.0;2056,0.0;2025,7.0;2057,0.0;2024,9.0;2058,1.0;2027,3.0;2059,0.0;2026,8.0;2060,9.0;2029,5.0;2061,4.0;2028,5.0;2062,2.0;2031,1.0;2063,1.0;2030,6.0;2002,6.0;2003,1.0;2000,9.0;2001,8.0;2006,2.0;2007,7.0;2004,3.0;2005,9.0;2010,5.0;2011,2.0;2008,2.0;2009,9.0;2014,6.0;2015,0.0;2012,2.0;2013,4.0',1,1,1),(2,'2065,5.0;2032,0.0;2064,7.0;2033,8.0;2067,4.0;2034,1.0;2066,3.0;2035,5.0;2069,5.0;2036,3.0;2068,1.0;2037,0.0;2038,6.0;2070,7.0;2039,8.0;2040,3.0;2041,5.0;2042,0.0;2043,4.0;2044,0.0;2045,8.0;2046,7.0;2047,4.0;2048,7.0;2017,7.0;2049,5.0;2016,1.0;2050,5.0;2019,0.0;2051,3.0;2018,5.0;2052,7.0;2021,2.0;2053,1.0;2020,6.0;2054,0.0;2023,6.0;2055,4.0;2022,2.0;2056,8.0;2025,8.0;2057,0.0;2024,6.0;2058,9.0;2027,4.0;2059,3.0;2026,8.0;2060,1.0;2029,4.0;2061,1.0;2028,2.0;2062,8.0;2031,5.0;2063,2.0;2030,2.0;2002,3.0;2003,2.0;2000,4.0;2001,8.0;2006,8.0;2007,5.0;2004,0.0;2005,6.0;2010,7.0;2011,0.0;2008,7.0;2009,1.0;2014,0.0;2015,3.0;2012,7.0;2013,2.0',1,2,1),(3,'2065,3.0;2032,5.0;2064,4.0;2033,6.0;2067,5.0;2034,0.0;2066,0.0;2035,4.0;2069,0.0;2036,1.0;2068,0.0;2037,3.0;2038,9.0;2070,7.0;2039,3.0;2040,8.0;2041,1.0;2042,7.0;2043,7.0;2044,5.0;2045,5.0;2046,8.0;2047,4.0;2048,0.0;2017,9.0;2049,5.0;2016,2.0;2050,5.0;2019,0.0;2051,3.0;2018,2.0;2052,0.0;2021,7.0;2053,3.0;2020,2.0;2054,3.0;2023,8.0;2055,7.0;2022,2.0;2056,6.0;2025,9.0;2057,9.0;2024,7.0;2058,9.0;2027,8.0;2059,4.0;2026,8.0;2060,0.0;2029,7.0;2061,9.0;2028,3.0;2062,9.0;2031,9.0;2063,9.0;2030,3.0;2002,0.0;2003,1.0;2000,5.0;2001,6.0;2006,2.0;2007,5.0;2004,6.0;2005,9.0;2010,9.0;2011,0.0;2008,7.0;2009,9.0;2014,2.0;2015,1.0;2012,3.0;2013,3.0',1,3,1),(4,'2065,9.0;2032,2.0;2064,0.0;2033,2.0;2067,5.0;2034,8.0;2066,6.0;2035,7.0;2069,5.0;2036,3.0;2068,4.0;2037,7.0;2038,6.0;2070,7.0;2039,8.0;2040,2.0;2041,2.0;2042,8.0;2043,4.0;2044,7.0;2045,2.0;2046,6.0;2047,1.0;2048,4.0;2017,7.0;2049,9.0;2016,9.0;2050,0.0;2019,8.0;2051,3.0;2018,4.0;2052,3.0;2021,8.0;2053,8.0;2020,4.0;2054,1.0;2023,8.0;2055,8.0;2022,7.0;2056,7.0;2025,8.0;2057,4.0;2024,0.0;2058,8.0;2027,2.0;2059,0.0;2026,8.0;2060,9.0;2029,2.0;2061,0.0;2028,6.0;2062,0.0;2031,5.0;2063,4.0;2030,4.0;2002,7.0;2003,6.0;2000,5.0;2001,9.0;2006,3.0;2007,0.0;2004,8.0;2005,5.0;2010,6.0;2011,3.0;2008,1.0;2009,4.0;2014,7.0;2015,8.0;2012,6.0;2013,6.0',1,4,1),(5,'2065,6.0;2032,4.0;2064,5.0;2033,9.0;2067,8.0;2034,7.0;2066,6.0;2035,4.0;2069,2.0;2036,9.0;2068,0.0;2037,9.0;2038,8.0;2070,2.0;2039,6.0;2040,3.0;2041,5.0;2042,5.0;2043,4.0;2044,6.0;2045,0.0;2046,4.0;2047,9.0;2048,5.0;2017,6.0;2049,7.0;2016,3.0;2050,8.0;2019,6.0;2051,9.0;2018,2.0;2052,3.0;2021,6.0;2053,5.0;2020,2.0;2054,5.0;2023,1.0;2055,4.0;2022,9.0;2056,9.0;2025,6.0;2057,2.0;2024,2.0;2058,6.0;2027,7.0;2059,0.0;2026,5.0;2060,8.0;2029,5.0;2061,0.0;2028,2.0;2062,9.0;2031,8.0;2063,4.0;2030,0.0;2002,5.0;2003,6.0;2000,9.0;2001,2.0;2006,1.0;2007,7.0;2004,9.0;2005,8.0;2010,1.0;2011,5.0;2008,2.0;2009,5.0;2014,8.0;2015,7.0;2012,2.0;2013,7.0',1,5,1),(6,'2065,0.0;2032,3.0;2064,3.0;2033,6.0;2067,9.0;2034,4.0;2066,3.0;2035,8.0;2069,6.0;2036,9.0;2068,5.0;2037,0.0;2038,7.0;2070,5.0;2039,9.0;2040,5.0;2041,3.0;2042,7.0;2043,8.0;2044,5.0;2045,0.0;2046,0.0;2047,2.0;2048,8.0;2017,4.0;2049,3.0;2016,9.0;2050,3.0;2019,4.0;2051,9.0;2018,5.0;2052,5.0;2021,3.0;2053,0.0;2020,2.0;2054,4.0;2023,0.0;2055,9.0;2022,5.0;2056,7.0;2025,8.0;2057,0.0;2024,9.0;2058,9.0;2027,2.0;2059,9.0;2026,1.0;2060,7.0;2029,0.0;2061,9.0;2028,9.0;2062,6.0;2031,4.0;2063,0.0;2030,0.0;2002,1.0;2003,0.0;2000,7.0;2001,7.0;2006,6.0;2007,5.0;2004,4.0;2005,0.0;2010,4.0;2011,6.0;2008,2.0;2009,4.0;2014,2.0;2015,3.0;2012,0.0;2013,5.0',1,6,1);
/*!40000 ALTER TABLE `engineering_model_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `engineering_model_variable`
--

DROP TABLE IF EXISTS `engineering_model_variable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `engineering_model_variable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `uom` varchar(255) DEFAULT NULL,
  `uom_variation` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engineering_model_variable`
--

LOCK TABLES `engineering_model_variable` WRITE;
/*!40000 ALTER TABLE `engineering_model_variable` DISABLE KEYS */;
INSERT INTO `engineering_model_variable` VALUES (1,'Corrosion initiation of reinforcing bar probability','Corrosion initiation of reinforcing bar probability','Pint','','%','Chloride'),(2,'Change in chloride concentration at concrete cover depth','Change in chloride concentration at concrete cover depth','C(h,t)','kg/m&sup3;','%','Chloride'),(3,'Change in corrosion rate of reinforcing bar','Change in corrosion rate of reinforcing bar','icorr (t)','&#181;A/cm&sup2;','%','Chloride'),(4,'Corrosion initiation time','Corrosion initiation time','ti','yr','%','Chloride'),(5,'Crack propagation time','Crack propagation time','tp','yr','%','Chloride'),(6,'Time to severe cracking (1mm crack width)','Time to severe cracking (1mm crack width)','tcr','yr','%','Chloride'),(7,'Chloride induced corrosion probability to severe cracking','Chloride induced corrosion probability to severe cracking','Pinit x Pcrack','','%','Chloride'),(8,'Reduction or loss in reinforcing bar','Reduction or loss in reinforcing bar','Rebar loss','mm','%','Chloride'),(9,'Corrosion initiation of reinforcing bar probability ','Corrosion initiation of reinforcing bar probability','Pint','','%','Carbonation'),(10,'Change in carbonation depth','Change in carbonation depth','&#916; xc(t\')','mm','%','Carbonation'),(11,'Change in corrosion rate of reinforcing bar','Change in corrosion rate of reinforcing bar','icorr(t\')','&#181;A/cm&sup2;','%','Carbonation'),(12,'Corrosion initiation time','Corrosion initiation time','ti','yr','%','Carbonation'),(13,'Crack propagation time','Crack propagation time','tp','yr','%','Carbonation'),(14,'Time to severe cracking (1mm crack width)','Time to severe cracking (1mm crack width)','tcr','yr','%','Carbonation'),(15,'Carbonation induced corrosion probability to severe cracking','Carbonation induced corrosion probability to severe cracking','Pinit x Pcrack','','%','Carbonation'),(16,'Reduction or loss in reinforcing bar','Reduction or loss in reinforcing bar','Rebar loss','mm','%','Carbonation');
/*!40000 ALTER TABLE `engineering_model_variable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES (1,'East Coast South'),(2,'Southern Slopes Vic East'),(3,'Southern and South-Western Flatlands');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seaport`
--

DROP TABLE IF EXISTS `seaport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seaport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK755195503365D05` (`region_id`),
  CONSTRAINT `FK755195503365D05` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seaport`
--

LOCK TABLES `seaport` WRITE;
/*!40000 ALTER TABLE `seaport` DISABLE KEYS */;
INSERT INTO `seaport` VALUES (1,'PORT 1',1),(2,'PORT 2',1),(3,'PORT 3',1),(4,'PORT 6',3),(5,'PORT 7',3),(6,NULL,NULL),(7,NULL,NULL);
/*!40000 ALTER TABLE `seaport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `non_locked` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('testadmin1','email@company.com','','testadmin1','testadmin1','','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','ROLE_ADMIN'),('testadmin2','email@company.com','','testadmin2','testadmin2','','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','ROLE_ADMIN'),('testuser1','email@company.com','','testuser1','testuser1','','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','ROLE_USER'),('testuser2','email@company.com','','testuser2','testuser2','','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','ROLE_USER'),('testuser3','email@company.com','','testuser3','testuser3','','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','ROLE_USER'),('testuser4','email@company.com','','testuser4','testuser4','','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','ROLE_USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_story`
--

DROP TABLE IF EXISTS `user_story`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access` varchar(255) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `owner_login` varchar(255) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK735303813365D05` (`region_id`),
  KEY `FK73530381BFE5533` (`owner_login`),
  CONSTRAINT `FK73530381BFE5533` FOREIGN KEY (`owner_login`) REFERENCES `user` (`username`),
  CONSTRAINT `FK735303813365D05` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_story`
--

LOCK TABLES `user_story` WRITE;
/*!40000 ALTER TABLE `user_story` DISABLE KEYS */;
INSERT INTO `user_story` VALUES (1,'private','active','User 1 Workboard','testuser1',1),(2,'private','passive','User 1 Story 1','testuser1',2),(3,'public','passive','User 1 Story 2 (Public)','testuser1',1),(4,'public','published','User 1 Story 3 (Published)','testuser1',1),(5,'private','active','User 2 Workboard (Empty)','testuser2',2),(6,'private','passive','User 2 Story (Empty)','testuser2',1);
/*!40000 ALTER TABLE `user_story` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weather_event`
--

DROP TABLE IF EXISTS `weather_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weather_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consequences` varchar(255) DEFAULT NULL,
  `consequences_rating` varchar(255) DEFAULT NULL,
  `direct` bit(1) DEFAULT NULL,
  `impact` varchar(255) DEFAULT NULL,
  `response` varchar(255) DEFAULT NULL,
  `response_adequate` bit(1) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weather_event`
--

LOCK TABLES `weather_event` WRITE;
/*!40000 ALTER TABLE `weather_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `weather_event` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-04-03 10:18:09
