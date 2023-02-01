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

import data.GenereBean;
import data.GenereModelDM;

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

public class GenereModelDMTest {
	private static IDatabaseTester tester;
    private GenereModelDM scanDAO;

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
                .build(GenereModelDMTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        refreshDataSet("db/init/init.xml");
        scanDAO = new GenereModelDM(tester.getConnection().getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

	@Test
	public void testDoSave() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(GenereModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("GENERE");

		GenereBean gen = mock(GenereBean.class);
		when(gen.getNome()).thenReturn("MOBO");

		scanDAO.doSave(gen);

        ITable actualTable = tester.getConnection().createDataSet().getTable("GENERE");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}


	@Test
	public void testDoDelete() {
		try {
			assertEquals(true, scanDAO.doDelete("CPU"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDoRetrieveAll() throws Exception {

		GenereBean gen = new GenereBean();
		gen.setNome("CPU");	
		GenereBean gen2 = new GenereBean();
		gen2.setNome("GPU");
		ArrayList<GenereBean> expectedProducts = new ArrayList<>();
		expectedProducts.add(gen);
		expectedProducts.add(gen2);

		ArrayList<GenereBean> actualProducts = scanDAO.doRetrieveAll();
        assertEquals(2, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
	}

}
