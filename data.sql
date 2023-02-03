INSERT INTO admin(email, password)
VALUES ('rakoto@gmail.com', 'rakoto'),
    ('rasoa@gmail.com', 'rasoa');

INSERT INTO Client VALUES (default, 'Rakoto', 'rakoto@gmail.com', 'rakoto123', 100000);
INSERT INTO Client VALUES (default, 'Rasoa', 'rasoa@yahoo.fr', 'rasoa0000', 150000);
INSERT INTO Client VALUES (default, 'Andry', 'andry@gmail.com', 'andry321', 50000);

INSERT INTO RECHARGECOMPTE VALUES(DEFAULT,1,'1-1-2023',10000,11);
INSERT INTO RECHARGECOMPTE VALUES(DEFAULT,2,'1-1-2023',11000,11);
INSERT INTO RECHARGECOMPTE VALUES(DEFAULT,3,'1-1-2023',12000,11);

INSERT INTO CATEGORIE VALUES(DEFAULT,'Tech',11);
INSERT INTO CATEGORIE VALUES(DEFAULT,'Vetement',11);
INSERT INTO categorie(libelle)
VALUES ('Sante beaute cosmetiques'),
    ('Produits et accessoires pour le sport'),
    ('Appareils electroniques et accessoires connectes'),
    ('DIY et produits artisanaux');


INSERT INTO ENCHERE VALUES(DEFAULT,'Ecouteur','Ecouteur noir avec fil',20000,12,11,'2023-1-15 6:00',1,1);
INSERT INTO ENCHERE VALUES(DEFAULT,'T-shirt','T-shirt bleu',25000,18,21,'2023-1-15 7:00',1,2);
INSERT INTO ENCHERE VALUES(DEFAULT,'Cable','Cable C transfer',10000,6,11,'2023-1-15 9:00',2,1);

INSERT INTO enchere(
        nomproduit,
        description,
        prixenchere,
        duree,
        statut,
        datedebut,
        idclient,
        idcategorie
    )
VALUES (
        'Iphone 14',
        'smartphone 2022',
        4000000,
        24,
        21,
        '2023-01-14',
        1,
        5
    ),
    --10
    (
        'Ballon',
        'Ballon de basket dedicacé par M.J',
        3000000,
        24,
        11,
        '2023-01-20',
        1,
        4
    ), --10
    (
        'Shampoo',
        'The best',
        3000,
        24,
        11,
        '2023-01-20',
        1,
        3
    ),
    --15
    (
        'Sac a main',
        'vita gasy',
        20000,
        24,
        11,
        '2023-01-20',
        1,
        6
    ) --18
;

INSERT INTO PROPOSITION VALUES(DEFAULT,2,1,21000,'2023-1-20 7:00');
INSERT INTO PROPOSITION VALUES(DEFAULT,3,1,22000,'2023-1-19 8:00');
INSERT INTO PROPOSITION VALUES(DEFAULT,1,2,26000,'2023-1-20 8:00');

INSERT INTO proposition(idclient, idenchere, montant, date)
VALUES (1, 4, 450000, '2023-01-14'),
    (2, 4, 500000, '2023-01-14');

INSERT INTO COMMISSION VALUES(DEFAULT,5,'2023-1-1');
INSERT INTO commission (date, commission)
VALUES ('2023-01-10', 10),
    ('2023-01-15', 15),
    ('2023-01-18', 18);
