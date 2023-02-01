package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class UserModelDM implements UserModel {

	private static final String TABLE_NAME = "UtenteRegistrato";
	private Connection connection;
	
	public UserModelDM(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public synchronized int doSave(UserBean user) throws SQLException {
		
		int i = 0;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + UserModelDM.TABLE_NAME
				+ " (NICKNAME, PASSWORD, EMAIL, FOTO, DESCRIZIONE) VALUES (?, MD5(?), ?, ?, ?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getNickname());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getFoto());
			preparedStatement.setString(5, user.getDescrizione());
			
			i = preparedStatement.executeUpdate();
            
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;	
	}
	
	@Override   //Usato per testing senza MD5  a causa di problemi con H2
	public synchronized int doSaveForTesting(UserBean user) throws SQLException {
		
		int i = 0;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + UserModelDM.TABLE_NAME
				+ " (NICKNAME, PASSWORD, EMAIL, FOTO, DESCRIZIONE) VALUES (?, ?, ?, ?, ?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getNickname());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getFoto());
			preparedStatement.setString(5, user.getDescrizione());
			
			i = preparedStatement.executeUpdate();
            
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;	
	}
	
	
	@Override
	public synchronized int doUpdate(UserBean user) throws SQLException {
		
		int i = 0;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "UPDATE " + UserModelDM.TABLE_NAME
				+ " SET NICKNAME = ? , EMAIL = ? , PASSWORD = ?, FOTO = ?, DESCRIZIONE = ?  WHERE NICKNAME = ?";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getNickname());
			preparedStatement.setString(2, user.getEmail());	
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getFoto());
			preparedStatement.setString(5, user.getDescrizione());
			preparedStatement.setString(6, user.getNickname());
			
			i = preparedStatement.executeUpdate();
            System.out.println(i);
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;	
	}
	
	
	@Override
	public synchronized int doUpdatePass(UserBean user) throws SQLException {
		
		int i = 0;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE " + UserModelDM.TABLE_NAME
				+ " SET PASSWORD = MD5(?) WHERE NICKNAME = ?";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, user.getNickname());

			
			i = preparedStatement.executeUpdate();

			connection.commit();
			
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;	
	}
	
	@Override
	public synchronized int doUpdatePassForTesting(UserBean user) throws SQLException {
		
		int i = 0;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE " + UserModelDM.TABLE_NAME
				+ " SET PASSWORD = ? WHERE NICKNAME = ?";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, user.getNickname());

			
			i = preparedStatement.executeUpdate();

			connection.commit();
			
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;	
	}


	@Override
	public synchronized UserBean doRetrieveByKey(String nickname) throws SQLException {
		PreparedStatement preparedStatement = null;

		UserBean bean = null;

		String selectSQL = "SELECT * FROM " + UserModelDM.TABLE_NAME + " WHERE NICKNAME = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nickname);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
				bean.setNickname(rs.getString("NICKNAME"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setFoto(rs.getString("FOTO"));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setIsGestore(rs.getInt("ISGESTORE"));
			}
				        
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	

	@Override
	public synchronized UserBean doRetrieveByCond(String email,String password) throws SQLException {
		PreparedStatement preparedStatement = null;

		UserBean bean = new UserBean();

		String selectSQL = "SELECT * FROM " + UserModelDM.TABLE_NAME + " WHERE EMAIL = ? and PASSWORD = MD5(?)";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setNickname(rs.getString("NICKNAME"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setFoto(rs.getString("FOTO"));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setIsGestore(rs.getInt("ISGESTORE"));
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	
	@Override
	public synchronized UserBean doRetrieveByCondForTesting(String email,String password) throws SQLException {
		PreparedStatement preparedStatement = null;

		UserBean bean = new UserBean();

		String selectSQL = "SELECT * FROM " + UserModelDM.TABLE_NAME + " WHERE EMAIL = ? and PASSWORD = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setNickname(rs.getString("NICKNAME"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setFoto(rs.getString("FOTO"));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setIsGestore(rs.getInt("ISGESTORE"));
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	
	@Override
	public synchronized boolean doDelete(String nickname) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + UserModelDM.TABLE_NAME + " WHERE NICKNAME = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, nickname);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}

	@Override
	public synchronized Collection<UserBean> doRetrieveAll(String order) throws SQLException {
		PreparedStatement preparedStatement = null;

		Collection<UserBean> users = new LinkedList<UserBean>();

		String selectSQL = "SELECT * FROM " + UserModelDM.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				UserBean bean = new UserBean();

				bean.setNickname(rs.getString("NICKNAME"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setPassword(rs.getString("FOTO"));
				bean.setPassword(rs.getString("DESCRIZIONE"));
				bean.setIsGestore(rs.getInt("ISGESTORE"));
				
				users.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return users;
	}

}