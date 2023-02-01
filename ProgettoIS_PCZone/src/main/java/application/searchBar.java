package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ConnectToDB;
import data.InsiemeProdotti;

@WebServlet("/searchBar")
public class searchBar extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		{
			
			String search = request.getParameter("searchTerm");  
			InsiemeProdotti prod = new InsiemeProdotti();
		    String destPage= "/risRicerca.jsp";     
		    
		        try {
		        	ComponenteModelDM prodottoDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
					ArrayList<ComponenteBean> prodotti = prodottoDAO.doRetrieveBySearchTerm(search);
					
					prod.setProdotti(prodotti);
					
		            if (prodotti.size() != 0) {
		            	HttpSession session = request.getSession();
		            	session.setAttribute("risRicerca", prod);
		            } else {
		                HttpSession session = request.getSession();
		                ArrayList<ComponenteBean> prodottiVuoto = new ArrayList<ComponenteBean>();
		                prod.setProdotti(prodottiVuoto);
		                session.setAttribute("risRicerca", prod);
		            }		             
		            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		            dispatcher.forward(request, response);
		            
		        } catch (SQLException ex) {
		            throw new ServletException(ex);
		        }
				
				
			
		}
	}
	
	private static final long serialVersionUID = 1L;

	public searchBar() {
		super();
	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

}
