package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ConnectToDB;
import data.UserModelDM;


@WebServlet("/regProdotto")
public class regProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComponenteModelDM regProduct = new ComponenteModelDM(ConnectToDB.createDBConnection());
	
    public regProdotto() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 
        String nome = request.getParameter("nome");
        Double prezzo = Double.parseDouble(request.getParameter("prezzo")); 
        String descrizione = request.getParameter("descrizione");
        String immagine = request.getParameter("immagine");    
        String tipo = request.getParameter("tipo");  
        String data = request.getParameter("data");
        String acquisto = request.getParameter("acquisto");
        
        
        ComponenteBean productbean = new ComponenteBean();        
       
        productbean.setNome(nome);
        productbean.setPrezzo(prezzo);
        productbean.setDescrizione(descrizione);
        productbean.setImmagine(immagine);
        productbean.setTipo(tipo);
        productbean.setDataUscita(data);
        productbean.setLinkAcquisto(acquisto);
        
		try {
			
               
        	int productReg = 0;
			
        	try {
        		productReg = regProduct.doSave(productbean);
        	} catch (SQLException e) {
    			productReg = 0;
    		}
			productbean = regProduct.doRetrieveByName(nome);
	        if(productReg != 0)   
	        {
	        	request.setAttribute("errMessage", "inserito correttamente");
	        	System.out.println("gamepage.jsp?id=" + productbean.getIDComponente());
	            request.getRequestDispatcher("/gamepage.jsp?id=" + productbean.getIDComponente()).forward(request, response);
	        }
	        else   
	        {

        	   request.setAttribute("errMessage", productReg);
	           request.getRequestDispatcher("/regProdotto.jsp").forward(request, response);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void setDAOComp(ComponenteModelDM user) {
		this.regProduct = user;
	}
	

}


