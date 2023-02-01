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

import data.ConfigurazioneBean;
import data.ConfigurazioneModelDM;

import java.sql.SQLException;
import java.sql.Timestamp;
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

public class ConfigurazioneModelDMTest {
	private static IDatabaseTester tester;
    private ConfigurazioneModelDM scanDAO;

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
                .build(ConfigurazioneModelDMTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        refreshDataSet("db/init/init.xml");
        scanDAO = new ConfigurazioneModelDM(tester.getConnection().getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

	@Test
	public void testDoSave() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ConfigurazioneModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("CONFIGURAZIONE");

		ConfigurazioneBean conf = mock(ConfigurazioneBean.class);
		when(conf.getIDOrdine()).thenReturn(3);
		when(conf.getNickname()).thenReturn("Lore3124");
		when(conf.getDataCompletamento()).thenReturn(Timestamp.valueOf("2021-06-07 00:00:00"));

		scanDAO.doSave(conf);

        ITable actualTable = tester.getConnection().createDataSet().getTable("CONFIGURAZIONE");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveByKey() throws Exception {

        ConfigurazioneBean confExpected = scanDAO.doRetrieveByKey(2);
        
		ConfigurazioneBean conf = new ConfigurazioneBean();
		conf.setIDOrdine(2);
		conf.setNickname("Lore2134");
		conf.setDataCompletamento(Timestamp.valueOf("2021-05-06 00:00:00"));



        assertEquals(confExpected, conf);
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
	public void testDoRetrieveAll() throws Exception {

		ConfigurazioneBean conf = new ConfigurazioneBean();
		conf.setIDOrdine(1);
		conf.setNickname("Lore2134");
		conf.setDataCompletamento(Timestamp.valueOf("2020-02-05 00:00:00")); 

		ConfigurazioneBean conf2 = new ConfigurazioneBean();
		conf2.setIDOrdine(2);
		conf2.setNickname("Lore2134");
		conf2.setDataCompletamento(Timestamp.valueOf("2021-05-06 00:00:00"));
		
		ArrayList<ConfigurazioneBean> expectedProducts = new ArrayList<>();
		expectedProducts.add(conf);
		expectedProducts.add(conf2);

		ArrayList<ConfigurazioneBean> actualProducts = scanDAO.doRetrieveAll(null);
        assertEquals(2, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
	}

	@Test
	public void testDoRetrieveByCond() throws Exception {
		ArrayList<ConfigurazioneBean> actualProducts = scanDAO.doRetrieveByCond("Lore2134");
        
		ConfigurazioneBean conf = new ConfigurazioneBean();
		conf.setIDOrdine(1);
		conf.setNickname("Lore2134");
		conf.setDataCompletamento(Timestamp.valueOf("2020-02-05 00:00:00")); 

		ConfigurazioneBean conf2 = new ConfigurazioneBean();
		conf2.setIDOrdine(2);
		conf2.setNickname("Lore2134");
		conf2.setDataCompletamento(Timestamp.valueOf("2021-05-06 00:00:00"));
		
		ArrayList<ConfigurazioneBean> expectedProducts = new ArrayList<>();
		expectedProducts.add(conf);
		expectedProducts.add(conf2);

		assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
	}

}
