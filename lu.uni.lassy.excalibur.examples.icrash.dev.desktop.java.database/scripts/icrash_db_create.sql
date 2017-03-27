-- MySQL dump 10.13  Distrib 5.5.41, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: icrashfx
-- ------------------------------------------------------
-- Server version	5.5.41-0ubuntu0.12.04.1

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


CREATE DATABASE IF NOT EXISTS icrashfx;
USE icrashfx;

--
-- Table structure for table `alerts`
--

DROP TABLE IF EXISTS `alerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alerts` (
	  `id` varchar(80) NOT NULL,
	  `status` varchar(80) DEFAULT NULL,
	  `latitude` double DEFAULT NULL,
	  `longitude` double DEFAULT NULL,
	  `instant` datetime DEFAULT NULL,
	  `comment` varchar(80) DEFAULT NULL,
  `crisis` varchar(80) DEFAULT NULL,
  `human` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alerts`
--

LOCK TABLES `alerts` WRITE;
/*!40000 ALTER TABLE `alerts` DISABLE KEYS */;
/*!40000 ALTER TABLE `alerts` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Table structure for table `crises`
--

DROP TABLE IF EXISTS `crises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crises` (
  `id` varchar(80) NOT NULL,
  `type` varchar(80) DEFAULT NULL,
  `status` varchar(80) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `instant` datetime DEFAULT NULL,
  `comment` varchar(80) DEFAULT NULL,
  `coordinator` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crises`
--

LOCK TABLES `crises` WRITE;
/*!40000 ALTER TABLE `crises` DISABLE KEYS */;
/*!40000 ALTER TABLE `crises` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `coordinators`
--

DROP TABLE IF EXISTS `coordinators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinators` (
 `id` varchar(80) NOT NULL,
  `login` varchar(80) DEFAULT NULL,
  `pwd` varchar(80) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinators`
--

LOCK TABLES `coordinators` WRITE;
/*!40000 ALTER TABLE `coordinators` DISABLE KEYS */;
/*!40000 ALTER TABLE `coordinators` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `comcompanies`
--

DROP TABLE IF EXISTS `comcompanies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comcompanies` (
 `id` varchar(80) NOT NULL,
  `name` varchar(80) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comcompanies`
--

LOCK TABLES `comcompanies` WRITE;
/*!40000 ALTER TABLE `comcompanies` DISABLE KEYS */;
/*!40000 ALTER TABLE `comcompanies` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `humans`
--

DROP TABLE IF EXISTS `humans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `humans` (
 `phone` varchar(80) NOT NULL,
  `kind` varchar(80) DEFAULT NULL,
  `comcompany` varchar(80) DEFAULT NULL,
 PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `humans`
--

LOCK TABLES `humans` WRITE;
/*!40000 ALTER TABLE `humans` DISABLE KEYS */;
/*!40000 ALTER TABLE `humans` ENABLE KEYS */;
UNLOCK TABLES;





--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reports` (
 `id` varchar(80) NOT NULL,
 `crisis` varchar(80) DEFAULT NULL,
  `message` varchar(80) DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


/*grant all on `database`.* to 'user'@'localhost' identified by 'password'*/
grant all on `icrashfx`.* to 'il2_icrash'@'%' identified by 'il2_icrash';
grant all on `icrashfx`.* to 'il2_icrash'@'localhost' identified by 'il2_icrash';


-- Dump completed on 2015-02-07 15:41:19
