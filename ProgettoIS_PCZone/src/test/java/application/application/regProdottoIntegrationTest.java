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

import application.regProdotto;
import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ComponenteModelDMTest;

class regProdottoIntegrationTest extends Mockito{
	private static IDatabaseTester tester;
	private regProdotto servlet;
	
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
		servlet= new regProdotto();
		refreshDataSet("db/init/init.xml");
	}
	

	@Test
	public void RegistrazioneGuidaCorretta() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("NVIDIA 3070");
		when(request.getParameter("prezzo")).thenReturn("999.99");
		when(request.getParameter("descrizione")).thenReturn("Lorem ipsum Lorem Ipsum Lorem Ipsum");
		when(request.getParameter("immagine")).thenReturn("www.foto.com/test");
		when(request.getParameter("tipo")).thenReturn("GPU");
		when(request.getParameter("acquisto")).thenReturn("www.acquisto.com/test");
		when(request.getParameter("data")).thenReturn("2023-01-05");
			
		String nome ="NVIDIA 3070";
		Double prezzo =999.99;
		String descrizione ="Lorem ipsum Lorem Ipsum Lorem Ipsum";
		String immagine ="www.foto.com/test";
		String tipo ="GPU";
		String acquisto ="www.acquisto.com/test";
		String data ="2023-01-05";
					
		ComponenteModelDM compDM = new ComponenteModelDM(tester.getConnection().getConnection());
		
		ComponenteBean comp = creaComponente(4,nome,prezzo,descrizione,immagine,tipo,data,acquisto);
		servlet.setDAOComp(compDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/gamepage.jsp?id=" + comp.getIDComponente())).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", "inserito correttamente");
		
	}
	
	@Test
	public void RegistrazioneGuidaErrata() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("nome")).thenReturn("NVIDIA 3070");
		when(request.getParameter("prezzo")).thenReturn("999.99");
		when(request.getParameter("descrizione")).thenReturn("Lorem ipsum Lorem Ipsum Lorem Ipsum");
		when(request.getParameter("immagine")).thenReturn("www.foto.com/test");
		when(request.getParameter("tipo")).thenReturn("GPU");
		when(request.getParameter("acquisto")).thenReturn("www.acquisto.com/test");
		when(request.getParameter("data")).thenReturn(null);
			
		String nome ="NVIDIA 3070";
		Double prezzo =999.99;
		String descrizione ="Lorem ipsum Lorem Ipsum Lorem Ipsum";
		String immagine ="www.foto.com/test";
		String tipo ="GPU";
		String acquisto ="www.acquisto.com/test";
		String data =null;
					
		ComponenteModelDM compDM = new ComponenteModelDM(tester.getConnection().getConnection());
		
		ComponenteBean comp = creaComponente(3,nome,prezzo,descrizione,immagine,tipo,data,acquisto);
		servlet.setDAOComp(compDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/regProdotto.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		servlet.doPost(request, response);
		
		verify(request).setAttribute("errMessage", 0);
		verify(dispatcher).forward(request,response);
		
		

	}
	
	
	public ComponenteBean creaComponente(int ID, String nome, Double prezzo, String descrizione, String immagine, String tipo, String data, String acquisto) {
		ComponenteBean comp = new ComponenteBean();

		comp.setIDComponente(ID);
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