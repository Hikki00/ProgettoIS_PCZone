package application;



import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import application.regGuida;
import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ComponenteModelDMTest;
import data.GuidaBean;
import data.GuidaModelDM;

class regGuidaIntegrationTest extends Mockito{
	private static IDatabaseTester tester;
	private regGuida servlet;
	
	@BeforeAll
    static void setUpAll() throws ClassNotFoundException {
        tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/schema.sql'",
                "sa",
                ""
        );
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
    }

    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(ComponenteModelDMTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }
    
    @BeforeEach
	void setUp() throws Exception {
		servlet= new regGuida();
		refreshDataSet("db/init/initGuida.xml");
	}
	

	@Test
	public void RegGuida_Giusto() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("Best Guide PC Build 2022");
		when(request.getParameter("descrizione")).thenReturn("TestTestTestTestTestTestTest");
		when(request.getParameter("CPU")).thenReturn("1");
		when(request.getParameter("GPU")).thenReturn("2");
		when(request.getParameter("Storage")).thenReturn("3");
		when(request.getParameter("RAM")).thenReturn("4");
		when(request.getParameter("MOBO")).thenReturn("5");
		when(request.getParameter("Case")).thenReturn("6");
		when(request.getParameter("PSU")).thenReturn("7");
			
		int id = 4;
		String nome ="Best Guide PC Build 2022";
		String descrizione ="TestTestTestTestTestTestTest";
					
		GuidaBean guidabean = creaGuida(id,nome,descrizione);
					
		GuidaModelDM compDM = new GuidaModelDM(tester.getConnection().getConnection());
		
		servlet.setDAOGuid(compDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/guidaSelezionata.jsp?id=" + guidabean.getIDGuida())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", "guida inserita");
		
	}

	@Test
	public void RegGuida_Errato() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn(null);
		when(request.getParameter("descrizione")).thenReturn("TestTestTestTestTestTestTest");
		when(request.getParameter("CPU")).thenReturn("1");
		when(request.getParameter("GPU")).thenReturn("2");
		when(request.getParameter("Storage")).thenReturn("3");
		when(request.getParameter("RAM")).thenReturn("4");
		when(request.getParameter("MOBO")).thenReturn("5");
		when(request.getParameter("Case")).thenReturn("6");
		when(request.getParameter("PSU")).thenReturn("7");
			
		int id = -1;
		String nome =null;
		String descrizione ="TestTestTestTestTestTestTest";
					
		GuidaBean guidabean = creaGuida(id,nome,descrizione);
					
		GuidaModelDM compDM = new GuidaModelDM(tester.getConnection().getConnection());
		
		servlet.setDAOGuid(compDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/inserisciGuida.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", -1);;
		
		

	}
	
	
	public GuidaBean creaGuida(int id,String nome,String descrizione) {
		GuidaBean guidabean = new GuidaBean();
		
		guidabean.setIDGuida(id);
		guidabean.setTitolo(nome);
		guidabean.setArticolo(descrizione);

		return guidabean;
	}
	
}