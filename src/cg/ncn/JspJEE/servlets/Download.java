package cg.ncn.JspJEE.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cg.ncn.JspJEE.outils.Props;

/**
 * 
 * @author atir17 ncn servlet Download 1.0.12
 *
 */

@WebServlet( "/upload/*" )
public class Download extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        /*
         * Récupération du chemin du fichier demandé au sein de l'URL de la
         * requête
         */
        String fichierRequis = request.getPathInfo();

        /* Vérifie qu'un fichier a bien été fourni */
        if ( fichierRequis == null ) {
            /*
             * Si non, alors on envoie une erreur 404, qui signifie que la
             * ressource demandée n'existe pas
             */
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
            return;
        }

        /*
         * Décode le nom de fichier récupéré, susceptible de contenir des
         * espaces et autres caractères spéciaux, et prépare l'objet File
         */
        fichierRequis = URLDecoder.decode( fichierRequis, "UTF-8" );
        File fichier = new File( Props.FILE_PATH, fichierRequis );

        /* Vérifie que le fichier existe bien */
        if ( !fichier.exists() ) {
            /*
             * Si non, alors on envoie une erreur 404, qui signifie que la
             * ressource demandée n'existe pas
             */
            response.sendError( HttpServletResponse.SC_NOT_FOUND );
            return;
        }

        /* Récupère le type du fichier */
        String type = getServletContext().getMimeType( fichier.getName() );

        /*
         * Si le type de fichier est inconnu, alors on initialise un type par
         * défaut
         */
        if ( type == null ) {
            type = "application/octet-stream";
        }

        /* Initialise la réponse HTTP */
        response.reset();
        response.setBufferSize( Props.TAILLE_TAMPON );
        response.setContentType( type );
        response.setHeader( "Content-Length", String.valueOf( fichier.length() ) );
        response.setHeader( "Content-Disposition", "inline; filename=\"" + fichier.getName() + "\"" );

        /* Prépare les flux */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux */
            entree = new BufferedInputStream( new FileInputStream( fichier ), Props.TAILLE_TAMPON );
            sortie = new BufferedOutputStream( response.getOutputStream(), Props.TAILLE_TAMPON );

            /* Lit le fichier et écrit son contenu dans la réponse HTTP */
            byte[] tampon = new byte[Props.TAILLE_TAMPON];
            int longueur;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } finally {
            sortie.close();
            entree.close();
        }
    }
}