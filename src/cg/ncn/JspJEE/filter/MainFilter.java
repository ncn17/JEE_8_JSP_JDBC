package cg.ncn.JspJEE.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cg.ncn.JspJEE.outils.BoxOutils;

@WebFilter( urlPatterns = "/*" )
public class MainFilter implements Filter {

    private static final String ATT_SESSION_USER = "sessionUser";
    private static final String CONNECT          = "/login";
    private static final String DOC_FREE         = "/public";

    public void destroy() {
        // TODO Auto-generated method stub
    }

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain )
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        /* Non-filtrage des ressources statiques */
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        if ( chemin.startsWith( DOC_FREE ) ) {
            chain.doFilter( request, response );
            return;
        }

        // get session
        HttpSession session = request.getSession();

        // verification session
        if ( session.getAttribute( ATT_SESSION_USER ) == null ) {
            /* self client and commande update */
            // clients zone
            BoxOutils.addClient( request );

            // commandes zone
            BoxOutils.addCommande( request );

            request.getRequestDispatcher( CONNECT ).forward( request, response );
        } else {
            chain.doFilter( request, response );
        }

    }

    public void init( FilterConfig fConfig ) throws ServletException {
        // TODO Auto-generated method stub
    }

}
