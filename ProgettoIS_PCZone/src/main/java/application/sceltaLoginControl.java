package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.InsiemeProdotti;
import data.UserBean;
import data.UserModelDM;

@WebServlet("/sceltaLoginControl")
public class sceltaLoginControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public sceltaLoginControl() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        //Copying all the input parameters in to local variables
        String value = request.getParameter("w");
        
        if(value.equals("1")) {
        	HttpSession session = request.getSession();
        	session.setAttribute("userAccess", true);
            InsiemeProdotti prod = new InsiemeProdotti();
            request.getSession().setAttribute("carrello", prod);
        } else {
        	HttpSession session = request.getSession();
        	session.setAttribute("AdminAccess", true);
        }
        
        String destPage = "/homepage.jsp";
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
        dispatcher.forward(request, response);
        

		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}


