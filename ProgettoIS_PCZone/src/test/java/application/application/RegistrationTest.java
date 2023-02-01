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

import application.Registration;
import data.UserBean;
import data.UserModelDM;

class RegistrationTest extends Mockito{

	private Registration servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new Registration();
	}
	

	@Test
	public void RegistrazioneCorretta() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("username")).thenReturn("lore21");
		when(request.getParameter("email")).thenReturn("lore21@gmail.com");
		when(request.getParameter("psw")).thenReturn("Test12345");
			
		String nome ="lore21";
		String email ="lore21@gmail.com";
		String psw ="Test12345";
					
		UserModelDM userDM = mock(UserModelDM.class); 
		UserBean user = creaUser(nome,psw,email);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/registrazioneCompleta.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(userDM.doSave(user)).thenReturn(1);
		servlet.setDAOUser(userDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("username");
		verify(request).getParameter("email");
		verify(request).getParameter("psw");
		
		
	}
	
	@Test
	public void RegistrazioneErrata() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("username")).thenReturn("gest1");
		when(request.getParameter("email")).thenReturn("gest@gmail.com");
		when(request.getParameter("psw")).thenReturn("Test12345");
			
		String nome ="gest1";
		String email ="gest@gmail.com";
		String psw ="Test12345";
					
		UserModelDM userDM = mock(UserModelDM.class); 
		UserBean user = creaUser(nome,psw,email);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/registrationError.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(userDM.doSave(user)).thenReturn(0);
		servlet.setDAOUser(userDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("username");
		verify(request).getParameter("email");
		verify(request).getParameter("psw");
		verify(request).setAttribute("errMessage",0);
	}
	
	
	public UserBean creaUser(String nome,String psw, String email) {
		UserBean user = new UserBean();

		user.setNickname(nome);
		user.setPassword(psw);
		user.setEmail(email);

		return user;
	}
	
}