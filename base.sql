CREATE USER avion WITH PASSWORD 'avion';
\c postgres postgres localhost 5432
val

DROP DATABASE avion;
CREATE DATABASE avion;
alter database avion owner to avion;
\c avion avion localhost 5432
avion

CREATE TABLE Admin(
	idAdmin SERIAL PRIMARY KEY,
	login varchar(255),
	mdp varchar(50) 
);

CREATE TABLE utilisateur(
	idUtilisateur SERIAL PRIMARY KEY,
	login varchar(255),
	mdp varchar(50) 
);

CREATE TABLE TOKEN(
	IDUTILISATEUR INT NOT NULL,
	TOKEN VARCHAR(255) NOT NULL,
	FOREIGN KEY (IDUTILISATEUR) REFERENCES UTILISATEUR(IDUTILISATEUR)
);

CREATE TABLE Avion(
	idAvion SERIAL PRIMARY KEY,
	marque varchar(100),
	immatriculation varchar(20) NOT NULL UNIQUE 
);

CREATE TABLE AvionSupprimer(
	idAvionSupprimer SERIAL PRIMARY KEY,
	dateSuppression date DEFAULT current_date,
	avionId int,
	FOREIGN KEY (avionId) REFERENCES Avion(idAvion)
);

CREATE TABLE Kilometrage(
	idKilometrage SERIAL PRIMARY KEY,
	avionId int,
	date date,
	debut double precision,
	fin double precision,
	FOREIGN KEY (avionId) REFERENCES Avion(idAvion)
);

CREATE TABLE KilometrageSupprimer(
	idKilometrageSupprimer SERIAL PRIMARY KEY,
	dateSuppression date DEFAULT current_date,
	kilometrageId int,
	FOREIGN KEY (kilometrageId) REFERENCES Kilometrage(idKilometrage)
);

CREATE TABLE Assurance(
	idAssurance SERIAL PRIMARY KEY,
	avionId int,
	date_renouvellement DATE NOT NULL,
	date_echeance DATE NOT NULL,
	FOREIGN KEY (avionId) REFERENCES Avion(idAvion)
);

CREATE TABLE AssuranceSupprimer(
	idAssuranceSupprimer SERIAL PRIMARY KEY,
	assuranceId int,
	date_suppression DATE NOT NULL,
	FOREIGN KEY (assuranceId) REFERENCES Assurance(idAssurance)
);

CREATE OR REPLACE VIEW v_lastEcheance AS
SELECT MAX(date_echeance) AS date_echeance,
	avionId
FROM Assurance
GROUP BY avionId;

CREATE OR REPLACE VIEW v_assurance AS
SELECT assurance.* 
FROM v_lastEcheance
	JOIN assurance ON v_lastEcheance.avionId = assurance.avionId
	AND v_lastEcheance.date_echeance = assurance.date_echeance
	WHERE idAssurance NOT IN (SELECT assuranceid FROM AssuranceSupprimer);

create or replace view test as select assurance.* from v_lastEcheance join assurance on assurance.avionId = v_lastEcheance.avionId and assurance.date_echeance = v_lastEcheance.date_echeance;

CREATE VIEW AVION_KILOMETRAGE AS
SELECT AVIONID, MAX(FIN) FROM KILOMETRAGE GROUP BY AVIONID;

CREATE VIEW AVION_ASSURANCE AS
SELECT AVIONID, MAX(DATE_ECHEANCE) FROM ASSURANCE GROUP BY AVIONID;

--
