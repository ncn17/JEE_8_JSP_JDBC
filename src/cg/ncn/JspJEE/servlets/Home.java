package cg.ncn.JspJEE.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( "/menu" )
public class Home extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String VUE              = "/WEB-INF/jsp/home.jsp";

    public Home() {
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        doGet( request, response );
    }

}
