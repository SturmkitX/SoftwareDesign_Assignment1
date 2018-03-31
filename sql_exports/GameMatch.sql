-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 31, 2018 at 04:53 PM
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
-- Table structure for table `GameMatch`
--

CREATE TABLE `GameMatch` (
  `game_id` int(11) NOT NULL,
  `match_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `GameMatch`
--

INSERT INTO `GameMatch` (`game_id`, `match_id`) VALUES
(1, 2),
(2, 2),
(3, 2),
(35, 20),
(36, 21),
(37, 21),
(38, 21),
(39, 21),
(40, 21),
(41, 23),
(45, 29);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `GameMatch`
--
ALTER TABLE `GameMatch`
  ADD PRIMARY KEY (`game_id`,`match_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
