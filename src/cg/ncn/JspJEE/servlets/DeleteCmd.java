package cg.ncn.JspJEE.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cg.ncn.JspJEE.beans.Commande;
import cg.ncn.JspJEE.dao.DAOFactory;
import cg.ncn.JspJEE.outils.BoxOutils;
import cg.ncn.JspJEE.outils.Props;

/**
 * Servlet implementation class DeleteCmd
 */
@WebServlet( "/deleteCmd" )
public class DeleteCmd extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String ATT_ID           = "id";
    private static final String SHOW             = "/listeCommande";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        // parse id
        String string_id = request.getParameter( ATT_ID );
        int id = BoxOutils.parseId( string_id );

        // delete client
        deleteCommande( request, id );
        // redirect page
        response.sendRedirect( request.getContextPath() + SHOW );
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet( request, response );
    }

    public void deleteCommande( HttpServletRequest req, int id ) {
        // delete session
        DAOFactory.getCommandeDAO().delete( id );

        // get cmd
        Commande commande = BoxOutils.getCommande( req, id );

        HttpSession session = req.getSession();

        @SuppressWarnings( "unchecked" )
        ArrayList<Commande> cmds = (ArrayList<Commande>) session.getAttribute( Props.BDD_COMMANDE );

        cmds.remove( commande );

        session.setAttribute( Props.BDD_COMMANDE, cmds );
    }

}
