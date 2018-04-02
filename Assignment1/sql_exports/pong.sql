-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 31, 2018 at 04:36 PM
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

-- --------------------------------------------------------

--
-- Table structure for table `Games`
--

CREATE TABLE `Games` (
  `id` int(11) NOT NULL,
  `p1score` int(11) NOT NULL,
  `p2score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Games`
--

INSERT INTO `Games` (`id`, `p1score`, `p2score`) VALUES
(1, 11, 2),
(2, 11, 9),
(3, 15, 13),
(34, 0, 0),
(35, 11, 8),
(36, 11, 8),
(37, 9, 11),
(38, 11, 8),
(39, 13, 11),
(40, 0, 0),
(41, 7, 0),
(42, 0, 0),
(43, 0, 0),
(44, 0, 0),
(45, 7, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Matches`
--

CREATE TABLE `Matches` (
  `id` int(11) NOT NULL,
  `player1_id` int(11) NOT NULL,
  `player2_id` int(11) NOT NULL,
  `stage` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Matches`
--

INSERT INTO `Matches` (`id`, `player1_id`, `player2_id`, `stage`) VALUES
(2, 5, 6, 1),
(13, 9, 10, 1),
(17, 5, 8, 1),
(18, 5, 9, 1),
(19, 5, 9, 1),
(20, 5, 9, 1),
(21, 5, 8, 1),
(23, 1, 9, 1),
(24, 1, 10, 1),
(25, 8, 10, 1),
(27, 7, 8, 1),
(29, 7, 8, 1);

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

-- --------------------------------------------------------

--
-- Table structure for table `Tournaments`
--

CREATE TABLE `Tournaments` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Tournaments`
--

INSERT INTO `Tournaments` (`id`, `name`) VALUES
(5, 'African Road to Glory'),
(19, 'Bayonet Class'),
(10, 'Cluj Napica'),
(2, 'European Concrete'),
(13, 'Gaalati'),
(7, 'Greek Runners'),
(18, 'New Tournament'),
(8, 'Palermo Strings'),
(17, 'Sons of Bragadiru'),
(11, 'Tour de viz'),
(16, 'Tridi Panta'),
(12, 'Ultras'),
(14, 'World Tour');

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
-- Indexes for table `GameMatch`
--
ALTER TABLE `GameMatch`
  ADD PRIMARY KEY (`game_id`,`match_id`);

--
-- Indexes for table `Games`
--
ALTER TABLE `Games`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Matches`
--
ALTER TABLE `Matches`
  ADD PRIMARY KEY (`id`),
  ADD KEY `p1_foreign` (`player1_id`),
  ADD KEY `p2_foreign` (`player2_id`);

--
-- Indexes for table `MatchTournament`
--
ALTER TABLE `MatchTournament`
  ADD PRIMARY KEY (`match_id`,`tournament_id`);

--
-- Indexes for table `Tournaments`
--
ALTER TABLE `Tournaments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_index` (`name`);

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
-- AUTO_INCREMENT for table `Games`
--
ALTER TABLE `Games`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT for table `Matches`
--
ALTER TABLE `Matches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `Tournaments`
--
ALTER TABLE `Tournaments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Matches`
--
ALTER TABLE `Matches`
  ADD CONSTRAINT `p1_foreign` FOREIGN KEY (`player1_id`) REFERENCES `Users` (`id`),
  ADD CONSTRAINT `p2_foreign` FOREIGN KEY (`player2_id`) REFERENCES `Users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
