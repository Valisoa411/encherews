/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Karen
 * Created: Jan 17, 2023
 */

create database enchere ;

CREATE TABLE Admin (
	id SERIAL NOT NULL, 
	email varchar(255), 
	password varchar(50), 
	PRIMARY KEY (id)
);

CREATE TABLE AdminToken (
	idAdmin int4 NOT NULL, 
	token varchar(255) UNIQUE
);

CREATE TABLE Categorie (
	id SERIAL NOT NULL, 
	libelle varchar(100), 
	etat int default 11,
	PRIMARY KEY (id)
);

CREATE TABLE Client (
	id SERIAL NOT NULL, 
	nom varchar(255), 
	email varchar(255), 
	password varchar(50), 
	solde float8 default 0, 
	PRIMARY KEY (id)
);

CREATE TABLE Enchere (
	id SERIAL NOT NULL, 
	nomProduit varchar(255), 
	description varchar(255), 
	prixEnchere float8, 
	duree float8 default 0, 
	statut int4 default 11, 
	dateDebut timestamp, 
	idClient int4 NOT NULL, 
	idCategorie int4 NOT NULL, 
	PRIMARY KEY (id)
);

CREATE TABLE EnchereImage (
	idEnchere int4 NOT NULL, 
	image bytea 
);


CREATE TABLE Parametre (
	id SERIAL NOT NULL, 
	libelle varchar(100), 
	valeur float8 default 0, 
	PRIMARY KEY (id)
);

CREATE TABLE Proposition (
	id SERIAL NOT NULL, 
	idClient int4 NOT NULL, 
	idEnchere int4 NOT NULL, 
	montant float8 default 0, 
	"date" date, 
	PRIMARY KEY (id)
);

CREATE TABLE RechargeCompte (
	id SERIAL NOT NULL, 
	idClient int4 NOT NULL, 
	"date" date, 
	valeur float8, 
	etat int4 default 11, 
	PRIMARY KEY (id)
);

CREATE TABLE Token (
	idClient int4 NOT NULL, 
	token varchar(255) UNIQUE
);

//creation table commission
create table commission(
	id serial PRIMARY key,
	commission real default 0,
	"date" date default now()
);
----creation vue 
create  or replace view v_rechargeCompte as
select rc.id ,c.id idclient , nom client ,date,valeur,etat 
from rechargecompte rc join client c on rc.idclient=c.id;

ALTER TABLE AdminToken ADD CONSTRAINT FKAdminToken60296 FOREIGN KEY (idAdmin) REFERENCES Admin (id);
ALTER TABLE Token ADD CONSTRAINTx FKToken783363 FOREIGN KEY (idClient) REFERENCES Client (id);
ALTER TABLE RechargeCompte ADD CONSTRAINT FKRechargeCo234396 FOREIGN KEY (idClient) REFERENCES Client (id);
ALTER TABLE Enchere ADD CONSTRAINT FKEnchere873404 FOREIGN KEY (idClient) REFERENCES Client (id);
ALTER TABLE Enchere ADD CONSTRAINT FKEnchere925968 FOREIGN KEY (idCategorie) REFERENCES Categorie (id);
ALTER TABLE EnchereImage ADD CONSTRAINT FKEnchereIma988998 FOREIGN KEY (idEnchere) REFERENCES Enchere (id);
ALTER TABLE Proposition ADD CONSTRAINT FKPropositio274870 FOREIGN KEY (idClient) REFERENCES Client (id);
ALTER TABLE Proposition ADD CONSTRAINT FKPropositio277092 FOREIGN KEY (idEnchere) REFERENCES Enchere (id);
