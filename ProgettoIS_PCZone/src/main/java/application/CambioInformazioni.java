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

@WebServlet("/CambioInformazioni")
public class CambioInformazioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserModelDM userDAO = new UserModelDM(ConnectToDB.createDBConnection());
    public CambioInformazioni() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
        String email = request.getParameter("email");
        String descrizione = request.getParameter("descrizione");
        String foto = request.getParameter("foto");
        
        UserBean userbean = (UserBean) request.getSession().getAttribute("user");
        
        if(descrizione == "" || descrizione == null)
        	descrizione = userbean.getDescrizione();
        if(email == "" || email == null)
        	email = userbean.getEmail();
              
        userbean.setNickname(userbean.getNickname());
        userbean.setEmail(email);
        userbean.setFoto(foto);
        userbean.setDescrizione(descrizione);
        

        try {		
        	
            int infoChanged;
            
    		infoChanged = userDAO.doUpdate(userbean);	
    	 	System.out.println(infoChanged);
    		if(infoChanged != 0)   //On success, you can display a message to user on Home page
            {    
    		   request.setAttribute("errMessage", infoChanged);
               request.getRequestDispatcher("/profiloUtente.jsp").forward(request, response);
            }
            else   //On Failure, display a meaningful message to the User.
            {
               request.setAttribute("errMessage", infoChanged);
               request.getRequestDispatcher("/profiloUtente.jsp").forward(request, response);
            	}
    		
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void setDAOUser(UserModelDM user) {
		this.userDAO = user;
	}

}


