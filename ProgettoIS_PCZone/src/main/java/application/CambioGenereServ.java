package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;

import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ConnectToDB;

/**
 * Servlet implementation class CambioGenereServ
 */
@WebServlet("/CambioGenere")
public class CambioGenereServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CambioGenereServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ComponenteModelDM prodottoDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
			ArrayList<ComponenteBean> prodotti;
			prodotti = prodottoDAO.doRetrieveByCond(request.getParameter("genereScelto"));
			
			response.setContentType("application/json");
			new Gson().toJson(prodotti, response.getWriter());		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
