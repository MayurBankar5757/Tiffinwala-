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
-- Dumping data for table `customer_order_log`
--

LOCK TABLES `customer_order_log` WRITE;
/*!40000 ALTER TABLE `customer_order_log` DISABLE KEYS */;
INSERT INTO `customer_order_log` VALUES (1,4,'2023-12-01',0),(2,5,'2023-12-02',0),(3,6,'2023-12-03',0);
/*!40000 ALTER TABLE `customer_order_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customer_subscribed_plans`
--

LOCK TABLES `customer_subscribed_plans` WRITE;
/*!40000 ALTER TABLE `customer_subscribed_plans` DISABLE KEYS */;
INSERT INTO `customer_subscribed_plans` VALUES (1,4,1),(2,5,2),(3,6,3);
/*!40000 ALTER TABLE `customer_subscribed_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `customer_subscriptions_junction`
--

LOCK TABLES `customer_subscriptions_junction` WRITE;
/*!40000 ALTER TABLE `customer_subscriptions_junction` DISABLE KEYS */;
INSERT INTO `customer_subscriptions_junction` VALUES (1,4,1),(2,5,2),(3,6,3);
/*!40000 ALTER TABLE `customer_subscriptions_junction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,1,4,1,'The meal was very tasty, but the delivery was late.',4),(2,2,5,2,'I loved the variety of meals in the Premium Plan!',5),(3,3,6,3,'The food was healthy but could use more flavor.',3);
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,'razorpay_123456','2023-12-01',500.00),(2,2,'razorpay_789012','2023-12-02',1000.00),(3,3,'razorpay_345678','2023-12-03',300.00);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(3,'Customer'),(2,'Vendor');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tiffin`
--

LOCK TABLES `tiffin` WRITE;
/*!40000 ALTER TABLE `tiffin` DISABLE KEYS */;
INSERT INTO `tiffin` VALUES (1,1,'Tasty Veg Meal','','','',NULL,0),(2,1,'Non-Veg Special','','','',NULL,0),(3,2,'Vegan Delight','','','',NULL,0),(4,2,'Paleo Tiffin','','','',NULL,0),(5,3,'Healthy Meal','','','',NULL,0),(6,1,'Tasty Veg Meal','Monday','A delicious vegetarian meal.','Veg',NULL,0),(7,1,'Non-Veg Special','Tuesday','A special non-vegetarian tiffin.','Non-Veg',NULL,0),(8,2,'Vegan Delight','Wednesday','A healthy and tasty vegan meal.','Vegan',NULL,0),(9,2,'Paleo Tiffin','Thursday','A nutritious paleo-friendly tiffin.','Paleo',NULL,0),(10,3,'Healthy Meal','Friday','A balanced meal for a healthy lifestyle.','Veg',NULL,0);
/*!40000 ALTER TABLE `tiffin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Amit','Pandhare','amit.pandhare@example.com',1,'Admin Area','Admin City','123456','Admin State','admin123','9999999999'),(2,'Rohit','Sharma','rohit.sharma@example.com',2,'Vendor Street 1','Vendor City','234567','Vendor State','vendor123','8888888888'),(3,'Priya','Singh','priya.singh@example.com',2,'Vendor Street 2','Vendor City','345678','Vendor State','vendor456','7777777777'),(4,'Ramesh','Kumar','ramesh.kumar@example.com',3,'Customer Lane 1','Customer City','456789','Customer State','customer123','6666666666'),(5,'Sita','Devi','sita.devi@example.com',3,'Customer Lane 2','Customer City','567890','Customer State','customer456','5555555555'),(6,'Arjun','Rao','arjun.rao@example.com',3,'Customer Lane 3','Customer City','678901','Customer State','customer789','4444444444'),(7,'Meera','Patil','meera.patil@example.com',3,'Customer Lane 4','Customer City','789012','Customer State','customer321','3333333333'),(8,'John','Doe','john.doe@example.com',2,NULL,NULL,NULL,NULL,'password123','9876543210'),(9,'dip','chaf','dipc@gmail.com',2,NULL,NULL,NULL,NULL,'dip@123','767667665'),(10,'Anurag','Bab','anurag@gmail.com',3,NULL,NULL,NULL,NULL,'cust@123','8746466212'),(11,'Иван','Иванов','ivan.ivanov@example.ru',3,NULL,NULL,NULL,NULL,'пароль123','+7-123-456-7890'),(12,'Carlos','Gonzalez','carlos.gonzalez@example.es',3,'Plaza Mayor','Madrid','28013','Madrid','password123','+34-123-456-789'),(13,'Ivan','Petrov','ivan.petrov@example.ru',3,'Red Square','Moscow','101000','Moscow Oblast','securepassword','+91-9876543210'),(14,'Ajay','Kulkarni','ajay.kulkarni@example.in',2,'Deccan Gymkhana','Pune','411004','Maharashtra','vendor123','+91-9876543214'),(15,'Aditya','Kalaskar','Kalaskar@gmail.com',3,'Deccan','Pune','444890','Maharastra','Aditya@1234','7839974903'),(16,'User101','lnm','user101@gmaul.com',2,'area1','city1','334345','MAHA','user101','343133487'),(18,'Nikhil','Divekar','Nikhil@gmail.com',2,'Deccan','Pune','789930','Maharastra','Divekar@123','7847599303');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` VALUES (1,1,2,'',NULL),(2,0,3,'',NULL),(3,1,8,'123456789012','FL123456789'),(4,1,9,'73763629','df34474890'),(5,1,14,'123456789016','IN567890123'),(6,1,16,'786757655','yu6799999'),(8,0,18,'4884774','98989');
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `vendor_subscription_plan`
--

LOCK TABLES `vendor_subscription_plan` WRITE;
/*!40000 ALTER TABLE `vendor_subscription_plan` DISABLE KEYS */;
INSERT INTO `vendor_subscription_plan` VALUES (1,1,'Basic Plan',500,'A simple meal plan','basic_plan.jpg',1,4),(2,1,'Premium Plan',1000,'Includes dessert and drinks','premium_plan.jpg',1,5),(3,2,'Economy Plan',300,'Affordable meal plan','economy_plan.jpg',1,3);
/*!40000 ALTER TABLE `vendor_subscription_plan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-04  9:33:00
