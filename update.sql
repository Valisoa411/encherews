CREATE VIEW v_count_categorie_temp AS
SELECT COUNT(enchere.id),
    idcategorie
FROM enchere
    LEFT JOIN categorie ON enchere.idcategorie = categorie.id
GROUP BY idcategorie;

CREATE OR REPLACE VIEW v_count_categorie AS
SELECT id,
    libelle,
    count
FROM categorie
    LEFT JOIN v_count_categorie_temp ON categorie.id = v_count_categorie_temp.idcategorie;


CREATE OR REPLACE VIEW v_commission_enchere AS 
SELECT enchere.*,
    (
        SELECT commission
        FROM commission
        WHERE date < enchere.datedebut
        ORDER BY date DESC
        LIMIT 1
    ) AS pourcentage_commission,
    (
        (
            (
                (
                    SELECT commission
                    FROM commission
                    WHERE date < enchere.datedebut
                    ORDER BY date DESC
                    LIMIT 1
                )
            ) * proposition.montant
        ) / 100
    ) AS commission
FROM enchere JOIN proposition
ON enchere.id = proposition.idenchere
WHERE enchere.statut=21;

CREATE OR REPLACE VIEW v_vendu_temp AS
select v_commission_enchere.id,MAX(commission) from v_commission_enchere GROUP BY id;

CREATE OR REPLACE VIEW v_vendu AS
SELECT v_commission_enchere.* FROM  v_vendu_temp JOIN v_commission_enchere  ON v_vendu_temp.id=v_commission_enchere.id AND v_vendu_temp.max=v_commission_enchere.commission;

CREATE OR REPLACE VIEW v_all_recette_by_categorie AS
SELECT categorie.*, sum(commission) FROM categorie LEFT JOIN v_vendu
ON categorie.id=v_vendu.idcategorie
GROUP BY categorie.id;






CREATE OR REPLACE VIEW v_bestprop AS
SELECT 
    IDENCHERE, 
    MAX(MONTANT) BEST
FROM PROPOSITION
GROUP BY IDENCHERE
;

CREATE OR REPLACE VIEW v_bestpropenchere AS
SELECT
    P.*
FROM PROPOSITION P 
    CROSS JOIN V_BESTPROP V
WHERE P.MONTANT=V.BEST
;

CREATE OR REPLACE VIEW v_enchere AS
SELECT
    E.ID,
    E.NOMPRODUIT,
    E.DESCRIPTION,
    C.LIBELLE CATEGORIE,
    VB.MONTANT,
    CL.NOM CLIENT,
    (SELECT E.DATEDEBUT + INTERVAL '1 HOURS' * E.DUREE) DATEFIN,
    (SELECT IMAGE FROM ENCHEREIMAGE WHERE IDENCHERE=E.ID LIMIT 1) IMAGE
FROM ENCHERE E 
    JOIN CATEGORIE C ON E.IDCATEGORIE=C.ID
    JOIN V_BESTPROPENCHERE VB ON E.ID=VB.IDENCHERE
    JOIN CLIENT CL ON VB.IDCLIENT=CL.ID
;



--SELECT categorie.*, sum(commission) FROM categorie LEFT JOIN v_commission_enchere 
--ON categorie.id=v_commission_enchere.idcategorie
--GROUP BY categorie.id;


