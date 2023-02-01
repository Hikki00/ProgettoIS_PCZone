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

import application.effettuaOrdine;
import data.ComponenteBean;
import data.ComponenteModelDM;
import data.ComponenteModelDMTest;
import data.ConfigurazioneModelDM;
import data.GuidaBean;
import data.GuidaModelDM;
import data.InclusoModelDM;
import data.InsiemeProdotti;
import data.UserBean;
import data.WishlistBean;
import data.WishlistModelDM;

class effettuaOrdineIntegrationTest extends Mockito{
	private static IDatabaseTester tester;
	private effettuaOrdine servlet;
	
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
		servlet= new effettuaOrdine();
		refreshDataSet("db/init/initGuida.xml");
	}
	

	@Test
	public void EffettuaOrdine_Giusto() throws Exception {
		
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("CPU")).thenReturn("1");
		when(request.getParameter("GPU")).thenReturn("2");
		when(request.getParameter("Storage")).thenReturn("3");
		when(request.getParameter("RAM")).thenReturn("4");
		when(request.getParameter("MOBO")).thenReturn("5");
		when(request.getParameter("Case")).thenReturn("6");
		when(request.getParameter("PSU")).thenReturn("7");
		

		WishlistModelDM wishDM = new WishlistModelDM(tester.getConnection().getConnection());
		ConfigurazioneModelDM confDM = new ConfigurazioneModelDM(tester.getConnection().getConnection());
		InclusoModelDM inclusoDM = new InclusoModelDM(tester.getConnection().getConnection());
		
		UserBean user = new UserBean();
		user.setNickname("Lore3124");
		WishlistBean wish = new WishlistBean();
		wish.setNickname("Lore3124");
		wish.setIDWishlist(2);
		
		servlet.setDAOs(wishDM, confDM, inclusoDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("ordineEffettuato.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		when(session.getAttribute("user")).thenReturn(user);
		InsiemeProdotti listaProdotti = new InsiemeProdotti();
		when(session.getAttribute("carrello")).thenReturn(listaProdotti);
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", "ordine salvato");
		
	}

	@Test
	public void EffettuaOrdine_Errato() throws Exception {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpSession session = Mockito.mock(HttpSession.class);
		RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
		
		when(request.getParameter("CPU")).thenReturn("1");
		when(request.getParameter("GPU")).thenReturn("2");
		when(request.getParameter("Storage")).thenReturn("3");
		when(request.getParameter("RAM")).thenReturn("4");
		when(request.getParameter("MOBO")).thenReturn("5");
		when(request.getParameter("Case")).thenReturn("6");
		when(request.getParameter("PSU")).thenReturn("7");
		

		WishlistModelDM wishDM = new WishlistModelDM(tester.getConnection().getConnection());
		ConfigurazioneModelDM confDM = new ConfigurazioneModelDM(tester.getConnection().getConnection());
		InclusoModelDM inclusoDM = new InclusoModelDM(tester.getConnection().getConnection());
		
		UserBean user = new UserBean();
		user.setNickname("Lore3124222");
		WishlistBean wish = new WishlistBean();
		wish.setNickname("Lore3124222");
		wish.setIDWishlist(2);
		
		servlet.setDAOs(wishDM, confDM, inclusoDM);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/effettuaOrdine.jsp")).thenReturn(dispatcher);
		when(request.getContextPath()).thenReturn("http://localhost/PCZone");
		when(session.getAttribute("user")).thenReturn(user);
		InsiemeProdotti listaProdotti = new InsiemeProdotti();
		when(session.getAttribute("carrello")).thenReturn(listaProdotti);
		
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request,response);
		verify(request).setAttribute("errMessage", "Impossibile salvare ordine");
		

	}
	
	
	public GuidaBean creaGuida(int id,String nome,String descrizione) {
		GuidaBean guidabean = new GuidaBean();
		
		guidabean.setIDGuida(id);
		guidabean.setTitolo(nome);
		guidabean.setArticolo(descrizione);

		return guidabean;
	}
	
}