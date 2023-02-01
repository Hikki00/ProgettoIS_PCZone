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

import application.CambioInformazioni;
import data.ComponenteModelDM;
import data.ComponenteModelDMTest;
import data.UserBean;
import data.UserModelDM;

class CambioInformazioniIntegrationTest extends Mockito{
	private CambioInformazioni servlet;
	private static IDatabaseTester tester;
	
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
		servlet= new CambioInformazioni();
		refreshDataSet("db/init/init.xml");
	}
	
	@Test
	public void CambioInfo_Errato() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		String email = "lore21123@gmail.com";
		when(request.getParameter("email")).thenReturn(email);
		String descrizione = "Lorem Ipsum Lorem Ipsum Lorem Ipsum";
		when(request.getParameter("descrizione")).thenReturn(descrizione);
		String foto = "www.image.test/img";
		when(request.getParameter("foto")).thenReturn(foto);
		
		String nick = "Lore213432";
		UserBean user = creaUser(nick,descrizione,email,foto);
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);
		when(request.getRequestDispatcher("/profiloUtente.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		UserModelDM scanDAO = new UserModelDM(tester.getConnection().getConnection());
		servlet.setDAOUser(scanDAO);
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", 0);
		
	}
	
	@Test
	public void CambioInfo_Giusto() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		String email = "lore21@gmail.com";
		when(request.getParameter("email")).thenReturn(email);
		String descrizione = "Lorem Ipsum Lorem Ipsum Lorem Ipsum";
		when(request.getParameter("descrizione")).thenReturn(descrizione);
		String foto = "www.image.test/img";
		when(request.getParameter("foto")).thenReturn(foto);
		
		String nick = "Lore2134";
		UserBean user = creaUser(nick,descrizione,email,foto);
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);
		when(request.getRequestDispatcher("/profiloUtente.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		UserModelDM scanDAO = new UserModelDM(tester.getConnection().getConnection());
		servlet.setDAOUser(scanDAO);
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", 1);
		
	}
	
	
	public UserBean creaUser(String nome,String descrizione, String email,String foto) {
		UserBean user = new UserBean();

		user.setNickname(nome);
		user.setDescrizione(descrizione);
		user.setFoto(foto);
		user.setEmail(email);

		return user;
	}
	
}