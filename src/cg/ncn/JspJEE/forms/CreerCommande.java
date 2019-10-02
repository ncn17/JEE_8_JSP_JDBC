package cg.ncn.JspJEE.forms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import cg.ncn.JspJEE.beans.Client;
import cg.ncn.JspJEE.beans.Commande;
import cg.ncn.JspJEE.dao.DAOFactory;
import cg.ncn.JspJEE.outils.BoxOutils;

public class CreerCommande {

    private static final String CHAMP_MONTANT = "montantCommande";
    private static final String CHAMP_MP      = "modePaiementCommande";
    private static final String CHAMP_SP      = "statutPaiementCommande";
    private static final String CHAMP_ML      = "modeLivraisonCommande";
    private static final String CHAMP_SL      = "statutLivraisonCommande";

    private String              resultat;
    private Map<String, String> erreurs       = new HashMap<String, String>();

    public Commande creerCommande( HttpServletRequest request, Client oldClient ) throws IOException {
        Commande commande = new Commande();

        String mnt = BoxOutils.getChamp( request, CHAMP_MONTANT );
        String modePayement = BoxOutils.getChamp( request, CHAMP_MP );
        String statutPayement = BoxOutils.getChamp( request, CHAMP_SP );
        String modeLivraison = BoxOutils.getChamp( request, CHAMP_ML );
        String statutLivraison = BoxOutils.getChamp( request, CHAMP_SL );
        double montant = 0d;

        try {
            montant = BoxOutils.verifyMontant( mnt );
        } catch ( Exception e ) {
            setErreurs( CHAMP_MONTANT, e.getMessage() );
        }

        try {
            BoxOutils.verifyTexte( modePayement, 2, "Le mode de payement doit contenir aumoins 2 caractères." );
        } catch ( Exception e ) {
            setErreurs( CHAMP_MP, e.getMessage() );
        }

        try {
            BoxOutils.verifyTexte( statutPayement, 2, "Le statut de payement doit contenir aumoins 2 caractères." );
        } catch ( Exception e ) {
            setErreurs( CHAMP_SP, e.getMessage() );
        }

        try {
            BoxOutils.verifyTexte( modeLivraison, 2, "Le mode de livraison doit contenir aumoins 2 caractères." );
        } catch ( Exception e ) {
            setErreurs( CHAMP_ML, e.getMessage() );
        }

        try {
            BoxOutils.verifyTexte( statutLivraison, 2, "Le mode de livraison doit contenir aumoins 2 caractères." );
        } catch ( Exception e ) {
            setErreurs( CHAMP_SL, e.getMessage() );
        }

        DateTime dt = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern( "dd/MM/yyyy HH:mm:ss" );
        String date = dt.toString( formatter );

        // gestion client
        if ( oldClient == null ) {
            InscriptionForm form = new InscriptionForm();
            oldClient = form.inscrireClient( request );
            commande.setClient( oldClient );
            // save
            DAOFactory.getClientDAO().create( oldClient );

            // add the new client refresh liste
            BoxOutils.addClient( request );

            // add Client error
            for ( Map.Entry<String, String> listError : form.getErreurs().entrySet() ) {
                setErreurs( listError.getKey(), listError.getValue() );
            }
        } else {
            commande.setClient( oldClient );
        }

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création du client";
            // assignation
            commande = new Commande( oldClient, date, montant, modePayement, statutPayement, modeLivraison,
                    statutLivraison );
            // save to database
            DAOFactory.getCommandeDAO().create( commande );
            // refresh page session
            BoxOutils.addCommande( request );
        } else {
            resultat = "Echec de la création du client";
        }

        return commande;
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