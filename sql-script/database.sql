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

INSERT INTO `patient_detail` VALUES 
(1, 39593859155, 'ali@ornek.com', '+902345678910'),
(2, 39591867107, 'beyza@ornek.com', '+901234567890'),
(3, 38371857167, 'efe@ornek.com', '+903456789101'),
(4, 37869947353, 'begum@ornek.com', '+901853486736'),
(5, 36037051931, 'mehmet@ornek.com', '+902750148616');


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

INSERT INTO `patient` VALUES
(1, 'Ali', 'Yılmaz', 1),
(2, 'Beyza', 'Kaya', 2),
(3, 'Efe', 'Arslan', 3),
(4, 'Begüm', 'Demir', 4),
(5, 'Mehmet', 'Çelik', 5);


DROP TABLE IF EXISTS `lab_technician`;

CREATE TABLE `lab_technician` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `hospital_identity_number` int(7) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `HOSPITAL_ID_NO_UNIQUE` (`hospital_identity_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `lab_technician` VALUES
(1, 'Deniz', 'Yılmaz', 3794750),
(2, 'Ayşe', 'Toprak', 3793818),
(3, 'Emre', 'Şahin', 3783018);


DROP TABLE IF EXISTS `report_image`;

CREATE TABLE `report_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `image_data` BLOB DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `report_detail`;

CREATE TABLE `report_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `diagnosis_details` varchar(255) DEFAULT NULL,
  `report_date` DATE DEFAULT NULL,
  `report_image_id` int DEFAULT NULL,

  PRIMARY KEY (`id`),

  KEY `FK_REPORT_IMAGE_idx` (`report_image_id`),
  CONSTRAINT `FK_REPORT_IMAGE` FOREIGN KEY (`report_image_id`) 
  REFERENCES `report_image` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `report_detail` VALUES
(1, 'Birinci rapor için tanı detayları', '2024-01-15', NULL),
(2, 'İkinci rapor için tanı detayları', '2023-04-24', NULL),
(3, 'Üçüncü rapor için tanı detayları', '2024-02-25', NULL),
(4, 'Dördüncü rapor için tanı detayları', '2023-11-28', NULL),
(5, 'Beşinci rapor için tanı detayları', '2024-03-01', NULL),
(6, 'Altıncı rapor için tanı detayları', '2023-05-12', NULL),
(7, 'Yedinci rapor için tanı detayları', '2024-03-29', NULL),
(8, 'Sekizinci rapor için tanı detayları', '2024-02-25', NULL);


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

INSERT INTO `report` VALUES
(1, 'RP3491-5830', 'Birinci tanı başlığı', 1, 2, 1),
(2, 'RP3460-5810', 'İkinci tanı başlığı', 1, 1, 2),
(3, 'RP3401-4814', 'Üçüncü tanı başlığı', 1, 1, 3),
(4, 'RP3414-1851', 'Dördüncü tanı başlığı', 2, 3, 4),
(5, 'RP3417-5813', 'Beşinci tanı başlığı', 3, 3, 5),
(6, 'RP3459-3571', 'Altıncı tanı başlığı', 3, 1, 6),
(7, 'RP3401-3361', 'Yedinci tanı başlığı', 4, 2, 7),
(8, 'RP3491-3950', 'Sekizinci tanı başlığı', 5, 1, 8);


SET FOREIGN_KEY_CHECKS = 1;