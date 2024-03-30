DROP SCHEMA IF EXISTS `test`;

CREATE SCHEMA `test`;

USE `test`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `patient_detail`;

CREATE TABLE `patient_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tr_identity_number` BIGINT(11) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TR_ID_NO_UNIQUE` (`tr_identity_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `patient`;

CREATE TABLE `patient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `patient_detail_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idx` (`patient_detail_id`),
  CONSTRAINT `FK_DETAIL` FOREIGN KEY (`patient_detail_id`) 
  REFERENCES `patient_detail` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `lab_technician`;

CREATE TABLE `lab_technician` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `hospital_identity_number` int(7) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `HOSPITAL_ID_NO_UNIQUE` (`hospital_identity_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `report_detail`;

CREATE TABLE `report_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `diagnosis_details` varchar(255) DEFAULT NULL,
  `report_date` DATE DEFAULT NULL,
  `report_photo` BLOB DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `report`;

CREATE TABLE `report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `report_code` varchar(40) DEFAULT NULL,
  `diagnosis_title` varchar(50) DEFAULT NULL,
  `patient_id` int DEFAULT NULL,
  `lab_technician_id` int DEFAULT NULL,
  `report_detail_id` int DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `REPORT_CODE_UNIQUE` (`report_code`),
  
  KEY `FK_REPORT_DETAIL_idx` (`report_detail_id`),
  CONSTRAINT `FK_REPORT_DETAIL` FOREIGN KEY (`report_detail_id`) 
  REFERENCES `report_detail` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_PATIENT_idx` (`patient_id`),
  CONSTRAINT `FK_PATIENT` 
  FOREIGN KEY (`patient_id`) 
  REFERENCES `patient` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  KEY `FK_LAB_TECHNICIAN_idx` (`lab_technician_id`),
  CONSTRAINT `FK_LAB_TECHNICIAN`
  FOREIGN KEY (`lab_technician_id`)
  REFERENCES `lab_technician` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


SET FOREIGN_KEY_CHECKS = 1;