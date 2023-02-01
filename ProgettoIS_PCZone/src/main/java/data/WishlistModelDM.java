package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WishlistModelDM implements WishlistModel {

	private static final String TABLE_NAME = "wishlist";
	private Connection connection;
	
	public WishlistModelDM(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public synchronized int doSave(WishlistBean wishlist) throws SQLException, ParseException {
		PreparedStatement preparedStatement = null;
		int i;
		String insertSQL = "INSERT INTO " + WishlistModelDM.TABLE_NAME
				+ " (NICKNAME) VALUES (?)";
		
		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, wishlist.getNickname());
			
			i = preparedStatement.executeUpdate();

			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;
	}

	public synchronized int doSave(int IDWishlist, String nickname, int IDComponente) throws SQLException {
		PreparedStatement preparedStatement = null;
		int i;
		String insertSQL = "INSERT INTO contiene(IDWISHLIST, NICKNAME, IDCOMPONENTE) VALUES (?,?,?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, IDWishlist);
			preparedStatement.setString(2, nickname);
			preparedStatement.setInt(3, IDComponente);
			
			i = preparedStatement.executeUpdate();

			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;
	}
	
	@Override
	public synchronized WishlistBean doRetrieveByKey(String username) throws SQLException {
		PreparedStatement preparedStatement = null;

		WishlistBean bean = new WishlistBean();
		String selectSQL = "SELECT * FROM " + WishlistModelDM.TABLE_NAME + " WHERE Nickname = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setIDWishlist(rs.getInt("IDWISHLIST"));
				bean.setNickname(rs.getString("NICKNAME"));
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	
	@Override
	public synchronized WishlistBean doRetrieveByKey(int IDWishlist) throws SQLException {
		PreparedStatement preparedStatement = null;

		WishlistBean bean = new WishlistBean();
		String selectSQL = "SELECT * FROM " + WishlistModelDM.TABLE_NAME + " WHERE IDWishlist = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDWishlist);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setIDWishlist(rs.getInt("IDWISHLIST"));
				bean.setNickname(rs.getString("NICKNAME"));
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	
	@Override
	public synchronized ContieneBean doRetrieveByKey(int IDWishlist, int IDComponente) throws SQLException {
		PreparedStatement preparedStatement = null;

		ContieneBean bean = new ContieneBean();
		String selectSQL = "SELECT * FROM contiene WHERE contiene.IDWishlist = ? AND contiene.IDComponente = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDWishlist);
			preparedStatement.setInt(2, IDComponente);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setIDWishlist(rs.getInt("IDWISHLIST"));
				bean.setIDComponente(rs.getInt("IDComponente"));
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	

	@Override
	public synchronized boolean doDelete(int IDWishlist) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + WishlistModelDM.TABLE_NAME + " WHERE IDWishlist = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, IDWishlist);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}
	
	@Override
	public synchronized boolean doDelete(String username) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + WishlistModelDM.TABLE_NAME + " WHERE Username = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, username);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}

	@Override
	public synchronized boolean doDelete(int IDWishlist, int IDComponente) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM contiene WHERE IDWishlist = ? AND IDComponente = ? ";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, IDWishlist);
			preparedStatement.setInt(2, IDComponente);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}

	
	
	@Override
	public synchronized ArrayList<ContieneBean> doRetrieveAll(int IDWishlist) throws SQLException {
		PreparedStatement preparedStatement = null;

		ArrayList<ContieneBean> elementiWish = new ArrayList<ContieneBean>();

		String selectSQL = "SELECT * FROM contiene WHERE IDWishlist = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDWishlist);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ContieneBean bean = new ContieneBean();

				bean.setIDWishlist(rs.getInt("IDWISHLIST"));
				bean.setIDComponente(rs.getInt("IDComponente"));
				
				elementiWish.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return elementiWish;
	}	
	
}