-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 07, 2022 at 08:19 PM
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
  `telClient` bigint(20) NOT NULL,
  `imageClient` varchar(100) NOT NULL COMMENT 'Path/link to client image',
  `permisScanneeClient` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`codeClient`, `nomClient`, `prenomClient`, `adresseClient`, `telClient`, `imageClient`, `permisScanneeClient`) VALUES
(5, 'abd', 'ab', 'agadir', 212632540777, 'C:\\Users\\Abd-AB\\Documents\\Wallpapers\\ror2.jpg', 'C:\\Users\\Abd-AB\\Documents\\Wallpapers\\ror2.jpg'),
(6, 'test', 'Noureddine', 'Tilila, Agadir', 2126, 'C:\\Users\\Abd-AB\\Documents\\Wallpapers\\Sekiro.jpg', 'C:\\Users\\Abd-AB\\Documents\\Wallpapers\\Sekiro.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `contrat`
--

CREATE TABLE `contrat` (
  `codeContrat` int(11) NOT NULL,
  `dateContrat` date NOT NULL,
  `dateEcheance` date NOT NULL,
  `dateRetActuel` date DEFAULT NULL,
  `sanctionRegle` tinyint(1) NOT NULL DEFAULT '0',
  `codeReservation` int(11) NOT NULL,
  `codeMatricule` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `contrat`
--

INSERT INTO `contrat` (`codeContrat`, `dateContrat`, `dateEcheance`, `dateRetActuel`, `sanctionRegle`, `codeReservation`, `codeMatricule`) VALUES
(4, '2022-06-06', '2022-06-09', '2022-06-10', 0, 14, 'ABC'),
(5, '2022-06-06', '2022-06-16', '2022-06-18', 0, 14, 'ABC'),
(6, '2022-06-03', '2022-06-11', '2022-06-12', 1, 15, 'ABC');

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
(11, '2022-06-07', 800, 6);

-- --------------------------------------------------------

--
-- Table structure for table `parking`
--

CREATE TABLE `parking` (
  `id` varchar(30) NOT NULL,
  `designation` varchar(30) NOT NULL,
  `nombre` varchar(30) NOT NULL
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
(14, '2022-06-06', '2022-01-01', '2022-02-02', 0, 1, 5, 'ABC'),
(15, '2022-06-07', '2022-02-03', '2022-02-11', 1, 0, 6, 'ABC');

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
('AB260931', 'Mercedes', 'Sport', 'Escence', 12003, '2021-05-01', 0, 500),
('ABC', 'rb16b', '1 seater', 'your dreams', 300, '2022-05-01', 0, 100),
('XA2158300', 'Renault', 'SUV', 'Diesel', 35000, '2021-04-09', 0, 350);

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
  ADD PRIMARY KEY (`codeContrat`),
  ADD KEY `codeReservation` (`codeReservation`),
  ADD KEY `codeMatricule` (`codeMatricule`);

--
-- Indexes for table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`codeFacture`),
  ADD KEY `codeContrat` (`codeContrat`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`codeReservation`),
  ADD KEY `codeClient` (`codeClient`),
  ADD KEY `codeVehicule` (`codeVehicule`);

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
  MODIFY `codeClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `contrat`
--
ALTER TABLE `contrat`
  MODIFY `codeContrat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `facture`
--
ALTER TABLE `facture`
  MODIFY `codeFacture` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `codeReservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `contrat`
--
ALTER TABLE `contrat`
  ADD CONSTRAINT `contrat_reservation` FOREIGN KEY (`codeReservation`) REFERENCES `reservation` (`codeReservation`) ON DELETE CASCADE,
  ADD CONSTRAINT `contrat_vehicule` FOREIGN KEY (`codeMatricule`) REFERENCES `vehicule` (`codeMatricule`) ON DELETE CASCADE;

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `facture_contrat` FOREIGN KEY (`codeContrat`) REFERENCES `contrat` (`codeContrat`) ON DELETE CASCADE;

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reserv_client` FOREIGN KEY (`codeClient`) REFERENCES `client` (`codeClient`) ON DELETE CASCADE,
  ADD CONSTRAINT `reserv_vehicule` FOREIGN KEY (`codeVehicule`) REFERENCES `vehicule` (`codeMatricule`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
