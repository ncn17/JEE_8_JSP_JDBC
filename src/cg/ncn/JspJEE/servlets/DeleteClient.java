package cg.ncn.JspJEE.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cg.ncn.JspJEE.beans.Client;
import cg.ncn.JspJEE.dao.DAOFactory;
import cg.ncn.JspJEE.outils.BoxOutils;
import cg.ncn.JspJEE.outils.Props;

/**
 * Servlet implementation class DeleteClient
 */
@WebServlet( "/deleteClient" )
public class DeleteClient extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String ATT_ID           = "id";
    private static final String SHOW             = "/listeClient";

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
        deleteClient( request, id );
        // redirect
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

    public void deleteClient( HttpServletRequest req, int id ) {
        // delete session
        DAOFactory.getClientDAO().delete( id );

        // erase image
        Client client = BoxOutils.getClient( req, id );

        File file = new File( Props.FILE_PATH + client.getImage() );
        if ( file.exists() ) {
            file.delete();
        }

        HttpSession session = req.getSession();

        @SuppressWarnings( "unchecked" )
        ArrayList<Client> clients = (ArrayList<Client>) session.getAttribute( Props.BDD_CLIENT );

        clients.remove( client );

        session.setAttribute( Props.BDD_CLIENT, clients );
    }

}
