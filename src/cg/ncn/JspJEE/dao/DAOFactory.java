package cg.ncn.JspJEE.dao;

import java.sql.Connection;

import cg.ncn.JspJEE.bdd.BDDMysql;
import cg.ncn.JspJEE.beans.Client;
import cg.ncn.JspJEE.beans.Commande;

public class DAOFactory {

    /* appel direct et unique de la bdd instenciation jvm */
    protected static Connection conn = BDDMysql.getConnexion();

    public DAO<Client> getUilisateurDAO() {
        return new ClientDAO( conn );
    }

    public static DAO<Client> getClientDAO() {
        return new ClientDAO( conn );
    }

    public static DAO<Commande> getCommandeDAO() {
        return new CommandeDAO( conn );
    }

}
