-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 23, 2018 at 02:14 AM
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
-- Database: `pong2`
--

-- --------------------------------------------------------

--
-- Table structure for table `Games`
--

CREATE TABLE `Games` (
  `id` int(11) NOT NULL,
  `p1score` int(11) NOT NULL,
  `p2score` int(11) NOT NULL,
  `match_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Matches`
--

CREATE TABLE `Matches` (
  `id` int(11) NOT NULL,
  `player1_id` int(11) NOT NULL,
  `player2_id` int(11) DEFAULT NULL,
  `stage` int(11) NOT NULL,
  `tournament_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Matches`
--

INSERT INTO `Matches` (`id`, `player1_id`, `player2_id`, `stage`, `tournament_id`) VALUES
(1, 1, NULL, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Tournaments`
--

CREATE TABLE `Tournaments` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `fee` float NOT NULL,
  `status` int(11) NOT NULL,
  `start_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Tournaments`
--

INSERT INTO `Tournaments` (`id`, `name`, `fee`, `status`, `start_date`) VALUES
(1, 'Super Tournament', 50, 0, '2018-04-18'),
(2, 'Papua Noua', 20, 0, '2018-04-21'),
(3, 'Nova Dacia', 30, 0, '2018-04-22'),
(4, 'Alexei', 0, 0, '2018-04-22');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `isadmin` tinyint(4) NOT NULL,
  `balance` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`id`, `name`, `email`, `password`, `isadmin`, `balance`) VALUES
(1, 'Test Guy', 'test@testus.com', 'sidetest', 1, 0),
(2, 'Test Gal', 'supertest@test.com', 'sidetest2', 0, 100),
(3, 'fiwah', 'dwao', 'fjawj', 0, 40),
(4, 'y8qyhg', 'fjwai', 'f9awhh', 0, 200),
(5, 'Jean', 'aarhus@netus.net', 'manea', 1, 500),
(6, 'Dorin B.', 'dorinb@juego.com', 'maidorin', 0, 20);

-- --------------------------------------------------------

--
-- Table structure for table `UserTournament`
--

CREATE TABLE `UserTournament` (
  `id_user` int(11) NOT NULL,
  `id_tournament` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `UserTournament`
--

INSERT INTO `UserTournament` (`id_user`, `id_tournament`) VALUES
(1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Games`
--
ALTER TABLE `Games`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Games_1_idx` (`match_id`);

--
-- Indexes for table `Matches`
--
ALTER TABLE `Matches`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Matches_2_idx` (`player1_id`),
  ADD KEY `fk_Matches_3_idx` (`player2_id`),
  ADD KEY `fk_Matches_1_idx` (`tournament_id`);

--
-- Indexes for table `Tournaments`
--
ALTER TABLE `Tournaments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_UNIQUE` (`name`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`);

--
-- Indexes for table `UserTournament`
--
ALTER TABLE `UserTournament`
  ADD PRIMARY KEY (`id_user`,`id_tournament`),
  ADD KEY `fk_UserTournament_2_idx` (`id_tournament`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Games`
--
ALTER TABLE `Games`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Matches`
--
ALTER TABLE `Matches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Tournaments`
--
ALTER TABLE `Tournaments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Games`
--
ALTER TABLE `Games`
  ADD CONSTRAINT `fk_Games_1` FOREIGN KEY (`match_id`) REFERENCES `Matches` (`id`);

--
-- Constraints for table `Matches`
--
ALTER TABLE `Matches`
  ADD CONSTRAINT `fk_Matches_1` FOREIGN KEY (`tournament_id`) REFERENCES `Tournaments` (`id`),
  ADD CONSTRAINT `fk_Matches_2` FOREIGN KEY (`player1_id`) REFERENCES `Users` (`id`),
  ADD CONSTRAINT `fk_Matches_3` FOREIGN KEY (`player2_id`) REFERENCES `Users` (`id`);

--
-- Constraints for table `UserTournament`
--
ALTER TABLE `UserTournament`
  ADD CONSTRAINT `fk_UserTournament_1` FOREIGN KEY (`id_user`) REFERENCES `Users` (`id`),
  ADD CONSTRAINT `fk_UserTournament_2` FOREIGN KEY (`id_tournament`) REFERENCES `Tournaments` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
