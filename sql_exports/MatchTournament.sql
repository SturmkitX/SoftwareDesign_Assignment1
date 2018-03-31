-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 31, 2018 at 04:54 PM
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
-- Table structure for table `MatchTournament`
--

CREATE TABLE `MatchTournament` (
  `match_id` int(11) NOT NULL,
  `tournament_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MatchTournament`
--

INSERT INTO `MatchTournament` (`match_id`, `tournament_id`) VALUES
(2, 2),
(13, 2),
(21, 5),
(23, 2),
(29, 13);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `MatchTournament`
--
ALTER TABLE `MatchTournament`
  ADD PRIMARY KEY (`match_id`,`tournament_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
