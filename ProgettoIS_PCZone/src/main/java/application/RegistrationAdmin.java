package application;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ConnectToDB;
import data.GuidaModelDM;
import data.UserBean;
import data.UserModelDM;

@WebServlet("/RegistrationAdmin")
public class RegistrationAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserModelDM registerDao = new UserModelDM(ConnectToDB.createDBConnection());
	
    public RegistrationAdmin() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String psw = request.getParameter("psw");
        
        UserBean userbean = new UserBean();
        userbean.setNickname(username);
        userbean.setEmail(email);
        userbean.setPassword(psw); 
        userbean.setIsGestore(1);
		 
		int userRegistered = 0;

		try {   	
			userRegistered = registerDao.doSave(userbean);
     
		} catch (SQLException ex) {
		    try {
		    	userRegistered = registerDao.doSaveForTesting(userbean);
			} catch (SQLException e) {
				userRegistered = 0;
			}
		}
		
		if(userRegistered != 0)   //On success, you can display a message to user on Home page
		{
		   request.setAttribute("errMessage", "admin registrato");
		   request.getRequestDispatcher("/homepage.jsp").forward(request, response);
		}
		else   //On Failure, display a meaningful message to the User.
		{
		   request.setAttribute("errMessage", userRegistered);
		   request.getRequestDispatcher("/registrationError.jsp").forward(request, response);
		}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void setDAOUser(UserModelDM user) {
		this.registerDao = user;
	}
	

}


