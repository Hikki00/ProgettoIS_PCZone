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

import data.GuidaBean;
import data.GuidaModelDM;
import data.SuggerisciBean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GuidaModelDMTest {
	private static IDatabaseTester tester;
    private GuidaModelDM scanDAO;

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
                .build(GuidaModelDMTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        refreshDataSet("db/init/init.xml");
        scanDAO = new GuidaModelDM(tester.getConnection().getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

	@Test
	public void testDoSave() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(GuidaModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("GUIDA");
        
        
		GuidaBean guida = mock(GuidaBean.class);
		when(guida.getIDGuida()).thenReturn(3);
		when(guida.getTitolo()).thenReturn("Best PC Builld 2021");
		when(guida.getData()).thenReturn(new Date(0));
		when(guida.getArticolo()).thenReturn("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");

		scanDAO.doSave(guida);

        ITable actualTable = tester.getConnection().createDataSet().getTable("GUIDA");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}
	
	@Test
	public void testDoSaveSuggerisce() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(GuidaModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("SUGGERISCE");
        
        
        GuidaBean guida = mock(GuidaBean.class);
		when(guida.getIDGuida()).thenReturn(2);

		scanDAO.doSaveSuggerisce(guida, 2);

        ITable actualTable = tester.getConnection().createDataSet().getTable("SUGGERISCE");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveByKey() throws Exception {

        GuidaBean compExpected = scanDAO.doRetrieveByKey(1);
        
        GuidaBean guida = new GuidaBean();
        guida.setIDGuida(1);
        guida.setTitolo("Best PC Builld 2022");
        guida.setData(new Date(0));
        guida.setArticolo("Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsumLorem ipsum Lorem ipsum");




        assertEquals(compExpected, guida);
	}
	
	@Test
	public void testDoRetrieveByTitolo() throws Exception {

        GuidaBean compExpected = scanDAO.doRetrieveByTitolo("Best PC Builld 2022");
        
        GuidaBean guida = new GuidaBean();
        guida.setIDGuida(1);
        guida.setTitolo("Best PC Builld 2022");
        guida.setData(new Date(0));
        guida.setArticolo("Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsumLorem ipsum Lorem ipsum");




        assertEquals(compExpected, guida);
	}


	@Test
	public void testDoDelete() {
		try {
			assertEquals(true, scanDAO.doDelete(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDoRetrieveSuggerisceByCond() throws Exception {

        ArrayList<SuggerisciBean> compExpected = scanDAO.doRetrieveSuggerisceByCond(1);
        
        SuggerisciBean sugg = new SuggerisciBean();
        sugg.setIDGuida(1);
        sugg.setIDComponente(2);

        assertEquals(compExpected.get(0), sugg);
	}
	
	@Test
	public void testDoRetrieveAll() throws Exception {

		GuidaBean guida = new GuidaBean();
        guida.setIDGuida(1);
        guida.setTitolo("Best PC Builld 2022");
        guida.setData(new Date(0));
        guida.setArticolo("Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsumLorem ipsum Lorem ipsum");

		GuidaBean guida2 = new GuidaBean();
        guida2.setIDGuida(2);
        guida2.setTitolo("Best PC Builld 2023");
        guida2.setData(new Date(0));
        guida2.setArticolo("QuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTestQuestoEunTest");
		
		ArrayList<GuidaBean> expectedProducts = new ArrayList<>();
		expectedProducts.add(guida);
		expectedProducts.add(guida2);

		ArrayList<GuidaBean> actualProducts = scanDAO.doRetrieveAll(null);
        assertEquals(2, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
	}

}
