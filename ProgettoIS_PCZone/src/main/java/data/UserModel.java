package data;

import java.sql.SQLException;
import java.util.Collection;

public interface UserModel {
	
	public int doSave(UserBean user) throws SQLException;

	public boolean doDelete(String nickname) throws SQLException;

	public UserBean doRetrieveByKey(String nickname) throws SQLException;
	
	public UserBean doRetrieveByCond(String email,String password) throws SQLException;
	
	public Collection<UserBean> doRetrieveAll(String order) throws SQLException;
	
	public int doUpdate(UserBean user) throws SQLException;
	
	public int doUpdatePass(UserBean user) throws SQLException;
	
	public  int doSaveForTesting(UserBean user) throws SQLException;
	
	public int doUpdatePassForTesting(UserBean user) throws SQLException;
	
	public UserBean doRetrieveByCondForTesting(String email,String password) throws SQLException;
}

