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

import data.InclusoBean;
import data.InclusoModelDM;

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

public class InclusoModelDMTest {
	private static IDatabaseTester tester;
    private InclusoModelDM scanDAO;

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
                .build(InclusoModelDMTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        refreshDataSet("db/init/init.xml");
        scanDAO = new InclusoModelDM(tester.getConnection().getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

	@Test
	public void testDoSave() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(InclusoModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("INCLUSO");

		InclusoBean inc = mock(InclusoBean.class);
		when(inc.getIDOrdine()).thenReturn(1);
		when(inc.getIDComponente()).thenReturn(2);

		scanDAO.doSave(inc);

        ITable actualTable = tester.getConnection().createDataSet().getTable("INCLUSO");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveByKey() throws Exception {

		InclusoBean compExpected = scanDAO.doRetrieveByKey(1,1);
        
		InclusoBean wish = new InclusoBean();
		wish.setIDOrdine(1);
		wish.setIDComponente(1);
		
        assertEquals(compExpected, wish);
	}
	
	@Test
	public void testDoRetrieveByKeyNick() throws Exception {

		InclusoBean compExpected = scanDAO.doRetrieveByKey(2,"Lore2134");
        
		InclusoBean wish = new InclusoBean();
		wish.setIDOrdine(2);
		wish.setIDComponente(2);
		
        assertEquals(compExpected, wish);
	}

	

	@Test
	public void testDoDelete() {
		try {
			assertEquals(true, scanDAO.doDelete(1,1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	@Test
	public void testDoRetrieveAll() throws Exception {

		InclusoBean wish = new InclusoBean();
		wish.setIDOrdine(1);
		wish.setIDComponente(1);
		InclusoBean wish2 = new InclusoBean();
		wish2.setIDOrdine(2);
		wish2.setIDComponente(2);
		ArrayList<InclusoBean> expectedProducts = new ArrayList<>();
		expectedProducts.add(wish);
		expectedProducts.add(wish2);

		ArrayList<InclusoBean> actualProducts = scanDAO.doRetrieveAll(0);
        assertEquals(2, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
	}

	@Test
	public void testDoRetrieveByCond() throws Exception {
		
		
		InclusoBean wish = new InclusoBean();
		wish.setIDOrdine(1);
		wish.setIDComponente(1);

		ArrayList<InclusoBean> actualProducts = scanDAO.doRetrieveByCond(1);
		
		assertEquals(actualProducts.get(0), wish);
	}

}
