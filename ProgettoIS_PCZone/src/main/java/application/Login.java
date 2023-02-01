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

import data.ConnectToDB;
import data.InsiemeProdotti;
import data.UserBean;
import data.UserModelDM;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private UserModelDM userDAO = new UserModelDM(ConnectToDB.createDBConnection());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		{
			String email = request.getParameter("email");
			String password = request.getParameter("password");
		    String destPage = "/loginError.jsp";   
		    UserBean user = null;
		    
		    
		    try {   	
		           user = userDAO.doRetrieveByCond(email, password);
          
		        } catch (SQLException ex) {
		            try {
						user = userDAO.doRetrieveByCondForTesting(email, password);
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
				
            if (user.getNickname() != "") {
            	HttpSession session = request.getSession();
                session.setAttribute("user", user);
            	if(user.getIsGestore() == 0) {
            		request.setAttribute("msg_error", "Accesso utente corretto.");
	                session.setAttribute("userAccess", true);
	                InsiemeProdotti prod = new InsiemeProdotti();
	                request.getSession().setAttribute("carrello", prod);
	                destPage = "/homepage.jsp";
            	} else {		  
            		request.setAttribute("msg_error", "Accesso gestore corretto.");
            		destPage = "/sceltaLogin.jsp";
            	}
                
            } else {
                String message = "Invalid username/password";
                request.setAttribute("msg_error", message);
            }
             
            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
			
		}
	}
	
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	public void setDAOUser(UserModelDM user) {
		this.userDAO = user;
	}
	

}
