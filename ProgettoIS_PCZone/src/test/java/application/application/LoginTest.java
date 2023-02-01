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

import application.Login;
import data.UserBean;
import data.UserModelDM;

class LoginTest extends Mockito{

	private Login servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new Login();
	}
	
	@Test
	public void Login_Errato() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("email")).thenReturn("lore21@gmail.com");
		when(request.getParameter("password")).thenReturn("Lorenzo2145");
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/loginError.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		String nickname="Lore22";
		String email="lore21@gmail.com";
		String password="Lorenzo2145";

		
		
		UserModelDM userDM = mock(UserModelDM.class); 
		when(userDM.doRetrieveByCond(email, password)).thenReturn(creautente("","","", 0));
		servlet.setDAOUser(userDM);
		
		
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("email");
		verify(request).getParameter("password");
		verify(request).setAttribute("msg_error", "Invalid username/password");
		
	}
	
	@Test
	public void Login_Giusto() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("email")).thenReturn("lore21@gmail.com");
		when(request.getParameter("password")).thenReturn("Lorenzo2145");
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/homepage.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		String nickname="Lore22";
		String email="lore21@gmail.com";
		String password="Lorenzo2145";

		
		UserBean user = creautente(nickname,email,password, 0);
		UserModelDM userDM = mock(UserModelDM.class); 
		when(userDM.doRetrieveByCond(email, password)).thenReturn(user);
		servlet.setDAOUser(userDM);
		
		
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("email");
		verify(request).getParameter("password");
		verify(session).setAttribute("user", user);
		verify(request).setAttribute("msg_error", "Accesso utente corretto.");
		verify(session).setAttribute("userAccess", true);
		
	}
	
	@Test
	public void Login_Giusto_Gestore() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("email")).thenReturn("lore21@gmail.com");
		when(request.getParameter("password")).thenReturn("Lorenzo2145");
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/sceltaLogin.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		
		String nickname="Lore22";
		String email="lore21@gmail.com";
		String password="Lorenzo2145";

		
		UserBean user = creautente(nickname,email,password, 1);
		UserModelDM userDM = mock(UserModelDM.class); 
		when(userDM.doRetrieveByCond(email, password)).thenReturn(user);
		servlet.setDAOUser(userDM);
		
		
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("email");
		verify(request).getParameter("password");
		verify(session).setAttribute("user", user);
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