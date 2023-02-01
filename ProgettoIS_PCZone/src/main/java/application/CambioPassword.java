package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ConnectToDB;
import data.UserBean;
import data.UserModelDM;

@WebServlet("/CambioPassword")
public class CambioPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserModelDM userDAO = new UserModelDM(ConnectToDB.createDBConnection());
    public CambioPassword() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String psw = request.getParameter("psw2");
		
        UserBean userbean = (UserBean) request.getSession().getAttribute("user");
        userbean.setPassword(psw); 

		int passChanged = 0; 	
		try {
			passChanged = userDAO.doUpdatePass(userbean);	
		} catch (SQLException e) {
			try {
				passChanged = userDAO.doUpdatePassForTesting(userbean);
			} catch (SQLException x) {
				passChanged = 0;
			}
		}
		
		if(passChanged != 0)   //On success, manda a pagina di successo
		{    
			request.setAttribute("errMessage", passChanged);
		   request.getRequestDispatcher("/homepage.jsp").forward(request, response);
		}
		else   //On Failure, manda a pagina di errore
		{
		   request.setAttribute("errMessage", passChanged);
		   request.getRequestDispatcher("/profiloUtente.jsp").forward(request, response);
			}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void setDAOUser(UserModelDM user) {
		this.userDAO = user;
	}
}


