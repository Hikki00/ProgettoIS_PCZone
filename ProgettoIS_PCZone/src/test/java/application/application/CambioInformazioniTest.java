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

import application.CambioInformazioni;
import data.UserBean;
import data.UserModelDM;

class CambioInformazioniTest extends Mockito{

	private CambioInformazioni servlet;
	
	
	@BeforeEach
	void setUp() throws Exception {
		servlet= new CambioInformazioni();
	}
	

	@Test
	public void CambioInfo_Giusto() throws ServletException, IOException, SQLException {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("email")).thenReturn("lore21@gmail.com");
		when(request.getParameter("descrizione")).thenReturn("Lorem Ipsum Lorem Ipsum Lorem Ipsum");
		when(request.getParameter("foto")).thenReturn("www.image.test/img");
		
		String nick = "Lore2134";
		String email ="lore21@gmail.com";
		String descrizione="Lorem Ipsum Lorem Ipsum Lorem Ipsum";
		String foto = "www.image.test/img";
				
		UserModelDM userDM = mock(UserModelDM.class); 
		UserBean user = creaUser(nick,descrizione,email,foto);
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);
		when(request.getRequestDispatcher("/profiloUtente.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(userDM.doUpdate(user)).thenReturn(1);
		servlet.setDAOUser(userDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("email");
		verify(request).getParameter("descrizione");
		verify(request).getParameter("foto");
		
		
	}
	
	@Test
	public void CambioInfo_Errato() throws ServletException, IOException, SQLException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("email")).thenReturn("lore21@gmail.com");
		when(request.getParameter("descrizione")).thenReturn("Lorem Ipsum Lorem Ipsum Lorem Ipsum");
		when(request.getParameter("foto")).thenReturn("www.image.test/img");
		
		String nick = "Lore2134";
		String email ="lore21@gmail.com";
		String descrizione="Lorem Ipsum Lorem Ipsum Lorem Ipsum";
		String foto = "www.image.test/img";
				
		UserModelDM userDM = mock(UserModelDM.class); 
		UserBean user = creaUser(nick,descrizione,email,foto);
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);
		when(request.getRequestDispatcher("/profiloUtente.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		
		when(userDM.doUpdate(user)).thenReturn(0);
		servlet.setDAOUser(userDM);
		
		servlet.doPost(request, response);
		
		verify(request).getParameter("email");
		verify(request).getParameter("descrizione");
		verify(request).getParameter("foto");
		verify(request).setAttribute("errMessage",0);
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