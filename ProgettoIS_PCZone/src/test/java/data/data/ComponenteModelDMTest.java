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

import data.ComponenteBean;
import data.ComponenteModelDM;

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

public class ComponenteModelDMTest {
	private static IDatabaseTester tester;
    private ComponenteModelDM scanDAO;

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

    @BeforeEach
    public void setUp() throws Exception {
        refreshDataSet("db/init/init.xml");
        scanDAO = new ComponenteModelDM(tester.getConnection().getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        tester.onTearDown();
    }

	@Test
	public void testDoSave() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ComponenteModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testInsertComponente.xml"))
                .getTable("COMPONENTE");

		ComponenteBean comp = mock(ComponenteBean.class);
		when(comp.getIDComponente()).thenReturn(10);
		when(comp.getNome()).thenReturn("Componente3");
		when(comp.getPrezzo()).thenReturn(10.12);
		when(comp.getDescrizione()).thenReturn("Componente3");
		when(comp.getDataUscita()).thenReturn("2021-12-01");
		when(comp.getImmagine()).thenReturn("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");
		when(comp.getTipo()).thenReturn("GPU");
		when(comp.getLinkAcquisto()).thenReturn("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");

		scanDAO.doSave(comp);

        ITable actualTable = tester.getConnection().createDataSet().getTable("COMPONENTE");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveByKey() throws Exception {

        ComponenteBean compExpected = scanDAO.doRetrieveByKey(2);
        
		ComponenteBean comp = new ComponenteBean();
		comp.setIDComponente(2);
		comp.setNome("Componente2");
		comp.setPrezzo(12.0);
		comp.setDescrizione("Componente2");
		comp.setDataUscita("2021-12-01");
		comp.setImmagine("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");
		comp.setTipo("GPU");
		comp.setLinkAcquisto("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");



        assertEquals(compExpected, comp);
	}

	@Test
	public void testDoRetrieveByName() throws Exception {
		ComponenteBean expectedProduct = scanDAO.doRetrieveByName("Componente2");
        
		ComponenteBean comp = new ComponenteBean();
		comp.setIDComponente(2);
		comp.setNome("Componente2");
		comp.setPrezzo(12.0);
		comp.setDescrizione("Componente2");
		comp.setDataUscita("2021-12-01");
		comp.setImmagine("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");
		comp.setTipo("GPU");
		comp.setLinkAcquisto("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");



        assertEquals(expectedProduct, comp);
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
	public void testDoUpdateComponenteBean() throws Exception {
        ITable expectedTable = new FlatXmlDataSetBuilder()
                .build(ComponenteModelDMTest.class.getClassLoader().getResourceAsStream("db/expected/testUpdComponente.xml"))
                .getTable("COMPONENTE");

		ComponenteBean comp = mock(ComponenteBean.class);
		when(comp.getIDComponente()).thenReturn(2);
		when(comp.getNome()).thenReturn("ComponenteModificata");
		when(comp.getPrezzo()).thenReturn(120.0);
		when(comp.getDescrizione()).thenReturn("Componente2");
		when(comp.getDataUscita()).thenReturn("2021-12-01");
		when(comp.getImmagine()).thenReturn("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");
		when(comp.getTipo()).thenReturn("GPU");
		when(comp.getLinkAcquisto()).thenReturn("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");

		scanDAO.doUpdate(comp);

        ITable actualTable = tester.getConnection().createDataSet().getTable("COMPONENTE");
        Assertion.assertEquals(new SortedTable(expectedTable), new SortedTable(actualTable));
	}

	@Test
	public void testDoRetrieveAll() throws Exception {

		ComponenteBean comp = new ComponenteBean();
		comp.setIDComponente(1);
		comp.setNome("Componente1");
		comp.setPrezzo(22.12);
		comp.setDescrizione("Componente1");
		comp.setDataUscita("2021-12-01");
		comp.setImmagine("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");
		comp.setTipo("CPU");
		comp.setLinkAcquisto("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");		
		ComponenteBean comp2 = new ComponenteBean();
		comp2.setIDComponente(2);
		comp2.setNome("Componente2");
		comp2.setPrezzo(12.0);
		comp2.setDescrizione("Componente2");
		comp2.setDataUscita("2021-12-01");
		comp2.setImmagine("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");
		comp2.setTipo("GPU");
		comp2.setLinkAcquisto("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");      
		ArrayList<ComponenteBean> expectedProducts = new ArrayList<>();
		expectedProducts.add(comp);
		expectedProducts.add(comp2);

		ArrayList<ComponenteBean> actualProducts = scanDAO.doRetrieveAll(null);
        assertEquals(2, actualProducts.size());
        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
	}

	@Test
	public void testDoRetrieveByCond() throws Exception {
		ArrayList<ComponenteBean> expectedProducts = scanDAO.doRetrieveByCond("GPU");
        
		ComponenteBean comp = new ComponenteBean();
		comp.setIDComponente(2);
		comp.setNome("Componente2");
		comp.setPrezzo(12.0);
		comp.setDescrizione("Componente2");
		comp.setDataUscita("2021-12-01");
		comp.setImmagine("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");
		comp.setTipo("GPU");
		comp.setLinkAcquisto("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");



        assertEquals(expectedProducts.get(0), comp);
	}

	@Test
	public void testDoRetrieveBySearchTerm() throws Exception {
		ArrayList<ComponenteBean> expectedProducts = scanDAO.doRetrieveBySearchTerm("Componente2");
        
		ComponenteBean comp = new ComponenteBean();
		comp.setIDComponente(2);
		comp.setNome("Componente2");
		comp.setPrezzo(12.0);
		comp.setDescrizione("Componente2");
		comp.setDataUscita("2021-12-01");
		comp.setImmagine("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");
		comp.setTipo("GPU");
		comp.setLinkAcquisto("https://www.assemblarepconline.it/wp-content/uploads/2019/03/come-scegliere-componenti-pc.png");



        assertEquals(expectedProducts.get(0), comp);
	}

}
