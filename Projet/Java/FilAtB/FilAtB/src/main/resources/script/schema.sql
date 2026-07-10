-- Création de la base de données avec l'encodage UTF-8
CREATE DATABASE filatb_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Création de l'utilisateur (remplacez 'motdepasse' par un mot de passe fort)
CREATE USER 'filatb_user'@'localhost' IDENTIFIED BY 'filatb_db123#';

-- Octroi de tous les privilèges sur la base à cet utilisateur
GRANT ALL PRIVILEGES ON filatb_db.* TO 'filatb_user'@'localhost';

-- Application des changements
FLUSH PRIVILEGES;

USE banque_file;

CREATE TABLE client_servi (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              numero_ticket INT NOT NULL,
                              nom VARCHAR(100) NOT NULL,
                              motif VARCHAR(50) NOT NULL,
                              priorite BOOLEAN NOT NULL DEFAULT FALSE,
                              heure_arrivee DATETIME NOT NULL,
                              heure_prise_en_charge DATETIME NOT NULL,
                              guichet VARCHAR(30)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Index optionnels
CREATE INDEX IF NOT EXISTS idx_numero_ticket ON client_servi(numero_ticket);
CREATE INDEX IF NOT EXISTS idx_heure_prise ON client_servi(heure_prise_en_charge);