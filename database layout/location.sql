-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 20, 2022 at 11:24 PM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `location`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `codeClient` int(11) NOT NULL,
  `nomClient` varchar(30) NOT NULL,
  `prenomClient` varchar(20) NOT NULL,
  `adresseClient` varchar(50) NOT NULL,
  `telClient` varchar(20) NOT NULL,
  `imageClient` varchar(200) NOT NULL,
  `permisScanneeClient` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`codeClient`, `nomClient`, `prenomClient`, `adresseClient`, `telClient`, `imageClient`, `permisScanneeClient`) VALUES
(4, 'nom1', 'p1', 'a1', 't1', 'C:\\Users\\HP ELITEBOOK\\Documents\\WebDevelopmentProjects\\images\\pexels-andrea-piacquadio-3777931.jpg', 'C:\\Users\\HP ELITEBOOK\\Documents\\WebDevelopmentProjects\\images\\kangaroo-6557734_960_720.webp'),
(5, 'nom', 'prenom', 'tel', 'ad', 'C:\\Users\\HP ELITEBOOK\\Desktop\\Sans titre.png', 'permisScannee'),
(6, 'n6', 'p6', 'a6', 't6', 'C:\\Users\\HP ELITEBOOK\\Desktop\\Sans titre.png', 'C:\\Users\\HP ELITEBOOK\\Documents\\WebDevelopmentProjects\\images\\kangaroo-6557734_960_720.webp'),
(7, 'nom7', 'prenom7', 'a7', 't7', 'C:\\Users\\HP ELITEBOOK\\Desktop\\Sans titre.png', 'C:\\Users\\HP ELITEBOOK\\Desktop\\Sans titre.png');

-- --------------------------------------------------------

--
-- Table structure for table `contrat`
--

CREATE TABLE `contrat` (
  `codeContrat` int(11) NOT NULL,
  `dateContrat` date NOT NULL,
  `dateEcheance` date NOT NULL,
  `codeReservation` int(11) NOT NULL,
  `codeMaticule` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `facture`
--

CREATE TABLE `facture` (
  `codeFacture` int(11) NOT NULL,
  `dateFacture` date NOT NULL,
  `montantFacture` int(11) NOT NULL,
  `codeContrat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `facture`
--

INSERT INTO `facture` (`codeFacture`, `dateFacture`, `montantFacture`, `codeContrat`) VALUES
(1, '2022-05-02', 420, 1);

-- --------------------------------------------------------

--
-- Table structure for table `parking`
--

CREATE TABLE `parking` (
  `id` int(11) NOT NULL,
  `designation` varchar(50) NOT NULL,
  `nombre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `codeReservation` int(11) NOT NULL,
  `dateReservation` date NOT NULL,
  `dateDepReservation` date NOT NULL,
  `dateRetReservation` date NOT NULL,
  `isValid` tinyint(1) NOT NULL,
  `isCanceled` tinyint(1) NOT NULL,
  `codeClient` int(11) NOT NULL,
  `codeVehicule` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`codeReservation`, `dateReservation`, `dateDepReservation`, `dateRetReservation`, `isValid`, `isCanceled`, `codeClient`, `codeVehicule`) VALUES
(8, '2022-05-13', '2023-01-01', '2024-01-01', 1, 0, 1, 'NULL'),
(9, '2022-05-13', '2023-01-01', '2022-01-01', 1, 0, 1, 'NULL'),
(16, '2022-05-14', '2022-01-01', '2023-01-01', 0, 1, 1, 'ABC');

-- --------------------------------------------------------

--
-- Table structure for table `vehicule`
--

CREATE TABLE `vehicule` (
  `codeMatricule` varchar(20) NOT NULL,
  `marqueVehicule` varchar(30) NOT NULL,
  `typeVehicule` varchar(20) NOT NULL,
  `carburant` varchar(20) NOT NULL,
  `kilometrage` int(11) NOT NULL,
  `dateMiseCirculation` date NOT NULL,
  `codePark` int(11) NOT NULL,
  `prixLocation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vehicule`
--

INSERT INTO `vehicule` (`codeMatricule`, `marqueVehicule`, `typeVehicule`, `carburant`, `kilometrage`, `dateMiseCirculation`, `codePark`, `prixLocation`) VALUES
('ABC', 'rb16b', '1 seater', 'your dreams', 300, '2022-05-01', 0, 100);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`codeClient`);

--
-- Indexes for table `contrat`
--
ALTER TABLE `contrat`
  ADD PRIMARY KEY (`codeContrat`);

--
-- Indexes for table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`codeFacture`);

--
-- Indexes for table `parking`
--
ALTER TABLE `parking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`codeReservation`);

--
-- Indexes for table `vehicule`
--
ALTER TABLE `vehicule`
  ADD PRIMARY KEY (`codeMatricule`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `codeClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `contrat`
--
ALTER TABLE `contrat`
  MODIFY `codeContrat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `facture`
--
ALTER TABLE `facture`
  MODIFY `codeFacture` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `parking`
--
ALTER TABLE `parking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `codeReservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
