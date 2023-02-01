package application;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import application.effettuaOrdine;
import data.ComponenteBean;
import data.ConfigurazioneBean;
import data.ConfigurazioneModelDM;
import data.GuidaBean;
import data.GuidaModelDM;
import data.InclusoBean;
import data.InclusoModelDM;
import data.InsiemeProdotti;
import data.UserBean;
import data.WishlistBean;
import data.WishlistModelDM;

class effettuaOrdineTest extends Mockito{

	private effettuaOrdine servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new effettuaOrdine();
	}
	

	@Test
	public void EffettuaOrdine_Giusto() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		
		when(request.getParameter("CPU")).thenReturn("1");
		when(request.getParameter("GPU")).thenReturn("3");
		when(request.getParameter("Storage")).thenReturn("5");
		when(request.getParameter("RAM")).thenReturn("6");
		when(request.getParameter("MOBO")).thenReturn("7");
		when(request.getParameter("Case")).thenReturn("8");
		when(request.getParameter("PSU")).thenReturn("2");
		
		int cpuid =1;
		int gpuid =3;
		int storageid =5;
		int ramid =6;
		int moboid =7;
		int caseid =8;
		int psuid =2;
					
		WishlistModelDM wishDM = mock(WishlistModelDM.class); 
		ConfigurazioneModelDM confDM = mock(ConfigurazioneModelDM.class); 
		InclusoModelDM inclusoDM = mock(InclusoModelDM.class); 
		UserBean user = new UserBean();
		user.setNickname("prova");
		
		WishlistBean wish = new WishlistBean();
		wish.setNickname("prova");
		wish.setIDWishlist(1);
		
		when(wishDM.doRetrieveByKey(user.getNickname())).thenReturn(wish);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("ordineEffettuato.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");

		when(session.getAttribute("user")).thenReturn(user);
		InsiemeProdotti listaProdotti = new InsiemeProdotti();
		when(session.getAttribute("carrello")).thenReturn(listaProdotti);
		
		
		
		
        ConfigurazioneBean confbean = new ConfigurazioneBean();        
        confbean.setDataCompletamento(new Timestamp(new Date().getTime()));	
		confbean.setNickname("prova");

		int confID = 1;
		when(confDM.doSave(confbean)).thenReturn(confID);
		when(inclusoDM.doSave(new InclusoBean(confID, cpuid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, gpuid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, storageid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, ramid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, moboid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, caseid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, psuid))).thenReturn(1);
		
		
		servlet.setDAOs(wishDM, confDM, inclusoDM);
		servlet.doPost(request, response);

		verify(request).setAttribute("errMessage","ordine salvato");
		verify(request).getParameter("CPU");
		verify(request).getParameter("GPU");
		verify(request).getParameter("Storage");
		verify(request).getParameter("RAM");
		verify(request).getParameter("MOBO");
		verify(request).getParameter("Case");
		verify(request).getParameter("PSU");
		
	}
	
	@Test
	public void EffettuaOrdine_Errato() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		
		when(request.getParameter("CPU")).thenReturn("1");
		when(request.getParameter("GPU")).thenReturn("3");
		when(request.getParameter("Storage")).thenReturn("5");
		when(request.getParameter("RAM")).thenReturn("6");
		when(request.getParameter("MOBO")).thenReturn("7");
		when(request.getParameter("Case")).thenReturn("8");
		when(request.getParameter("PSU")).thenReturn("2");
		
		int cpuid =1;
		int gpuid =3;
		int storageid =5;
		int ramid =6;
		int moboid =7;
		int caseid =8;
		int psuid =2;
					
		WishlistModelDM wishDM = mock(WishlistModelDM.class); 
		ConfigurazioneModelDM confDM = mock(ConfigurazioneModelDM.class); 
		InclusoModelDM inclusoDM = mock(InclusoModelDM.class); 
		UserBean user = new UserBean();
		user.setNickname("prova");
		
		WishlistBean wish = new WishlistBean();
		wish.setNickname("prova");
		wish.setIDWishlist(1);
		
		when(wishDM.doRetrieveByKey(user.getNickname())).thenReturn(wish);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/effettuaOrdine.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");

		when(session.getAttribute("user")).thenReturn(user);
		InsiemeProdotti listaProdotti = new InsiemeProdotti();
		when(session.getAttribute("carrello")).thenReturn(listaProdotti);
		
		
		
		
        ConfigurazioneBean confbean = new ConfigurazioneBean();        
        confbean.setDataCompletamento(new Timestamp(new Date().getTime()));	
		confbean.setNickname("prova");

		int confID = -1;
		when(confDM.doSave(confbean)).thenReturn(confID);
		when(inclusoDM.doSave(new InclusoBean(confID, cpuid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, gpuid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, storageid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, ramid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, moboid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, caseid))).thenReturn(1);
		when(inclusoDM.doSave(new InclusoBean(confID, psuid))).thenReturn(1);
		
		
		servlet.setDAOs(wishDM, confDM, inclusoDM);
		servlet.doPost(request, response);

		verify(request).setAttribute("errMessage","Impossibile salvare ordine");
		verify(request).getParameter("CPU");
		verify(request).getParameter("GPU");
		verify(request).getParameter("Storage");
		verify(request).getParameter("RAM");
		verify(request).getParameter("MOBO");
		verify(request).getParameter("Case");
		verify(request).getParameter("PSU");
	}
	
	
	public GuidaBean creaGuida(int id,String nome,String descrizione) {
		GuidaBean guidabean = new GuidaBean();
		
		guidabean.setIDGuida(id);
		guidabean.setTitolo(nome);
		guidabean.setArticolo(descrizione);

		return guidabean;
	}
	
}