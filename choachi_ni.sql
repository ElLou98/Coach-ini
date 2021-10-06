-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 20 mars 2021 à 13:17
-- Version du serveur :  10.4.16-MariaDB
-- Version de PHP : 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `choachi'ni`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `role` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`id`, `role`) VALUES
(150, 'moudir'),
(160, 'admin');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`) VALUES
(151),
(152),
(154),
(155);

-- --------------------------------------------------------

--
-- Structure de la table `coach`
--

CREATE TABLE `coach` (
  `id` int(11) NOT NULL,
  `profession` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `coach`
--

INSERT INTO `coach` (`id`, `profession`) VALUES
(147, ''),
(153, '');

-- --------------------------------------------------------

--
-- Structure de la table `compte`
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
-- Déchargement des données de la table `compte`
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
(168, 'uo', 'uo', 'uo', 12, 'i@i.i', 'up', 22365478);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `coach`
--
ALTER TABLE `coach`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=169;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `fk_compte_admin` FOREIGN KEY (`id`) REFERENCES `compte` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
