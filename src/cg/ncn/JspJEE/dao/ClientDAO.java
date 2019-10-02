package cg.ncn.JspJEE.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cg.ncn.JspJEE.beans.Client;
import cg.ncn.JspJEE.outils.DAOException;

public class ClientDAO extends DAO<Client> {

    public ClientDAO( Connection conn ) {
        super( conn );
    }

    public final static String QUERY_DELETE   = "DELETE FROM Client WHERE id = ?";
    public final static String QUERY_FIND     = "SELECT * FROM Client WHERE id = ?";
    public final static String QUERY_FIND_ALL = "SELECT * FROM Client ";
    public final static String QUERY_CREATE   = "INSERT INTO Client (id, nom, prenom, adresse, telephone, email, image)"
            + " VALUES(?,?,?,?,?,?,?) ";

    public Client find( int id ) throws DAOException {
        Client client = null;
        ResultSet resultat = null;
        PreparedStatement requete = null;

        try {
            requete = DAOUtils.buildQuery( this.conn, QUERY_FIND, DAOUtils.READ_QUERY );
            requete.setInt( 1, id );
            resultat = requete.executeQuery();

            if ( resultat.first() ) {
                client = Hydrate( resultat );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            DAOUtils.close( requete, resultat );
        }

        return client;
    }

    @Override
    public ArrayList<Client> findAll() throws DAOException {
        ArrayList<Client> clientList = new ArrayList<Client>();
        ResultSet resultat = null;
        PreparedStatement requete = null;

        try {
            requete = DAOUtils.buildQuery( conn, QUERY_FIND_ALL, DAOUtils.READ_QUERY );
            resultat = requete.executeQuery();

            while ( resultat.next() ) {
                clientList.add( Hydrate( resultat ) );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            DAOUtils.close( requete, resultat );
        }

        return clientList;
    }

    @Override
    public boolean create( Client client ) throws DAOException {
        PreparedStatement requete = null;
        boolean temoin = true;

        try {
            requete = DAOUtils.buildQuery( conn, QUERY_CREATE, DAOUtils.UPDATE_QUERY );
            requete.setLong( 1, client.getId() );
            requete.setString( 2, client.getNom() );
            requete.setString( 3, client.getPrenom() );
            requete.setString( 4, client.getAdresse() );
            requete.setString( 5, client.getNumero() );
            requete.setString( 6, client.getEmail() );
            requete.setString( 7, client.getImage() );

            int status = requete.executeUpdate();

            if ( status == 0 ) {
                temoin = false;
                throw new DAOException( "Echec de la cr�ation du client" );
            }

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            DAOUtils.close( requete );
        }

        return temoin;
    }

    @Override
    public boolean update( Client obj ) throws DAOException {
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
            throw new DAOException( e.getMessage() );
        } finally {
            DAOUtils.close( requete, resultat );
        }

        return temoin;
    }

    public Client Hydrate( ResultSet res ) throws SQLException {
        return new Client( res.getLong( "id" ),
                res.getString( "nom" ),
                res.getString( "prenom" ),
                res.getString( "adresse" ),
                res.getString( "telephone" ),
                res.getString( "email" ),
                res.getString( "image" ) );
    }

}
