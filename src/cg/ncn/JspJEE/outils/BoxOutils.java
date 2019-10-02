package cg.ncn.JspJEE.outils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import cg.ncn.JspJEE.beans.Client;
import cg.ncn.JspJEE.beans.Commande;
import cg.ncn.JspJEE.dao.DAOFactory;

public class BoxOutils {
    public static final String BDD_CLIENT   = "listeClients";
    public static final String BDD_COMMANDE = "listeCmd";

    public static String getChamp( HttpServletRequest request, String champ ) {
        return request.getParameter( champ );
    }

    public static void verifyTexte( String champ, int lenght, String err ) throws Exception {
        if ( champ != null && champ.trim().isEmpty() != true ) {
            if ( champ.length() < lenght ) {
                throw new Exception( err );
            }
        } else {
            throw new Exception( "Le champ ne peut pas être vide." );
        }
    }

    public static void verifyMail( String champ ) throws Exception {
        if ( champ != null && champ.trim().isEmpty() != true ) {
            if ( !champ.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new Exception( "Merci de saisir une adresse mail valide." );
            }
        } else {
            throw new Exception( "Le champ ne peut pas être vide." );
        }
    }

    public static void verifyNumero( String champ ) throws Exception {
        if ( champ != null && champ.trim().isEmpty() != true ) {
            if ( champ.length() < 4 || champ.length() > 10 ) {
                throw new Exception( "Merci d'entrez un numéro de téléphone valable." );
            }
        } else {
            throw new Exception( "Le champ ne peut pas être vide." );
        }
    }

    public static double verifyMontant( String champ ) throws Exception {
        double montant = 0;
        if ( champ != null && champ.trim().isEmpty() != true ) {
            try {
                montant = Double.parseDouble( champ );
                montant = ( montant < 0 ) ? -1 : montant;
            } catch ( NumberFormatException e ) {
                throw new Exception( "Erreur le montant doit être en chiffre et positive." );
            }
        } else {
            throw new Exception( "Le champ ne peut pas être vide." );
        }
        return montant;
    }

    public static void addCommande( HttpServletRequest req ) {
        // get bdd clients list
        ArrayList<Commande> cmds = DAOFactory.getCommandeDAO().findAll();

        if ( !cmds.isEmpty() && cmds != null ) {
            // get session
            HttpSession session = req.getSession();
            session.removeAttribute( BDD_COMMANDE );
            session.setAttribute( BDD_COMMANDE, cmds );
        }
    }

    @SuppressWarnings( "unchecked" )
    public static Commande getCommande( HttpServletRequest req, int id ) {
        Commande cmd = null;
        // get session
        HttpSession session = req.getSession();
        ArrayList<Commande> cmds = (ArrayList<Commande>) session.getAttribute( Props.BDD_COMMANDE );

        for ( Commande found : cmds ) {
            if ( found.getId() == id ) {
                cmd = found;
            }
        }
        return cmd;
    }

    public static void addClient( HttpServletRequest req ) {
        // get bdd clients list
        ArrayList<Client> clients = DAOFactory.getClientDAO().findAll();

        if ( !clients.isEmpty() && clients != null ) {
            // get session
            HttpSession session = req.getSession();
            session.removeAttribute( BDD_CLIENT );
            session.setAttribute( BDD_CLIENT, clients );
        }
    }

    @SuppressWarnings( "unchecked" )
    public static Client getClient( HttpServletRequest req, int id ) {
        Client client = null;
        // get session
        HttpSession session = req.getSession();
        ArrayList<Client> clients = (ArrayList<Client>) session.getAttribute( Props.BDD_CLIENT );

        for ( Client cli : clients ) {
            if ( cli.getId() == id ) {
                client = cli;
            }
        }
        return client;
    }

    @SuppressWarnings( "unchecked" )
    public static int countClient( HttpServletRequest req ) {
        HttpSession session = req.getSession();
        ArrayList<Client> liste = (ArrayList<Client>) session.getAttribute( BDD_CLIENT );

        int number = ( liste != null && !liste.isEmpty() ) ? liste.size() + 1 : 1;

        return number;
    }

    public static int parseId( String txtId ) {
        int id = 0;
        try {
            id = Integer.parseInt( txtId );
        } catch ( ArithmeticException e ) {
        }
        return id;
    }

    /* Pour les Champ de type file */
    public static String getNomFichier( Part part ) {
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                // client and return fileName
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).replace( "\"", "" );
            }
        }
        return null;
    }

    /* stocker le fichier dans la machine */
    public static void writeFile( InputStream dataFile, String nomFichier, String chemin ) throws IOException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream( dataFile, Props.TAILLE_TAMPON );
            out = new BufferedOutputStream( new FileOutputStream( chemin + nomFichier ), Props.TAILLE_TAMPON );

            /* read and write store in disk */
            byte[] tempo = new byte[Props.TAILLE_TAMPON];
            int length;

            while ( ( length = in.read( tempo ) ) > 0 ) {
                out.write( tempo, 0, length );
            }
        } finally {
            try {
                out.close();
            } catch ( IOException ignore ) {
            }
            try {
                in.close();
            } catch ( IOException ignore ) {
            }
        }
    }

    /* validation du fichier nom && contenu != null */
    public static void validationFichier( String nomFichier, InputStream contenuFichier ) throws Exception {
        if ( nomFichier == null || contenuFichier == null ) {
            throw new Exception( "Merci de sélectionner un fichier à envoyer." );
        }
    }

    /* transformation de la date */

    public static String dateToString( DateTime date ) {

        /* Conversion de la date en String selon le format défini */
        DateTimeFormatter formatter = DateTimeFormat.forPattern( Props.FORMAT_DATE );

        return date.toString( formatter );
    }

    public static DateTime stringToDate( Timestamp myDate ) throws Exception {
        if ( myDate != null ) {

            DateTime date = new DateTime( myDate );
            // DateTimeFormatter formatter = DateTimeFormat.forPattern(
            // Props.FORMAT_DATE );

            // return formatter.parseDateTime( myDate );
            return date;
        } else {
            throw new Exception( "La date entrée est invalide." );
        }
    }

}
