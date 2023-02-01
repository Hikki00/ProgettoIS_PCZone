package data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface ReviewModel {
	
	public int doSave(ReviewBean review) throws SQLException;

	public boolean doDelete(int IDProdotto,String username) throws SQLException;

	public ReviewBean doRetrieveByKey(int IDProdotto,String username) throws SQLException;
	
	public ArrayList<ReviewBean> doRetrieveByCond(int IDProdotto) throws SQLException;
}

