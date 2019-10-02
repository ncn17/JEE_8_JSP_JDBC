package cg.ncn.JspJEE.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtils {

    public final static int CD_QUERY     = 0;
    public final static int UPDATE_QUERY = 1;
    public final static int READ_QUERY   = 2;

    /* createur de requete update bidirectionnelle */
    public static PreparedStatement buildQuery( Connection conn, String query, int type ) {
        PreparedStatement data = null;
        try {
            switch ( type ) {
            case 0:
                data = conn.prepareStatement( query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
                break;
            case 1:
                data = conn.prepareStatement( query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
                break;
            case 2:
                data = conn.prepareStatement( query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE );
                break;
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return data;
    }

    /* Fermeture silencieuse des ressources */
    public static void close( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "�chec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }

    public static void close( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "�chec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }

    public static void close( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException e ) {
                System.out.println( "�chec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }

    public static void close( Statement statement, ResultSet resultSet ) {
        close( statement );
        close( resultSet );
    }

}
