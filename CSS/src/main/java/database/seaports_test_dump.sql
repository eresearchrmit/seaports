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
-- Table structure for table `climateemissionscenario`
--

DROP TABLE IF EXISTS `climateemissionscenario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `climateemissionscenario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `climateemissionscenario`
--

LOCK TABLES `climateemissionscenario` WRITE;
/*!40000 ALTER TABLE `climateemissionscenario` DISABLE KEYS */;
INSERT INTO `climateemissionscenario` VALUES (1,'No CO2 emissions increase','Base'),(2,'medium','A1B'),(3,'high','A1FI');
/*!40000 ALTER TABLE `climateemissionscenario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `climatemodel`
--

DROP TABLE IF EXISTS `climatemodel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `climatemodel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `climatemodel`
--

LOCK TABLES `climatemodel` WRITE;
/*!40000 ALTER TABLE `climatemodel` DISABLE KEYS */;
INSERT INTO `climatemodel` VALUES (1,'reference','Reference climate model: considering there is no change','(Reference)'),(2,'csiro_mk3_5','The CSIRO MK 3.5 climate model','CSIRO Mk3.5'),(3,'mri_cgcm2_3_2','The MRI-CGCM 2.3.2 climate model','MRI-CGCM2.3.2'),(4,'ipsl_cm4','The IPSL CM4 climate model','IPSL CM4'),(5,'miroc_3_2_medres','The Miroc 3.2 MedRes climate model','MIROC-Medres');
/*!40000 ALTER TABLE `climatemodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `climateparams`
--

DROP TABLE IF EXISTS `climateparams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `climateparams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modelName` varchar(255) DEFAULT NULL,
  `climate_emission_scenario_id` int(11) DEFAULT NULL,
  `climate_model_id` int(11) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6D2A9DEBF00AAA5A` (`climate_model_id`),
  KEY `FK6D2A9DEB1E25895D` (`climate_emission_scenario_id`),
  KEY `FK6D2A9DEB3365D05` (`region_id`),
  CONSTRAINT `FK6D2A9DEB3365D05` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `FK6D2A9DEB1E25895D` FOREIGN KEY (`climate_emission_scenario_id`) REFERENCES `climateemissionscenario` (`id`),
  CONSTRAINT `FK6D2A9DEBF00AAA5A` FOREIGN KEY (`climate_model_id`) REFERENCES `climatemodel` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `climateparams`
--

LOCK TABLES `climateparams` WRITE;
/*!40000 ALTER TABLE `climateparams` DISABLE KEYS */;
INSERT INTO `climateparams` VALUES (1,'Hotter and Drier',2,2,1),(2,'Hotter and Drier',3,2,1),(3,'Most Likely',2,4,1),(4,'Most Likely',3,4,1),(5,'Colder and Wetter',2,5,1),(6,'Colder and Wetter',3,5,1),(7,'Hotter and Drier',2,5,2),(8,'Hotter and Drier',3,5,2),(9,'Most Likely',2,2,2),(10,'Most Likely',3,2,2),(11,'Colder and Wetter',2,4,2),(12,'Colder and Wetter',3,4,2),(13,'Hotter and Drier',2,5,3),(14,'Hotter and Drier',3,5,3),(15,'Most Likely',2,2,3),(16,'Most Likely',3,2,3),(17,'Colder and Wetter',2,4,3),(18,'Colder and Wetter',3,4,3);
/*!40000 ALTER TABLE `climateparams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmardata`
--

DROP TABLE IF EXISTS `cmardata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmardata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `picture` longblob,
  `value` longtext,
  `year` int(11) DEFAULT NULL,
  `climate_params_id` int(11) DEFAULT NULL,
  `climate_variable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8CBD7C65201EEBA` (`climate_params_id`),
  KEY `FK8CBD7C65B7D2D10B` (`climate_variable_id`),
  CONSTRAINT `FK8CBD7C65B7D2D10B` FOREIGN KEY (`climate_variable_id`) REFERENCES `csirovariable` (`id`),
  CONSTRAINT `FK8CBD7C65201EEBA` FOREIGN KEY (`climate_params_id`) REFERENCES `climateparams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmardata`
--

LOCK TABLES `cmardata` WRITE;
/*!40000 ALTER TABLE `cmardata` DISABLE KEYS */;
INSERT INTO `cmardata` VALUES (1,'2013-03-26 14:49:47',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,3,5),(2,'2013-03-26 14:49:47',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,3,5),(3,'2013-03-26 14:49:47',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,9,5),(4,'2013-03-26 14:49:47',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,9,5),(5,'2013-03-26 14:49:47',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,15,5),(6,'2013-03-26 14:49:47',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,15,5);
/*!40000 ALTER TABLE `cmardata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `csirodata`
--

DROP TABLE IF EXISTS `csirodata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `csirodata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `picture` longblob,
  `value` double DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `climate_params_id` int(11) DEFAULT NULL,
  `climate_variable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK21F886A0201EEBA` (`climate_params_id`),
  KEY `FK21F886A0B7D2D10B` (`climate_variable_id`),
  CONSTRAINT `FK21F886A0B7D2D10B` FOREIGN KEY (`climate_variable_id`) REFERENCES `csirovariable` (`id`),
  CONSTRAINT `FK21F886A0201EEBA` FOREIGN KEY (`climate_params_id`) REFERENCES `climateparams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `csirodata`
--

LOCK TABLES `csirodata` WRITE;
/*!40000 ALTER TABLE `csirodata` DISABLE KEYS */;
INSERT INTO `csirodata` VALUES (1,'2013-03-26 14:49:47',NULL,0.7589763807828285,2030,1,1),(2,'2013-03-26 14:49:47',NULL,0.9619916740482758,2055,1,1),(3,'2013-03-26 14:49:47',NULL,0.28090177464865207,2070,1,1),(4,'2013-03-26 14:49:47',NULL,0.663782334823228,2030,1,2),(5,'2013-03-26 14:49:47',NULL,0.39583935667291414,2055,1,2),(6,'2013-03-26 14:49:47',NULL,0.709401739765448,2070,1,2),(7,'2013-03-26 14:49:47',NULL,0.9797904239620479,2030,1,3),(8,'2013-03-26 14:49:47',NULL,0.42381840909052393,2055,1,3),(9,'2013-03-26 14:49:47',NULL,0.9793481124875852,2070,1,3),(10,'2013-03-26 14:49:47',NULL,0.7431022721693831,2030,1,4),(11,'2013-03-26 14:49:47',NULL,0.1592516962898768,2055,1,4),(12,'2013-03-26 14:49:47',NULL,0.3429483013206467,2070,1,4),(13,'2013-03-26 14:49:47',NULL,0.7485314352560282,2030,2,1),(14,'2013-03-26 14:49:47',NULL,0.35516238531862,2055,2,1),(15,'2013-03-26 14:49:47',NULL,0.6002099824871703,2070,2,1),(16,'2013-03-26 14:49:47',NULL,0.05563974247208925,2030,2,2),(17,'2013-03-26 14:49:47',NULL,0.47174755638343646,2055,2,2),(18,'2013-03-26 14:49:47',NULL,0.17854823061121694,2070,2,2),(19,'2013-03-26 14:49:47',NULL,0.7361505630865601,2030,2,3),(20,'2013-03-26 14:49:47',NULL,0.2924397622807965,2055,2,3),(21,'2013-03-26 14:49:47',NULL,0.22444481419426143,2070,2,3),(22,'2013-03-26 14:49:47',NULL,0.10644990316505176,2030,2,4),(23,'2013-03-26 14:49:47',NULL,0.347208905176566,2055,2,4),(24,'2013-03-26 14:49:47',NULL,0.8158388877524365,2070,2,4),(25,'2013-03-26 14:49:47',NULL,0.15486097800080934,2030,3,1),(26,'2013-03-26 14:49:47',NULL,0.06872631793795547,2055,3,1),(27,'2013-03-26 14:49:47',NULL,0.47280522475549547,2070,3,1),(28,'2013-03-26 14:49:47',NULL,0.7784537986255986,2030,3,2),(29,'2013-03-26 14:49:47',NULL,0.7284147806350549,2055,3,2),(30,'2013-03-26 14:49:47',NULL,0.5226756795697258,2070,3,2),(31,'2013-03-26 14:49:47',NULL,0.002815180088698299,2030,3,3),(32,'2013-03-26 14:49:47',NULL,0.3807563688869916,2055,3,3),(33,'2013-03-26 14:49:47',NULL,0.26283163588777636,2070,3,3),(34,'2013-03-26 14:49:47',NULL,0.7506918672009736,2030,3,4),(35,'2013-03-26 14:49:47',NULL,0.6191826313950435,2055,3,4),(36,'2013-03-26 14:49:47',NULL,0.45655309949284584,2070,3,4),(37,'2013-03-26 14:49:47',NULL,0.9185850555448835,2030,4,1),(38,'2013-03-26 14:49:47',NULL,0.5739678850292271,2055,4,1),(39,'2013-03-26 14:49:47',NULL,0.5030440400781516,2070,4,1),(40,'2013-03-26 14:49:47',NULL,0.14664620304389098,2030,4,2),(41,'2013-03-26 14:49:47',NULL,0.6930238713358553,2055,4,2),(42,'2013-03-26 14:49:47',NULL,0.8892592329144416,2070,4,2),(43,'2013-03-26 14:49:47',NULL,0.4968477023094342,2030,4,3),(44,'2013-03-26 14:49:47',NULL,0.13458359248405793,2055,4,3),(45,'2013-03-26 14:49:47',NULL,0.38159493236332276,2070,4,3),(46,'2013-03-26 14:49:47',NULL,0.5148220179963936,2030,4,4),(47,'2013-03-26 14:49:47',NULL,0.21661612208065206,2055,4,4),(48,'2013-03-26 14:49:47',NULL,0.013131809305679143,2070,4,4),(49,'2013-03-26 14:49:47',NULL,0.9158597152278422,2030,5,1),(50,'2013-03-26 14:49:47',NULL,0.19199144242510302,2055,5,1),(51,'2013-03-26 14:49:47',NULL,0.9464129261006102,2070,5,1),(52,'2013-03-26 14:49:47',NULL,0.22608265288024731,2030,5,2),(53,'2013-03-26 14:49:47',NULL,0.9655421908709665,2055,5,2),(54,'2013-03-26 14:49:47',NULL,0.2304687504495353,2070,5,2),(55,'2013-03-26 14:49:47',NULL,0.015240204578529148,2030,5,3),(56,'2013-03-26 14:49:47',NULL,0.6872305837658711,2055,5,3),(57,'2013-03-26 14:49:47',NULL,0.2006348457723074,2070,5,3),(58,'2013-03-26 14:49:47',NULL,0.20823537299233041,2030,5,4),(59,'2013-03-26 14:49:47',NULL,0.7560062249750518,2055,5,4),(60,'2013-03-26 14:49:47',NULL,0.6168343470076011,2070,5,4),(61,'2013-03-26 14:49:47',NULL,0.1496283015235026,2030,6,1),(62,'2013-03-26 14:49:47',NULL,0.34111535633090484,2055,6,1),(63,'2013-03-26 14:49:47',NULL,0.0768674678386364,2070,6,1),(64,'2013-03-26 14:49:47',NULL,0.35560375452722226,2030,6,2),(65,'2013-03-26 14:49:47',NULL,0.1612194680159782,2055,6,2),(66,'2013-03-26 14:49:47',NULL,0.622358342585021,2070,6,2),(67,'2013-03-26 14:49:47',NULL,0.13716296428721986,2030,6,3),(68,'2013-03-26 14:49:47',NULL,0.917227751427859,2055,6,3),(69,'2013-03-26 14:49:47',NULL,0.5793918431831044,2070,6,3),(70,'2013-03-26 14:49:47',NULL,0.5992167393834914,2030,6,4),(71,'2013-03-26 14:49:47',NULL,0.613323018245983,2055,6,4),(72,'2013-03-26 14:49:47',NULL,0.8226875973920368,2070,6,4),(73,'2013-03-26 14:49:47',NULL,0.8755509265468925,2030,7,1),(74,'2013-03-26 14:49:47',NULL,0.7013428554933623,2055,7,1),(75,'2013-03-26 14:49:47',NULL,0.5645485732118326,2070,7,1),(76,'2013-03-26 14:49:47',NULL,0.3230750639057137,2030,7,2),(77,'2013-03-26 14:49:47',NULL,0.1464367016535102,2055,7,2),(78,'2013-03-26 14:49:47',NULL,0.3649327213904323,2070,7,2),(79,'2013-03-26 14:49:47',NULL,0.963721181740838,2030,7,3),(80,'2013-03-26 14:49:47',NULL,0.46236205205664904,2055,7,3),(81,'2013-03-26 14:49:47',NULL,0.08282398735799767,2070,7,3),(82,'2013-03-26 14:49:47',NULL,0.8068953684102868,2030,7,4),(83,'2013-03-26 14:49:47',NULL,0.5459180673485452,2055,7,4),(84,'2013-03-26 14:49:47',NULL,0.5575450188367937,2070,7,4),(85,'2013-03-26 14:49:47',NULL,0.2415610241040077,2030,8,1),(86,'2013-03-26 14:49:47',NULL,0.19492752538972768,2055,8,1),(87,'2013-03-26 14:49:47',NULL,0.7450706131563626,2070,8,1),(88,'2013-03-26 14:49:47',NULL,0.580813378393954,2030,8,2),(89,'2013-03-26 14:49:47',NULL,0.21228507129285934,2055,8,2),(90,'2013-03-26 14:49:47',NULL,0.1322255126068428,2070,8,2),(91,'2013-03-26 14:49:47',NULL,0.13871843996234634,2030,8,3),(92,'2013-03-26 14:49:47',NULL,0.6168686732658409,2055,8,3),(93,'2013-03-26 14:49:47',NULL,0.06019338502999261,2070,8,3),(94,'2013-03-26 14:49:47',NULL,0.8296676752842952,2030,8,4),(95,'2013-03-26 14:49:47',NULL,0.0014758084209214317,2055,8,4),(96,'2013-03-26 14:49:47',NULL,0.03824681067973623,2070,8,4),(97,'2013-03-26 14:49:47',NULL,0.24753681462740407,2030,9,1),(98,'2013-03-26 14:49:47',NULL,0.07365191352778222,2055,9,1),(99,'2013-03-26 14:49:47',NULL,0.27974060754401575,2070,9,1),(100,'2013-03-26 14:49:47',NULL,0.2924731134315457,2030,9,2),(101,'2013-03-26 14:49:47',NULL,0.08904225059402426,2055,9,2),(102,'2013-03-26 14:49:47',NULL,0.11437495422521038,2070,9,2),(103,'2013-03-26 14:49:47',NULL,0.5433408354541275,2030,9,3),(104,'2013-03-26 14:49:47',NULL,0.2271056332798339,2055,9,3),(105,'2013-03-26 14:49:47',NULL,0.6802592019564625,2070,9,3),(106,'2013-03-26 14:49:47',NULL,0.8971910955856559,2030,9,4),(107,'2013-03-26 14:49:47',NULL,0.24422677953640648,2055,9,4),(108,'2013-03-26 14:49:47',NULL,0.6921997977903654,2070,9,4),(109,'2013-03-26 14:49:47',NULL,0.152865204445627,2030,10,1),(110,'2013-03-26 14:49:47',NULL,0.6387901439992669,2055,10,1),(111,'2013-03-26 14:49:47',NULL,0.9179819675783192,2070,10,1),(112,'2013-03-26 14:49:47',NULL,0.7544527623137585,2030,10,2),(113,'2013-03-26 14:49:47',NULL,0.05725325205826348,2055,10,2),(114,'2013-03-26 14:49:47',NULL,0.6102853080059133,2070,10,2),(115,'2013-03-26 14:49:47',NULL,0.8008396175359306,2030,10,3),(116,'2013-03-26 14:49:47',NULL,0.23326360054584971,2055,10,3),(117,'2013-03-26 14:49:47',NULL,0.650263895203893,2070,10,3),(118,'2013-03-26 14:49:47',NULL,0.13090409094115507,2030,10,4),(119,'2013-03-26 14:49:47',NULL,0.7030550576262682,2055,10,4),(120,'2013-03-26 14:49:47',NULL,0.4261712344600658,2070,10,4),(121,'2013-03-26 14:49:47',NULL,0.8747138520829328,2030,11,1),(122,'2013-03-26 14:49:47',NULL,0.19882590106232967,2055,11,1),(123,'2013-03-26 14:49:47',NULL,0.8788995977073965,2070,11,1),(124,'2013-03-26 14:49:47',NULL,0.8296087724468969,2030,11,2),(125,'2013-03-26 14:49:47',NULL,0.8574112059540369,2055,11,2),(126,'2013-03-26 14:49:47',NULL,0.8898124986042285,2070,11,2),(127,'2013-03-26 14:49:47',NULL,0.06420422480810606,2030,11,3),(128,'2013-03-26 14:49:47',NULL,0.3806398140789682,2055,11,3),(129,'2013-03-26 14:49:47',NULL,0.8236378473980196,2070,11,3),(130,'2013-03-26 14:49:47',NULL,0.6396283940290592,2030,11,4),(131,'2013-03-26 14:49:47',NULL,0.8142965617419653,2055,11,4),(132,'2013-03-26 14:49:47',NULL,0.24631817345411056,2070,11,4),(133,'2013-03-26 14:49:47',NULL,0.4286168220087935,2030,12,1),(134,'2013-03-26 14:49:47',NULL,0.7928502253003537,2055,12,1),(135,'2013-03-26 14:49:47',NULL,0.7683379532885888,2070,12,1),(136,'2013-03-26 14:49:47',NULL,0.46953987394832264,2030,12,2),(137,'2013-03-26 14:49:47',NULL,0.6402736024574904,2055,12,2),(138,'2013-03-26 14:49:47',NULL,0.3485153500985787,2070,12,2),(139,'2013-03-26 14:49:47',NULL,0.49048190734927866,2030,12,3),(140,'2013-03-26 14:49:47',NULL,0.8437319418836157,2055,12,3),(141,'2013-03-26 14:49:47',NULL,0.47263730240503965,2070,12,3),(142,'2013-03-26 14:49:47',NULL,0.6621618816235861,2030,12,4),(143,'2013-03-26 14:49:47',NULL,0.09393895847085054,2055,12,4),(144,'2013-03-26 14:49:47',NULL,0.23344994705300215,2070,12,4),(145,'2013-03-26 14:49:47',NULL,0.06551831139057118,2030,13,1),(146,'2013-03-26 14:49:47',NULL,0.9741634901644625,2055,13,1),(147,'2013-03-26 14:49:47',NULL,0.9409475153722323,2070,13,1),(148,'2013-03-26 14:49:47',NULL,0.18393194717692052,2030,13,2),(149,'2013-03-26 14:49:47',NULL,0.8336372113661429,2055,13,2),(150,'2013-03-26 14:49:47',NULL,0.02586445391410197,2070,13,2),(151,'2013-03-26 14:49:47',NULL,0.3298137485744135,2030,13,3),(152,'2013-03-26 14:49:47',NULL,0.06988405256971963,2055,13,3),(153,'2013-03-26 14:49:47',NULL,0.6671692364239721,2070,13,3),(154,'2013-03-26 14:49:47',NULL,0.6978711681688335,2030,13,4),(155,'2013-03-26 14:49:47',NULL,0.22959788686719151,2055,13,4),(156,'2013-03-26 14:49:47',NULL,0.22224815365328487,2070,13,4),(157,'2013-03-26 14:49:47',NULL,0.455707214034737,2030,14,1),(158,'2013-03-26 14:49:47',NULL,0.5589587562277105,2055,14,1),(159,'2013-03-26 14:49:47',NULL,0.8036887217913405,2070,14,1),(160,'2013-03-26 14:49:47',NULL,0.44143026054253587,2030,14,2),(161,'2013-03-26 14:49:47',NULL,0.5289354434065183,2055,14,2),(162,'2013-03-26 14:49:47',NULL,0.4494024983524004,2070,14,2),(163,'2013-03-26 14:49:47',NULL,0.6754214158747679,2030,14,3),(164,'2013-03-26 14:49:47',NULL,0.05644323494259673,2055,14,3),(165,'2013-03-26 14:49:47',NULL,0.9598456852558521,2070,14,3),(166,'2013-03-26 14:49:47',NULL,0.7391453399098279,2030,14,4),(167,'2013-03-26 14:49:47',NULL,0.6320584658379794,2055,14,4),(168,'2013-03-26 14:49:47',NULL,0.22164536742312435,2070,14,4),(169,'2013-03-26 14:49:47',NULL,0.06011572826198497,2030,15,1),(170,'2013-03-26 14:49:47',NULL,0.6990645178772461,2055,15,1),(171,'2013-03-26 14:49:47',NULL,0.7378673829826636,2070,15,1),(172,'2013-03-26 14:49:47',NULL,0.4562316335104444,2030,15,2),(173,'2013-03-26 14:49:47',NULL,0.8123098027703214,2055,15,2),(174,'2013-03-26 14:49:47',NULL,0.5027017452956245,2070,15,2),(175,'2013-03-26 14:49:47',NULL,0.6326927798112194,2030,15,3),(176,'2013-03-26 14:49:47',NULL,0.526362377939023,2055,15,3),(177,'2013-03-26 14:49:47',NULL,0.4298495346071556,2070,15,3),(178,'2013-03-26 14:49:47',NULL,0.7361732504771843,2030,15,4),(179,'2013-03-26 14:49:47',NULL,0.12933464755976576,2055,15,4),(180,'2013-03-26 14:49:47',NULL,0.6457893826216458,2070,15,4),(181,'2013-03-26 14:49:47',NULL,0.7132426810489735,2030,16,1),(182,'2013-03-26 14:49:47',NULL,0.935576806591613,2055,16,1),(183,'2013-03-26 14:49:47',NULL,0.09120871846605794,2070,16,1),(184,'2013-03-26 14:49:47',NULL,0.6280668368272965,2030,16,2),(185,'2013-03-26 14:49:47',NULL,0.3798467426254186,2055,16,2),(186,'2013-03-26 14:49:47',NULL,0.5940655090912649,2070,16,2),(187,'2013-03-26 14:49:47',NULL,0.9711785567077713,2030,16,3),(188,'2013-03-26 14:49:47',NULL,0.06191089260449978,2055,16,3),(189,'2013-03-26 14:49:47',NULL,0.08448249477579195,2070,16,3),(190,'2013-03-26 14:49:47',NULL,0.2848586187889066,2030,16,4),(191,'2013-03-26 14:49:47',NULL,0.3231137839426286,2055,16,4),(192,'2013-03-26 14:49:47',NULL,0.8009980278940078,2070,16,4),(193,'2013-03-26 14:49:47',NULL,0.4195028239591171,2030,17,1),(194,'2013-03-26 14:49:47',NULL,0.03181208706840766,2055,17,1),(195,'2013-03-26 14:49:47',NULL,0.059641937050817884,2070,17,1),(196,'2013-03-26 14:49:47',NULL,0.060468951706798446,2030,17,2),(197,'2013-03-26 14:49:47',NULL,0.7854600811553228,2055,17,2),(198,'2013-03-26 14:49:47',NULL,0.8257723172476712,2070,17,2),(199,'2013-03-26 14:49:47',NULL,0.2227265129054229,2030,17,3),(200,'2013-03-26 14:49:47',NULL,0.19463177684091848,2055,17,3),(201,'2013-03-26 14:49:47',NULL,0.657305339568512,2070,17,3),(202,'2013-03-26 14:49:47',NULL,0.23278901943655406,2030,17,4),(203,'2013-03-26 14:49:47',NULL,0.5581169183773975,2055,17,4),(204,'2013-03-26 14:49:47',NULL,0.7938341210541219,2070,17,4),(205,'2013-03-26 14:49:47',NULL,0.436091037107741,2030,18,1),(206,'2013-03-26 14:49:47',NULL,0.3693637349106158,2055,18,1),(207,'2013-03-26 14:49:47',NULL,0.2403808746955004,2070,18,1),(208,'2013-03-26 14:49:47',NULL,0.08320079336342634,2030,18,2),(209,'2013-03-26 14:49:47',NULL,0.772438357276963,2055,18,2),(210,'2013-03-26 14:49:47',NULL,0.13026288419927945,2070,18,2),(211,'2013-03-26 14:49:47',NULL,0.6821433583974832,2030,18,3),(212,'2013-03-26 14:49:47',NULL,0.7319101092775822,2055,18,3),(213,'2013-03-26 14:49:47',NULL,0.9755689718902055,2070,18,3),(214,'2013-03-26 14:49:47',NULL,0.4460254528634946,2030,18,4),(215,'2013-03-26 14:49:47',NULL,0.47932042298461497,2055,18,4),(216,'2013-03-26 14:49:47',NULL,0.0889086854641351,2070,18,4);
/*!40000 ALTER TABLE `csirodata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `csirodatabaseline`
--

DROP TABLE IF EXISTS `csirodatabaseline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `csirodatabaseline` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `value` double DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  `climate_variable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC4213105B7D2D10B` (`climate_variable_id`),
  KEY `FKC42131053365D05` (`region_id`),
  CONSTRAINT `FKC42131053365D05` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `FKC4213105B7D2D10B` FOREIGN KEY (`climate_variable_id`) REFERENCES `csirovariable` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `csirodatabaseline`
--

LOCK TABLES `csirodatabaseline` WRITE;
/*!40000 ALTER TABLE `csirodatabaseline` DISABLE KEYS */;
INSERT INTO `csirodatabaseline` VALUES (1,'2013-03-26 14:49:47',16.1,1,1),(2,'2013-03-26 14:49:47',1,1,2),(3,'2013-03-26 14:49:47',1029.8,1,3),(4,'2013-03-26 14:49:47',74.8,1,4);
/*!40000 ALTER TABLE `csirodatabaseline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `csirovariable`
--

DROP TABLE IF EXISTS `csirovariable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `csirovariable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `shortName` varchar(255) DEFAULT NULL,
  `uom` varchar(255) DEFAULT NULL,
  `uomVariation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `csirovariable`
--

LOCK TABLES `csirovariable` WRITE;
/*!40000 ALTER TABLE `csirovariable` DISABLE KEYS */;
INSERT INTO `csirovariable` VALUES (1,'Forecasted hange of temperature from now','Temperature','T','&#8451;','&#8451;'),(2,'Forecasted wind speed','Wind speed','WS','km/h','%'),(3,'Forecasted rain fall','Rainfall','RF','mm/y','%'),(4,'Forecasted relative humidity','Relative humidity','RH','%','%'),(5,'Forecasted sea level rise','Sea Level Rise','SLR','mm','%');
/*!40000 ALTER TABLE `csirovariable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dataelement`
--

DROP TABLE IF EXISTS `dataelement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataelement` (
  `type` varchar(31) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `included` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `content` longblob,
  `filetype` varchar(255) DEFAULT NULL,
  `picturesIncluded` bit(1) DEFAULT NULL,
  `text` longtext,
  `user_story_id` int(11) DEFAULT NULL,
  `weatherEvent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA8035BD270186365` (`weatherEvent_id`),
  KEY `FKA8035BD2994A1ED8` (`user_story_id`),
  CONSTRAINT `FKA8035BD2994A1ED8` FOREIGN KEY (`user_story_id`) REFERENCES `userstory` (`id`),
  CONSTRAINT `FKA8035BD270186365` FOREIGN KEY (`weatherEvent_id`) REFERENCES `weatherevent` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataelement`
--

LOCK TABLES `dataelement` WRITE;
/*!40000 ALTER TABLE `dataelement` DISABLE KEYS */;
INSERT INTO `dataelement` VALUES ('File',1,'2013-03-26 14:49:47','','Test 1',0,'This is a test for Data Element','csv',NULL,NULL,1,NULL),('File',2,'2013-03-26 14:49:47','','Test 2',0,'This is the second test','xml',NULL,NULL,1,NULL),('File',3,'2013-03-26 14:49:47','','Test 3',0,'This is the third test','txt',NULL,NULL,1,NULL),('File',4,'2013-03-26 14:49:47','','Test 4',1,'User Story\'s Data Element content test','txt',NULL,NULL,2,NULL),('Text',5,'2013-03-26 14:49:47','','Comment text 1',2,NULL,NULL,NULL,'This is a text comment',2,NULL),('File',6,'2013-03-26 14:49:47','','Test 5',3,'User Story\'s Data Element content test','txt',NULL,NULL,2,NULL),('Text',7,'2013-03-26 14:49:47','','Comment text 2',4,NULL,NULL,NULL,'This is a second text comment',2,NULL),('File',8,'2013-03-26 14:49:47','','Test 6',1,'User Story\'s Data Element content test','txt',NULL,NULL,3,NULL),('Csiro',9,'2013-03-26 14:49:47','','CSIRO Data Element Test',0,NULL,NULL,'',NULL,1,NULL),('EngineeringModel',10,'2013-03-26 14:49:47','','Data Element for Pint ()',0,NULL,NULL,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `dataelement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dataelement_cmardata`
--

DROP TABLE IF EXISTS `dataelement_cmardata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataelement_cmardata` (
  `DataElement_Id` int(11) NOT NULL,
  `CmarData_Id` int(11) NOT NULL,
  KEY `FK9219EA525C15E2A` (`DataElement_Id`),
  KEY `FK9219EA5251873CC5` (`CmarData_Id`),
  CONSTRAINT `FK9219EA5251873CC5` FOREIGN KEY (`CmarData_Id`) REFERENCES `cmardata` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK9219EA525C15E2A` FOREIGN KEY (`DataElement_Id`) REFERENCES `dataelement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataelement_cmardata`
--

LOCK TABLES `dataelement_cmardata` WRITE;
/*!40000 ALTER TABLE `dataelement_cmardata` DISABLE KEYS */;
/*!40000 ALTER TABLE `dataelement_cmardata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dataelement_csirodata`
--

DROP TABLE IF EXISTS `dataelement_csirodata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataelement_csirodata` (
  `DataElement_Id` int(11) NOT NULL,
  `CsiroData_Id` int(11) NOT NULL,
  KEY `FKC9ECC6738192BB97` (`DataElement_Id`),
  KEY `FKC9ECC673DD18A2F` (`CsiroData_Id`),
  CONSTRAINT `FKC9ECC6738192BB97` FOREIGN KEY (`DataElement_Id`) REFERENCES `dataelement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKC9ECC673DD18A2F` FOREIGN KEY (`CsiroData_Id`) REFERENCES `csirodata` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataelement_csirodata`
--

LOCK TABLES `dataelement_csirodata` WRITE;
/*!40000 ALTER TABLE `dataelement_csirodata` DISABLE KEYS */;
INSERT INTO `dataelement_csirodata` VALUES (9,1),(9,2),(9,3),(9,4);
/*!40000 ALTER TABLE `dataelement_csirodata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dataelement_engineeringmodeldata`
--

DROP TABLE IF EXISTS `dataelement_engineeringmodeldata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataelement_engineeringmodeldata` (
  `DataElement_Id` int(11) NOT NULL,
  `EngineeringModelData_Id` int(11) NOT NULL,
  KEY `FK3035230DC9652565` (`DataElement_Id`),
  KEY `FK3035230D907E0245` (`EngineeringModelData_Id`),
  CONSTRAINT `FK3035230DC9652565` FOREIGN KEY (`DataElement_Id`) REFERENCES `dataelement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK3035230D907E0245` FOREIGN KEY (`EngineeringModelData_Id`) REFERENCES `engineeringmodeldata` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataelement_engineeringmodeldata`
--

LOCK TABLES `dataelement_engineeringmodeldata` WRITE;
/*!40000 ALTER TABLE `dataelement_engineeringmodeldata` DISABLE KEYS */;
INSERT INTO `dataelement_engineeringmodeldata` VALUES (10,1),(10,2),(10,3),(10,4),(10,5),(10,6);
/*!40000 ALTER TABLE `dataelement_engineeringmodeldata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `engineeringmodelasset`
--

DROP TABLE IF EXISTS `engineeringmodelasset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `engineeringmodelasset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assetCode` varchar(255) DEFAULT NULL,
  `carbonationClass` varchar(255) DEFAULT NULL,
  `ce` double DEFAULT NULL,
  `chlorideClass` varchar(255) DEFAULT NULL,
  `cover` double DEFAULT NULL,
  `dbar` double DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `distanceFromCoast` double DEFAULT NULL,
  `dmember` double DEFAULT NULL,
  `exposureClass` varchar(255) DEFAULT NULL,
  `fprimec` double DEFAULT NULL,
  `wc` double DEFAULT NULL,
  `yearBuilt` int(11) NOT NULL,
  `zone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engineeringmodelasset`
--

LOCK TABLES `engineeringmodelasset` WRITE;
/*!40000 ALTER TABLE `engineeringmodelasset` DISABLE KEYS */;
INSERT INTO `engineeringmodelasset` VALUES (1,'006B','CB3',300,'CL1',60,13,'Pile No 6 @ Berth 08',2,1300,'C2',60,0.37,1991,'atmospheric');
/*!40000 ALTER TABLE `engineeringmodelasset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `engineeringmodeldata`
--

DROP TABLE IF EXISTS `engineeringmodeldata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `engineeringmodeldata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` text,
  `engineering_model_asset_id` int(11) DEFAULT NULL,
  `climate_params_id` int(11) DEFAULT NULL,
  `engineering_model_variable_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK188B6CC0201EEBA` (`climate_params_id`),
  KEY `FK188B6CC0D1BF0EF9` (`engineering_model_variable_id`),
  KEY `FK188B6CC02AA35D5B` (`engineering_model_asset_id`),
  CONSTRAINT `FK188B6CC02AA35D5B` FOREIGN KEY (`engineering_model_asset_id`) REFERENCES `engineeringmodelasset` (`id`),
  CONSTRAINT `FK188B6CC0201EEBA` FOREIGN KEY (`climate_params_id`) REFERENCES `climateparams` (`id`),
  CONSTRAINT `FK188B6CC0D1BF0EF9` FOREIGN KEY (`engineering_model_variable_id`) REFERENCES `engineeringmodelvariable` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engineeringmodeldata`
--

LOCK TABLES `engineeringmodeldata` WRITE;
/*!40000 ALTER TABLE `engineeringmodeldata` DISABLE KEYS */;
INSERT INTO `engineeringmodeldata` VALUES (1,'2065,8.0;2032,0.0;2064,0.0;2033,6.0;2067,5.0;2034,4.0;2066,8.0;2035,2.0;2069,0.0;2036,4.0;2068,2.0;2037,9.0;2038,6.0;2070,4.0;2039,8.0;2040,3.0;2041,0.0;2042,0.0;2043,5.0;2044,1.0;2045,9.0;2046,0.0;2047,7.0;2048,4.0;2017,4.0;2049,9.0;2016,6.0;2050,2.0;2019,5.0;2051,1.0;2018,1.0;2052,6.0;2021,2.0;2053,0.0;2020,6.0;2054,1.0;2023,6.0;2055,4.0;2022,4.0;2056,6.0;2025,3.0;2057,7.0;2024,5.0;2058,6.0;2027,8.0;2059,3.0;2026,4.0;2060,1.0;2029,0.0;2061,6.0;2028,9.0;2062,1.0;2031,7.0;2063,8.0;2030,2.0;2002,0.0;2003,3.0;2000,3.0;2001,4.0;2006,3.0;2007,2.0;2004,9.0;2005,1.0;2010,0.0;2011,6.0;2008,4.0;2009,3.0;2014,5.0;2015,7.0;2012,3.0;2013,3.0',1,1,1),(2,'2065,0.0;2032,7.0;2064,3.0;2033,5.0;2067,5.0;2034,1.0;2066,4.0;2035,2.0;2069,5.0;2036,4.0;2068,2.0;2037,6.0;2038,1.0;2070,0.0;2039,4.0;2040,5.0;2041,2.0;2042,1.0;2043,2.0;2044,6.0;2045,3.0;2046,1.0;2047,7.0;2048,9.0;2017,6.0;2049,1.0;2016,7.0;2050,5.0;2019,7.0;2051,3.0;2018,4.0;2052,3.0;2021,5.0;2053,6.0;2020,4.0;2054,0.0;2023,0.0;2055,8.0;2022,7.0;2056,1.0;2025,4.0;2057,0.0;2024,3.0;2058,0.0;2027,7.0;2059,9.0;2026,4.0;2060,2.0;2029,7.0;2061,8.0;2028,3.0;2062,6.0;2031,4.0;2063,5.0;2030,9.0;2002,0.0;2003,6.0;2000,5.0;2001,2.0;2006,6.0;2007,6.0;2004,4.0;2005,4.0;2010,9.0;2011,6.0;2008,7.0;2009,5.0;2014,6.0;2015,5.0;2012,3.0;2013,7.0',1,2,1),(3,'2065,8.0;2032,3.0;2064,3.0;2033,4.0;2067,6.0;2034,4.0;2066,3.0;2035,1.0;2069,0.0;2036,5.0;2068,7.0;2037,4.0;2038,8.0;2070,8.0;2039,2.0;2040,0.0;2041,3.0;2042,7.0;2043,9.0;2044,6.0;2045,8.0;2046,3.0;2047,5.0;2048,3.0;2017,0.0;2049,6.0;2016,6.0;2050,7.0;2019,6.0;2051,4.0;2018,0.0;2052,9.0;2021,3.0;2053,9.0;2020,5.0;2054,6.0;2023,8.0;2055,6.0;2022,7.0;2056,2.0;2025,6.0;2057,6.0;2024,0.0;2058,9.0;2027,4.0;2059,2.0;2026,2.0;2060,2.0;2029,3.0;2061,4.0;2028,7.0;2062,0.0;2031,2.0;2063,4.0;2030,1.0;2002,3.0;2003,3.0;2000,0.0;2001,9.0;2006,1.0;2007,8.0;2004,8.0;2005,1.0;2010,5.0;2011,4.0;2008,5.0;2009,5.0;2014,2.0;2015,0.0;2012,6.0;2013,3.0',1,3,1),(4,'2065,7.0;2032,3.0;2064,8.0;2033,4.0;2067,5.0;2034,1.0;2066,6.0;2035,4.0;2069,6.0;2036,2.0;2068,6.0;2037,9.0;2038,8.0;2070,7.0;2039,0.0;2040,0.0;2041,6.0;2042,9.0;2043,7.0;2044,9.0;2045,4.0;2046,5.0;2047,0.0;2048,3.0;2017,2.0;2049,3.0;2016,1.0;2050,4.0;2019,9.0;2051,6.0;2018,7.0;2052,6.0;2021,0.0;2053,3.0;2020,8.0;2054,1.0;2023,6.0;2055,6.0;2022,3.0;2056,1.0;2025,1.0;2057,1.0;2024,3.0;2058,3.0;2027,4.0;2059,1.0;2026,5.0;2060,5.0;2029,1.0;2061,3.0;2028,6.0;2062,1.0;2031,7.0;2063,0.0;2030,5.0;2002,1.0;2003,2.0;2000,5.0;2001,4.0;2006,8.0;2007,2.0;2004,1.0;2005,3.0;2010,1.0;2011,4.0;2008,8.0;2009,8.0;2014,2.0;2015,7.0;2012,7.0;2013,2.0',1,4,1),(5,'2065,2.0;2032,3.0;2064,8.0;2033,8.0;2067,4.0;2034,4.0;2066,1.0;2035,8.0;2069,5.0;2036,8.0;2068,7.0;2037,3.0;2038,1.0;2070,9.0;2039,5.0;2040,4.0;2041,4.0;2042,1.0;2043,2.0;2044,5.0;2045,7.0;2046,8.0;2047,6.0;2048,9.0;2017,2.0;2049,3.0;2016,6.0;2050,3.0;2019,6.0;2051,7.0;2018,6.0;2052,1.0;2021,4.0;2053,9.0;2020,2.0;2054,1.0;2023,4.0;2055,1.0;2022,5.0;2056,8.0;2025,3.0;2057,8.0;2024,4.0;2058,8.0;2027,0.0;2059,3.0;2026,6.0;2060,1.0;2029,8.0;2061,6.0;2028,0.0;2062,5.0;2031,7.0;2063,4.0;2030,4.0;2002,8.0;2003,6.0;2000,7.0;2001,5.0;2006,5.0;2007,5.0;2004,6.0;2005,5.0;2010,7.0;2011,1.0;2008,1.0;2009,6.0;2014,3.0;2015,8.0;2012,6.0;2013,4.0',1,5,1),(6,'2065,2.0;2032,7.0;2064,7.0;2033,8.0;2067,6.0;2034,6.0;2066,8.0;2035,6.0;2069,8.0;2036,0.0;2068,1.0;2037,0.0;2038,8.0;2070,7.0;2039,5.0;2040,5.0;2041,5.0;2042,7.0;2043,1.0;2044,5.0;2045,9.0;2046,4.0;2047,5.0;2048,9.0;2017,0.0;2049,0.0;2016,9.0;2050,3.0;2019,6.0;2051,1.0;2018,7.0;2052,8.0;2021,7.0;2053,2.0;2020,7.0;2054,6.0;2023,7.0;2055,4.0;2022,8.0;2056,4.0;2025,8.0;2057,9.0;2024,2.0;2058,0.0;2027,3.0;2059,7.0;2026,2.0;2060,2.0;2029,9.0;2061,5.0;2028,2.0;2062,4.0;2031,4.0;2063,4.0;2030,8.0;2002,0.0;2003,5.0;2000,6.0;2001,7.0;2006,3.0;2007,2.0;2004,1.0;2005,7.0;2010,4.0;2011,5.0;2008,7.0;2009,8.0;2014,6.0;2015,1.0;2012,0.0;2013,4.0',1,6,1);
/*!40000 ALTER TABLE `engineeringmodeldata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `engineeringmodelvariable`
--

DROP TABLE IF EXISTS `engineeringmodelvariable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `engineeringmodelvariable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `shortName` varchar(255) DEFAULT NULL,
  `uom` varchar(255) DEFAULT NULL,
  `uomVariation` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engineeringmodelvariable`
--

LOCK TABLES `engineeringmodelvariable` WRITE;
/*!40000 ALTER TABLE `engineeringmodelvariable` DISABLE KEYS */;
INSERT INTO `engineeringmodelvariable` VALUES (1,'Corrosion initiation of reinforcing bar probability','Corrosion initiation of reinforcing bar probability','Pint','','%','Chloride'),(2,'Change in chloride concentration at concrete cover depth','Change in chloride concentration at concrete cover depth','C(h,t)','kg/m&sup3;','%','Chloride'),(3,'Change in corrosion rate of reinforcing bar','Change in corrosion rate of reinforcing bar','icorr (t)','&#181;A/cm&sup2;','%','Chloride'),(4,'Corrosion initiation time','Corrosion initiation time','ti','yr','%','Chloride'),(5,'Crack propagation time','Crack propagation time','tp','yr','%','Chloride'),(6,'Time to severe cracking (1mm crack width)','Time to severe cracking (1mm crack width)','tcr','yr','%','Chloride'),(7,'Chloride induced corrosion probability to severe cracking','Chloride induced corrosion probability to severe cracking','Pinit x Pcrack','','%','Chloride'),(8,'Reduction or loss in reinforcing bar','Reduction or loss in reinforcing bar','Rebar loss','mm','%','Chloride'),(9,'Corrosion initiation of reinforcing bar probability ','Corrosion initiation of reinforcing bar probability','Pint','','%','Carbonation'),(10,'Change in carbonation depth','Change in carbonation depth','&#916; xc(t\')','mm','%','Carbonation'),(11,'Change in corrosion rate of reinforcing bar','Change in corrosion rate of reinforcing bar','icorr(t\')','&#181;A/cm&sup2;','%','Carbonation'),(12,'Corrosion initiation time','Corrosion initiation time','ti','yr','%','Carbonation'),(13,'Crack propagation time','Crack propagation time','tp','yr','%','Carbonation'),(14,'Time to severe cracking (1mm crack width)','Time to severe cracking (1mm crack width)','tcr','yr','%','Carbonation'),(15,'Carbonation induced corrosion probability to severe cracking','Carbonation induced corrosion probability to severe cracking','Pinit x Pcrack','','%','Carbonation'),(16,'Reduction or loss in reinforcing bar','Reduction or loss in reinforcing bar','Rebar loss','mm','%','Carbonation');
/*!40000 ALTER TABLE `engineeringmodelvariable` ENABLE KEYS */;
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
  KEY `FKD88A2D303365D05` (`region_id`),
  CONSTRAINT `FKD88A2D303365D05` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
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
  `nonLocked` bit(1) DEFAULT NULL,
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
-- Table structure for table `userstory`
--

DROP TABLE IF EXISTS `userstory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userstory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access` varchar(255) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `owner_login` varchar(255) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8B05E3CA3365D05` (`region_id`),
  KEY `FK8B05E3CABFE5533` (`owner_login`),
  CONSTRAINT `FK8B05E3CABFE5533` FOREIGN KEY (`owner_login`) REFERENCES `user` (`username`),
  CONSTRAINT `FK8B05E3CA3365D05` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userstory`
--

LOCK TABLES `userstory` WRITE;
/*!40000 ALTER TABLE `userstory` DISABLE KEYS */;
INSERT INTO `userstory` VALUES (1,'private','active','User 1 Workboard','testuser1',1),(2,'private','passive','User 1 Story 1','testuser1',2),(3,'public','passive','User 1 Story 2 (Public)','testuser1',1),(4,'public','published','User 1 Story 3 (Published)','testuser1',1),(5,'private','active','User 2 Workboard (Empty)','testuser2',2),(6,'private','passive','User 2 Story (Empty)','testuser2',1);
/*!40000 ALTER TABLE `userstory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weatherevent`
--

DROP TABLE IF EXISTS `weatherevent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weatherevent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `consequences` varchar(255) DEFAULT NULL,
  `consequencesRating` varchar(255) DEFAULT NULL,
  `direct` bit(1) DEFAULT NULL,
  `impact` varchar(255) DEFAULT NULL,
  `response` varchar(255) DEFAULT NULL,
  `responseAdequate` bit(1) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weatherevent`
--

LOCK TABLES `weatherevent` WRITE;
/*!40000 ALTER TABLE `weatherevent` DISABLE KEYS */;
/*!40000 ALTER TABLE `weatherevent` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-26 14:53:03
