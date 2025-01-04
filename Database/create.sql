CREATE DATABASE  IF NOT EXISTS `tiffinwala` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tiffinwala`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: tiffinwala
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer_order_log`
--

DROP TABLE IF EXISTS `customer_order_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_order_log` (
  `Order_id` int NOT NULL AUTO_INCREMENT,
  `Uid` int NOT NULL,
  `Order_date` date NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`Order_id`),
  KEY `Uid` (`Uid`),
  CONSTRAINT `customer_order_log_ibfk_1` FOREIGN KEY (`Uid`) REFERENCES `user` (`Uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_subscribed_plans`
--

DROP TABLE IF EXISTS `customer_subscribed_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_subscribed_plans` (
  `Customer_plan_id` int NOT NULL AUTO_INCREMENT,
  `Uid` int NOT NULL,
  `v_subscription_id` int NOT NULL,
  PRIMARY KEY (`Customer_plan_id`),
  KEY `Uid` (`Uid`),
  KEY `v_subscription_id` (`v_subscription_id`),
  CONSTRAINT `customer_subscribed_plans_ibfk_1` FOREIGN KEY (`Uid`) REFERENCES `user` (`Uid`),
  CONSTRAINT `customer_subscribed_plans_ibfk_2` FOREIGN KEY (`v_subscription_id`) REFERENCES `vendor_subscription_plan` (`Plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_subscriptions_junction`
--

DROP TABLE IF EXISTS `customer_subscriptions_junction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_subscriptions_junction` (
  `Customer_plan_id` int NOT NULL,
  `Uid` int NOT NULL,
  `Plan_id` int NOT NULL,
  PRIMARY KEY (`Customer_plan_id`,`Uid`,`Plan_id`),
  KEY `Uid` (`Uid`),
  KEY `Plan_id` (`Plan_id`),
  CONSTRAINT `customer_subscriptions_junction_ibfk_1` FOREIGN KEY (`Customer_plan_id`) REFERENCES `customer_subscribed_plans` (`Customer_plan_id`),
  CONSTRAINT `customer_subscriptions_junction_ibfk_2` FOREIGN KEY (`Uid`) REFERENCES `user` (`Uid`),
  CONSTRAINT `customer_subscriptions_junction_ibfk_3` FOREIGN KEY (`Plan_id`) REFERENCES `vendor_subscription_plan` (`Plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `customer_plan_id` int NOT NULL,
  `uid` int NOT NULL,
  `v_subscription_id` int NOT NULL,
  `feedback_text` varchar(500) NOT NULL,
  `Rating` int DEFAULT NULL,
  PRIMARY KEY (`feedback_id`),
  KEY `customer_plan_id` (`customer_plan_id`),
  KEY `uid` (`uid`),
  KEY `v_subscription_id` (`v_subscription_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`customer_plan_id`) REFERENCES `customer_subscribed_plans` (`Customer_plan_id`),
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`Uid`),
  CONSTRAINT `feedback_ibfk_3` FOREIGN KEY (`v_subscription_id`) REFERENCES `vendor_subscription_plan` (`Plan_id`),
  CONSTRAINT `feedback_chk_1` CHECK ((`Rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `Pid` int NOT NULL AUTO_INCREMENT,
  `Order_id` int NOT NULL,
  `RazorPay_id` varchar(255) NOT NULL,
  `Payment_date` date NOT NULL,
  `Amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`Pid`),
  UNIQUE KEY `RazorPay_id` (`RazorPay_id`),
  KEY `Order_id` (`Order_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`Order_id`) REFERENCES `customer_order_log` (`Order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `Role_Id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`Role_Id`),
  UNIQUE KEY `Role_Name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiffin`
--

DROP TABLE IF EXISTS `tiffin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiffin` (
  `Tiffin_id` int NOT NULL AUTO_INCREMENT,
  `V_Subscription_id` int NOT NULL,
  `Tiffin_name` varchar(255) NOT NULL,
  `day` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `food_type` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`Tiffin_id`),
  KEY `V_Subscription_id` (`V_Subscription_id`),
  CONSTRAINT `tiffin_ibfk_1` FOREIGN KEY (`V_Subscription_id`) REFERENCES `vendor_subscription_plan` (`Plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `Uid` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `email` varchar(70) NOT NULL,
  `Rid` int NOT NULL,
  `area` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `password` varchar(30) NOT NULL,
  `contact` varchar(20) NOT NULL,
  PRIMARY KEY (`Uid`),
  UNIQUE KEY `email` (`email`),
  KEY `role_id` (`Rid`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`Rid`) REFERENCES `role` (`Role_Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendor` (
  `Vendor_id` int NOT NULL AUTO_INCREMENT,
  `Is_verified` tinyint(1) NOT NULL DEFAULT '0',
  `Uid` int NOT NULL,
  `adhar_no` varchar(255) DEFAULT NULL,
  `food_licence_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Vendor_id`),
  KEY `Uid` (`Uid`),
  CONSTRAINT `vendor_ibfk_1` FOREIGN KEY (`Uid`) REFERENCES `user` (`Uid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vendor_subscription_plan`
--

DROP TABLE IF EXISTS `vendor_subscription_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendor_subscription_plan` (
  `Plan_id` int NOT NULL AUTO_INCREMENT,
  `Vendor_id` int NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Price` int NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Image` varchar(255) NOT NULL,
  `is_available` tinyint(1) NOT NULL DEFAULT '1',
  `Rating` int DEFAULT NULL,
  PRIMARY KEY (`Plan_id`),
  KEY `Vendor_id` (`Vendor_id`),
  CONSTRAINT `vendor_subscription_plan_ibfk_1` FOREIGN KEY (`Vendor_id`) REFERENCES `vendor` (`Vendor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-04  9:32:17
