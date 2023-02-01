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

import data.ReviewBean;
import data.ReviewModelDM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewModelDMTest {
	private static IDatabaseTester tester;
    private ReviewModelDM scanDAO;

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
                .build(ReviewModelDMTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        refreshDataSet("db/init/init.xml");
        scanDAO = new ReviewModelDM(tester.getConnection().getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

	@Test
	public void testDoSave() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ReviewModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("RECENSIONE");

		ReviewBean rec = mock(ReviewBean.class);
		when(rec.getIDComponente()).thenReturn(2);
		when(rec.getNickname()).thenReturn("Lore3124");
		when(rec.getTesto()).thenReturn("QuestaEUnaRecensioneQuestaEUna");
		when(rec.getData()).thenReturn(Timestamp.valueOf("2023-03-07 00:00:00"));
		when(rec.getPunteggio()).thenReturn(5);
		when(rec.getTitolo()).thenReturn("Componente Ottima");

		scanDAO.doSave(rec);

        ITable actualTable = tester.getConnection().createDataSet().getTable("RECENSIONE");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveByKey() throws Exception {

        ReviewBean compExpected = scanDAO.doRetrieveByKey(1,"Lore2134");
        
		ReviewBean rec = new ReviewBean();
		rec.setIDComponente(1);
		rec.setNickname("Lore2134");;
		rec.setTesto("QuestaEUnaRecensioneQuestaEUnaRecensioneQuestaEUnaRecensione");;
		rec.setData(Timestamp.valueOf("2023-01-06 00:00:00"));;
		rec.setPunteggio(5);
		rec.setTitolo("Componente buona");;




        assertEquals(compExpected, rec);
	}


	@Test
	public void testDoDelete() {
		try {
			assertEquals(true, scanDAO.doDelete(1,"Lore2134"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testDoRetrieveByCond() throws Exception {
		ArrayList<ReviewBean> expectedProducts = scanDAO.doRetrieveByCond(2);
        
		ReviewBean rec = new ReviewBean();
		rec.setIDComponente(2);
		rec.setNickname("Lore2134");;
		rec.setTesto("QuestaEUnaRecensioneQuestaEUna");;
		rec.setData(Timestamp.valueOf("2023-01-06 00:00:00"));;
		rec.setPunteggio(3);
		rec.setTitolo("Componente Ok");;



        assertEquals(expectedProducts.get(0), rec);
	}



}
