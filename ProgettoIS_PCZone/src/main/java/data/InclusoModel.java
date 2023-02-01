package data;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InclusoModel {
	
	public int doSave(InclusoBean incluso) throws SQLException;

	public boolean doDelete(int IDProdotto, int IDOrdine) throws SQLException;

	public InclusoBean doRetrieveByKey(int IDProdotto, int IDOrdine) throws SQLException;
	
	public InclusoBean doRetrieveByKey(int IDProdotto, String username) throws SQLException;	
	
	public ArrayList<InclusoBean> doRetrieveByCond(int IDOrdine) throws SQLException;	
	
	public ArrayList<InclusoBean> doRetrieveByCond(String username) throws SQLException;	
	
	public ArrayList<InclusoBean> doRetrieveAll(int IDOrdine) throws SQLException;
}

