package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import application.modifInformComp;
import data.ComponenteBean;
import data.ComponenteModelDM;

class modifInformCompTest extends Mockito{

	private modifInformComp servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new modifInformComp();
	}
	

	@Test
	public void ComponenteModificata() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("id")).thenReturn("1");
		when(request.getParameter("nome")).thenReturn("NVIDIA 3070");
		when(request.getParameter("prezzo")).thenReturn("999.99");
		when(request.getParameter("descrizione")).thenReturn("Lorem ipsum Lorem Ipsum Lorem Ipsum");
		when(request.getParameter("immagine")).thenReturn("www.foto.com/test");
		when(request.getParameter("tipo")).thenReturn("GPU");
		when(request.getParameter("acquisto")).thenReturn("www.acquisto.com/test");
		when(request.getParameter("data")).thenReturn("2023-01-05");
		
		int id = 1;
		String nome ="NVIDIA 3070";
		Double prezzo =999.99;
		String descrizione ="Lorem ipsum Lorem Ipsum Lorem Ipsum";
		String immagine ="www.foto.com/test";
		String tipo ="GPU";
		String acquisto ="www.acquisto.com/test";
		String data ="2023-01-05";
					
		ComponenteModelDM compDM = mock(ComponenteModelDM.class); 
		ComponenteBean comp = creaComponente(id,nome,prezzo,descrizione,immagine,tipo,data,acquisto);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/gamepage.jsp?id=" + comp.getIDComponente())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(compDM.doUpdate(comp)).thenReturn(1);
		servlet.setDAOComp(compDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("nome");
		verify(request).getParameter("prezzo");
		verify(request).getParameter("descrizione");
		verify(request).getParameter("immagine");
		verify(request).getParameter("tipo");
		verify(request).getParameter("acquisto");
		verify(request).getParameter("data");
		
	}
	
	@Test
	public void ComponenteNonModificata() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("id")).thenReturn("1");
		when(request.getParameter("nome")).thenReturn("NVIDIA 3070");
		when(request.getParameter("prezzo")).thenReturn("999.99");
		when(request.getParameter("descrizione")).thenReturn("Lorem ipsum Lorem Ipsum Lorem Ipsum");
		when(request.getParameter("immagine")).thenReturn("www.foto.com/test");
		when(request.getParameter("tipo")).thenReturn("GPU");
		when(request.getParameter("acquisto")).thenReturn("www.acquisto.com/test");
		when(request.getParameter("data")).thenReturn("2023-01-05");
		
		
			
		int id = 1;
		String nome ="NVIDIA 3070";
		Double prezzo =999.99;
		String descrizione ="Lorem ipsum Lorem Ipsum Lorem Ipsum";
		String immagine ="www.foto.com/test";
		String tipo ="GPU";
		String acquisto ="www.acquisto.com/test";
		String data ="2023-01-05";
		
				
		ComponenteModelDM compDM = mock(ComponenteModelDM.class); 
		ComponenteBean comp = creaComponente(id,nome,prezzo,descrizione,immagine,tipo,data,acquisto);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/modifComponente.jsp?id=" + comp.getIDComponente())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(compDM.doSave(comp)).thenReturn(0);
		servlet.setDAOComp(compDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("nome");
		verify(request).getParameter("prezzo");
		verify(request).getParameter("descrizione");
		verify(request).getParameter("immagine");
		verify(request).getParameter("tipo");
		verify(request).getParameter("acquisto");
		verify(request).getParameter("data");
		verify(request).setAttribute("errMessage",0);
	}
	
	
	public ComponenteBean creaComponente(int id,String nome, Double prezzo, String descrizione, String immagine, String tipo, String data, String acquisto) {
		ComponenteBean comp = new ComponenteBean();
		
		comp.setIDComponente(id);
		comp.setNome(nome);
		comp.setPrezzo(prezzo);
		comp.setDescrizione(descrizione);
		comp.setImmagine(immagine);
		comp.setTipo(tipo);
		comp.setDataUscita(data);
		comp.setLinkAcquisto(acquisto);
		return comp;
	}
	
}