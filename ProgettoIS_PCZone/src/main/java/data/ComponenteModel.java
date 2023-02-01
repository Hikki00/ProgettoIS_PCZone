package data;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ComponenteModel {
	
	public int doSave(ComponenteBean prodotto) throws SQLException;

	public boolean doDelete(int IDComponente) throws SQLException;

	public int doUpdate(ComponenteBean product) throws SQLException;

	public int doUpdate(int cancellato, int IDComponente) throws SQLException;
	
	public ComponenteBean doRetrieveByKey(int IDComponente) throws SQLException;
	
	public ArrayList<ComponenteBean> doRetrieveByCond(String gen) throws SQLException;
	
	public ArrayList<ComponenteBean> doRetrieveAll(String order) throws SQLException;
	
	public ArrayList<ComponenteBean> doRetrieveBySearchTerm(String searchTerm) throws SQLException;
	
	public ComponenteBean doRetrieveByName(String name) throws SQLException;
}

