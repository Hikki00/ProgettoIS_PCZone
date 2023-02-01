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

import application.sceltaLoginControl;
import data.UserBean;
import data.UserModelDM;

class sceltaLoginTest extends Mockito{

	private sceltaLoginControl servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new sceltaLoginControl();
	}
	
	@Test
	public void LoginUtente() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("w")).thenReturn("1");
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/homepage.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("w");
		verify(session).setAttribute("userAccess", true);
		
	}
	
	@Test
	public void LoginAdmin() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("w")).thenReturn("2");
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/homepage.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("w");
		verify(session).setAttribute("AdminAccess", true);
		
	}
	
}