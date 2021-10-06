-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 25, 2021 at 03:15 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.4.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `choachi'ni`
--

-- --------------------------------------------------------

--
-- Table structure for table `actualite`
--

CREATE TABLE `actualite` (
  `IdAct` int(11) NOT NULL,
  `Statut` varchar(255) NOT NULL,
  `Image` varchar(2083) NOT NULL,
  `Fichier` varchar(2083) NOT NULL,
  `Bio` varchar(255) NOT NULL,
  `Compétence` varchar(255) NOT NULL,
  `likepub` int(11) DEFAULT 0,
  `datepub` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `actualite`
--

INSERT INTO `actualite` (`IdAct`, `Statut`, `Image`, `Fichier`, `Bio`, `Compétence`, `likepub`, `datepub`) VALUES
(127, 'poj,jopikjo', 'C:\\Users\\Espace Info\\Pictures\\msi_logo.jpg', '', '', '', 0, '2021-03-23 07:32:14'),
(128, 'kuyguygu', 'C:\\Users\\Espace Info\\Pictures\\70335860_127713721859441_5758993734490390528_o.jpg', '', '', '', 0, '2021-03-23 08:07:10'),
(129, 'mùgbk,fsùmfld', 'C:\\Users\\Espace Info\\Pictures\\pic.jpg', '', '', '', 0, '2021-03-23 11:16:04');

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `role` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `role`) VALUES
(150, 'moudir'),
(160, 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `CategorieEvent`
--

CREATE TABLE `CategorieEvent` (
  `id` int(255) NOT NULL,
  `nom` varchar(60) NOT NULL,
  `description` varchar(2500) NOT NULL,
  `photo` varchar(2000) NOT NULL,
  `db_picture` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `CategorieEvent`
--

INSERT INTO `CategorieEvent` (`id`, `nom`, `description`, `photo`, `db_picture`) VALUES
(30, 'Tournoi Football', 'Compétition entre équipes de football pour gagner des récompenses', '/home/kagha/Desktop/002-football-2.png', 'localhost/coachini/uploads/f_605790c4725aa.png'),
(31, 'Compétition Tennis', 'Compétition entre équipes de Tennispour gagner des récompenses', '/home/kagha/Desktop/502108-sport/png/065-tennis-1.png', 'localhost/coachini/uploads/f_605791bb7e511.png'),
(32, 'Tour de cyclisme', 'c\'est une compétition de cyclisme qui permet aux cyclistes de faire  un tour bien précisés', '/home/kagha/Desktop/502108-sport/502108-sport/png/093-cycling-1.png', 'localhost/coachini/uploads/f_6057964fdcabb.png'),
(33, 'Tournoi échecs', 'Un tournoi d\'échecs est souvent organisé sous forme de rondes. Chaque joueur est ainsi assuré de jouer le même nombre de parties qu\'il gagne ou qu\'il perde', '/home/kagha/Desktop/502108-sport/502108-sport/png/078-chess-1.png', 'localhost/coachini/uploads/f_605796b4b1ed5.png'),
(34, 'Formation Sportif', 'Formation professionnelle qui permet l\'apprentissage des aspects généraux sportifs.', '/home/kagha/Desktop/012-fitness-1.png', 'localhost/coachini/uploads/f_6057ac87d01f5.png');

-- --------------------------------------------------------

--
-- Table structure for table `categoriesport`
--

CREATE TABLE `categoriesport` (
  `id` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `description` varchar(255) NOT NULL,
  `photo` varchar(2083) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categoriesport`
--

INSERT INTO `categoriesport` (`id`, `nom`, `description`, `photo`) VALUES
(62, 'chess', 'chess', 'pool.png'),
(64, 'test', 'test', 'foot.png'),
(66, 'test', 'test', 'box.png');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`) VALUES
(151),
(152),
(154),
(155),
(169),
(170),
(171),
(174);

-- --------------------------------------------------------

--
-- Table structure for table `coach`
--

CREATE TABLE `coach` (
  `id` int(11) NOT NULL,
  `profession` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `coach`
--

INSERT INTO `coach` (`id`, `profession`) VALUES
(147, ''),
(153, ''),
(172, 'coachZ'),
(173, 'integration');

-- --------------------------------------------------------

--
-- Table structure for table `compte`
--

CREATE TABLE `compte` (
  `id` int(11) NOT NULL,
  `userName` varchar(32) NOT NULL,
  `nom` varchar(32) NOT NULL,
  `prenom` varchar(32) NOT NULL,
  `age` int(11) NOT NULL,
  `adresse_mail` varchar(64) NOT NULL,
  `mot_de_passe` varchar(32) NOT NULL,
  `num_tel` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `compte`
--

INSERT INTO `compte` (`id`, `userName`, `nom`, `prenom`, `age`, `adresse_mail`, `mot_de_passe`, `num_tel`) VALUES
(147, '2', 'r', '2', 2, '2', '2', 2),
(150, 'qq', 'elyes', 'qq', 99, 'qq', 'qr', 99),
(151, '7', 'a', '7', 7, '7', '7', 7),
(152, 'jj', 'jj', 'jj', 44, 'j@j.j', 'jj', 44),
(153, 'uu', 'uu', 'uu', 44, 'uu@u.u', 'uu', 44),
(154, 'oo', 'oo', 'oo', 77, 'o@o.o', 'oo', 55),
(155, 'hh', 'hh', 'hh', 7, 'h@h.h', 'h j l', 789),
(159, '465', 'ezea', 'eza', 45, 'a@a.a', '561ze', 45621278),
(160, 'admin', 'massoussi', 'elyes', 21, 'massoussielyes2@gmail.com', 'aeolr', 26213651),
(165, 'ii', 'ii', 'ii', 11, 'ii@ii.ii', '', 11),
(166, 'popo', 'po', 'po', 23, 'po@po.po', 'pprr', 54213698),
(167, 'poo', 'po', 'po', 26, 'po@po.po', 'pprr', 54213698),
(168, 'uo', 'uo', 'uo', 12, 'i@i.i', 'up', 22365478),
(169, 'adem', 'ysf', 'youssef', 22, 'youssef@esprit.tn', 'aegp', 25222222),
(170, 'adem98', 'adem', 'yssf', 22, 'adem@esprit.com', 'aegp', 23222222),
(171, 'ghassen98', 'ghassen', 'ysf', 22, 'ghassen@esprit.tn', 'gicvwjt', 23444444),
(172, 'adem70', 'adem', 'ysf', 11, 'ade@ade.tn', 'aegp', 23333222),
(173, 'ademcoach', 'adem', 'ysf coach', 22, 'adem@adem.com', 'aegp', 23555444),
(174, 'ademclient', 'adem', 'youssef', 23, 'adem2015@gmail.com', 'aegp', 25444333);

-- --------------------------------------------------------

--
-- Table structure for table `Event`
--

CREATE TABLE `Event` (
  `id` int(25) NOT NULL,
  `Nom_event` varchar(35) NOT NULL,
  `Date_debut` date NOT NULL,
  `Heure_debut` varchar(35) NOT NULL,
  `Date_fin` date NOT NULL,
  `Heure_fin` varchar(35) NOT NULL,
  `Participation` varchar(35) NOT NULL,
  `Nb_participant` int(25) NOT NULL,
  `Description` varchar(200) NOT NULL,
  `id_Cat` int(11) DEFAULT NULL,
  `db_map` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Event`
--

INSERT INTO `Event` (`id`, `Nom_event`, `Date_debut`, `Heure_debut`, `Date_fin`, `Heure_fin`, `Participation`, `Nb_participant`, `Description`, `id_Cat`, `db_map`) VALUES
(300, 'ch9awlik Louay 7aja 7louwa wala la ', '2021-03-10', '00:45', '2021-03-11', '01:15', 'En Personne', 20, '  qsdqs', 31, 'localhost/coachini/uploads/f_605c98a7b01c4.png');

-- --------------------------------------------------------

--
-- Table structure for table `Map`
--

CREATE TABLE `Map` (
  `id` int(11) NOT NULL,
  `Latitude` float DEFAULT NULL,
  `Longtitude` float DEFAULT NULL,
  `Lieu` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Map`
--

INSERT INTO `Map` (`id`, `Latitude`, `Longtitude`, `Lieu`) VALUES
(300, 36.7979, 10.1693, 'جامع حمودة باشا');

-- --------------------------------------------------------

--
-- Table structure for table `offre`
--

CREATE TABLE `offre` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `offre`
--

INSERT INTO `offre` (`id`, `titre`, `date`, `description`) VALUES
(19, 'offre1', '2021-03-17', ' testtest'),
(20, 'test2', '2021-03-19', '  testtt'),
(21, 'test6', '2021-03-19', '   testtt'),
(22, 'test2', '2021-03-19', '   testtt'),
(23, 'test26', '2021-03-18', '    descr1'),
(29, 'edrfg', '2021-03-12', ' qsfdbn'),
(32, 'sfgh', '2021-03-12', ' jkg'),
(33, 'sfgh', '2021-03-12', ' jkg'),
(34, 'yfgj', '2021-03-09', ' fghk'),
(35, 'sdzfvgrh', '2021-03-10', ' efgvb'),
(43, 'test', '2021-03-16', ' testhzkl'),
(44, 'test', '2021-03-25', ' oihgfd'),
(45, 'testttttt', '2021-03-25', ' teyrzqesq'),
(46, 'testtt', '2021-03-25', ' yhzq'),
(47, 'sdfeg', '2021-03-25', ' fgrthy'),
(48, 'dfg', '2021-03-24', ' ergsh'),
(50, 'doniaa', '2021-03-25', ' dddddd'),
(51, 'test', '2021-03-26', ' tttt');

-- --------------------------------------------------------

--
-- Table structure for table `Profile`
--

CREATE TABLE `Profile` (
  `ID_Coach` int(11) NOT NULL,
  `Photo` varchar(2083) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Rating` int(11) NOT NULL,
  `Catégorie` varchar(255) NOT NULL,
  `Détail` varchar(255) NOT NULL,
  `Nom` varchar(255) NOT NULL,
  `ID_Compte` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Profile`
--

INSERT INTO `Profile` (`ID_Coach`, `Photo`, `Description`, `Rating`, `Catégorie`, `Détail`, `Nom`, `ID_Compte`) VALUES
(153, 'file:/C:/Users/Espace%20Info/Pictures/pic.jpg', 'vfdmklv,mdlf', 2, 'Squat', 'dlfkv,sfdùlk', 'mohamed el hedii', 175);

-- --------------------------------------------------------

--
-- Table structure for table `review_client`
--

CREATE TABLE `review_client` (
  `id_review` int(11) NOT NULL,
  `description_review` text NOT NULL,
  `nom_client_review` varchar(30) NOT NULL,
  `nom_coach_review` varchar(30) NOT NULL,
  `date_review` varchar(30) NOT NULL,
  `rating` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `review_client`
--

INSERT INTO `review_client` (`id_review`, `description_review`, `nom_client_review`, `nom_coach_review`, `date_review`, `rating`) VALUES
(37, 'R1 w kima 9al sahib elyes hard work pays off ', 'Adem Youssef', 'COACH XY', '2021-02-24 25:15:45', 4.5),
(39, '1er execution du crud review', 'Adem YSF', 'COACH XY', '2021-02-24 25:17:45', 5),
(149, 'test integration en tant que coach', 'adem', 'ademcoach', '2021-03-23 11:39:07', 2);

-- --------------------------------------------------------

--
-- Table structure for table `seance_planning`
--

CREATE TABLE `seance_planning` (
  `id` int(11) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `Summary` varchar(30) NOT NULL,
  `Description` varchar(500) NOT NULL,
  `Date` varchar(30) NOT NULL,
  `Starts_at` varchar(30) NOT NULL,
  `Finishs_at` varchar(30) NOT NULL,
  `Localisation` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `seance_planning`
--

INSERT INTO `seance_planning` (`id`, `user_name`, `Summary`, `Description`, `Date`, `Starts_at`, `Finishs_at`, `Localisation`) VALUES
(1, '', 'test', 'aaaaaaaaa', '2021-03-18', '05:35:00', '11:15:00', 'zzzzzzzzzzz'),
(2, '', 'aaaaa', 'null', '2021-03-18', '07:50:00', '12:00:00', 'null'),
(74, 'adem99999999', 'azeza aze aze ', 'aze aze aze ', '2021-03-21', '03:50:00', '08:35:00', 'ezaeeeeee'),
(75, 'adem70', 'testcalender integration', 'integration test , ', '2021-03-25', '07:25:00', '12:05:00', 'ena el kaleb eli jey l esprit');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `CategorieEvent`
--
ALTER TABLE `CategorieEvent`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `categoriesport`
--
ALTER TABLE `categoriesport`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `coach`
--
ALTER TABLE `coach`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Event`
--
ALTER TABLE `Event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Cat` (`id_Cat`);

--
-- Indexes for table `Map`
--
ALTER TABLE `Map`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `offre`
--
ALTER TABLE `offre`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `review_client`
--
ALTER TABLE `review_client`
  ADD PRIMARY KEY (`id_review`);

--
-- Indexes for table `seance_planning`
--
ALTER TABLE `seance_planning`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `CategorieEvent`
--
ALTER TABLE `CategorieEvent`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `categoriesport`
--
ALTER TABLE `categoriesport`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=175;

--
-- AUTO_INCREMENT for table `Event`
--
ALTER TABLE `Event`
  MODIFY `id` int(25) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=301;

--
-- AUTO_INCREMENT for table `offre`
--
ALTER TABLE `offre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `review_client`
--
ALTER TABLE `review_client`
  MODIFY `id_review` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=154;

--
-- AUTO_INCREMENT for table `seance_planning`
--
ALTER TABLE `seance_planning`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `fk_compte_admin` FOREIGN KEY (`id`) REFERENCES `compte` (`id`);

--
-- Constraints for table `Event`
--
ALTER TABLE `Event`
  ADD CONSTRAINT `FK_Cat` FOREIGN KEY (`id_Cat`) REFERENCES `CategorieEvent` (`id`);

--
-- Constraints for table `Map`
--
ALTER TABLE `Map`
  ADD CONSTRAINT `FK` FOREIGN KEY (`id`) REFERENCES `Event` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
