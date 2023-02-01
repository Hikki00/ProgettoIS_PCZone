package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class InclusoModelDM implements InclusoModel {

	private static final String TABLE_NAME = "incluso";
	private Connection connection;
	
	public InclusoModelDM(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public synchronized int doSave(InclusoBean product) throws SQLException {

		PreparedStatement preparedStatement = null;
		int i;
		String insertSQL = "INSERT INTO " + InclusoModelDM.TABLE_NAME
				+ " (IDORDINE, IDCOMPONENTE) VALUES (?, ?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, product.getIDOrdine());
			preparedStatement.setInt(2, product.getIDComponente());
			
			i = preparedStatement.executeUpdate();

			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;
	}

	@Override
	public synchronized InclusoBean doRetrieveByKey(int IDCOMPONENTE, int IDOrdine) throws SQLException {
		PreparedStatement preparedStatement = null;

		InclusoBean bean = new InclusoBean();
		String selectSQL = "SELECT * FROM " + InclusoModelDM.TABLE_NAME + " WHERE IDCOMPONENTE = ? AND IDOrdine = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDCOMPONENTE);
			preparedStatement.setInt(2, IDOrdine);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				bean.setIDOrdine(rs.getInt("IDORDINE"));
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	
	@Override
	public synchronized InclusoBean doRetrieveByKey(int IDCOMPONENTE, String nickname) throws SQLException {
		PreparedStatement preparedStatement = null;

		InclusoBean bean = new InclusoBean();
		String selectSQL = "SELECT * FROM configurazione, " + InclusoModelDM.TABLE_NAME + " WHERE configurazione.nickname = ? AND configurazione.IDOrdine = incluso.idordine AND incluso.IDCOMPONENTE = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, nickname);
			preparedStatement.setInt(2, IDCOMPONENTE);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				
				bean.setIDOrdine(rs.getInt("IDORDINE"));
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	

	@Override
	public synchronized boolean doDelete(int IDCOMPONENTE, int IDOrdine) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + InclusoModelDM.TABLE_NAME + " WHERE IDCOMPONENTE = ? AND IDOrdine = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, IDCOMPONENTE);
			preparedStatement.setInt(2, IDOrdine);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}
	
	@Override
	public synchronized ArrayList<InclusoBean> doRetrieveByCond(int IDOrdine) throws SQLException {
		PreparedStatement preparedStatement = null;

		ArrayList<InclusoBean> inclusi = new ArrayList<InclusoBean>();

		String selectSQL = "SELECT * FROM " + InclusoModelDM.TABLE_NAME + " WHERE IDOrdine = ? ORDER BY IDOrdine ASC";


		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDOrdine);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			
			while (rs.next()) {
				InclusoBean bean = new InclusoBean();

				bean.setIDOrdine(rs.getInt("IDORDINE"));
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				
				inclusi.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return inclusi;
	}

	@Override
	public synchronized ArrayList<InclusoBean> doRetrieveByCond(String username) throws SQLException {
		PreparedStatement preparedStatement = null;

		ArrayList<InclusoBean> comprati = new ArrayList<InclusoBean>();

		String selectSQL = "SELECT DISTINCT Incluso.IDCOMPONENTE FROM Incluso, Configurazione WHERE Configurazione.Nickname = ? AND Incluso.IDOrdine = Configurazione.IDOrdine ORDER BY Incluso.IDCOMPONENTE";


		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			
			
			while (rs.next()) {
				InclusoBean bean = new InclusoBean();
				
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				
				comprati.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return comprati;
	}
	
	@Override
	public synchronized ArrayList<InclusoBean> doRetrieveAll(int IDOrdine) throws SQLException {
		PreparedStatement preparedStatement = null;

		ArrayList<InclusoBean> inclusi = new ArrayList<InclusoBean>();

		String selectSQL = "SELECT * FROM " + InclusoModelDM.TABLE_NAME + " ORDER BY IDOrdine ASC";


		try {
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				InclusoBean bean = new InclusoBean();

				bean.setIDOrdine(rs.getInt("IDORDINE"));
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				
				inclusi.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return inclusi;
	}

	
}