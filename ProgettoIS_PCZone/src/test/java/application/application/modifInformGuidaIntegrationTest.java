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

import application.modifInformGuida;
import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ComponenteModelDMTest;
import data.GuidaBean;
import data.GuidaModelDM;

class modifInformGuidaIntegrationTest extends Mockito{
	private static IDatabaseTester tester;
	private modifInformGuida servlet;
	
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
		servlet= new modifInformGuida();
		refreshDataSet("db/init/initGuida.xml");
	}
	

	@Test
	public void ModifGuida_Giusto() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("Best Guide PC Build 2040");
		when(request.getParameter("descrizione")).thenReturn("TestaaaaaaTestTestTestTestTestTest");
		when(request.getParameter("cpu")).thenReturn("1");
		when(request.getParameter("gpu")).thenReturn("2");
		when(request.getParameter("storage")).thenReturn("3");
		when(request.getParameter("ram")).thenReturn("4");
		when(request.getParameter("mobo")).thenReturn("5");
		when(request.getParameter("case")).thenReturn("6");
		when(request.getParameter("psu")).thenReturn("7");
		when(request.getParameter("id")).thenReturn("1");
			
		int id = 1;
		String nome ="Best Guide PC Build 2040";
		String descrizione ="TestaaaaaaTestTestTestTestTestTest";
					
		GuidaBean guidabean = creaGuida(id,nome,descrizione);
					
		GuidaModelDM compDM = new GuidaModelDM(tester.getConnection().getConnection());
		
		servlet.setDAOGuid(compDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/guidaSelezionata.jsp?id=" + guidabean.getIDGuida())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", "guida modificata");
		
	}

	@Test
	public void ModifGuida_Errato() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("Best Guide PC Build 2040");
		when(request.getParameter("descrizione")).thenReturn("TestaaaaaaTestTestTestTestTestTest");
		when(request.getParameter("cpu")).thenReturn("1");
		when(request.getParameter("gpu")).thenReturn("2");
		when(request.getParameter("storage")).thenReturn("3");
		when(request.getParameter("ram")).thenReturn("4");
		when(request.getParameter("mobo")).thenReturn("5");
		when(request.getParameter("case")).thenReturn("6");
		when(request.getParameter("psu")).thenReturn("7");
		when(request.getParameter("id")).thenReturn("1");
			
		int id = 1;
		String nome ="Best Guide PC Build 2040";
		String descrizione ="TestaaaaaaTestTestTestTestTestTest";
					
		GuidaBean guidabean = creaGuida(id,nome,descrizione);
					
		GuidaModelDM compDM = new GuidaModelDM(tester.getConnection().getConnection());
		
		servlet.setDAOGuid(compDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/guidaSelezionata.jsp?id=" + guidabean.getIDGuida())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", "guida modificata");
		
		

	}
	
	
	public GuidaBean creaGuida(int id,String nome,String descrizione) {
		GuidaBean guidabean = new GuidaBean();
		
		guidabean.setIDGuida(id);
		guidabean.setTitolo(nome);
		guidabean.setArticolo(descrizione);

		return guidabean;
	}
	
}