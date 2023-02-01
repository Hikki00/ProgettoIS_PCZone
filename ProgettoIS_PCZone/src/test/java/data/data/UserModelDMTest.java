package data;

import static org.junit.Assert.*;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data.UserBean;
import data.UserModelDM;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserModelDMTest {
	private static IDatabaseTester tester;
    private UserModelDM scanDAO;

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
                .build(UserModelDMTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        refreshDataSet("db/init/init.xml");
        scanDAO = new UserModelDM(tester.getConnection().getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

	@Test
	public void testDoSave() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(UserModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("UTENTEREGISTRATO");

		UserBean user = mock(UserBean.class);
		when(user.getNickname()).thenReturn("Lore5213");
		when(user.getEmail()).thenReturn("lore512@gmail.com");
		when(user.getPassword()).thenReturn("79cea72184093a59d4ba48496846dc1d");
		when(user.getFoto()).thenReturn("www.test.com/img");
		when(user.getDescrizione()).thenReturn("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");

		scanDAO.doSaveForTesting(user);

        ITable actualTable = tester.getConnection().createDataSet().getTable("UTENTEREGISTRATO");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveByKey() throws Exception {

        UserBean userExpected = scanDAO.doRetrieveByKey("Lore2134");
        
		UserBean user = new UserBean();
		user.setNickname("Lore2134");
		user.setEmail("lore21@gmail.com");
		user.setPassword("79cea72184093a59d4ba48496846dc1d");
		user.setDescrizione("Componente2");
		user.setDescrizione("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");
		user.setFoto("www.test.com/img");
		user.setIsGestore(0);


        assertEquals(userExpected, user);
	}

	@Test
	public void testDoDelete() {
		try {
			assertEquals(true, scanDAO.doDelete("Lore3124"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDoUpdate() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(UserModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testUpdComponente.xml"))
                .getTable("UTENTEREGISTRATO");

		UserBean user = mock(UserBean.class);
		when(user.getNickname()).thenReturn("Lore3124");
		when(user.getEmail()).thenReturn("lore@gmail.com");
		when(user.getPassword()).thenReturn("79cea72184093a59d4ba48496846dc1d");
		when(user.getFoto()).thenReturn("www.test.com/img");
		when(user.getDescrizione()).thenReturn("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");

		scanDAO.doUpdate(user);

        ITable actualTable = tester.getConnection().createDataSet().getTable("UTENTEREGISTRATO");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}
	
	@Test
	public void testDoUpdatePassword() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(UserModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testUpdComponente.xml"))
                .getTable("UTENTEREGISTRATO");

		UserBean user = mock(UserBean.class);
		when(user.getNickname()).thenReturn("Lore3124");
		when(user.getPassword()).thenReturn("79cea72184093a59d4ba48496846dc1d");

		scanDAO.doUpdatePassForTesting(user);

        ITable actualTable = tester.getConnection().createDataSet().getTable("UTENTEREGISTRATO");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveAll() throws Exception {

		UserBean user = new UserBean();
		user.setNickname("Lore2134");
		user.setEmail("lore21@gmail.com");
		user.setPassword("79cea72184093a59d4ba48496846dc1d");
		user.setDescrizione("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");
		user.setFoto("www.test.com/image");
		user.setIsGestore(0);	
		UserBean user2 = new UserBean();
		user2.setNickname("Gest21");
		user2.setEmail("gest@gmail.com");
		user2.setPassword("79cea72184093a59d4ba48496846dc1d");
		user2.setDescrizione("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");
		user2.setFoto("www.test.com/img");
		user2.setIsGestore(1); 
		UserBean user3 = new UserBean();
		user3.setNickname("Lore3124");
		user3.setEmail("lore@gmail.com");
		user3.setPassword("79cea72184093a59d4ba48496846dc1d");
		user3.setDescrizione("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");
		user3.setFoto("www.test.com/img");
		user3.setIsGestore(0); 
		ArrayList<UserBean> expectedProducts = new ArrayList<>();
		expectedProducts.add(user);
		expectedProducts.add(user2);
		expectedProducts.add(user3);

		Collection<UserBean> actualProducts = scanDAO.doRetrieveAll(null);
        assertEquals(3, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
	}

	@Test
	public void testDoRetrieveByCond() throws Exception {
		UserBean userExpected = scanDAO.doRetrieveByCondForTesting("lore21@gmail.com","79cea72184093a59d4ba48496846dc1d");
        
		UserBean user = new UserBean();
		user.setNickname("Lore2134");
		user.setEmail("lore21@gmail.com");
		user.setPassword("79cea72184093a59d4ba48496846dc1d");
		user.setDescrizione("Componente2");
		user.setDescrizione("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");
		user.setFoto("www.test.com/img");
		user.setIsGestore(0);


        assertEquals(userExpected, user);
	}

}
