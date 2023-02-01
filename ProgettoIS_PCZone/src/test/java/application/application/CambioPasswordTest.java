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

import application.CambioPassword;
import data.UserBean;
import data.UserModelDM;

class CambioPasswordTest extends Mockito{

	private CambioPassword servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new CambioPassword();
	}
	

	@Test
	public void CambioPass_Giusto() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("psw2")).thenReturn("Test12345");
			
		String psw ="Test12345";
		String nome="Lore2143";
		String email = "lore21@gmail.com";
				
		UserModelDM userDM = mock(UserModelDM.class); 
		UserBean user = creaUser(nome,psw,email);
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);
		when(request.getRequestDispatcher("/homepage.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(userDM.doUpdatePass(user)).thenReturn(1);
		servlet.setDAOUser(userDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("psw2");
		
		
	}
	
	@Test
	public void CambioPass_Errato() throws ServletException, IOException, SQLException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("psw2")).thenReturn("Test12345");
			
		String psw ="Test12345";
		String nome="Lore2143";
		String email = "lore21@gmail.com";
				
		UserModelDM userDM = mock(UserModelDM.class); 
		UserBean user = creaUser(nome,psw,email);
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);
		when(request.getRequestDispatcher("/profiloUtente.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(userDM.doUpdatePass(user)).thenReturn(0);
		servlet.setDAOUser(userDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("psw2");
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