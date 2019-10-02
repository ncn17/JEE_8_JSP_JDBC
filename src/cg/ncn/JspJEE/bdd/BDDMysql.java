package cg.ncn.JspJEE.bdd;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import cg.ncn.JspJEE.outils.Props;

public abstract class BDDMysql {

    // Objet connexion instanciation direct avec la JVM
    private static Connection conn = getConnexion();

    public synchronized static Connection getConnexion() {
        if ( conn == null ) {
            try {
                Class.forName( Props.MYSQL_DRIVER );

                // configuration de Bone CP
                BoneCPConfig config = new BoneCPConfig();

                config.setJdbcUrl( Props.getMysqlUrl() );
                config.setUsername( Props.MYSQL_USER );
                config.setPassword( Props.MYSQL_PASS );

                config.setMinConnectionsPerPartition( 5 );
                config.setMaxConnectionsPerPartition( 10 );
                config.setPartitionCount( 2 );

                @SuppressWarnings( "resource" )
                BoneCP connectionPool = new BoneCP( config );

                // get connect data
                conn = connectionPool.getConnection();

            } catch ( SQLException | ClassNotFoundException e ) {
                e.printStackTrace();
            }
        }
        return conn;
    }

}
