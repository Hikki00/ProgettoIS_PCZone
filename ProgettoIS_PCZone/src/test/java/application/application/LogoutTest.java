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

import application.Logout;
import data.UserBean;
import data.UserModelDM;

class LogoutTest extends Mockito{

	private Logout servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new Logout();
	}
	
	@Test
	public void LogoutCorretto() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getSession(false)).thenReturn(session);
		when(request.getRequestDispatcher("/homepage.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		servlet.doPost(request, response);
		
		verify(session).removeAttribute("user");
		verify(session).removeAttribute("userAccess");
		verify(session).removeAttribute("adminAccess");
		verify(session).invalidate();
		
	}
	
}