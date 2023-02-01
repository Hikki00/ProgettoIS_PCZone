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

public class ReviewModelDM implements ReviewModel {

	private static final String TABLE_NAME = "recensione";
	private Connection connection;
	
	public ReviewModelDM(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public synchronized int doSave(ReviewBean review) throws SQLException {

		PreparedStatement preparedStatement = null;
		int i = -1;
		String insertSQL = "INSERT INTO " + ReviewModelDM.TABLE_NAME
				+ " (TITOLO,PUNTEGGIO,TESTO,DATACREAZIONE,IDCOMPONENTE,NICKNAME) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, review.getTitolo());
			preparedStatement.setInt(2, review.getPunteggio());
			preparedStatement.setString(3, review.getTesto());
			preparedStatement.setTimestamp(4, review.getData());
			preparedStatement.setInt(5, review.getIDComponente());
			preparedStatement.setString(6, review.getNickname());
			
			
			
			
			preparedStatement.executeUpdate();

			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;
	}

	@Override
	public synchronized ReviewBean doRetrieveByKey(int IDComponente,String nickname) throws SQLException {
		PreparedStatement preparedStatement = null;

		ReviewBean bean = new ReviewBean();
		String selectSQL = "SELECT * FROM " + ReviewModelDM.TABLE_NAME + " WHERE IDComponente = ? AND NICKNAME = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDComponente);
			preparedStatement.setString(2, nickname);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setTitolo(rs.getString("Titolo"));
				bean.setPunteggio(rs.getInt("Punteggio"));
				bean.setTesto(rs.getString("Testo"));
				bean.setData(rs.getTimestamp("DataCreazione"));
				bean.setIDComponente(rs.getInt("IDComponente"));
				bean.setNickname(rs.getString("Nickname"));
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	

	@Override
	public synchronized boolean doDelete(int IDComponente,String nickname) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + ReviewModelDM.TABLE_NAME + " WHERE IDComponente = ? AND NICKNAME = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, IDComponente);
			preparedStatement.setString(2, nickname);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}

	//prende tutti i prodotti di un dato genere
	public synchronized ArrayList<ReviewBean> doRetrieveByCond(int IDComponente) throws SQLException {
		PreparedStatement preparedStatement = null;
		
		ArrayList<ReviewBean> review = new ArrayList<ReviewBean>();
		
		String selectSQL = "SELECT * FROM " + ReviewModelDM.TABLE_NAME + " WHERE IDComponente = ? ORDER BY DATACREAZIONE ;";
		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDComponente);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				
				ReviewBean bean = new ReviewBean();
				
				bean.setTitolo(rs.getString("Titolo"));
				bean.setPunteggio(rs.getInt("Punteggio"));
				bean.setTesto(rs.getString("Testo"));
				bean.setData(rs.getTimestamp("DataCreazione"));
				bean.setIDComponente(rs.getInt("IDComponente"));
				bean.setNickname(rs.getString("Nickname"));
				
				review.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return review;
	}	
	
}