package data;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenereModel {
	
	public int doSave(GenereBean gen) throws SQLException;

	public boolean doDelete(String Nome) throws SQLException;
	
	public ArrayList<GenereBean> doRetrieveAll() throws SQLException;
}

