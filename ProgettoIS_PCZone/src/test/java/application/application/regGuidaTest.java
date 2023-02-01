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

import application.regGuida;
import data.GuidaBean;
import data.GuidaModelDM;

class regGuidaTest extends Mockito{

	private regGuida servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new regGuida();
	}
	

	@Test
	public void RegGuida_Giusto() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("Best Guide PC Build 2022");
		when(request.getParameter("descrizione")).thenReturn("TestTestTestTestTestTestTest");
		when(request.getParameter("CPU")).thenReturn("1");
		when(request.getParameter("GPU")).thenReturn("3");
		when(request.getParameter("Storage")).thenReturn("5");
		when(request.getParameter("RAM")).thenReturn("6");
		when(request.getParameter("MOBO")).thenReturn("7");
		when(request.getParameter("Case")).thenReturn("8");
		when(request.getParameter("PSU")).thenReturn("2");
			
		int id = 0;
		String nome ="Best Guide PC Build 2022";
		String descrizione ="TestTestTestTestTestTestTest";
		int cpuid =1;
		int gpuid =3;
		int storageid =5;
		int ramid =6;
		int moboid =7;
		int casid =8;
		int psuid =2;
					
		GuidaModelDM guidaDM = mock(GuidaModelDM.class); 
		GuidaBean guidabean = creaGuida(id,nome,descrizione);	
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/guidaSelezionata.jsp?id=" + guidabean.getIDGuida())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(guidaDM.doSave(guidabean)).thenReturn(0);
		when(guidaDM.doRetrieveByTitolo(nome)).thenReturn(guidabean);
		when(guidaDM.doSaveSuggerisce(guidabean,cpuid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,gpuid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,storageid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,ramid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,moboid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,casid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,psuid)).thenReturn(-1);
		servlet.setDAOGuid(guidaDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("nome");
		verify(request).getParameter("descrizione");
		verify(request).getParameter("CPU");
		verify(request).getParameter("GPU");
		verify(request).getParameter("Storage");
		verify(request).getParameter("RAM");
		verify(request).getParameter("MOBO");
		verify(request).getParameter("Case");
		verify(request).getParameter("PSU");
		
	}
	
	@Test
	public void RegGuida_Errato() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("Best Guide PC Build 2022");
		when(request.getParameter("descrizione")).thenReturn("TestTestTestTestTestTestTest");
		when(request.getParameter("CPU")).thenReturn("1");
		when(request.getParameter("GPU")).thenReturn("3");
		when(request.getParameter("Storage")).thenReturn("5");
		when(request.getParameter("RAM")).thenReturn("6");
		when(request.getParameter("MOBO")).thenReturn("7");
		when(request.getParameter("Case")).thenReturn("8");
		when(request.getParameter("PSU")).thenReturn("2");
			
		String nome ="Best Guide PC Build 2022";
		String descrizione ="TestTestTestTestTestTestTest";
		int cpuid =1;
		int gpuid =2;
		int storageid =3;
		int ramid =5;
		int moboid =6;
		int casid =7;
		int psuid =8;
					
		GuidaModelDM guidaDM = mock(GuidaModelDM.class); 
		GuidaBean guidabean = creaGuida(-1,nome,descrizione);
		
		
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/inserisciGuida.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(guidaDM.doSave(guidabean)).thenReturn(-1);
		when(guidaDM.doRetrieveByTitolo(nome)).thenReturn(null);
		when(guidaDM.doSaveSuggerisce(guidabean,cpuid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,gpuid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,storageid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,ramid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,moboid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,ramid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,casid)).thenReturn(-1);
		when(guidaDM.doSaveSuggerisce(guidabean,psuid)).thenReturn(-1);
		servlet.setDAOGuid(guidaDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("nome");
		verify(request).getParameter("descrizione");
		verify(request).getParameter("CPU");
		verify(request).getParameter("GPU");
		verify(request).getParameter("Storage");
		verify(request).getParameter("RAM");
		verify(request).getParameter("MOBO");
		verify(request).getParameter("Case");
		verify(request).getParameter("PSU");
		verify(request).setAttribute("errMessage",-1);
	}
	
	
	public GuidaBean creaGuida(int id,String nome,String descrizione) {
		GuidaBean guidabean = new GuidaBean();
		
		guidabean.setIDGuida(id);
		guidabean.setTitolo(nome);
		guidabean.setArticolo(descrizione);

		return guidabean;
	}
	
}