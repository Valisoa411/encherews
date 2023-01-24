CREATE ROLE enchere LOGIN PASSWORD 'enchere';

--------------------------------------------------
\c postgres postgres
val

DROP DATABASE enchere;
CREATE DATABASE enchere;
ALTER DATABASE enchere OWNER TO enchere;
\c enchere enchere
enchere

CREATE TABLE Admin (
  id       SERIAL NOT NULL,
  email    varchar(255), 
  password varchar(50), 
  PRIMARY KEY (id));
CREATE TABLE Client (
  id       SERIAL NOT NULL, 
  nom      varchar(255), 
  email    varchar(255), 
  password varchar(50), 
  solde    float8, 
  PRIMARY KEY (id));
CREATE TABLE AdminToken (
  idAdmin int4 NOT NULL, 
  token   varchar(255) UNIQUE);
CREATE TABLE Token (
  idClient int4 NOT NULL, 
  token    varchar(255) UNIQUE);
CREATE TABLE RechargeCompte (
  id       SERIAL NOT NULL, 
  idClient int4 NOT NULL, 
  "date"   date, 
  valeur   float8, 
  etat     int4 default 1, 
  PRIMARY KEY (id));
--0 refuser
--1 creer
--5 accepter
CREATE TABLE Categorie (
  id      SERIAL NOT NULL, 
  libelle varchar(100),
  etat    int4 default 0,
  PRIMARY KEY (id));
--0 existe
--1 supprimer
CREATE TABLE Parametre (
  id      SERIAL NOT NULL, 
  libelle varchar(100), 
  valeur  float8, 
  PRIMARY KEY (id));
CREATE TABLE Enchere (
  id          SERIAL NOT NULL, 
  nomProduit  varchar(255), 
  description varchar(255), 
  prixEnchere float8, 
  duree       float8, 
  statut      int4, 
  dateDebut   timestamp, 
  idClient    int4 NOT NULL, 
  idCategorie int4 NOT NULL, 
  PRIMARY KEY (id));
--STATUS
--1 CREER
--5 VENDU

CREATE TABLE EnchereImage (
  idEnchere int4 NOT NULL, 
  image     bytea);
CREATE TABLE Proposition (
  id        SERIAL NOT NULL, 
  idClient  int4 NOT NULL, 
  idEnchere int4 NOT NULL, 
  montant   float8, 
  "date"    date, 
  PRIMARY KEY (id));
ALTER TABLE AdminToken ADD CONSTRAINT FKAdminToken60296 FOREIGN KEY (idAdmin) REFERENCES Admin (id);
ALTER TABLE Token ADD CONSTRAINT FKToken783363 FOREIGN KEY (idClient) REFERENCES Client (id);
ALTER TABLE RechargeCompte ADD CONSTRAINT FKRechargeCo234396 FOREIGN KEY (idClient) REFERENCES Client (id);
ALTER TABLE Enchere ADD CONSTRAINT FKEnchere873404 FOREIGN KEY (idClient) REFERENCES Client (id);
ALTER TABLE Enchere ADD CONSTRAINT FKEnchere925968 FOREIGN KEY (idCategorie) REFERENCES Categorie (id);
ALTER TABLE EnchereImage ADD CONSTRAINT FKEnchereIma988998 FOREIGN KEY (idEnchere) REFERENCES Enchere (id);
ALTER TABLE Proposition ADD CONSTRAINT FKPropositio274870 FOREIGN KEY (idClient) REFERENCES Client (id);
ALTER TABLE Proposition ADD CONSTRAINT FKPropositio277092 FOREIGN KEY (idEnchere) REFERENCES Enchere (id);
