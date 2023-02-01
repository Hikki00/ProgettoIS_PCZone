package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ConnectToDB;
import data.GuidaBean;
import data.GuidaModelDM;
import data.SuggerisciBean;
import data.UserBean;
import data.UserModelDM;

@WebServlet("/modifInformComp")
public class modifInformComp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComponenteModelDM pDAO = new ComponenteModelDM(ConnectToDB.createDBConnection()); 
	
    public modifInformComp() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        Double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String immagine = request.getParameter("immagine");
        String tipo = request.getParameter("tipo");
        String dataUscita = request.getParameter("data");
        String acquisto = request.getParameter("acquisto");
        int id = Integer.parseInt(request.getParameter("id"));       
		  	
		ComponenteBean compBean = new ComponenteBean(); 
		
		compBean.setIDComponente(id);
		compBean.setNome(nome);
		compBean.setDescrizione(descrizione);
		compBean.setImmagine(immagine);
		compBean.setLinkAcquisto(acquisto);
		compBean.setPrezzo(prezzo);
		compBean.setTipo(tipo);
		compBean.setDataUscita(dataUscita);
		
		int infoChanged = 0;
		try {
			infoChanged = pDAO.doUpdate(compBean);	
		} catch (SQLException e) {
			infoChanged = 0;
		}
		
		if(infoChanged != 0)     //On success, you can display a message to user on Home page
		{    
		   request.setAttribute("errMessage", "informazioni modificate");
			
		   request.getRequestDispatcher("/gamepage.jsp?id=" + compBean.getIDComponente()).forward(request, response);
		}
		else   //On Failure, display a meaningful message to the User.
		{
		   request.setAttribute("errMessage", infoChanged);
		   request.getRequestDispatcher("/modifComponente.jsp?id=" + compBean.getIDComponente()).forward(request, response);
		}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void setDAOComp(ComponenteModelDM user) {
		this.pDAO = user;
	}

}


