package cg.ncn.JspJEE.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cg.ncn.JspJEE.beans.User;
import cg.ncn.JspJEE.outils.BoxOutils;

public class LoginForm {

    private static final String CHAMP_LOGIN = "email";
    private static final String CHAMP_MDP   = "motdepasse";

    private String              resultat;
    private Map<String, String> erreurs     = new HashMap<String, String>();

    public User connect( HttpServletRequest request ) {

        String mail = BoxOutils.getChamp( request, CHAMP_LOGIN );
        String pass = BoxOutils.getChamp( request, CHAMP_MDP );

        try {
            BoxOutils.verifyMail( mail );
        } catch ( Exception e ) {
            setErreurs( CHAMP_LOGIN, e.getMessage() );
        }

        try {
            BoxOutils.verifyTexte( pass, 2, "le  mot de pass doit contneir aumoins 4 caractères." );
        } catch ( Exception e ) {
            setErreurs( CHAMP_MDP, e.getMessage() );
        }

        if ( erreurs.isEmpty() ) {
            resultat = "Client connecter avec succès.";
        } else {
            resultat = "Echec de la connection du client";
        }

        // assignation
        User user = new User( mail, pass );

        return user;
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
