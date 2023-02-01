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

import application.RegistrationAdmin;
import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ComponenteModelDMTest;
import data.UserBean;
import data.UserModelDM;

class RegistrationAdminIntegrationTest extends Mockito{
	private static IDatabaseTester tester;
	private RegistrationAdmin servlet;
	
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
		servlet= new RegistrationAdmin();
		refreshDataSet("db/init/init.xml");
	}
	

	@Test
	public void RegistrazioneCorretta() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("username")).thenReturn("gest1");
		when(request.getParameter("email")).thenReturn("gest@gmail.com");
		when(request.getParameter("psw")).thenReturn("Test12345");
			
		String nome ="lore21";
		String email ="lore212@gmail.com";
		String psw ="Test12345";
		int isGestore = 1;
		
		UserModelDM compDM = new UserModelDM(tester.getConnection().getConnection());
		
		UserBean user = creaUser(nome,psw,email,isGestore);
		servlet.setDAOUser(compDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/homepage.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		servlet.doPost(request, response);

		verify(request).setAttribute("errMessage", "admin registrato");
		verify(dispatcher).forward(request,response);
		
	}
	
	@Test
	public void RegistrazioneErrata() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("username")).thenReturn("Gest21");
		when(request.getParameter("email")).thenReturn("gest@gmail.com");
		when(request.getParameter("psw")).thenReturn("79cea72184093a59d4ba48496846dc1d");
			
		String nome ="Gest21";
		String email ="gest@gmail.com";
		String psw ="79cea72184093a59d4ba48496846dc1d";
					
		UserModelDM compDM = new UserModelDM(tester.getConnection().getConnection());
		
		UserBean user = creaUser(nome,psw,email,1);
		servlet.setDAOUser(compDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/registrationError.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		
		servlet.doPost(request, response);
		
		verify(request).setAttribute("errMessage", 0);
		verify(dispatcher).forward(request,response);
		
		

	}
	
	
	public UserBean creaUser(String nome,String psw, String email,int isGestore) {
		UserBean user = new UserBean();

		user.setNickname(nome);
		user.setPassword(psw);
		user.setEmail(email);
		user.setIsGestore(isGestore);

		return user;
	}
	
}