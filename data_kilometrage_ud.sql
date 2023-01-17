INSERT INTO Utilisateur(login,mdp) VALUES('user@gmail.com','user');
INSERT INTO Utilisateur(login,mdp) VALUES('jean@gmail.com','jean');

INSERT INTO avion(marque,immatriculation) VALUES
('Air Bus','456RTY'),
('Air Bus','5000T'),
('Boing','123QWE'),
('Boing','730K'),
('Boing','3000U');

INSERT INTO Kilometrage(avionId,date,debut,fin) VALUES
(1,'10-11-2022',0,10),
(1,'13-11-2022',10,15),

(2,'10-11-2022',0,10),
(2,'13-11-2022',10,20),

(3,'10-11-2022',0,10),
(3,'13-11-2022',10,30),

(4,'10-11-2022',0,20),
(4,'13-11-2022',20,40),

(5,'10-11-2022',0,10),
(5,'13-11-2022',10,15);


INSERT INTO Assurance(avionId, date_renouvellement, date_echeance)VALUES
(1, '2022-09-01','2022-10-01'),
(2, '2022-09-11','2022-11-11'),
(3, '2022-09-01','2022-10-01'),
(4, '2022-09-11','2022-11-11'),
(5, '2022-09-01','2022-10-01'), --expired

(1, '2022-10-01','2022-12-01'), --1 month
(2, '2022-11-11','2022-12-11'), --1 month
(3, '2022-10-01','2023-03-01'), --not expired
(4, '2022-11-11', '2023-02-11'); --3 month

