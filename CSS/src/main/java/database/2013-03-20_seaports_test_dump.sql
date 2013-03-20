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
  `picture` blob,
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
INSERT INTO `cmardata` VALUES (1,'2013-03-20 11:58:24',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,3,5),(2,'2013-03-20 11:58:24',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,3,5),(3,'2013-03-20 11:58:24',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,9,5),(4,'2013-03-20 11:58:24',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,9,5),(5,'2013-03-20 11:58:24',NULL,'-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159',2030,15,5),(6,'2013-03-20 11:58:24',NULL,'-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429',2070,15,5);
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
  `picture` blob,
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
INSERT INTO `csirodata` VALUES (1,'2013-03-20 11:58:24',NULL,0.9337306380642876,2030,1,1),(2,'2013-03-20 11:58:24',NULL,0.4513421482472083,2055,1,1),(3,'2013-03-20 11:58:24',NULL,0.49980657278948715,2070,1,1),(4,'2013-03-20 11:58:24',NULL,0.9453605536467499,2030,1,2),(5,'2013-03-20 11:58:24',NULL,0.24617517270968614,2055,1,2),(6,'2013-03-20 11:58:24',NULL,0.9953298509008723,2070,1,2),(7,'2013-03-20 11:58:24',NULL,0.12062874506911614,2030,1,3),(8,'2013-03-20 11:58:24',NULL,0.43666001602511795,2055,1,3),(9,'2013-03-20 11:58:24',NULL,0.6771380220579657,2070,1,3),(10,'2013-03-20 11:58:24',NULL,0.3406371675962927,2030,1,4),(11,'2013-03-20 11:58:24',NULL,0.15765553010515732,2055,1,4),(12,'2013-03-20 11:58:24',NULL,0.1279290449055025,2070,1,4),(13,'2013-03-20 11:58:24',NULL,0.26329800707037787,2030,2,1),(14,'2013-03-20 11:58:24',NULL,0.09569433852981779,2055,2,1),(15,'2013-03-20 11:58:24',NULL,0.14919338171389052,2070,2,1),(16,'2013-03-20 11:58:24',NULL,0.7299102633450713,2030,2,2),(17,'2013-03-20 11:58:24',NULL,0.4936121848909053,2055,2,2),(18,'2013-03-20 11:58:24',NULL,0.20403025511902062,2070,2,2),(19,'2013-03-20 11:58:24',NULL,0.10698138140773128,2030,2,3),(20,'2013-03-20 11:58:24',NULL,0.3835616688133351,2055,2,3),(21,'2013-03-20 11:58:24',NULL,0.2851984818476525,2070,2,3),(22,'2013-03-20 11:58:24',NULL,0.6091125546991896,2030,2,4),(23,'2013-03-20 11:58:24',NULL,0.7526428863240833,2055,2,4),(24,'2013-03-20 11:58:24',NULL,0.7415365035291228,2070,2,4),(25,'2013-03-20 11:58:24',NULL,0.5648674308487522,2030,3,1),(26,'2013-03-20 11:58:24',NULL,0.9737580144788462,2055,3,1),(27,'2013-03-20 11:58:24',NULL,0.1837152745641497,2070,3,1),(28,'2013-03-20 11:58:24',NULL,0.8745734134839659,2030,3,2),(29,'2013-03-20 11:58:24',NULL,0.23624048533129638,2055,3,2),(30,'2013-03-20 11:58:24',NULL,0.9882011608451008,2070,3,2),(31,'2013-03-20 11:58:24',NULL,0.314136388124869,2030,3,3),(32,'2013-03-20 11:58:24',NULL,0.10573243503057739,2055,3,3),(33,'2013-03-20 11:58:24',NULL,0.8563383022407454,2070,3,3),(34,'2013-03-20 11:58:24',NULL,0.4151672615099723,2030,3,4),(35,'2013-03-20 11:58:24',NULL,0.431137646781687,2055,3,4),(36,'2013-03-20 11:58:24',NULL,0.06779564425173568,2070,3,4),(37,'2013-03-20 11:58:24',NULL,0.04571710030882481,2030,4,1),(38,'2013-03-20 11:58:24',NULL,0.04080324441155736,2055,4,1),(39,'2013-03-20 11:58:24',NULL,0.8638132353185555,2070,4,1),(40,'2013-03-20 11:58:24',NULL,0.377783762936859,2030,4,2),(41,'2013-03-20 11:58:24',NULL,0.5126768529272836,2055,4,2),(42,'2013-03-20 11:58:24',NULL,0.1270783321649871,2070,4,2),(43,'2013-03-20 11:58:24',NULL,0.06820567867142124,2030,4,3),(44,'2013-03-20 11:58:24',NULL,0.3274229471830601,2055,4,3),(45,'2013-03-20 11:58:24',NULL,0.584760385683211,2070,4,3),(46,'2013-03-20 11:58:24',NULL,0.7942736401685785,2030,4,4),(47,'2013-03-20 11:58:24',NULL,0.39956805616094393,2055,4,4),(48,'2013-03-20 11:58:24',NULL,0.8995287943580513,2070,4,4),(49,'2013-03-20 11:58:24',NULL,0.30970753633817616,2030,5,1),(50,'2013-03-20 11:58:24',NULL,0.001437956611483715,2055,5,1),(51,'2013-03-20 11:58:24',NULL,0.46453370616699896,2070,5,1),(52,'2013-03-20 11:58:24',NULL,0.2376574702278752,2030,5,2),(53,'2013-03-20 11:58:24',NULL,0.5132241447563214,2055,5,2),(54,'2013-03-20 11:58:24',NULL,0.43819470324589416,2070,5,2),(55,'2013-03-20 11:58:24',NULL,0.22424426974296097,2030,5,3),(56,'2013-03-20 11:58:24',NULL,0.4200066612537564,2055,5,3),(57,'2013-03-20 11:58:24',NULL,0.6823707587367509,2070,5,3),(58,'2013-03-20 11:58:24',NULL,0.6930429947050137,2030,5,4),(59,'2013-03-20 11:58:24',NULL,0.9540576419851928,2055,5,4),(60,'2013-03-20 11:58:24',NULL,0.018431633486656107,2070,5,4),(61,'2013-03-20 11:58:24',NULL,0.18107115002839502,2030,6,1),(62,'2013-03-20 11:58:24',NULL,0.5213033094315744,2055,6,1),(63,'2013-03-20 11:58:24',NULL,0.7562772192359937,2070,6,1),(64,'2013-03-20 11:58:24',NULL,0.4595749977108131,2030,6,2),(65,'2013-03-20 11:58:24',NULL,0.09818653964048063,2055,6,2),(66,'2013-03-20 11:58:24',NULL,0.900116197680568,2070,6,2),(67,'2013-03-20 11:58:24',NULL,0.5694147153597838,2030,6,3),(68,'2013-03-20 11:58:24',NULL,0.6474909629623925,2055,6,3),(69,'2013-03-20 11:58:24',NULL,0.9787960000659646,2070,6,3),(70,'2013-03-20 11:58:24',NULL,0.828410592167135,2030,6,4),(71,'2013-03-20 11:58:24',NULL,0.5208183872067164,2055,6,4),(72,'2013-03-20 11:58:24',NULL,0.6557781275493267,2070,6,4),(73,'2013-03-20 11:58:24',NULL,0.7957694717143335,2030,7,1),(74,'2013-03-20 11:58:24',NULL,0.5065152242347103,2055,7,1),(75,'2013-03-20 11:58:24',NULL,0.029069827604425447,2070,7,1),(76,'2013-03-20 11:58:24',NULL,0.5521178075143904,2030,7,2),(77,'2013-03-20 11:58:24',NULL,0.7814148771228833,2055,7,2),(78,'2013-03-20 11:58:24',NULL,0.6130956760762145,2070,7,2),(79,'2013-03-20 11:58:24',NULL,0.27837167949532715,2030,7,3),(80,'2013-03-20 11:58:24',NULL,0.5792467771562676,2055,7,3),(81,'2013-03-20 11:58:24',NULL,0.1579260122655749,2070,7,3),(82,'2013-03-20 11:58:24',NULL,0.8863562621050722,2030,7,4),(83,'2013-03-20 11:58:24',NULL,0.5592197949831179,2055,7,4),(84,'2013-03-20 11:58:24',NULL,0.98377762632806,2070,7,4),(85,'2013-03-20 11:58:24',NULL,0.38055344812208003,2030,8,1),(86,'2013-03-20 11:58:24',NULL,0.11195449177527428,2055,8,1),(87,'2013-03-20 11:58:24',NULL,0.49797334902169044,2070,8,1),(88,'2013-03-20 11:58:24',NULL,0.7579110176246003,2030,8,2),(89,'2013-03-20 11:58:24',NULL,0.23886579784512618,2055,8,2),(90,'2013-03-20 11:58:24',NULL,0.9218334460664334,2070,8,2),(91,'2013-03-20 11:58:24',NULL,0.3825065603771127,2030,8,3),(92,'2013-03-20 11:58:24',NULL,0.43896870347510575,2055,8,3),(93,'2013-03-20 11:58:24',NULL,0.04916415533096363,2070,8,3),(94,'2013-03-20 11:58:24',NULL,0.5161850286609606,2030,8,4),(95,'2013-03-20 11:58:24',NULL,0.6319464193534352,2055,8,4),(96,'2013-03-20 11:58:24',NULL,0.8978072946350103,2070,8,4),(97,'2013-03-20 11:58:24',NULL,0.21505775331388732,2030,9,1),(98,'2013-03-20 11:58:24',NULL,0.5350152818890609,2055,9,1),(99,'2013-03-20 11:58:24',NULL,0.9555707569855852,2070,9,1),(100,'2013-03-20 11:58:24',NULL,0.7395568949218921,2030,9,2),(101,'2013-03-20 11:58:24',NULL,0.6334041467886813,2055,9,2),(102,'2013-03-20 11:58:24',NULL,0.8262902966870999,2070,9,2),(103,'2013-03-20 11:58:24',NULL,0.8992612464557279,2030,9,3),(104,'2013-03-20 11:58:24',NULL,0.5387609744867071,2055,9,3),(105,'2013-03-20 11:58:24',NULL,0.7792626496262264,2070,9,3),(106,'2013-03-20 11:58:24',NULL,0.7843994385418894,2030,9,4),(107,'2013-03-20 11:58:24',NULL,0.28747693651689366,2055,9,4),(108,'2013-03-20 11:58:24',NULL,0.6358763018133057,2070,9,4),(109,'2013-03-20 11:58:24',NULL,0.8162051002687314,2030,10,1),(110,'2013-03-20 11:58:24',NULL,0.26568022716609374,2055,10,1),(111,'2013-03-20 11:58:24',NULL,0.5345331157096753,2070,10,1),(112,'2013-03-20 11:58:24',NULL,0.6332256893974639,2030,10,2),(113,'2013-03-20 11:58:24',NULL,0.7472730679535565,2055,10,2),(114,'2013-03-20 11:58:24',NULL,0.8270070362298267,2070,10,2),(115,'2013-03-20 11:58:24',NULL,0.9210416141397624,2030,10,3),(116,'2013-03-20 11:58:24',NULL,0.045877512642785945,2055,10,3),(117,'2013-03-20 11:58:24',NULL,0.4470662578903618,2070,10,3),(118,'2013-03-20 11:58:24',NULL,0.17895370165129654,2030,10,4),(119,'2013-03-20 11:58:24',NULL,0.7096884902673338,2055,10,4),(120,'2013-03-20 11:58:24',NULL,0.7865606199067304,2070,10,4),(121,'2013-03-20 11:58:24',NULL,0.39689211892639986,2030,11,1),(122,'2013-03-20 11:58:24',NULL,0.29679774039370177,2055,11,1),(123,'2013-03-20 11:58:24',NULL,0.40909530109017966,2070,11,1),(124,'2013-03-20 11:58:24',NULL,0.2936042708829477,2030,11,2),(125,'2013-03-20 11:58:24',NULL,0.08260169419425434,2055,11,2),(126,'2013-03-20 11:58:24',NULL,0.5906420059903723,2070,11,2),(127,'2013-03-20 11:58:24',NULL,0.5853307854785363,2030,11,3),(128,'2013-03-20 11:58:24',NULL,0.14702701985372124,2055,11,3),(129,'2013-03-20 11:58:24',NULL,0.30868235890559714,2070,11,3),(130,'2013-03-20 11:58:24',NULL,0.9001869606254966,2030,11,4),(131,'2013-03-20 11:58:24',NULL,0.36770989327018144,2055,11,4),(132,'2013-03-20 11:58:24',NULL,0.9602801883357224,2070,11,4),(133,'2013-03-20 11:58:24',NULL,0.13237342536857144,2030,12,1),(134,'2013-03-20 11:58:24',NULL,0.8501950699695626,2055,12,1),(135,'2013-03-20 11:58:24',NULL,0.1988372333034527,2070,12,1),(136,'2013-03-20 11:58:24',NULL,0.18378520372070128,2030,12,2),(137,'2013-03-20 11:58:24',NULL,0.7280156783663574,2055,12,2),(138,'2013-03-20 11:58:24',NULL,0.2794462634617001,2070,12,2),(139,'2013-03-20 11:58:24',NULL,0.4384228028011513,2030,12,3),(140,'2013-03-20 11:58:24',NULL,0.21128633842469957,2055,12,3),(141,'2013-03-20 11:58:24',NULL,0.25454128558331657,2070,12,3),(142,'2013-03-20 11:58:24',NULL,0.03899140965925363,2030,12,4),(143,'2013-03-20 11:58:24',NULL,0.14621780830364073,2055,12,4),(144,'2013-03-20 11:58:24',NULL,0.8411733390662746,2070,12,4),(145,'2013-03-20 11:58:24',NULL,0.7179560289889002,2030,13,1),(146,'2013-03-20 11:58:24',NULL,0.44476842466729594,2055,13,1),(147,'2013-03-20 11:58:24',NULL,0.7414886867777458,2070,13,1),(148,'2013-03-20 11:58:24',NULL,0.7123115210519508,2030,13,2),(149,'2013-03-20 11:58:24',NULL,0.8958609471510859,2055,13,2),(150,'2013-03-20 11:58:24',NULL,0.06515351286038629,2070,13,2),(151,'2013-03-20 11:58:24',NULL,0.2647678610818901,2030,13,3),(152,'2013-03-20 11:58:24',NULL,0.8075491818825125,2055,13,3),(153,'2013-03-20 11:58:24',NULL,0.6225056825829971,2070,13,3),(154,'2013-03-20 11:58:24',NULL,0.32577816291799877,2030,13,4),(155,'2013-03-20 11:58:24',NULL,0.652190391153074,2055,13,4),(156,'2013-03-20 11:58:24',NULL,0.49795577247169565,2070,13,4),(157,'2013-03-20 11:58:24',NULL,0.12056145005641883,2030,14,1),(158,'2013-03-20 11:58:24',NULL,0.6219494432010638,2055,14,1),(159,'2013-03-20 11:58:24',NULL,0.7735451401909729,2070,14,1),(160,'2013-03-20 11:58:24',NULL,0.8869760305455,2030,14,2),(161,'2013-03-20 11:58:24',NULL,0.6097465978675233,2055,14,2),(162,'2013-03-20 11:58:24',NULL,0.2432701518566025,2070,14,2),(163,'2013-03-20 11:58:24',NULL,0.9822402843762065,2030,14,3),(164,'2013-03-20 11:58:24',NULL,0.4060691576722515,2055,14,3),(165,'2013-03-20 11:58:24',NULL,0.12263319288873076,2070,14,3),(166,'2013-03-20 11:58:24',NULL,0.43556221701838527,2030,14,4),(167,'2013-03-20 11:58:24',NULL,0.5787029601403132,2055,14,4),(168,'2013-03-20 11:58:24',NULL,0.7120342373588141,2070,14,4),(169,'2013-03-20 11:58:24',NULL,0.3144081172235478,2030,15,1),(170,'2013-03-20 11:58:24',NULL,0.3519550171549285,2055,15,1),(171,'2013-03-20 11:58:24',NULL,0.8983029207098069,2070,15,1),(172,'2013-03-20 11:58:24',NULL,0.9411062175529511,2030,15,2),(173,'2013-03-20 11:58:24',NULL,0.06605801149135615,2055,15,2),(174,'2013-03-20 11:58:24',NULL,0.4780080638509896,2070,15,2),(175,'2013-03-20 11:58:24',NULL,0.7007106808084272,2030,15,3),(176,'2013-03-20 11:58:24',NULL,0.28603938199615764,2055,15,3),(177,'2013-03-20 11:58:24',NULL,0.8493920404508494,2070,15,3),(178,'2013-03-20 11:58:24',NULL,0.35499513162407714,2030,15,4),(179,'2013-03-20 11:58:24',NULL,0.15967091565412095,2055,15,4),(180,'2013-03-20 11:58:24',NULL,0.6797782201461796,2070,15,4),(181,'2013-03-20 11:58:24',NULL,0.14751227500994324,2030,16,1),(182,'2013-03-20 11:58:24',NULL,0.07098493841282072,2055,16,1),(183,'2013-03-20 11:58:24',NULL,0.030492279864088534,2070,16,1),(184,'2013-03-20 11:58:24',NULL,0.09294849799672877,2030,16,2),(185,'2013-03-20 11:58:24',NULL,0.7057866031201943,2055,16,2),(186,'2013-03-20 11:58:24',NULL,0.041111145972465235,2070,16,2),(187,'2013-03-20 11:58:24',NULL,0.5071245338208571,2030,16,3),(188,'2013-03-20 11:58:24',NULL,0.5822837148310818,2055,16,3),(189,'2013-03-20 11:58:24',NULL,0.02607437978356919,2070,16,3),(190,'2013-03-20 11:58:24',NULL,0.0564323427195349,2030,16,4),(191,'2013-03-20 11:58:24',NULL,0.8715150999254829,2055,16,4),(192,'2013-03-20 11:58:24',NULL,0.7632355087736127,2070,16,4),(193,'2013-03-20 11:58:24',NULL,0.31090041400183976,2030,17,1),(194,'2013-03-20 11:58:24',NULL,0.44504047965848825,2055,17,1),(195,'2013-03-20 11:58:24',NULL,0.2850068344302936,2070,17,1),(196,'2013-03-20 11:58:24',NULL,0.8223990246990339,2030,17,2),(197,'2013-03-20 11:58:24',NULL,0.01446802524723434,2055,17,2),(198,'2013-03-20 11:58:24',NULL,0.4595839379967708,2070,17,2),(199,'2013-03-20 11:58:24',NULL,0.9748591273772139,2030,17,3),(200,'2013-03-20 11:58:24',NULL,0.53401709292542,2055,17,3),(201,'2013-03-20 11:58:24',NULL,0.26384764819460393,2070,17,3),(202,'2013-03-20 11:58:24',NULL,0.6821293129343183,2030,17,4),(203,'2013-03-20 11:58:24',NULL,0.6285453143594003,2055,17,4),(204,'2013-03-20 11:58:24',NULL,0.9997099881307144,2070,17,4),(205,'2013-03-20 11:58:24',NULL,0.6763721232978795,2030,18,1),(206,'2013-03-20 11:58:24',NULL,0.32029396778879826,2055,18,1),(207,'2013-03-20 11:58:24',NULL,0.02429877281571513,2070,18,1),(208,'2013-03-20 11:58:24',NULL,0.9917103960817834,2030,18,2),(209,'2013-03-20 11:58:24',NULL,0.5182157516016406,2055,18,2),(210,'2013-03-20 11:58:24',NULL,0.7578388202399357,2070,18,2),(211,'2013-03-20 11:58:24',NULL,0.798451454330054,2030,18,3),(212,'2013-03-20 11:58:24',NULL,0.8472658620385489,2055,18,3),(213,'2013-03-20 11:58:24',NULL,0.7243291201733595,2070,18,3),(214,'2013-03-20 11:58:24',NULL,0.6184189793473505,2030,18,4),(215,'2013-03-20 11:58:24',NULL,0.778427964528345,2055,18,4),(216,'2013-03-20 11:58:24',NULL,0.8355037420177078,2070,18,4);
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
INSERT INTO `csirodatabaseline` VALUES (1,'2013-03-20 11:58:24',16.1,1,1),(2,'2013-03-20 11:58:24',1,1,2),(3,'2013-03-20 11:58:24',1029.8,1,3),(4,'2013-03-20 11:58:24',74.8,1,4);
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
  PRIMARY KEY (`id`),
  KEY `FKA8035BD2994A1ED8` (`user_story_id`),
  CONSTRAINT `FKA8035BD2994A1ED8` FOREIGN KEY (`user_story_id`) REFERENCES `userstory` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataelement`
--

LOCK TABLES `dataelement` WRITE;
/*!40000 ALTER TABLE `dataelement` DISABLE KEYS */;
INSERT INTO `dataelement` VALUES ('File',1,'2013-03-20 11:58:24','','Test 1',0,'This is a test for Data Element','csv',NULL,NULL,1),('File',2,'2013-03-20 11:58:24','','Test 2',0,'This is the second test','xml',NULL,NULL,1),('File',3,'2013-03-20 11:58:24','','Test 3',0,'This is the third test','txt',NULL,NULL,1),('File',4,'2013-03-20 11:58:24','','Test 4',1,'User Story\'s Data Element content test','txt',NULL,NULL,2),('Text',5,'2013-03-20 11:58:24','','Comment text 1',2,NULL,NULL,NULL,'This is a text comment',2),('File',6,'2013-03-20 11:58:24','','Test 5',3,'User Story\'s Data Element content test','txt',NULL,NULL,2),('Text',7,'2013-03-20 11:58:24','','Comment text 2',4,NULL,NULL,NULL,'This is a second text comment',2),('File',8,'2013-03-20 11:58:24','','Test 6',1,'User Story\'s Data Element content test','txt',NULL,NULL,3),('Csiro',9,'2013-03-20 11:58:24','','CSIRO Data Element Test',0,NULL,NULL,'',NULL,1),('EngineeringModel',10,'2013-03-20 11:58:24','','Data Element for Pint ()',0,NULL,NULL,NULL,NULL,1);
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
INSERT INTO `engineeringmodeldata` VALUES (1,'2065,3.0;2032,1.0;2064,5.0;2033,5.0;2067,7.0;2034,4.0;2066,4.0;2035,0.0;2069,2.0;2036,9.0;2068,2.0;2037,6.0;2038,4.0;2070,5.0;2039,5.0;2040,5.0;2041,4.0;2042,2.0;2043,1.0;2044,0.0;2045,4.0;2046,0.0;2047,3.0;2048,3.0;2017,2.0;2049,6.0;2016,4.0;2050,5.0;2019,0.0;2051,7.0;2018,5.0;2052,8.0;2021,7.0;2053,5.0;2020,1.0;2054,8.0;2023,0.0;2055,8.0;2022,2.0;2056,4.0;2025,2.0;2057,2.0;2024,2.0;2058,5.0;2027,0.0;2059,1.0;2026,7.0;2060,7.0;2029,1.0;2061,7.0;2028,9.0;2062,6.0;2031,8.0;2063,0.0;2030,4.0;2002,9.0;2003,0.0;2000,9.0;2001,8.0;2006,6.0;2007,2.0;2004,5.0;2005,2.0;2010,5.0;2011,5.0;2008,1.0;2009,2.0;2014,5.0;2015,8.0;2012,3.0;2013,9.0',1,1,1),(2,'2065,5.0;2032,8.0;2064,0.0;2033,4.0;2067,2.0;2034,9.0;2066,9.0;2035,1.0;2069,0.0;2036,8.0;2068,0.0;2037,4.0;2038,6.0;2070,5.0;2039,8.0;2040,3.0;2041,3.0;2042,6.0;2043,9.0;2044,0.0;2045,1.0;2046,5.0;2047,2.0;2048,7.0;2017,0.0;2049,7.0;2016,0.0;2050,5.0;2019,8.0;2051,4.0;2018,9.0;2052,1.0;2021,5.0;2053,2.0;2020,0.0;2054,7.0;2023,5.0;2055,5.0;2022,4.0;2056,9.0;2025,3.0;2057,3.0;2024,5.0;2058,2.0;2027,2.0;2059,3.0;2026,7.0;2060,2.0;2029,9.0;2061,4.0;2028,5.0;2062,7.0;2031,4.0;2063,7.0;2030,5.0;2002,8.0;2003,0.0;2000,2.0;2001,3.0;2006,4.0;2007,2.0;2004,8.0;2005,3.0;2010,1.0;2011,1.0;2008,2.0;2009,1.0;2014,8.0;2015,0.0;2012,7.0;2013,7.0',1,2,1),(3,'2065,9.0;2032,0.0;2064,7.0;2033,0.0;2067,9.0;2034,4.0;2066,3.0;2035,6.0;2069,6.0;2036,4.0;2068,2.0;2037,8.0;2038,0.0;2070,9.0;2039,2.0;2040,8.0;2041,3.0;2042,0.0;2043,1.0;2044,4.0;2045,9.0;2046,2.0;2047,0.0;2048,8.0;2017,2.0;2049,5.0;2016,7.0;2050,7.0;2019,9.0;2051,9.0;2018,1.0;2052,7.0;2021,7.0;2053,4.0;2020,3.0;2054,5.0;2023,2.0;2055,2.0;2022,2.0;2056,7.0;2025,1.0;2057,6.0;2024,0.0;2058,2.0;2027,3.0;2059,0.0;2026,6.0;2060,6.0;2029,7.0;2061,5.0;2028,9.0;2062,0.0;2031,9.0;2063,2.0;2030,2.0;2002,2.0;2003,1.0;2000,7.0;2001,7.0;2006,3.0;2007,0.0;2004,1.0;2005,6.0;2010,1.0;2011,6.0;2008,2.0;2009,2.0;2014,8.0;2015,1.0;2012,8.0;2013,8.0',1,3,1),(4,'2065,2.0;2032,2.0;2064,7.0;2033,0.0;2067,1.0;2034,4.0;2066,1.0;2035,7.0;2069,7.0;2036,8.0;2068,8.0;2037,3.0;2038,2.0;2070,7.0;2039,8.0;2040,5.0;2041,2.0;2042,5.0;2043,5.0;2044,7.0;2045,2.0;2046,4.0;2047,4.0;2048,3.0;2017,7.0;2049,0.0;2016,7.0;2050,6.0;2019,1.0;2051,0.0;2018,4.0;2052,7.0;2021,4.0;2053,5.0;2020,7.0;2054,5.0;2023,5.0;2055,4.0;2022,7.0;2056,1.0;2025,6.0;2057,0.0;2024,9.0;2058,9.0;2027,4.0;2059,7.0;2026,7.0;2060,4.0;2029,1.0;2061,4.0;2028,5.0;2062,8.0;2031,8.0;2063,6.0;2030,1.0;2002,7.0;2003,8.0;2000,6.0;2001,2.0;2006,1.0;2007,7.0;2004,9.0;2005,8.0;2010,8.0;2011,3.0;2008,9.0;2009,9.0;2014,8.0;2015,7.0;2012,1.0;2013,2.0',1,4,1),(5,'2065,2.0;2032,5.0;2064,2.0;2033,4.0;2067,1.0;2034,1.0;2066,7.0;2035,4.0;2069,7.0;2036,4.0;2068,5.0;2037,0.0;2038,7.0;2070,8.0;2039,4.0;2040,0.0;2041,9.0;2042,4.0;2043,4.0;2044,3.0;2045,8.0;2046,0.0;2047,2.0;2048,2.0;2017,6.0;2049,5.0;2016,8.0;2050,7.0;2019,6.0;2051,8.0;2018,0.0;2052,9.0;2021,6.0;2053,7.0;2020,6.0;2054,7.0;2023,7.0;2055,0.0;2022,5.0;2056,5.0;2025,1.0;2057,4.0;2024,2.0;2058,2.0;2027,4.0;2059,8.0;2026,4.0;2060,1.0;2029,9.0;2061,0.0;2028,9.0;2062,7.0;2031,4.0;2063,6.0;2030,3.0;2002,1.0;2003,8.0;2000,4.0;2001,0.0;2006,4.0;2007,8.0;2004,2.0;2005,2.0;2010,7.0;2011,9.0;2008,0.0;2009,3.0;2014,5.0;2015,2.0;2012,5.0;2013,9.0',1,5,1),(6,'2065,4.0;2032,4.0;2064,2.0;2033,7.0;2067,0.0;2034,9.0;2066,4.0;2035,0.0;2069,3.0;2036,8.0;2068,8.0;2037,2.0;2038,5.0;2070,0.0;2039,3.0;2040,0.0;2041,7.0;2042,4.0;2043,9.0;2044,2.0;2045,3.0;2046,2.0;2047,8.0;2048,4.0;2017,4.0;2049,5.0;2016,3.0;2050,9.0;2019,0.0;2051,4.0;2018,6.0;2052,9.0;2021,5.0;2053,5.0;2020,8.0;2054,5.0;2023,5.0;2055,9.0;2022,2.0;2056,3.0;2025,6.0;2057,2.0;2024,9.0;2058,9.0;2027,8.0;2059,3.0;2026,3.0;2060,5.0;2029,8.0;2061,8.0;2028,7.0;2062,1.0;2031,5.0;2063,0.0;2030,6.0;2002,2.0;2003,4.0;2000,2.0;2001,2.0;2006,9.0;2007,5.0;2004,2.0;2005,1.0;2010,7.0;2011,0.0;2008,8.0;2009,9.0;2014,5.0;2015,2.0;2012,3.0;2013,6.0',1,6,1);
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
INSERT INTO `user` VALUES ('testadmin1','email@company.com','','testadmin1','testadmin1','','password','ROLE_ADMIN'),('testadmin2','email@company.com','','testadmin2','testadmin2','','password','ROLE_ADMIN'),('testuser1','email@company.com','','testuser1','testuser1','','password','ROLE_USER'),('testuser2','email@company.com','','testuser2','testuser2','','password','ROLE_USER'),('testuser3','email@company.com','','testuser3','testuser3','','password','ROLE_USER'),('testuser4','email@company.com','','testuser4','testuser4','','password','ROLE_USER');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userstory`
--

LOCK TABLES `userstory` WRITE;
/*!40000 ALTER TABLE `userstory` DISABLE KEYS */;
INSERT INTO `userstory` VALUES (1,'private','active','User 1 Workboard','testuser1',1),(2,'private','passive','User 1 Story 1','testuser1',2),(3,'public','passive','User 1 Story 2 (Public)','testuser1',1),(4,'public','published','User 1 Story 3 (Published)','testuser1',1),(5,'private','active','User 2 Workboard (Empty)','testuser2',2),(6,'private','passive','User 2 Story (Empty)','testuser2',1);
/*!40000 ALTER TABLE `userstory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-20 13:39:01
