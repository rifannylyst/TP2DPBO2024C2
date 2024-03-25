-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Mar 2024 pada 17.06
-- Versi server: 10.4.27-MariaDB
-- Versi PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_mahasiswa`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id` int(11) NOT NULL,
  `nim` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `jenis_kelamin` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `usia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `mahasiswa`
--

INSERT INTO `mahasiswa` (`id`, `nim`, `nama`, `jenis_kelamin`, `email`, `usia`) VALUES
(1, '2203999', 'Amelia Zalfa Julianti', 'Perempuan', 'amelia@gmail.com', 20),
(2, '2202292', 'Muhammad Iqbal Fadhilah', 'Laki-laki', 'iqbal@gmail.com', 22),
(3, '2202346', 'Muhammad Rifky Afandi', 'Laki-laki', 'rifky@gmail.com', 25),
(4, '2210239', 'Muhammad Hanif Abdillah', 'Laki-laki', 'hanif@gmail.com', 24),
(5, '2202046', 'Nurainun', 'Perempuan', 'ainun@gmail.com', 22),
(6, '2205101', 'Kelvin Julian Putra', 'Laki-laki', 'kelvin@gmail.com', 21),
(7, '2200163', 'Rifanny Lysara Annastasya', 'Perempuan', 'rifanny@gmail.com', 20),
(8, '2202869', 'Revana Faliha Salma', 'Perempuan', 'revana@gmail.com', 21),
(9, '2209489', 'Rakha Dhifiargo Hariadi', 'Laki-laki', 'rakha@gmail.com', 24),
(10, '2203142', 'Roshan Syalwan Nurilham', 'Laki-laki', 'roshan@gmail.com', 23),
(11, '2200311', 'Raden Rahman Ismail', 'Laki-laki', 'rais@gmail.com', 22),
(12, '2200978', 'Ratu Syahirah Khairunnisa', 'Perempuan', 'ratu@gmail.com', 20),
(13, '2204509', 'Muhammad Fahreza Fauzan', 'Laki-laki', 'fahreza@gmail.com', 21),
(14, '2205027', 'Muhammad Rizki Revandi', 'Laki-laki', 'raven@gmail.com', 25),
(15, '2203484', 'Arya Aydin Margono', 'Laki-laki', 'arya@gmail.com', 23),
(16, '2200481', 'Marvel Ravindra Dioputra', 'Laki-laki', 'marvel@gmail.com', 24),
(17, '2209889', 'Muhammad Fadlul Hafiizh', 'Laki-laki', 'hafiizh@gmail.com', 21),
(18, '2206697', 'Rifa Sania', 'Perempuan', 'rifa@gmail.com', 22),
(19, '2207260', 'Imam Chalish Rafidhul Haque', 'Laki-laki', 'imam@gmail.com', 24),
(20, '2204343', 'Meiva Labibah Putri', 'Perempuan', 'meiva@gmail.com', 23),
(21, '2200111', 'Annisa Shafira Yasmin', 'Perempuan', 'annisa@gmail.com', 21),
(23, '2200123', 'Siapa Cik', 'Laki-laki', 'siapacik@gmail.com', 20),
(24, '2200678', 'Siapa Hayo', 'Laki-laki', 'pepepe@gmail.com', 22);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `mahasiswa`
--
ALTER TABLE `mahasiswa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
