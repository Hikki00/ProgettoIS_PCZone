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

import application.Login;
import data.ComponenteModelDM;
import data.ComponenteModelDMTest;
import data.UserBean;
import data.UserModelDM;

class LoginIntegrationTest extends Mockito{
	private Login servlet;
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
		servlet= new Login();
		refreshDataSet("db/init/init.xml");
	}
	
	@Test
	public void Login_Errato() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		String email = "lore21@gmail.com";
		when(request.getParameter("email")).thenReturn(email);
		String password = "passerrata";
		when(request.getParameter("password")).thenReturn(password);
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/loginError.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		UserModelDM scanDAO = new UserModelDM(tester.getConnection().getConnection());
		servlet.setDAOUser(scanDAO);
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("msg_error", "Invalid username/password");
		
	}
	
	@Test
	public void Login_Giusto() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		String email = "lore21@gmail.com";
		when(request.getParameter("email")).thenReturn(email);
		String password = "79cea72184093a59d4ba48496846dc1d";
		when(request.getParameter("password")).thenReturn(password);
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/homepage.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		UserModelDM scanDAO = new UserModelDM(tester.getConnection().getConnection());
		servlet.setDAOUser(scanDAO);
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("msg_error", "Accesso utente corretto.");
		verify(session).setAttribute("userAccess", true);
		
	}
	
	@Test
	public void Login_Giusto_Gestore() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		String email = "gest@gmail.com";
		when(request.getParameter("email")).thenReturn(email);
		String password = "79cea72184093a59d4ba48496846dc1d";
		when(request.getParameter("password")).thenReturn(password);
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/sceltaLogin.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		UserModelDM scanDAO = new UserModelDM(tester.getConnection().getConnection());
		servlet.setDAOUser(scanDAO);
		
		
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("msg_error", "Accesso gestore corretto.");
		
		
	}
	
	
	public UserBean creautente(String nickname, String email, String password, int ISGestore) {
		UserBean user = new UserBean();

		user.setNickname(nickname);
		user.setEmail(email);
		user.setPassword(password);
		user.setIsGestore(ISGestore);
		return user;
	}
	
}