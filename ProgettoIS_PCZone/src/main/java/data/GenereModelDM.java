package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class GenereModelDM implements GenereModel {

	private static final String TABLE_NAME = "genere";
	private Connection connection;
	
	public GenereModelDM(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public synchronized int doSave(GenereBean gen) throws SQLException {
		
		int i = 0;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + GenereModelDM.TABLE_NAME
				+ " (NOME) VALUES (?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, gen.getNome());
			
			i = preparedStatement.executeUpdate();
            
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;	
	}
	
	
	@Override
	public synchronized ArrayList<GenereBean> doRetrieveAll() throws SQLException {
		PreparedStatement preparedStatement = null;

		ArrayList<GenereBean> generi = new ArrayList<GenereBean>();

		String selectSQL = "SELECT DISTINCT NOME FROM " + GenereModelDM.TABLE_NAME + " ORDER BY NOME ASC";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				GenereBean bean = new GenereBean();
				bean.setNome(rs.getString("NOME"));
				generi.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return generi;
	}

	@Override
	public synchronized boolean doDelete(String gen) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + GenereModelDM.TABLE_NAME + " WHERE NOME = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, gen);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}
	
}