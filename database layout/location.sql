-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 09, 2022 at 03:16 PM
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
(8, 'Ait Brik', 'Abdelwahed', 'agadir', 21263254, 'C:\\Users\\Abd-AB\\Desktop\\man.jpg', 'C:\\Users\\Abd-AB\\Desktop\\man.jpg');

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

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `matricule` int(11) NOT NULL,
  `nomUtilisateur` varchar(45) NOT NULL,
  `prenomUtilisateur` varchar(45) NOT NULL,
  `TelUtilisateur` varchar(45) NOT NULL,
  `adresseUtilisateur` varchar(100) NOT NULL,
  `IsActive` tinyint(4) NOT NULL DEFAULT '1',
  `IsAdmin` tinyint(4) NOT NULL DEFAULT '0',
  `username` varchar(45) NOT NULL,
  `password` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`matricule`, `nomUtilisateur`, `prenomUtilisateur`, `TelUtilisateur`, `adresseUtilisateur`, `IsActive`, `IsAdmin`, `username`, `password`) VALUES
(1, 'root', 'root', 'root', 'root', 1, 1, 'root', '63a9f0ea7bb98050796b649e85481845'),
(2, 'Danger', 'Ilyaas', '69420', 'bruh', 1, 0, 'danger', 'b136ee6c797c1a851260b9c1ab5ff414');

-- --------------------------------------------------------

--
-- Table structure for table `vehicule`
--

CREATE TABLE `vehicule` (
  `Immatriculation` varchar(20) NOT NULL,
  `marqueVehicule` varchar(30) NOT NULL,
  `typeVehicule` varchar(20) NOT NULL,
  `carburant` varchar(20) NOT NULL,
  `kilometrage` bigint(20) NOT NULL,
  `dateMiseCirculation` date NOT NULL,
  `codePark` int(11) NOT NULL,
  `prixLocation` int(11) NOT NULL,
  `disponible` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vehicule`
--

INSERT INTO `vehicule` (`Immatriculation`, `marqueVehicule`, `typeVehicule`, `carburant`, `kilometrage`, `dateMiseCirculation`, `codePark`, `prixLocation`, `disponible`) VALUES
('AB260931', 'Mercedes', 'Sport', 'Escence', 12003, '2021-05-01', 0, 500, 1),
('AB3100', 'Mercedes', 'sport', 'diesel', 120000, '2006-01-01', 1, 500, 1);

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
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`matricule`);

--
-- Indexes for table `vehicule`
--
ALTER TABLE `vehicule`
  ADD PRIMARY KEY (`Immatriculation`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `codeClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `contrat`
--
ALTER TABLE `contrat`
  MODIFY `codeContrat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `facture`
--
ALTER TABLE `facture`
  MODIFY `codeFacture` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `codeReservation` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `matricule` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `contrat`
--
ALTER TABLE `contrat`
  ADD CONSTRAINT `contrat_reservation` FOREIGN KEY (`codeReservation`) REFERENCES `reservation` (`codeReservation`) ON DELETE CASCADE,
  ADD CONSTRAINT `contrat_vehicule` FOREIGN KEY (`codeMatricule`) REFERENCES `vehicule` (`Immatriculation`) ON DELETE CASCADE;

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
  ADD CONSTRAINT `reserv_vehicule` FOREIGN KEY (`codeVehicule`) REFERENCES `vehicule` (`Immatriculation`) ON DELETE CASCADE;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `ReservationUpdate` ON SCHEDULE EVERY 1 HOUR STARTS '2022-02-01 12:40:54' ENDS '2024-01-01 12:40:54' ON COMPLETION PRESERVE ENABLE COMMENT 'si date la reservation n''est pas validee avant 2 jours, annule' DO UPDATE reservation
SET reservation.isCanceled = 1
WHERE DATEDIFF(reservation.dateDepReservation, CURRENT_DATE()) <= 2
AND reservation.isValid = 0$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
