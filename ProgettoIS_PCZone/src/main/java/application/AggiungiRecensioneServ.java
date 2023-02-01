package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;

import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ConnectToDB;
import data.ReviewBean;
import data.ReviewModelDM;

/**
 * Servlet implementation class CambioGenereServ
 */
@WebServlet("/AggiungiRecensione")
public class AggiungiRecensioneServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiRecensioneServ() {
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
			
			//aggiungere recensione
			ReviewModelDM recensioneDAO = new ReviewModelDM(ConnectToDB.createDBConnection());
			
			ReviewBean recensione = new ReviewBean();
			recensione.setIDComponente(Integer.parseInt(request.getParameter("id")));
			recensione.setTitolo(request.getParameter("titolo"));
			recensione.setTesto(request.getParameter("testo"));
			recensione.setNickname(request.getParameter("username"));
			recensione.setData(new Timestamp(new Date().getTime()));
			recensioneDAO.doSave(recensione);
			
			
			ArrayList<ReviewBean> recensioni = recensioneDAO.doRetrieveByCond(recensione.getIDComponente());
			

			response.setContentType("application/json");
			new Gson().toJson(recensioni, response.getWriter());		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
