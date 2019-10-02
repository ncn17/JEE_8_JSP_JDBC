package cg.ncn.JspJEE.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet( "/logOut" )
public class LogOut extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String CONNECT          = "/login";

    public LogOut() {
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // deconnexion du user destruction de la session
        HttpSession session = request.getSession();

        session.invalidate();

        response.sendRedirect( request.getContextPath() + CONNECT );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        doGet( request, response );
    }

}
