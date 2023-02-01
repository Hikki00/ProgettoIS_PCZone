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

import data.ContieneBean;
import data.WishlistBean;
import data.WishlistModelDM;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WishlistModelDMTest {
	private static IDatabaseTester tester;
    private WishlistModelDM scanDAO;

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
                .build(WishlistModelDMTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        refreshDataSet("db/init/init.xml");
        scanDAO = new WishlistModelDM(tester.getConnection().getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

	@Test
	public void testDoSave() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(WishlistModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("WISHLIST");

		WishlistBean wish = mock(WishlistBean.class);
		when(wish.getIDWishlist()).thenReturn(3);
		when(wish.getNickname()).thenReturn("Gest21");

		scanDAO.doSave(wish);

        ITable actualTable = tester.getConnection().createDataSet().getTable("WISHLIST");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}
	
	@Test
	public void testDoSave2() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(WishlistModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("CONTIENE");

		scanDAO.doSave(2,"Lore3124",1);

        ITable actualTable = tester.getConnection().createDataSet().getTable("CONTIENE");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveByKeyUser() throws Exception {

        WishlistBean compExpected = scanDAO.doRetrieveByKey("Lore2134");
        
		WishlistBean wish = new WishlistBean();
		wish.setIDWishlist(1);
		wish.setNickname("Lore2134");
		
        assertEquals(compExpected, wish);
	}
	
	@Test
	public void testDoRetrieveByKeyIDWish() throws Exception {

        WishlistBean compExpected = scanDAO.doRetrieveByKey(1);
        
		WishlistBean wish = new WishlistBean();
		wish.setIDWishlist(1);
		wish.setNickname("Lore2134");
		
        assertEquals(compExpected, wish);
	}
	
	@Test
	public void testDoRetrieveByKeyIDWishIDComp() throws Exception {

        ContieneBean compExpected = scanDAO.doRetrieveByKey(1, 1);
        
        ContieneBean wish = new ContieneBean();
		wish.setIDWishlist(1);
		wish.setIDComponente(1);
		
        assertEquals(compExpected, wish);
	}

	

	@Test
	public void testDoDeleteID() {
		try {
			assertEquals(true, scanDAO.doDelete(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDoDeleteUsername() {
		try {
			assertEquals(true, scanDAO.doDelete("Lore2134"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDoDeleteIDWishIDComp() {
		try {
			assertEquals(true, scanDAO.doDelete(1,1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	@Test
	public void testDoRetrieveAll() throws Exception {

		ContieneBean wish = new ContieneBean();
		wish.setIDWishlist(1);
		wish.setIDComponente(1); 
		ArrayList<ContieneBean> expectedProducts = new ArrayList<>();
		expectedProducts.add(wish);

		ArrayList<ContieneBean> actualProducts = scanDAO.doRetrieveAll(1);
        assertEquals(1, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
	}

	

}
