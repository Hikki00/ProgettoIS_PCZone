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
import data.GuidaBean;
import data.GuidaModelDM;


@WebServlet("/regGuida")
public class regGuida extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GuidaModelDM regProduct = new GuidaModelDM(ConnectToDB.createDBConnection());
	
    public regGuida() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");   
        int cpuid = Integer.parseInt(request.getParameter("CPU"));
        int gpuid = Integer.parseInt(request.getParameter("GPU"));
        int storageid = Integer.parseInt(request.getParameter("Storage"));
        int ramid = Integer.parseInt(request.getParameter("RAM"));
        int moboid = Integer.parseInt(request.getParameter("MOBO"));
        int caseid = Integer.parseInt(request.getParameter("Case"));
        int psuid = Integer.parseInt(request.getParameter("PSU"));
        GuidaBean guidabean = new GuidaBean();        
       
        guidabean.setTitolo(nome);
        guidabean.setArticolo(descrizione);
         
       int productReg = -1;
       int compReg = 0;
       
		try {
			productReg = regProduct.doSave(guidabean);
			guidabean = regProduct.doRetrieveByTitolo(nome);
			compReg += regProduct.doSaveSuggerisce(guidabean, cpuid);
			compReg += regProduct.doSaveSuggerisce(guidabean, gpuid);
			compReg += regProduct.doSaveSuggerisce(guidabean, storageid);
			compReg += regProduct.doSaveSuggerisce(guidabean, ramid);
			compReg += regProduct.doSaveSuggerisce(guidabean, moboid);
			compReg += regProduct.doSaveSuggerisce(guidabean, caseid);
			compReg += regProduct.doSaveSuggerisce(guidabean, psuid);	
			System.out.println(compReg);
			System.out.println(productReg);
		} catch (SQLException e) {
			productReg = -1;
		}
		

		if(productReg != -1)   
		{
		   request.setAttribute("errMessage", "guida inserita");
		   request.getRequestDispatcher("/guidaSelezionata.jsp?id=" + guidabean.getIDGuida()).forward(request, response);
		}
		else   
		{
		   request.setAttribute("errMessage", productReg);
		   request.getRequestDispatcher("/inserisciGuida.jsp").forward(request, response);
		}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void setDAOGuid(GuidaModelDM guid) {
		this.regProduct = guid;
	}
	
}


