package cg.ncn.JspJEE.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import cg.ncn.JspJEE.beans.User;
import cg.ncn.JspJEE.forms.LoginForm;

@WebServlet( urlPatterns = "/login" )
public class Login extends HttpServlet {
    private static final long   serialVersionUID          = 1L;
    private static final String VUE                       = "/WEB-INF/jsp/login.jsp";
    private static final String MENU                      = "/menu";
    private static final String ATT_CLIENT                = "client";
    private static final String ATT_FORM                  = "form";
    private static final String ATT_SESSION_USER          = "sessionUser";
    public static final String  ATT_INTERVALLE_CONNEXIONS = "intervalleConnexions";
    public static final String  COOKIE_DERNIERE_CONNEXION = "derniereConnexion";
    public static final String  FORMAT_DATE               = "dd/MM/yyyy HH:mm:ss";
    public static final int     COOKIE_MAX_AGE            = 60 * 60 * 24 * 365;
    public static final String  CHAMP_MEMOIRE             = "connect";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        // verification de la connexion
        HttpSession session = request.getSession();

        if ( session.getAttribute( ATT_SESSION_USER ) != null ) {
            // redirection vers le menu si deja connecter
            response.sendRedirect( request.getContextPath() + MENU );
            return;
        }

        // dernière connexion
        String derniereConnexion = getCookieValue( request, COOKIE_DERNIERE_CONNEXION );

        if ( derniereConnexion != null ) {
            /* Récupération de la date courante */
            DateTime dtCourante = new DateTime();

            /* Récupération de la date présente dans le cookie */
            DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );

            DateTime dtDerniereConnexion = formatter.parseDateTime( derniereConnexion );
            /* Calcul de la durée de l'intervalle */
            Period periode = new Period( dtDerniereConnexion, dtCourante );
            /* Formatage de la durée de l'intervalle */
            PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
                    .appendYears().appendSuffix( " an ", " ans " )
                    .appendMonths().appendSuffix( " mois " )
                    .appendDays().appendSuffix( " jour ", " jours " )
                    .appendHours().appendSuffix( " heure ", " heures " )
                    .appendMinutes().appendSuffix( " minute ", " minutes " )
                    .appendSeparator( "et " )
                    .appendSeconds().appendSuffix( " seconde", " secondes" )
                    .toFormatter();
            String intervalleConnexions = periodFormatter.print( periode );

            request.setAttribute( ATT_INTERVALLE_CONNEXIONS, intervalleConnexions );
        }

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        LoginForm form = new LoginForm();

        User user = form.connect( request );

        request.setAttribute( ATT_CLIENT, user );
        request.setAttribute( ATT_FORM, form );

        HttpSession session = request.getSession();

        if ( form.getErreurs().isEmpty() ) {
            // definition de la session & redirection
            session.setAttribute( ATT_SESSION_USER, user );

            // champ memoire
            String memoire = request.getParameter( CHAMP_MEMOIRE );

            if ( memoire != null ) {
                DateTime dt = new DateTime();
                DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
                String date = dt.toString( formatter );

                // définition du cookie
                setCookie( response, COOKIE_DERNIERE_CONNEXION, date, COOKIE_MAX_AGE );
            } else {
                // destruction du cookie
                setCookie( response, COOKIE_DERNIERE_CONNEXION, "", 0 );
            }

            response.sendRedirect( request.getContextPath() + MENU );
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
            doGet( request, response );
        }

    }

    private static String getCookieValue( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }
}
