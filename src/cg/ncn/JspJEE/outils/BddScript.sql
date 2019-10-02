/* creation bdd */

CREATE DATABASE JspJEE DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

/* Table Client */

CREATE TABLE JspJEE.Client (
 id INT( 11 ) NOT NULL AUTO_INCREMENT ,
 nom VARCHAR( 20 ) NOT NULL ,
 prenom VARCHAR( 20 ) ,
 adresse VARCHAR( 200 ) NOT NULL ,
 telephone VARCHAR( 10 ) NOT NULL,
 email VARCHAR( 60 ) ,
 image VARCHAR( 200 ) ,
 PRIMARY KEY ( id )
) ENGINE = INNODB;

/* Table Commande */

CREATE TABLE JspJEE.Commande (
 id INT( 11 ) NOT NULL AUTO_INCREMENT ,
 id_client INT( 11 ) , -- 
 date DATETIME NOT NULL ,
 montant INT( 11 ) NOT NULL ,
 mode_paiement VARCHAR( 20 ) NOT NULL ,
 statut_paiement VARCHAR( 20 ) ,
 mode_livraison VARCHAR( 20 ) NOT NULL ,
 statut_livraison VARCHAR( 20 ) ,
 PRIMARY KEY ( id ) ,
 CONSTRAINT fk_id_client      
    FOREIGN KEY (id_client)   
    REFERENCES Client(id)     
    ON DELETE SET NULL        
) ENGINE = INNODB;