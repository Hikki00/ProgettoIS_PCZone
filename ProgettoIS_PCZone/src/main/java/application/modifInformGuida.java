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
import data.ConnectToDB;
import data.GuidaBean;
import data.GuidaModelDM;
import data.SuggerisciBean;
import data.UserBean;
import data.UserModelDM;

@WebServlet("/modifInformGuida")
public class modifInformGuida extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GuidaModelDM pDAO = new GuidaModelDM(ConnectToDB.createDBConnection());
    public modifInformGuida() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        int cpu = Integer.parseInt(request.getParameter("CPU"));
        int gpu = Integer.parseInt(request.getParameter("GPU"));
        int psu = Integer.parseInt(request.getParameter("PSU"));
        int cas = Integer.parseInt(request.getParameter("Case"));
        int storage = Integer.parseInt(request.getParameter("Storage"));
        int mobo = Integer.parseInt(request.getParameter("MOBO"));
        int ram = Integer.parseInt(request.getParameter("RAM"));
        
        int id = Integer.parseInt(request.getParameter("id"));
		
		GuidaBean guidaBean = new GuidaBean(); 
		
		guidaBean.setTitolo(nome);
		guidaBean.setArticolo(descrizione);
		
		int infoChanged;
		int compModif = 0;
		ArrayList<SuggerisciBean> listaComponenti = new ArrayList<SuggerisciBean>();
		
		try {
			guidaBean = pDAO.doRetrieveByKey(id);
			guidaBean.setTitolo(nome);
		    guidaBean.setArticolo(descrizione);
			infoChanged = pDAO.doUpdate(guidaBean);	
		 	listaComponenti = pDAO.doRetrieveSuggerisceByCond(id);
		 	compModif += pDAO.doUpdateSuggerisce(id, cpu, listaComponenti.get(0).getIDComponente());
			compModif += pDAO.doUpdateSuggerisce(id, gpu, listaComponenti.get(1).getIDComponente());
			compModif += pDAO.doUpdateSuggerisce(id, storage, listaComponenti.get(2).getIDComponente());
			compModif += pDAO.doUpdateSuggerisce(id, ram, listaComponenti.get(3).getIDComponente());
			compModif += pDAO.doUpdateSuggerisce(id, mobo, listaComponenti.get(4).getIDComponente());
			compModif += pDAO.doUpdateSuggerisce(id, cas, listaComponenti.get(5).getIDComponente());
			compModif += pDAO.doUpdateSuggerisce(id, psu, listaComponenti.get(6).getIDComponente());
		} catch (SQLException e) {
			infoChanged = 0;
		}
      
			

		if(infoChanged != 0)     //On success, you can display a message to user on Home page
		{    
		   request.setAttribute("errMessage", "guida modificata");
		   request.getRequestDispatcher("/guidaSelezionata.jsp?id=" + guidaBean.getIDGuida()).forward(request, response);
		}
		else   //On Failure, display a meaningful message to the User.
		{
			request.setAttribute("errMessage", infoChanged);
		   request.getRequestDispatcher("/modifGuida.jsp?id=" + guidaBean.getIDGuida()).forward(request, response);
			}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void setDAOGuid(GuidaModelDM guid) {
		this.pDAO = guid;
	}
	
}


