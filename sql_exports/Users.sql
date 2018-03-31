-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 31, 2018 at 04:55 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pong`
--

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `isadmin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`id`, `name`, `email`, `password`, `isadmin`) VALUES
(1, 'Test Guy', 'test@testus.com', 'sidetest', 0),
(2, 'Main Administrator', 'bogdanr@utcluj.ro', 'adminpass', 1),
(3, 'Florin Salam', 'fsalam@amma.ro', 'flo', 0),
(4, 'Nicolae Guta', 'ng@ro.ro', 'passguta', 0),
(5, 'Costel Ciofu', 'dwah@ajax.ro', 'wag', 0),
(6, 'Sorinel copilu', 'dagai@ghaiw.net', 'wagia', 0),
(7, 'DJ Sebi', 'diwah@jgpoa.ru', 'fawjg', 0),
(8, 'Dan Bursuc', 'dwag@pj.ne', 'fwoapj', 0),
(9, 'Paul Lica', 'dwaighi@caransebes.ro', 'dojwajg', 0),
(10, 'Gica Hagi', 'jjpij@jijg.net', 'opjj', 0),
(11, 'Nicu Paleru', 'npaleru@jfa.net', 'hopa', 0),
(12, 'Ciorba', 'fwa@jaba.net', 'gwaa', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_index` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
