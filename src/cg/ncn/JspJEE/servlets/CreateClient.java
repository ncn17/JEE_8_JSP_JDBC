package cg.ncn.JspJEE.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cg.ncn.JspJEE.beans.Client;
import cg.ncn.JspJEE.forms.InscriptionForm;

@WebServlet( "/createClient" )
@MultipartConfig( location = "/servers/uploads/tmp/", maxFileSize = 1048576, maxRequestSize = 5242880, fileSizeThreshold = 10485576 )
public class CreateClient extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String CREATE           = "/WEB-INF/jsp/createClient.jsp";
    private static final String SHOW             = "/listeClient";
    private static final String ATT_CLIENT       = "client";
    private static final String ATT_FORM         = "form";

    public CreateClient() {
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( CREATE ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        InscriptionForm form = new InscriptionForm();

        Client client = form.inscrireClient( request );

        request.setAttribute( ATT_CLIENT, client );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErreurs().isEmpty() ) {
            // redirect to vue page
            response.sendRedirect( request.getContextPath() + SHOW );
        } else {
            this.getServletContext().getRequestDispatcher( CREATE ).forward( request, response );
        }
    }

}