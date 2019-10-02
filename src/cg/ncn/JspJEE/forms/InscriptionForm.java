package cg.ncn.JspJEE.forms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import cg.ncn.JspJEE.beans.Client;
import cg.ncn.JspJEE.dao.DAOFactory;
import cg.ncn.JspJEE.outils.BoxOutils;
import cg.ncn.JspJEE.outils.SaveFile;

@MultipartConfig
public class InscriptionForm {

    private static final String CHAMP_NOM     = "nomClient";
    private static final String CHAMP_PRENOM  = "prenomClient";
    private static final String CHAMP_ADRESSE = "adresseClient";
    private static final String CHAMP_TEL     = "telephoneClient";
    private static final String CHAMP_MAIL    = "emailClient";
    private static final String CHAMP_IMAGE   = "imageClient";

    private String              resultat;
    private Map<String, String> erreurs       = new HashMap<String, String>();

    public Client inscrireClient( HttpServletRequest request ) throws IOException {
        Client client = new Client();

        String nom = BoxOutils.getChamp( request, CHAMP_NOM );
        String prenom = BoxOutils.getChamp( request, CHAMP_PRENOM );
        String adresse = BoxOutils.getChamp( request, CHAMP_ADRESSE );
        String tel = BoxOutils.getChamp( request, CHAMP_TEL );
        String mail = BoxOutils.getChamp( request, CHAMP_MAIL );
        String fileName = "";

        try {
            BoxOutils.verifyTexte( nom, 2, "le  nom doit contneir aumoins 2 caractères." );
        } catch ( Exception e ) {
            setErreurs( CHAMP_NOM, e.getMessage() );
        }

        try {
            BoxOutils.verifyTexte( prenom, 2, "le  prénom doit contenir aumoins 2 caractères." );
        } catch ( Exception e ) {
            setErreurs( CHAMP_PRENOM, e.getMessage() );
        }

        try {
            BoxOutils.verifyTexte( adresse, 10, "L'adresse doit contenir aumoins 10 caractères." );
        } catch ( Exception e ) {
            setErreurs( CHAMP_ADRESSE, e.getMessage() );
        }

        try {
            BoxOutils.verifyNumero( tel );
        } catch ( Exception e ) {
            setErreurs( CHAMP_TEL, e.getMessage() );
        }

        try {
            BoxOutils.verifyMail( mail );
        } catch ( Exception e ) {
            setErreurs( CHAMP_MAIL, e.getMessage() );
        }

        // traitement de l'image
        if ( erreurs.isEmpty() ) {

            // Method image tools
            SaveFile saveFile = new SaveFile( fileName );

            saveFile.save( request, CHAMP_IMAGE );

            // get error and data
            fileName = saveFile.getFileName();

            for ( Map.Entry<String, String> listError : saveFile.getErreurs().entrySet() ) {
                setErreurs( listError.getKey(), listError.getValue() );
            }
        }

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création du client";
            // assignation don't forget to add image name
            client = new Client( nom, prenom, adresse, tel, mail, fileName );
            DAOFactory.getClientDAO().create( client );

            // add the new client refresh liste
            BoxOutils.addClient( request );
        } else {

            resultat = "Echec de la création du client";
        }

        return client;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public void setErreurs( String champ, String err ) {
        this.erreurs.put( champ, err );
    }

}
