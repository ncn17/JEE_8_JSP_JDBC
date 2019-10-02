package cg.ncn.JspJEE.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cg.ncn.JspJEE.beans.Client;
import cg.ncn.JspJEE.beans.Commande;
import cg.ncn.JspJEE.outils.DAOException;

public class CommandeDAO extends DAO<Commande> {

    public CommandeDAO( Connection conn ) {
        super( conn );
    }

    public final static String QUERY_DELETE   = "DELETE FROM Commande WHERE id = ?";
    public final static String QUERY_FIND     = "SELECT * FROM Commande WHERE id = ?";
    public final static String QUERY_FIND_ALL = "SELECT * FROM Commande ";
    public final static String QUERY_CREATE   = "INSERT INTO Commande VALUES(?,?,?,?,?,?,?,?) ";

    @Override
    public Commande find( int id ) throws DAOException {
        Commande commande = null;
        ResultSet resultat = null;
        PreparedStatement requete = null;

        try {
            requete = DAOUtils.buildQuery( this.conn, QUERY_FIND, DAOUtils.READ_QUERY );
            requete.setInt( 1, id );
            resultat = requete.executeQuery();

            if ( resultat.first() ) {
                commande = Hydrate( resultat );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            DAOUtils.close( requete, resultat );
        }

        return commande;
    }

    @Override
    public ArrayList<Commande> findAll() throws DAOException {
        ArrayList<Commande> commandeList = new ArrayList<Commande>();
        ResultSet resultat = null;
        PreparedStatement requete = null;

        try {
            requete = DAOUtils.buildQuery( conn, QUERY_FIND_ALL, DAOUtils.READ_QUERY );
            resultat = requete.executeQuery();

            while ( resultat.next() ) {

                commandeList.add( Hydrate( resultat ) );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            DAOUtils.close( requete, resultat );
        }

        return commandeList;
    }

    @Override
    public boolean create( Commande cmd ) throws DAOException {
        PreparedStatement requete = null;
        boolean temoin = true;

        try {
            requete = DAOUtils.buildQuery( conn, QUERY_CREATE, DAOUtils.UPDATE_QUERY );
            requete.setLong( 1, cmd.getId() );
            requete.setLong( 2, cmd.getClient().getId() );
            requete.setString( 3, cmd.getDate() );
            requete.setDouble( 4, cmd.getMontant() );
            requete.setString( 5, cmd.getModePayment() );
            requete.setString( 6, cmd.getStatutPayment() );
            requete.setString( 7, cmd.getModeLIvraison() );
            requete.setString( 8, cmd.getStatutLIvraison() );

            int status = requete.executeUpdate();

            if ( status == 0 ) {
                temoin = false;
                throw new DAOException( "Echec de la cr�ation de la commande" );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            DAOUtils.close( requete );
        }

        return temoin;
    }

    @Override
    public boolean update( Commande obj ) throws DAOException {
        return false;
    }

    @Override
    public boolean delete( int id ) throws DAOException {
        ResultSet resultat = null;
        PreparedStatement requete = null;
        Boolean temoin = true;

        try {
            requete = DAOUtils.buildQuery( this.conn, QUERY_DELETE, DAOUtils.READ_QUERY );

            requete.setLong( 1, id );

            temoin = requete.execute();

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            DAOUtils.close( requete, resultat );
        }

        return temoin;
    }

    @Override
    public Commande Hydrate( ResultSet res ) throws SQLException {

        /* recuperation du client de la commmande */
        Client client = DAOFactory.getClientDAO().find( res.getInt( "id_client" ) );

        return new Commande( res.getLong( "id" ),
                client,
                res.getString( "date" ),
                res.getDouble( "montant" ),
                res.getString( "mode_paiement" ),
                res.getString( "statut_paiement" ),
                res.getString( "mode_livraison" ),
                res.getString( "statut_livraison" ) );

    }

}
