CREATE DATABASE IF NOT EXISTS `crm_customer_tracker`;
USE `crm_customer_tracker`;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (first_name, last_name, email)
VALUES
    ('David','Adams','david@gmail.com'),
	('John','Doe','john@gmail.com'),
	('Ajay','Rao','ajay@gmail.com'),
	('Mary','Public','mary@gmail.com'),
	('Maxwell','Dixon','max@gmail.com');
