package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import application.modifInformGuida;
import data.GuidaBean;
import data.GuidaModelDM;
import data.SuggerisciBean;

class modifInformGuidaTest extends Mockito{

	private modifInformGuida servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new modifInformGuida();
	}
	

	@Test
	public void ModifGuida_Giusto() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("Best Guide PC Build 2022");
		when(request.getParameter("descrizione")).thenReturn("TestTestTestTestTestTestTest");
		when(request.getParameter("cpu")).thenReturn("1");
		when(request.getParameter("gpu")).thenReturn("3");
		when(request.getParameter("storage")).thenReturn("5");
		when(request.getParameter("ram")).thenReturn("6");
		when(request.getParameter("mobo")).thenReturn("7");
		when(request.getParameter("case")).thenReturn("8");
		when(request.getParameter("psu")).thenReturn("2");
		when(request.getParameter("id")).thenReturn("0");
		
		int id = 0;
		String nome ="Best Guide PC Build 2022";
		String descrizione ="TestTestTestTestTestTestTest";
		int cpu =1;
		int gpu =2;
		int storage =3;
		int ram =5;
		int mobo =6;
		int cas =7;
		int psu =8;
					
		GuidaModelDM guidaDM = mock(GuidaModelDM.class); 
		GuidaBean guid = creaGuida(nome,descrizione);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/guidaSelezionata.jsp?id=" + guid.getIDGuida())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		when(guidaDM.doRetrieveByKey(id)).thenReturn(guid);
		when(guidaDM.doUpdate(guid)).thenReturn(1);
		SuggerisciBean sug = new SuggerisciBean();
		sug.setIDComponente(1);
		sug.setIDGuida(1);
		
		ArrayList<SuggerisciBean> listaComponenti = new ArrayList<SuggerisciBean>();
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		
		when(guidaDM.doRetrieveSuggerisceByCond(id)).thenReturn(listaComponenti);
		when(guidaDM.doUpdateSuggerisce(id,cpu,listaComponenti.get(0).getIDComponente())).thenReturn(-1);
		when(guidaDM.doUpdateSuggerisce(id,gpu,listaComponenti.get(1).getIDComponente())).thenReturn(-1);
		when(guidaDM.doUpdateSuggerisce(id,storage,listaComponenti.get(2).getIDComponente())).thenReturn(-1);
		when(guidaDM.doUpdateSuggerisce(id,ram,listaComponenti.get(3).getIDComponente())).thenReturn(-1);
		when(guidaDM.doUpdateSuggerisce(id,mobo,listaComponenti.get(4).getIDComponente())).thenReturn(-1);
		when(guidaDM.doUpdateSuggerisce(id,cas,listaComponenti.get(5).getIDComponente())).thenReturn(-1);
		when(guidaDM.doUpdateSuggerisce(id,psu,listaComponenti.get(6).getIDComponente())).thenReturn(-1);
		servlet.setDAOGuid(guidaDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("nome");
		verify(request).getParameter("descrizione");
		verify(request).getParameter("cpu");
		verify(request).getParameter("gpu");
		verify(request).getParameter("storage");
		verify(request).getParameter("ram");
		verify(request).getParameter("mobo");
		verify(request).getParameter("case");
		verify(request).getParameter("psu");
		
	}
	
	@Test
	public void ModifGuida_Errato() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("Best Guide PC Build 2022");
		when(request.getParameter("descrizione")).thenReturn("TestTestTestTestTestTestTest");
		when(request.getParameter("cpu")).thenReturn("1");
		when(request.getParameter("gpu")).thenReturn("3");
		when(request.getParameter("storage")).thenReturn("5");
		when(request.getParameter("ram")).thenReturn("6");
		when(request.getParameter("mobo")).thenReturn("7");
		when(request.getParameter("case")).thenReturn("8");
		when(request.getParameter("psu")).thenReturn("2");
		when(request.getParameter("id")).thenReturn("0");
		
		int id = 0;
		String nome ="Best Guide PC Build 2022";
		String descrizione ="TestTestTestTestTestTestTest";
		int cpu =1;
		int gpu =2;
		int storage =3;
		int ram =5;
		int mobo =6;
		int cas =7;
		int psu =8;
					
		GuidaModelDM guidaDM = mock(GuidaModelDM.class); 
		GuidaBean guid = creaGuida(nome,descrizione);
		GuidaBean guid2 = creaGuida("","");
		guid2.setIDGuida(0);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/modifGuida.jsp?id=" + guid2.getIDGuida())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		when(guidaDM.doRetrieveByKey(id)).thenReturn(guid2);
		when(guidaDM.doUpdate(guid)).thenReturn(0);
		SuggerisciBean sug = new SuggerisciBean();
		sug.setIDComponente(1);
		sug.setIDGuida(1);
		
		ArrayList<SuggerisciBean> listaComponenti = new ArrayList<SuggerisciBean>();
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		listaComponenti.add(sug);
		
		when(guidaDM.doRetrieveSuggerisceByCond(id)).thenReturn(listaComponenti);
		when(guidaDM.doUpdateSuggerisce(id,cpu,listaComponenti.get(0).getIDComponente())).thenReturn(0);
		when(guidaDM.doUpdateSuggerisce(id,gpu,listaComponenti.get(1).getIDComponente())).thenReturn(0);
		when(guidaDM.doUpdateSuggerisce(id,storage,listaComponenti.get(2).getIDComponente())).thenReturn(0);
		when(guidaDM.doUpdateSuggerisce(id,ram,listaComponenti.get(3).getIDComponente())).thenReturn(0);
		when(guidaDM.doUpdateSuggerisce(id,mobo,listaComponenti.get(4).getIDComponente())).thenReturn(0);
		when(guidaDM.doUpdateSuggerisce(id,cas,listaComponenti.get(5).getIDComponente())).thenReturn(0);
		when(guidaDM.doUpdateSuggerisce(id,psu,listaComponenti.get(6).getIDComponente())).thenReturn(0);
		servlet.setDAOGuid(guidaDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("nome");
		verify(request).getParameter("descrizione");
		verify(request).getParameter("cpu");
		verify(request).getParameter("gpu");
		verify(request).getParameter("storage");
		verify(request).getParameter("ram");
		verify(request).getParameter("mobo");
		verify(request).getParameter("case");
		verify(request).getParameter("psu");
		verify(request).setAttribute("errMessage",0);
	}
	
	
	public GuidaBean creaGuida(String nome,String descrizione) {
		GuidaBean guid = new GuidaBean();

		guid.setTitolo(nome);
		guid.setArticolo(descrizione);

		return guid;
	}
	
}