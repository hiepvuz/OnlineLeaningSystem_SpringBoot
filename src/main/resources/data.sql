CREATE
DATABASE  IF NOT EXISTS `ols` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE
`ols`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: ols
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `class_user`
--

DROP TABLE IF EXISTS `class_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_user`
(
    `note`               varchar(255) DEFAULT NULL,
    `status`             varchar(255) DEFAULT NULL,
    `user_user_id`       bigint NOT NULL,
    `classroom_class_id` bigint NOT NULL,
    PRIMARY KEY (`classroom_class_id`, `user_user_id`),
    KEY                  `FKqay591xe8hegjwwqi6e4n5667` (`user_user_id`),
    CONSTRAINT `FK5qs69jqvylb10a29vn8pgggnl` FOREIGN KEY (`classroom_class_id`) REFERENCES `classroom` (`class_id`),
    CONSTRAINT `FKqay591xe8hegjwwqi6e4n5667` FOREIGN KEY (`user_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_user`
--

LOCK
TABLES `class_user` WRITE;
/*!40000 ALTER TABLE `class_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_user` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `classroom`
--

DROP TABLE IF EXISTS `classroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classroom`
(
    `class_id`    bigint NOT NULL,
    `class_code`  varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `status`      bit(1)       DEFAULT NULL,
    `trainer_id`  bigint       DEFAULT NULL,
    `package_id`  bigint NOT NULL,
    `subject_id`  bigint NOT NULL,
    PRIMARY KEY (`class_id`),
    KEY           `FKofb5sj9e72sg4grlitwu2w2hu` (`package_id`),
    KEY           `FKiolxh9fx4qrckbo5eitaqduot` (`subject_id`),
    CONSTRAINT `FKiolxh9fx4qrckbo5eitaqduot` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`),
    CONSTRAINT `FKofb5sj9e72sg4grlitwu2w2hu` FOREIGN KEY (`package_id`) REFERENCES `package` (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classroom`
--

LOCK
TABLES `classroom` WRITE;
/*!40000 ALTER TABLE `classroom` DISABLE KEYS */;
/*!40000 ALTER TABLE `classroom` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `combo`
--

DROP TABLE IF EXISTS `combo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo`
(
    `combo_id` bigint NOT NULL,
    PRIMARY KEY (`combo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo`
--

LOCK
TABLES `combo` WRITE;
/*!40000 ALTER TABLE `combo` DISABLE KEYS */;
/*!40000 ALTER TABLE `combo` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `combo_package`
--

DROP TABLE IF EXISTS `combo_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `combo_package`
(
    `combo_combo_id`  bigint NOT NULL,
    `pack_package_id` bigint NOT NULL,
    PRIMARY KEY (`combo_combo_id`, `pack_package_id`),
    KEY               `FKanems4bmyw2tpg0weopiylnyx` (`pack_package_id`),
    CONSTRAINT `FKanems4bmyw2tpg0weopiylnyx` FOREIGN KEY (`pack_package_id`) REFERENCES `package` (`package_id`),
    CONSTRAINT `FKh0npslxs8co1v38adr40udnbk` FOREIGN KEY (`combo_combo_id`) REFERENCES `combo` (`combo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `combo_package`
--

LOCK
TABLES `combo_package` WRITE;
/*!40000 ALTER TABLE `combo_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `combo_package` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK
TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence`
VALUES (4);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package`
(
    `package_id`    bigint NOT NULL,
    `description`   varchar(255) DEFAULT NULL,
    `duration`      varchar(255) DEFAULT NULL,
    `is_combo`      bit(1)       DEFAULT NULL,
    `subject_id`    bigint       DEFAULT NULL,
    `term_id`       bigint       DEFAULT NULL,
    `thumbnail_url` varchar(255) DEFAULT NULL,
    `title`         varchar(255) DEFAULT NULL,
    PRIMARY KEY (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK
TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission`
(
    `all_data`          bit(1) DEFAULT NULL,
    `can_add`           bit(1) DEFAULT NULL,
    `can_delete`        bit(1) DEFAULT NULL,
    `can_edit`          bit(1) DEFAULT NULL,
    `roleId_setting_id` bigint NOT NULL,
    `screenId_screenId` bigint NOT NULL,
    PRIMARY KEY (`roleId_setting_id`, `screenId_screenId`),
    KEY                 `FKkpiyb0sgt9yjwxr7hu6w7skas` (`screenId_screenId`),
    CONSTRAINT `FKkpiyb0sgt9yjwxr7hu6w7skas` FOREIGN KEY (`screenId_screenId`) REFERENCES `screen` (`screenId`),
    CONSTRAINT `FKo10dgk3fumul6lx5xh1e9n4gb` FOREIGN KEY (`roleId_setting_id`) REFERENCES `setting` (`setting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK
TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `screen`
--

DROP TABLE IF EXISTS `screen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `screen`
(
    `screenId` bigint NOT NULL,
    PRIMARY KEY (`screenId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `screen`
--

LOCK
TABLES `screen` WRITE;
/*!40000 ALTER TABLE `screen` DISABLE KEYS */;
/*!40000 ALTER TABLE `screen` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setting`
(
    `setting_id`    bigint NOT NULL,
    `description`   varchar(255) DEFAULT NULL,
    `display_order` varchar(255) DEFAULT NULL,
    `setting_title` varchar(255) DEFAULT NULL,
    `setting_value` varchar(255) DEFAULT NULL,
    `status`        bit(1)       DEFAULT NULL,
    `type_id`       bigint       DEFAULT NULL,
    PRIMARY KEY (`setting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK
TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting`
VALUES (1, NULL, NULL, 'USER_ROLE', NULL, _binary '', NULL),
       (2, NULL, NULL, 'WEB_CONTACT_CATEGORY', NULL, _binary '', NULL),
       (3, NULL, NULL, 'CLASS_SETTING_TYPE', NULL, _binary '', NULL),
       (4, NULL, NULL, 'SUBJECT_SETTING_TYPE', NULL, _binary '', NULL),
       (5, NULL, NULL, 'SYSTEM_SCREEN', NULL, _binary '', NULL),
       (6, NULL, NULL, 'SUBJECT_CATEGORY', NULL, _binary '', NULL),
       (7, NULL, NULL, 'A', NULL, _binary '\0', NULL),
       (8, NULL, NULL, 'B', NULL, _binary '\0', NULL),
       (9, NULL, NULL, 'C', NULL, _binary '\0', NULL),
       (10, NULL, NULL, 'D', NULL, _binary '\0', NULL),
       (11, NULL, NULL, 'E', NULL, _binary '\0', NULL),
       (12, NULL, NULL, 'F', NULL, _binary '\0', NULL),
       (13, NULL, NULL, 'G', NULL, _binary '\0', NULL),
       (14, NULL, NULL, 'H', NULL, _binary '\0', NULL),
       (15, NULL, NULL, 'I', NULL, _binary '\0', NULL),
       (16, NULL, NULL, 'K', NULL, _binary '\0', NULL),
       (17, NULL, NULL, 'N', NULL, _binary '\0', NULL),
       (18, NULL, NULL, 'M', NULL, _binary '\0', NULL),
       (19, NULL, NULL, 'L', NULL, _binary '\0', NULL),
       (20, NULL, NULL, 'J', NULL, _binary '\0', NULL),
       (21, 'Role Admin', '1', 'Admin', NULL, _binary '', 1),
       (22, 'Role Manager', '2', 'Manager', NULL, _binary '', 1),
       (23, 'Role Expert', '3', 'Expert', NULL, _binary '', 1),
       (24, 'Role Trainee', '4', 'Trainee', NULL, _binary '', 1);
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject`
(
    `subject_id`   bigint NOT NULL,
    `body`         varchar(255) DEFAULT NULL,
    `category_id`  bigint       DEFAULT NULL,
    `expert_id`    bigint       DEFAULT NULL,
    `manager_id`   bigint       DEFAULT NULL,
    `status`       bit(1)       DEFAULT NULL,
    `subject_code` varchar(255) DEFAULT NULL,
    `subject_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK
TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `user_package`
--

DROP TABLE IF EXISTS `user_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_package`
(
    `id`         bigint NOT NULL,
    `from_date`  datetime(6) DEFAULT NULL,
    `package_id` bigint NOT NULL,
    `user_id`    bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY          `FKjcwapl2htr7k7l485y29cinog` (`package_id`),
    KEY          `FK23wrg2jabxivswndr07og5q0y` (`user_id`),
    CONSTRAINT `FK23wrg2jabxivswndr07og5q0y` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FKjcwapl2htr7k7l485y29cinog` FOREIGN KEY (`package_id`) REFERENCES `package` (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_package`
--

LOCK
TABLES `user_package` WRITE;
/*!40000 ALTER TABLE `user_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_package` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    KEY       `FK1sc2g2ox9c5m3j7k2horvcsmy` (`role_id`),
    CONSTRAINT `FK1sc2g2ox9c5m3j7k2horvcsmy` FOREIGN KEY (`role_id`) REFERENCES `setting` (`setting_id`),
    CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK
TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role`
VALUES (2, 21),
       (3, 21),
       (3, 23),
       (1, 24);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
    `user_id`      bigint       NOT NULL,
    `address`      varchar(255) DEFAULT NULL,
    `avatar_url`   varchar(255) DEFAULT NULL,
    `created_date` timestamp NULL DEFAULT NULL,
    `dateOfBirth`  datetime(6) DEFAULT NULL,
    `disabled`     bit(1)       DEFAULT NULL,
    `email`        varchar(255) DEFAULT NULL,
    `full_name`    varchar(255) DEFAULT NULL,
    `gender`       varchar(255) DEFAULT NULL,
    `password`     varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `updated_date` timestamp NULL DEFAULT NULL,
    `username`     varchar(255) NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`),
    UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK
TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users`
VALUES (1, 'Ha Noi', 'avatar.png', '2022-09-18 04:33:34', '2022-09-15 07:00:00.000000', _binary '\0', 'user1@gmail.com',
        'User user 1', 'Male', '$2a$10$rgPpac1FceJ70Guw05sNk.WH1lBZ.Ck3BmjRKDMfNJuCeaKy4ceyC', '0999123123',
        '2022-09-18 05:12:47', 'user1'),
       (2, 'Ha Noi', 'avatar.png', '2022-09-18 05:14:45', '2022-09-15 07:00:00.000000', _binary '\0', 'user2@gmail.com',
        'User user 1', 'Male', '$2a$10$v8Ji.zf3zKmtbTMgrGK88ul0ibjI6UECMxa4bSeFLqb6NgF1/HkPm', '0999123123',
        '2022-09-18 05:35:30', 'user2'),
       (3, 'Ha Noi', 'avatar.png', '2022-09-18 05:21:58', '2022-09-15 07:00:00.000000', _binary '\0', 'user3@gmail.com',
        'User user 1', 'Male', '$2a$10$NyBUV3zPKT6Nmzo9uJNVP.5krou61LDUgB74wW/Pmh8mv/NT95OSq', '0999123123',
        '2022-09-18 05:35:38', 'user3');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Dumping events for database 'ols'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-18 12:46:57
