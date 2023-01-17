--Vehicule
INSERT INTO Vehicule(marque,immatriculation) 
VALUES
    ('Peugeot','1235 TAN'),
    ('Mazda','8648 TBA'),
    ('Renault','3564 TN'),
    ('Peugeot','7957 TAV'),
    ('Peugeot','7493 TAA');

--Kilometrage
INSERT INTO Vehicule(marque,immatriculation) VALUES('BMW','123QWE');
INSERT INTO Vehicule(marque,immatriculation) VALUES('Peugeot','456RTY');
INSERT INTO Kilometrage(vehiculeId,date,debut,fin) VALUES(1,'10-11-2022',0,10);
INSERT INTO Kilometrage(vehiculeId,date,debut,fin) VALUES(1,'13-11-2022',10,15);
