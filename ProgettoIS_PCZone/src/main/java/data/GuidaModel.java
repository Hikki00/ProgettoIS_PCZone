package data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface GuidaModel {
	
	public int doSave(GuidaBean news) throws SQLException;

	public boolean doDelete(int IDGuida) throws SQLException;

	public GuidaBean doRetrieveByKey(int IDGuida) throws SQLException;
	
	public ArrayList<GuidaBean> doRetrieveAll(String order) throws SQLException;
	
	public ArrayList<ComponenteBean> doRetrieveByCond(int IDGuida) throws SQLException;
	
	public int doSaveSuggerisce(GuidaBean news,int prodottoID) throws SQLException;
	
	public  int doUpdateSuggerisce(int guidaID,int prodottoID, int compID) throws SQLException;
	
	public  GuidaBean doRetrieveByTitolo(String titolo) throws SQLException;
	
	public  int doUpdate(GuidaBean guida) throws SQLException;
	
	public  ArrayList<SuggerisciBean> doRetrieveSuggerisceByCond(int IDGuida) throws SQLException;
}

