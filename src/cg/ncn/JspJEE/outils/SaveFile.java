package cg.ncn.JspJEE.outils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import eu.medsea.mimeutil.MimeUtil;

/**
 * 
 * @author atir17 ncn save file 1.2.0
 */
public class SaveFile {

    private Map<String, String> erreurs = new HashMap<String, String>();
    private String              fileName;

    public void save( HttpServletRequest request, String CHAMP_FILE ) {
        InputStream dataFile = null;

        try {
            Part part = request.getPart( CHAMP_FILE );

            if ( part != null ) {
                /* vérification si type champ File */
                fileName = BoxOutils.getNomFichier( part );
            }
            // test type file
            if ( !fileName.isEmpty() && fileName != null ) {
                /* champ file confirmer extraction nom Simple fix IE bug */
                fileName = fileName.substring( fileName.lastIndexOf( '/' ) + 1 )
                        .substring( fileName.lastIndexOf( '\\' ) + 1 );

                // make a new name for count
                String ext = fileName.substring( fileName.lastIndexOf( '.' ) + 1 );

                fileName = Props.PREFIX_IMG + BoxOutils.countClient( request ) + '.' + ext;

                /* get data file */
                dataFile = part.getInputStream();
            }
        } catch ( IllegalStateException e ) {
            /* fichier trop volumineux */
            e.printStackTrace();
            setErreurs( CHAMP_FILE, "Fichier trop lourd : Limite 1Mo" );
        } catch ( IOException e ) {
            /* erreur niveau serveur */
            e.printStackTrace();
            setErreurs( CHAMP_FILE, "Oops une erreur interne ! veuillez réessayer plus tard" );
        } catch ( ServletException e ) {
            /* erreur niveau requete formulaire corrompue */
            e.printStackTrace();
            setErreurs( CHAMP_FILE,
                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
        }

        // stockage de l'image
        /* pas d'erreur validation fileName */
        if ( erreurs.isEmpty() ) {
            try {
                BoxOutils.validationFichier( fileName, dataFile );
            } catch ( Exception e ) {
                setErreurs( CHAMP_FILE, e.getMessage() );
            }
            /*
             * verification du mime du fichier depuis le stream du fichier
             */
            MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
            Collection<?> mimeTypes = MimeUtil.getMimeTypes( dataFile );
            /*
             * MIME must start by "image"
             */
            if ( mimeTypes.toString().startsWith( "image" ) ) {
                /* Écriture du fichier sur le disque */
                try {
                    BoxOutils.writeFile( dataFile, fileName, Props.FILE_PATH );
                } catch ( Exception e ) {
                    setErreurs( CHAMP_FILE, "Erreur lors de l'écriture du fichier sur le disque." );
                }
            } else {
                setErreurs( CHAMP_FILE, "Veuillez selectionner une image valide.( .jpg , .jpeg)" );
            }
        }

    }

    public SaveFile( String fileName ) {
        this.fileName = fileName;
    }

    public void setErreurs( String champ, String err ) {
        this.erreurs.put( champ, err );
    }

    public String getFileName() {
        return fileName;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

}
