CREATE USER vehicule WITH PASSWORD 'vehicule';
\c postgres postgres localhost 5432

DROP DATABASE vehicule;
CREATE DATABASE vehicule;
alter database vehicule owner to vehicule;
\c vehicule vehicule localhost 5432

DROP TABLE Admin CASCADE;
CREATE TABLE Admin(
	idAdmin SERIAL PRIMARY KEY,
	login varchar(255),
	mdp varchar(50) 
);

DROP TABLE Vehicule CASCADE;
CREATE TABLE Vehicule(
	idVehicule SERIAL PRIMARY KEY,
	marque varchar(100),
	immatriculation varchar(20) NOT NULL UNIQUE 
);

DROP TABLE VehiculeSupprimer CASCADE;
CREATE TABLE VehiculeSupprimer(
	idVehiculeSupprimer SERIAL PRIMARY KEY,
	dateSuppression date DEFAULT current_date,
	vehiculeId int,
	FOREIGN KEY (vehiculeId) REFERENCES Vehicule(idVehicule)
);

DROP TABLE Kilometrage CASCADE;
CREATE TABLE Kilometrage(
	idKilometrage SERIAL PRIMARY KEY,
	vehiculeId int,
	date date,
	debut double precision,
	fin double precision,
	FOREIGN KEY (vehiculeId) REFERENCES Vehicule(idVehicule)
);

DROP TABLE KilometrageSupprimer CASCADE;
CREATE TABLE KilometrageSupprimer(
	idKilometrageSupprimer SERIAL PRIMARY KEY,
	dateSuppression date DEFAULT current_date,
	kilometrageId int,
	FOREIGN KEY (kilometrageId) REFERENCES Kilometrage(idKilometrage)
);
