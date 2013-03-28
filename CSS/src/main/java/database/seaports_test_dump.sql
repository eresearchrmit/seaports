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
INSERT INTO `cmar_data` VALUES (1,'2013-03-28 11:53:54',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,3,5),(2,'2013-03-28 11:53:54',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,3,5),(3,'2013-03-28 11:53:54',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,9,5),(4,'2013-03-28 11:53:54',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,9,5),(5,'2013-03-28 11:53:54',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,15,5),(6,'2013-03-28 11:53:54',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,15,5);
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
INSERT INTO `csiro_data` VALUES (1,'2013-03-28 11:53:54',NULL,0.6135318764198925,2030,1,1),(2,'2013-03-28 11:53:54',NULL,0.9180569255368324,2055,1,1),(3,'2013-03-28 11:53:54',NULL,0.5417093485081614,2070,1,1),(4,'2013-03-28 11:53:54',NULL,0.37050303039730725,2030,1,2),(5,'2013-03-28 11:53:54',NULL,0.5143176966407285,2055,1,2),(6,'2013-03-28 11:53:54',NULL,0.45467606963505913,2070,1,2),(7,'2013-03-28 11:53:54',NULL,0.31283549836601465,2030,1,3),(8,'2013-03-28 11:53:54',NULL,0.8003852699817833,2055,1,3),(9,'2013-03-28 11:53:54',NULL,0.46258763105520995,2070,1,3),(10,'2013-03-28 11:53:54',NULL,0.46455642373606465,2030,1,4),(11,'2013-03-28 11:53:54',NULL,0.7284394960415045,2055,1,4),(12,'2013-03-28 11:53:54',NULL,0.5869518578659485,2070,1,4),(13,'2013-03-28 11:53:54',NULL,0.849000586336085,2030,2,1),(14,'2013-03-28 11:53:54',NULL,0.26727550077099005,2055,2,1),(15,'2013-03-28 11:53:54',NULL,0.6053306818351962,2070,2,1),(16,'2013-03-28 11:53:54',NULL,0.9857549553057782,2030,2,2),(17,'2013-03-28 11:53:54',NULL,0.10011429718417486,2055,2,2),(18,'2013-03-28 11:53:54',NULL,0.5148768141538599,2070,2,2),(19,'2013-03-28 11:53:54',NULL,0.30369211842324817,2030,2,3),(20,'2013-03-28 11:53:54',NULL,0.09920903276053528,2055,2,3),(21,'2013-03-28 11:53:54',NULL,0.8958157264212493,2070,2,3),(22,'2013-03-28 11:53:54',NULL,0.3313404821055699,2030,2,4),(23,'2013-03-28 11:53:54',NULL,0.6037483776700467,2055,2,4),(24,'2013-03-28 11:53:54',NULL,0.8836919861784303,2070,2,4),(25,'2013-03-28 11:53:54',NULL,0.28122255477024394,2030,3,1),(26,'2013-03-28 11:53:54',NULL,0.21771792531781087,2055,3,1),(27,'2013-03-28 11:53:54',NULL,0.07726150948421784,2070,3,1),(28,'2013-03-28 11:53:54',NULL,0.8738636642324211,2030,3,2),(29,'2013-03-28 11:53:54',NULL,0.16307671842065263,2055,3,2),(30,'2013-03-28 11:53:54',NULL,0.6282346126626155,2070,3,2),(31,'2013-03-28 11:53:54',NULL,0.5888956009607477,2030,3,3),(32,'2013-03-28 11:53:54',NULL,0.006556553615885807,2055,3,3),(33,'2013-03-28 11:53:54',NULL,0.1660020739520255,2070,3,3),(34,'2013-03-28 11:53:54',NULL,0.5845790189092793,2030,3,4),(35,'2013-03-28 11:53:54',NULL,0.5634665330596507,2055,3,4),(36,'2013-03-28 11:53:54',NULL,0.5291325240487585,2070,3,4),(37,'2013-03-28 11:53:54',NULL,0.5906002210063699,2030,4,1),(38,'2013-03-28 11:53:54',NULL,0.6887594212114315,2055,4,1),(39,'2013-03-28 11:53:54',NULL,0.4373206672796359,2070,4,1),(40,'2013-03-28 11:53:54',NULL,0.6109883376113173,2030,4,2),(41,'2013-03-28 11:53:54',NULL,0.26320987765054105,2055,4,2),(42,'2013-03-28 11:53:54',NULL,0.41932536596692294,2070,4,2),(43,'2013-03-28 11:53:54',NULL,0.2755621898332862,2030,4,3),(44,'2013-03-28 11:53:54',NULL,0.9419319575836377,2055,4,3),(45,'2013-03-28 11:53:54',NULL,0.3962568608209611,2070,4,3),(46,'2013-03-28 11:53:54',NULL,0.6514132929975552,2030,4,4),(47,'2013-03-28 11:53:54',NULL,0.8815757024739865,2055,4,4),(48,'2013-03-28 11:53:54',NULL,0.2974405964494824,2070,4,4),(49,'2013-03-28 11:53:54',NULL,0.188472989141785,2030,5,1),(50,'2013-03-28 11:53:54',NULL,0.9279912891636607,2055,5,1),(51,'2013-03-28 11:53:54',NULL,0.8512671256666786,2070,5,1),(52,'2013-03-28 11:53:54',NULL,0.778350591676323,2030,5,2),(53,'2013-03-28 11:53:54',NULL,0.7858645580677438,2055,5,2),(54,'2013-03-28 11:53:54',NULL,0.1278405215731232,2070,5,2),(55,'2013-03-28 11:53:54',NULL,0.9316728609960048,2030,5,3),(56,'2013-03-28 11:53:54',NULL,0.19131817407578156,2055,5,3),(57,'2013-03-28 11:53:54',NULL,0.39716135318397805,2070,5,3),(58,'2013-03-28 11:53:54',NULL,0.825226561732385,2030,5,4),(59,'2013-03-28 11:53:54',NULL,0.7320628660434839,2055,5,4),(60,'2013-03-28 11:53:54',NULL,0.8264292652019345,2070,5,4),(61,'2013-03-28 11:53:54',NULL,0.8115802200676324,2030,6,1),(62,'2013-03-28 11:53:54',NULL,0.8168763723364354,2055,6,1),(63,'2013-03-28 11:53:54',NULL,0.30539094078268025,2070,6,1),(64,'2013-03-28 11:53:54',NULL,0.7096257861725757,2030,6,2),(65,'2013-03-28 11:53:54',NULL,0.30208739554322794,2055,6,2),(66,'2013-03-28 11:53:54',NULL,0.003791924852768025,2070,6,2),(67,'2013-03-28 11:53:54',NULL,0.36892142925111016,2030,6,3),(68,'2013-03-28 11:53:54',NULL,0.26015428409965824,2055,6,3),(69,'2013-03-28 11:53:54',NULL,0.9139463110499368,2070,6,3),(70,'2013-03-28 11:53:54',NULL,0.05859470376667142,2030,6,4),(71,'2013-03-28 11:53:54',NULL,0.9650880284409947,2055,6,4),(72,'2013-03-28 11:53:54',NULL,0.16703285991385264,2070,6,4),(73,'2013-03-28 11:53:54',NULL,0.4361886639924111,2030,7,1),(74,'2013-03-28 11:53:54',NULL,0.08351017449021692,2055,7,1),(75,'2013-03-28 11:53:54',NULL,0.17711745310677174,2070,7,1),(76,'2013-03-28 11:53:54',NULL,0.21357025534047824,2030,7,2),(77,'2013-03-28 11:53:54',NULL,0.9327483780361274,2055,7,2),(78,'2013-03-28 11:53:54',NULL,0.7697642341290066,2070,7,2),(79,'2013-03-28 11:53:54',NULL,0.10867690000403507,2030,7,3),(80,'2013-03-28 11:53:54',NULL,0.7252610272806823,2055,7,3),(81,'2013-03-28 11:53:54',NULL,0.5703494313392483,2070,7,3),(82,'2013-03-28 11:53:54',NULL,0.40607925750215734,2030,7,4),(83,'2013-03-28 11:53:54',NULL,0.7127808247486643,2055,7,4),(84,'2013-03-28 11:53:54',NULL,0.2939189517568047,2070,7,4),(85,'2013-03-28 11:53:54',NULL,0.6218400485345377,2030,8,1),(86,'2013-03-28 11:53:54',NULL,0.4521197686680881,2055,8,1),(87,'2013-03-28 11:53:54',NULL,0.21241929530124526,2070,8,1),(88,'2013-03-28 11:53:54',NULL,0.43970070337074185,2030,8,2),(89,'2013-03-28 11:53:54',NULL,0.946517874921743,2055,8,2),(90,'2013-03-28 11:53:54',NULL,0.29215322450567327,2070,8,2),(91,'2013-03-28 11:53:54',NULL,0.7938465666974887,2030,8,3),(92,'2013-03-28 11:53:54',NULL,0.9127101120387001,2055,8,3),(93,'2013-03-28 11:53:54',NULL,0.9530913042335298,2070,8,3),(94,'2013-03-28 11:53:54',NULL,0.2148545719226651,2030,8,4),(95,'2013-03-28 11:53:54',NULL,0.9731952175136618,2055,8,4),(96,'2013-03-28 11:53:54',NULL,0.4071629166395778,2070,8,4),(97,'2013-03-28 11:53:54',NULL,0.036274780182179445,2030,9,1),(98,'2013-03-28 11:53:54',NULL,0.6270976059815532,2055,9,1),(99,'2013-03-28 11:53:54',NULL,0.5380576930375597,2070,9,1),(100,'2013-03-28 11:53:54',NULL,0.5636308140835935,2030,9,2),(101,'2013-03-28 11:53:54',NULL,0.7446166879721282,2055,9,2),(102,'2013-03-28 11:53:54',NULL,0.002740282135717531,2070,9,2),(103,'2013-03-28 11:53:54',NULL,0.24790357712304079,2030,9,3),(104,'2013-03-28 11:53:54',NULL,0.2686725915388851,2055,9,3),(105,'2013-03-28 11:53:54',NULL,0.4850102560979801,2070,9,3),(106,'2013-03-28 11:53:54',NULL,0.0918252602744446,2030,9,4),(107,'2013-03-28 11:53:54',NULL,0.540677721867503,2055,9,4),(108,'2013-03-28 11:53:54',NULL,0.42619931855360615,2070,9,4),(109,'2013-03-28 11:53:54',NULL,0.3984396132540923,2030,10,1),(110,'2013-03-28 11:53:54',NULL,0.4712804108318509,2055,10,1),(111,'2013-03-28 11:53:54',NULL,0.337650547892562,2070,10,1),(112,'2013-03-28 11:53:54',NULL,0.5712096218528262,2030,10,2),(113,'2013-03-28 11:53:54',NULL,0.1518645069381681,2055,10,2),(114,'2013-03-28 11:53:54',NULL,0.6051191929089433,2070,10,2),(115,'2013-03-28 11:53:54',NULL,0.6645688335412565,2030,10,3),(116,'2013-03-28 11:53:54',NULL,0.9240699090686678,2055,10,3),(117,'2013-03-28 11:53:54',NULL,0.7827487704827614,2070,10,3),(118,'2013-03-28 11:53:54',NULL,0.20030377238223318,2030,10,4),(119,'2013-03-28 11:53:54',NULL,0.21438155920724333,2055,10,4),(120,'2013-03-28 11:53:54',NULL,0.7423039426482497,2070,10,4),(121,'2013-03-28 11:53:54',NULL,0.40558917047547305,2030,11,1),(122,'2013-03-28 11:53:54',NULL,0.793848343727255,2055,11,1),(123,'2013-03-28 11:53:54',NULL,0.16529193616628768,2070,11,1),(124,'2013-03-28 11:53:54',NULL,0.1168947078459327,2030,11,2),(125,'2013-03-28 11:53:54',NULL,0.5439032089645783,2055,11,2),(126,'2013-03-28 11:53:54',NULL,0.18740431131193647,2070,11,2),(127,'2013-03-28 11:53:54',NULL,0.5376136276878788,2030,11,3),(128,'2013-03-28 11:53:54',NULL,0.6813908720846062,2055,11,3),(129,'2013-03-28 11:53:54',NULL,0.08648515536412027,2070,11,3),(130,'2013-03-28 11:53:54',NULL,0.5574826550037405,2030,11,4),(131,'2013-03-28 11:53:54',NULL,0.12388515687096313,2055,11,4),(132,'2013-03-28 11:53:54',NULL,0.054649134774707786,2070,11,4),(133,'2013-03-28 11:53:54',NULL,0.38334221551849135,2030,12,1),(134,'2013-03-28 11:53:54',NULL,0.8126330686729331,2055,12,1),(135,'2013-03-28 11:53:54',NULL,0.5589238555490872,2070,12,1),(136,'2013-03-28 11:53:54',NULL,0.2218328937465187,2030,12,2),(137,'2013-03-28 11:53:54',NULL,0.7945734654615981,2055,12,2),(138,'2013-03-28 11:53:54',NULL,0.043313737416946885,2070,12,2),(139,'2013-03-28 11:53:54',NULL,0.40997650621792225,2030,12,3),(140,'2013-03-28 11:53:54',NULL,0.47332662365895284,2055,12,3),(141,'2013-03-28 11:53:54',NULL,0.08455609935256392,2070,12,3),(142,'2013-03-28 11:53:54',NULL,0.7158490458102486,2030,12,4),(143,'2013-03-28 11:53:54',NULL,0.2618506421530892,2055,12,4),(144,'2013-03-28 11:53:54',NULL,0.9675142923058346,2070,12,4),(145,'2013-03-28 11:53:54',NULL,0.41415515987128115,2030,13,1),(146,'2013-03-28 11:53:54',NULL,0.5987874544850185,2055,13,1),(147,'2013-03-28 11:53:54',NULL,0.647887554650358,2070,13,1),(148,'2013-03-28 11:53:54',NULL,0.46090964359617426,2030,13,2),(149,'2013-03-28 11:53:54',NULL,0.32527433932112093,2055,13,2),(150,'2013-03-28 11:53:54',NULL,0.2781349676148458,2070,13,2),(151,'2013-03-28 11:53:54',NULL,0.33486359269199495,2030,13,3),(152,'2013-03-28 11:53:54',NULL,0.25791470141741035,2055,13,3),(153,'2013-03-28 11:53:54',NULL,0.03491682472549473,2070,13,3),(154,'2013-03-28 11:53:54',NULL,0.5173544070689613,2030,13,4),(155,'2013-03-28 11:53:54',NULL,0.9083839354032778,2055,13,4),(156,'2013-03-28 11:53:54',NULL,0.9725224231579347,2070,13,4),(157,'2013-03-28 11:53:54',NULL,0.560027374587114,2030,14,1),(158,'2013-03-28 11:53:54',NULL,0.648684189500679,2055,14,1),(159,'2013-03-28 11:53:54',NULL,0.825058767307767,2070,14,1),(160,'2013-03-28 11:53:54',NULL,0.26556793940255163,2030,14,2),(161,'2013-03-28 11:53:54',NULL,0.6797374894190626,2055,14,2),(162,'2013-03-28 11:53:54',NULL,0.6442448397153319,2070,14,2),(163,'2013-03-28 11:53:54',NULL,0.09872907318239821,2030,14,3),(164,'2013-03-28 11:53:54',NULL,0.32367243279138325,2055,14,3),(165,'2013-03-28 11:53:54',NULL,0.5082379062071648,2070,14,3),(166,'2013-03-28 11:53:54',NULL,0.5085651152779409,2030,14,4),(167,'2013-03-28 11:53:54',NULL,0.5702350236146417,2055,14,4),(168,'2013-03-28 11:53:54',NULL,0.4798123927900353,2070,14,4),(169,'2013-03-28 11:53:54',NULL,0.49935365388556485,2030,15,1),(170,'2013-03-28 11:53:54',NULL,0.15857410690501628,2055,15,1),(171,'2013-03-28 11:53:54',NULL,0.2736973777258357,2070,15,1),(172,'2013-03-28 11:53:54',NULL,0.04048620937154046,2030,15,2),(173,'2013-03-28 11:53:54',NULL,0.032998594160999484,2055,15,2),(174,'2013-03-28 11:53:54',NULL,0.01276925840123555,2070,15,2),(175,'2013-03-28 11:53:54',NULL,0.7570080884825937,2030,15,3),(176,'2013-03-28 11:53:54',NULL,0.08073907739947672,2055,15,3),(177,'2013-03-28 11:53:54',NULL,0.3440384266815726,2070,15,3),(178,'2013-03-28 11:53:54',NULL,0.09818487080238247,2030,15,4),(179,'2013-03-28 11:53:54',NULL,0.3402032791995063,2055,15,4),(180,'2013-03-28 11:53:54',NULL,0.6958462281464566,2070,15,4),(181,'2013-03-28 11:53:54',NULL,0.4456916820666078,2030,16,1),(182,'2013-03-28 11:53:54',NULL,0.6019491871048273,2055,16,1),(183,'2013-03-28 11:53:54',NULL,0.20261850692739736,2070,16,1),(184,'2013-03-28 11:53:54',NULL,0.9572437338945183,2030,16,2),(185,'2013-03-28 11:53:54',NULL,0.8724509859856738,2055,16,2),(186,'2013-03-28 11:53:54',NULL,0.44363250384759845,2070,16,2),(187,'2013-03-28 11:53:54',NULL,0.8782020688699176,2030,16,3),(188,'2013-03-28 11:53:54',NULL,0.6103348306880707,2055,16,3),(189,'2013-03-28 11:53:54',NULL,0.5216073968499334,2070,16,3),(190,'2013-03-28 11:53:54',NULL,0.6252528658833095,2030,16,4),(191,'2013-03-28 11:53:54',NULL,0.20808961967813355,2055,16,4),(192,'2013-03-28 11:53:54',NULL,0.3730892290516321,2070,16,4),(193,'2013-03-28 11:53:54',NULL,0.6078128326641308,2030,17,1),(194,'2013-03-28 11:53:54',NULL,0.8277197113242071,2055,17,1),(195,'2013-03-28 11:53:54',NULL,0.050519033763970156,2070,17,1),(196,'2013-03-28 11:53:54',NULL,0.5468915788147625,2030,17,2),(197,'2013-03-28 11:53:54',NULL,0.7617170504912029,2055,17,2),(198,'2013-03-28 11:53:54',NULL,0.9656602091467504,2070,17,2),(199,'2013-03-28 11:53:54',NULL,0.08039181412524032,2030,17,3),(200,'2013-03-28 11:53:54',NULL,0.5348843887040133,2055,17,3),(201,'2013-03-28 11:53:54',NULL,0.6015668165419855,2070,17,3),(202,'2013-03-28 11:53:54',NULL,0.18698672058487942,2030,17,4),(203,'2013-03-28 11:53:54',NULL,0.8242631244309367,2055,17,4),(204,'2013-03-28 11:53:54',NULL,0.7780901399559721,2070,17,4),(205,'2013-03-28 11:53:54',NULL,0.6857571621254778,2030,18,1),(206,'2013-03-28 11:53:54',NULL,0.10922839676156104,2055,18,1),(207,'2013-03-28 11:53:54',NULL,0.6712696845076954,2070,18,1),(208,'2013-03-28 11:53:54',NULL,0.5658855857604249,2030,18,2),(209,'2013-03-28 11:53:54',NULL,0.6146754422843584,2055,18,2),(210,'2013-03-28 11:53:54',NULL,0.9215632979365972,2070,18,2),(211,'2013-03-28 11:53:54',NULL,0.9764812496354649,2030,18,3),(212,'2013-03-28 11:53:54',NULL,0.15494121548591067,2055,18,3),(213,'2013-03-28 11:53:54',NULL,0.1917809672037908,2070,18,3),(214,'2013-03-28 11:53:54',NULL,0.3496522366927224,2030,18,4),(215,'2013-03-28 11:53:54',NULL,0.5303875488303081,2055,18,4),(216,'2013-03-28 11:53:54',NULL,0.639527350819498,2070,18,4);
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
INSERT INTO `csiro_data_baseline` VALUES (1,'2013-03-28 11:53:54',16.1,1,1),(2,'2013-03-28 11:53:54',1,1,2),(3,'2013-03-28 11:53:54',1029.8,1,3),(4,'2013-03-28 11:53:54',74.8,1,4);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_element`
--

LOCK TABLES `data_element` WRITE;
/*!40000 ALTER TABLE `data_element` DISABLE KEYS */;
INSERT INTO `data_element` VALUES ('File',1,'2013-03-28 11:53:54','','Test 1',0,'This is a test for Data Element','csv',NULL,NULL,1,NULL),('File',2,'2013-03-28 11:53:54','','Test 2',0,'This is the second test','xml',NULL,NULL,1,NULL),('File',3,'2013-03-28 11:53:54','','Test 3',0,'This is the third test','txt',NULL,NULL,1,NULL),('File',4,'2013-03-28 11:53:54','','Test 4',1,'User Story\'s Data Element content test','txt',NULL,NULL,2,NULL),('Text',5,'2013-03-28 11:53:54','','Comment text 1',2,NULL,NULL,NULL,'This is a text comment',2,NULL),('File',6,'2013-03-28 11:53:54','','Test 5',3,'User Story\'s Data Element content test','txt',NULL,NULL,2,NULL),('Text',7,'2013-03-28 11:53:54','','Comment text 2',4,NULL,NULL,NULL,'This is a second text comment',2,NULL),('File',8,'2013-03-28 11:53:54','','Test 6',1,'User Story\'s Data Element content test','txt',NULL,NULL,3,NULL),('Csiro',9,'2013-03-28 11:53:54','','CSIRO Data Element Test',0,NULL,NULL,'',NULL,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
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

-- Dump completed on 2013-03-28 12:17:31
