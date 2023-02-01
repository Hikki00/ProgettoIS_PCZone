package application;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ConfigurazioneBean;
import data.ConfigurazioneModelDM;
import data.ConnectToDB;
import data.ContieneBean;
import data.GuidaBean;
import data.GuidaModelDM;
import data.InclusoBean;
import data.InclusoModelDM;
import data.InsiemeProdotti;
import data.UserBean;
import data.WishlistBean;
import data.WishlistModelDM;


@WebServlet("/effettuaOrdine")
public class effettuaOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WishlistModelDM wishDAO = new WishlistModelDM(ConnectToDB.createDBConnection());
	ConfigurazioneModelDM confDM = new ConfigurazioneModelDM(ConnectToDB.createDBConnection());
	InclusoModelDM inclusoDM = new InclusoModelDM(ConnectToDB.createDBConnection());
	
    public effettuaOrdine() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			WishlistBean wishlist = null;
			wishlist = wishDAO.doRetrieveByKey(((UserBean) request.getSession().getAttribute("user")).getNickname());

		
			InsiemeProdotti listaProd = (InsiemeProdotti) request.getSession().getAttribute("carrello");
			ArrayList<ComponenteBean> listaProdotti = listaProd.getProdotti();
			for(ComponenteBean prod : listaProdotti){
				
				try {
					//vediamo se è presente nella wishlist
					ContieneBean contenuto = wishDAO.doRetrieveByKey(wishlist.getIDWishlist(), prod.getIDComponente());
					if(contenuto.getIDComponente() != -1) {
						wishDAO.doDelete(wishlist.getIDWishlist(), prod.getIDComponente());
				}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
	        int cpuid = Integer.parseInt(request.getParameter("CPU"));
	        int gpuid = Integer.parseInt(request.getParameter("GPU"));
	        int storageid = Integer.parseInt(request.getParameter("Storage"));
	        int ramid = Integer.parseInt(request.getParameter("RAM"));
	        int moboid = Integer.parseInt(request.getParameter("MOBO"));
	        int caseid = Integer.parseInt(request.getParameter("Case"));
	        int psuid = Integer.parseInt(request.getParameter("PSU"));
	        
	        ConfigurazioneBean confbean = new ConfigurazioneBean();        
	        confbean.setDataCompletamento(new Timestamp(new Date().getTime()));	
			confbean.setNickname(((UserBean) request.getSession().getAttribute("user")).getNickname());
		
			
        
        
			int confID;
			int compReg = 0;
       
		
			
			confID = confDM.doSave(confbean);
			if(confID == -1) {
				throw new SQLException();
			}
			
			compReg += inclusoDM.doSave(new InclusoBean(confID, cpuid));
			compReg += inclusoDM.doSave(new InclusoBean(confID, gpuid));
			compReg += inclusoDM.doSave(new InclusoBean(confID, storageid));
			compReg += inclusoDM.doSave(new InclusoBean(confID, ramid));
			compReg += inclusoDM.doSave(new InclusoBean(confID, moboid));
			compReg += inclusoDM.doSave(new InclusoBean(confID, caseid));
			compReg += inclusoDM.doSave(new InclusoBean(confID, psuid));	
			
			System.out.println(compReg);
			
	        
			ArrayList<ComponenteBean> listaVuota = new ArrayList<ComponenteBean>();
			((InsiemeProdotti) request.getSession().getAttribute("carrello")).setProdotti(listaVuota);
			
			

	           request.setAttribute("errMessage", "ordine salvato");
			RequestDispatcher dispatcher = request.getRequestDispatcher("ordineEffettuato.jsp");
		    dispatcher.forward(request, response);

		} catch (SQLException e) {
	           request.setAttribute("errMessage", "Impossibile salvare ordine");
	           request.getRequestDispatcher("/effettuaOrdine.jsp").forward(request, response);
		}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void setDAOs(WishlistModelDM wish, ConfigurazioneModelDM conf, InclusoModelDM incluso) {
		this.wishDAO = wish;
		this.confDM = conf;
		this.inclusoDM = incluso;
	}
}


