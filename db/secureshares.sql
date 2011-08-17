-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: secureshares
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

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
-- Current Database: `secureshares`
--

/*!40000 DROP DATABASE IF EXISTS `secureshares`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `secureshares` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `secureshares`;

--
-- Table structure for table `downloadTypes`
--

DROP TABLE IF EXISTS `downloadTypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `downloadTypes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `count` int(11) NOT NULL,
  `validity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `downloadTypes`
--

LOCK TABLES `downloadTypes` WRITE;
/*!40000 ALTER TABLE `downloadTypes` DISABLE KEYS */;
INSERT INTO `downloadTypes` VALUES (1,'Single Download',1,-1),(2,'One Hour',-1,1),(3,'One Day',-1,24),(4,'One Week',-1,168),(5,'Disable Download',-1,-1);
/*!40000 ALTER TABLE `downloadTypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `downloads`
--

DROP TABLE IF EXISTS `downloads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `downloads` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fileId` int(10) unsigned NOT NULL,
  `downloadTypeId` int(10) unsigned NOT NULL,
  `date` datetime NOT NULL,
  `count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `downloads`
--

LOCK TABLES `downloads` WRITE;
/*!40000 ALTER TABLE `downloads` DISABLE KEYS */;
INSERT INTO `downloads` VALUES (1,13,2,'2011-07-14 14:22:03',1),(2,14,2,'2011-08-05 10:15:12',0);
/*!40000 ALTER TABLE `downloads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `files` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `filename` varchar(250) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `UQ_filename` (`filename`),
  KEY `fk_to_users` (`userId`),
  CONSTRAINT `fk_to_users` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` VALUES (12,2,'4PZHRA.pdf','2011-06-18 19:51:24'),(13,2,'README-LINUX.TXT','2011-06-19 12:32:12'),(14,2,'Sutopia-Fotos-07072011.zip','2011-07-07 22:25:35');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `i18n`
--

DROP TABLE IF EXISTS `i18n`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `i18n` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lang` char(2) CHARACTER SET latin1 NOT NULL,
  `key` varchar(50) NOT NULL,
  `value` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_lang_key` (`lang`,`key`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `i18n`
--

LOCK TABLES `i18n` WRITE;
/*!40000 ALTER TABLE `i18n` DISABLE KEYS */;
INSERT INTO `i18n` VALUES (1,'gb','menuHome','Home'),(2,'gb','menuHowItWorks','How it works?'),(3,'gb','menuSupport','Support'),(4,'gb','menuLogout','Logout'),(5,'gb','menuLeftUserManagement','user management'),(6,'gb','menuLeftFileManagement','file management'),(7,'gb','menuLeftUploadFiles','upload files'),(8,'gb','footerImprint','imprint'),(9,'gb','footerTerms','terms'),(10,'gb','contentHomeTitle','Welcome to SecureShares'),(11,'gb','contentHomeP1','SecureShares</b> is a secure file sharing service from PCConsultants Ltd &amp; Co KG for individuals as well as small and mid-sized companies who need to share files securely.'),(12,'gb','contentHomeP2','With <b>SecureShares</b> you can easily upload files to your secure shares areas and easily make them available to customers for a one-time download or set an expiration time (e.g. one hour, one day or one week) Your customer will receive an mail with a secure link to the encyrpted file. Downloads are as easy as a click of the mouse. Works with all browsers.'),(13,'gb','contentHomeP3','If you wish your customer to upload an edited file, they need only login to the web interface and drag &amp; drop their file into the upload box. You will be alerted by email when a new file arrives.'),(14,'gb','contentHowItWorksTitle','How SecureShares Works'),(15,'gb','contentHowItWorksP1','Four easy steps to secure file sharing'),(16,'gb','contentHowItWorksP2','<ol><li>An authorized and authenticated user uploads a file via a secure connection using https.</li><li>The file is stored securely on the server for a defined period (e.g. one download, one day, one week)</li><li>An email is sent to the intended recipient(s) which includes a secure link (again using https) to the file</li><li>The recipient then downloads the file safely to her computer</li></ol>'),(17,'gb','contentHowItWorksP3','With <b>SecureShares</b> you are assured of secure storage and transmission of your files and once downloaded in the case you choose the single download option or after the time you allot to keep it available. After that, it is removed from the server forever.'),(18,'gb','contentSupportTitle','Support for SecureShares'),(19,'gb','contentSupportP1','If something has gone wrong please alert our security administrators so we can help you.'),(20,'gb','contentSupportP2','Send mail to our <a href=\"mailto:help@secure-shares.net?subject=SecureShare Request\">help desk</a> help(at)secure-shares.net.'),(21,'fr','menuHome','Home'),(22,'fr','menuHowItWorks','How it works?'),(23,'fr','menuSupport','Support'),(24,'fr','menuLogout','Logout'),(25,'fr','menuLeftUserManagement','user management'),(26,'fr','menuLeftFileManagement','file management'),(27,'fr','menuLeftUploadFiles','upload files'),(28,'fr','footerImprint','imprint'),(29,'fr','footerTerms','terms'),(30,'fr','contentHomeTitle','Welcome to SecureShares'),(31,'fr','contentHomeP1','SecureShares</b> is a secure file sharing service from PCConsultants Ltd &amp; Co KG for individuals as well as small and mid-sized companies who need to share files securely.'),(32,'fr','contentHomeP2','With <b>SecureShares</b> you can easily upload files to your secure shares areas and easily make them available to customers for a one-time download or set an expiration time (e.g. one hour, one day or one week) Your customer will receive an mail with a secure link to the encyrpted file. Downloads are as easy as a click of the mouse. Works with all browsers.'),(33,'fr','contentHomeP3','If you wish your customer to upload an edited file, they need only login to the web interface and drag &amp; drop their file into the upload box. You will be alerted by email when a new file arrives.'),(34,'fr','contentHowItWorksTitle','How SecureShares Works'),(35,'fr','contentHowItWorksP1','Four easy steps to secure file sharing'),(36,'fr','contentHowItWorksP2','<ol><li>An authorized and authenticated user uploads a file via a secure connection using https.</li><li>The file is stored securely on the server for a defined period (e.g. one download, one day, one week)</li><li>An email is sent to the intended recipient(s) which includes a secure link (again using https) to the file</li><li>The recipient then downloads the file safely to her computer</li></ol>'),(37,'fr','contentHowItWorksP3','With <b>SecureShares</b> you are assured of secure storage and transmission of your files and once downloaded in the case you choose the single download option or after the time you allot to keep it available. After that, it is removed from the server forever.'),(38,'fr','contentSupportTitle','Support for SecureShares'),(39,'fr','contentSupportP1','If something has gone wrong please alert our security administrators so we can help you.'),(40,'fr','contentSupportP2','Send mail to our <a href=\"mailto:help@secure-shares.net?subject=SecureShare Request\">help desk</a> help(at)secure-shares.net.'),(41,'de','menuHome','Home'),(42,'de','menuHowItWorks','Wie funktioniert es?'),(43,'de','menuSupport','Unterst&uuml;tzung'),(44,'de','menuLogout','Logout'),(45,'de','menuLeftUserManagement','benutzer management'),(46,'de','menuLeftFileManagement','datei management'),(47,'de','menuLeftUploadFiles','dateien hochladen'),(48,'de','footerImprint','impressum'),(49,'de','footerTerms','agb'),(50,'de','contentHomeTitle','Willkommen bei SecureShares'),(51,'de','contentHomeP1','SecureShares ist ein sicheres Filesharing-Service von PCConsultants Ltd & Co KG f&uuml;r Einzelpersonen sowie kleine und mittelst&auml;ndische Unternehmen, die Dateien sicher gemeinsam nutzen m&uuml;ssen.'),(52,'de','contentHomeP2','Mit SecureShares k&ouml;nnen Sie ganz einfach Dateien hochladen, um Ihre sichere Aktien Bereichen und leicht zug&auml;nglich zu machen Kunden f&uuml;r einen einmaligen Download oder eine Ablaufzeit (zB eine Stunde, einen Tag oder eine Woche) Ihr Kunde eine Mail mit einem sicheren Empfang Link zu den encyrpted Datei. Downloads sind so einfach wie ein Mausklick. Funktioniert mit allen Browsern.'),(53,'de','contentHomeP3','Wenn Sie Ihren Kunden eine bearbeitete Datei hochladen wollen, m&uuml;ssen sie nur auf das Web-Interface anmelden und per Drag & Drop die Datei in der Upload-Feld. Sie werden per E-Mail benachrichtigt werden, wenn eine neue Datei ankommt.'),(54,'de','contentHowItWorksTitle','Wie SecureShares funktioniert'),(55,'de','contentHowItWorksP1','Vier einfache Schritte, um File Sharing zu sichern'),(56,'de','contentHowItWorksP2','<ol> <li> Eine berechtigte und authentifizierte Benutzer eine Datei &uuml;ber eine sichere Verbindung &uuml;ber https. </ li> <li> Die Datei ist sicher auf dem Server f&uuml;r einen bestimmten Zeitraum (zB ein Download, einen Tag, eine Woche gelagert ) </ li> <li> Eine E-Mail wird an den Empf&auml;nger (s), die eine sichere Verbindung (wieder mit https), um die Datei enth&auml;lt gesendet </ li> <li> der Empf&auml;nger l&aumldt dann die Datei sicher auf ihrem Computer </ li> </ ol>'),(57,'de','contentHowItWorksP3','Mit <b>SecureShares</b> Sie sind der sichere Speicherung und &Uuml;bertragung von Dateien gesichert und einmal im Fall w&auml;hlen Sie die einzelnen Download-Option oder nach der Zeit, die Sie zuweisen, damit es verf&uuml;gbar heruntergeladen. Danach wird sie vom Server f&uuml;r immer beseitigt.'),(58,'de','contentSupportTitle','Unterst&uuml;tzung f&uumlr SecureShares'),(59,'de','contentSupportP1','Wenn etwas schief gegangen ist bitte aufmerksam unsere Sicherheits-Administratoren, damit wir Ihnen helfen k&ouml;nnen.'),(60,'de','contentSupportP2','Sende eine Mail an unseren <a href=\"mailto:help@secure-shares.net?subject=SecureShare Request\"> Helpdesk </ a> help (at) secure-shares.net.');
/*!40000 ALTER TABLE `i18n` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `rolename` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ciprianticu@gmail.com','admin'),(2,'pcaron@pcconsultants.de','admin'),(6,'ciprianticu@yahoo.com','user'),(7,'simone@schroederdesign.com','user'),(8,'etournes@e-majuscule.fr','user'),(9,'nzjacic@pcconsultants.de','user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'ciprianticu@gmail.com','bdbe366733db79f0f4bfcac2c7c637f9'),(2,'pcaron@pcconsultants.de','febe7831563d14687d257df737f6fd03'),(9,'ciprianticu@yahoo.com','bdbe366733db79f0f4bfcac2c7c637f9'),(10,'simone@schroederdesign.com','3207c8392b9793c26fddf7956388269e'),(11,'etournes@e-majuscule.fr','3207c8392b9793c26fddf7956388269e'),(12,'nzjacic@pcconsultants.de','3207c8392b9793c26fddf7956388269e');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-17  8:50:16
