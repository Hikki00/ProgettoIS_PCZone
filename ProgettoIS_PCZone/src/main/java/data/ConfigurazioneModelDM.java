package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ConfigurazioneModelDM implements ConfigurazioneModel {

	private static final String TABLE_NAME = "configurazione";
	private Connection connection;
	
	public ConfigurazioneModelDM(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public synchronized int doSave(ConfigurazioneBean ordine) throws SQLException {

		PreparedStatement preparedStatement = null;
		int i = -1;
		String insertSQL = "INSERT INTO " + ConfigurazioneModelDM.TABLE_NAME
				+ " (DATACOMPLETAMENTO, NICKNAME) VALUES (?, ?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setTimestamp(1, ordine.getDataCompletamento());
			preparedStatement.setString(2, ordine.getNickname());
			
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			    i = (int) rs.getLong(1);
			}
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;
	}

	@Override
	public synchronized ConfigurazioneBean doRetrieveByKey(int IDOrdine) throws SQLException {
		PreparedStatement preparedStatement = null;

		ConfigurazioneBean bean = new ConfigurazioneBean();
		String selectSQL = "SELECT * FROM " + ConfigurazioneModelDM.TABLE_NAME + " WHERE IDORDINE = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDOrdine);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setIDOrdine(rs.getInt("IDORDINE"));
				bean.setDataCompletamento(rs.getTimestamp("DATACOMPLETAMENTO"));
				bean.setNickname(rs.getString("NICKNAME"));
				
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	

	@Override
	public synchronized boolean doDelete(int IDOrdine) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + ConfigurazioneModelDM.TABLE_NAME + " WHERE IDOrdine = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, IDOrdine);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}

	@Override
	public synchronized ArrayList<ConfigurazioneBean> doRetrieveAll(String order) throws SQLException {
		PreparedStatement preparedStatement = null;

		ArrayList<ConfigurazioneBean> ordini = new ArrayList<ConfigurazioneBean>();

		String selectSQL = "SELECT * FROM " + ConfigurazioneModelDM.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ConfigurazioneBean bean = new ConfigurazioneBean();
				bean.setIDOrdine(rs.getInt("IDORDINE"));
				bean.setDataCompletamento(rs.getTimestamp("DATACOMPLETAMENTO"));
				bean.setNickname(rs.getString("NICKNAME"));
				
				ordini.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return ordini;
	}

	public synchronized ArrayList<ConfigurazioneBean> doRetrieveByCond(String nickname) throws SQLException {
		PreparedStatement preparedStatement = null;
		
		ArrayList<ConfigurazioneBean> ordini = new ArrayList<ConfigurazioneBean>();
		
		String selectSQL = "SELECT * FROM " + ConfigurazioneModelDM.TABLE_NAME + " WHERE Nickname = ? ORDER BY Nickname ASC;";
		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nickname);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				
				ConfigurazioneBean bean = new ConfigurazioneBean();
				
				bean.setIDOrdine(rs.getInt("IDORDINE"));
				bean.setDataCompletamento(rs.getTimestamp("DATACOMPLETAMENTO"));
				bean.setNickname(rs.getString("NICKNAME"));
				
				ordini.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return ordini;
	}	
	
}