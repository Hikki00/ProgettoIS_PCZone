package data;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ConfigurazioneModel {
	
	public int doSave(ConfigurazioneBean ordine) throws SQLException;

	public boolean doDelete(int IDOrdine) throws SQLException;

	public ConfigurazioneBean doRetrieveByKey(int IDProdotto) throws SQLException;

	public ArrayList<ConfigurazioneBean> doRetrieveByCond(String username) throws SQLException;
	
	public ArrayList<ConfigurazioneBean> doRetrieveAll(String order) throws SQLException;
}

