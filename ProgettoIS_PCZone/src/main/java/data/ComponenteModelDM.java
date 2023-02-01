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


public class ComponenteModelDM implements ComponenteModel {

	private static final String TABLE_NAME = "componente";
	private Connection connection;
	
	public ComponenteModelDM(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public synchronized int doSave(ComponenteBean product) throws SQLException {
		PreparedStatement preparedStatement = null;
		int i = 0;
		String insertSQL = "INSERT INTO " + ComponenteModelDM.TABLE_NAME
				+ " (NOME, DESCRIZIONE, PREZZO, DATAUSCITA, IMMAGINE, TIPO, LINKACQUISTO) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, product.getNome());
			preparedStatement.setString(2, product.getDescrizione());
			preparedStatement.setDouble(3, product.getPrezzo());
			preparedStatement.setString(4,product.getDataUscita());
			preparedStatement.setString(5, product.getImmagine());
			preparedStatement.setString(6, product.getTipo());
			preparedStatement.setString(7, product.getLinkAcquisto());

			i = preparedStatement.executeUpdate();
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;
	}

	@Override
	public synchronized ComponenteBean doRetrieveByKey(int IDComponente) throws SQLException {
		PreparedStatement preparedStatement = null;
		ComponenteBean bean = new ComponenteBean();
		String selectSQL = "SELECT * FROM " + ComponenteModelDM.TABLE_NAME + " WHERE IDCOMPONENTE = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDComponente);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				bean.setNome(rs.getString("NOME"));
				bean.setPrezzo(rs.getFloat("PREZZO"));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setDataUscita(rs.getString("DATAUSCITA"));
				bean.setImmagine(rs.getString("IMMAGINE"));
				bean.setLinkAcquisto(rs.getString("LINKACQUISTO"));
				
				bean.setTipo(rs.getString("TIPO"));
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}
	
	@Override
	public synchronized ComponenteBean doRetrieveByName(String name) throws SQLException {
		PreparedStatement preparedStatement = null;
		ComponenteBean bean = new ComponenteBean();
		String selectSQL = "SELECT * FROM " + ComponenteModelDM.TABLE_NAME + " WHERE NOME = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				bean.setNome(rs.getString("NOME"));
				bean.setPrezzo(rs.getFloat("PREZZO"));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setDataUscita(rs.getString("DATAUSCITA"));
				bean.setImmagine(rs.getString("IMMAGINE"));
				bean.setLinkAcquisto(rs.getString("LINKACQUISTO"));
				
				bean.setTipo(rs.getString("TIPO"));
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return bean;
	}

	@Override
	public synchronized boolean doDelete(int IDComponente) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + ComponenteModelDM.TABLE_NAME + " WHERE IDCOMPONENTE = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, IDComponente);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}
	
	@Override
	public synchronized int doUpdate(int cancellato, int IDComponente) throws SQLException {
		PreparedStatement preparedStatement = null;

		int i = 0;

		String deleteSQL = "UPDATE " + ComponenteModelDM.TABLE_NAME + " SET Cancellato = ? WHERE IDComponente = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, cancellato);
			preparedStatement.setInt(2, IDComponente);

			i = preparedStatement.executeUpdate();
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;
	}
	
	@Override
	public synchronized int doUpdate(ComponenteBean product) throws SQLException {
		PreparedStatement preparedStatement = null;

		int i = 0;

		
		String deleteSQL = "UPDATE " + ComponenteModelDM.TABLE_NAME + " SET NOME = ?, DESCRIZIONE = ?, PREZZO = ?, DATAUSCITA = ?,  IMMAGINE  = ?, TIPO = ?, LINKACQUISTO = ? WHERE IDCOMPONENTE = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, product.getNome());
			preparedStatement.setString(2, product.getDescrizione());
			preparedStatement.setDouble(3, product.getPrezzo());
			preparedStatement.setString(4, product.getDataUscita());
			preparedStatement.setString(5, product.getImmagine());
			preparedStatement.setString(6, product.getTipo());
			preparedStatement.setString(7, product.getLinkAcquisto());
			preparedStatement.setInt(8, product.getIDComponente());
			

			i = preparedStatement.executeUpdate();
			connection.commit();
		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return i;
	}

	@Override
	public synchronized ArrayList<ComponenteBean> doRetrieveAll(String order) throws SQLException {
		PreparedStatement preparedStatement = null;

		ArrayList<ComponenteBean> products = new ArrayList<ComponenteBean>();

		String selectSQL = "SELECT * FROM " + ComponenteModelDM.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ComponenteBean bean = new ComponenteBean();

				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				bean.setNome(rs.getString("NOME"));
				bean.setPrezzo(rs.getFloat("PREZZO"));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setDataUscita(rs.getString("DATAUSCITA"));
				bean.setTipo(rs.getString("TIPO"));
				bean.setImmagine(rs.getString("IMMAGINE"));
				bean.setLinkAcquisto(rs.getString("LINKACQUISTO"));
				
				products.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return products;
	}
	
	
	//prende tutte le componenti di un dato tipo
	public synchronized ArrayList<ComponenteBean> doRetrieveByCond(String gen) throws SQLException {
		PreparedStatement preparedStatement = null;
		
		ArrayList<ComponenteBean> products = new ArrayList<ComponenteBean>();
		
		String selectSQL = "SELECT * FROM " + ComponenteModelDM.TABLE_NAME + " WHERE TIPO = ? ORDER BY Componente.Nome ASC;";
		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, gen);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				
				ComponenteBean bean = new ComponenteBean();
				
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				bean.setNome(rs.getString("NOME"));
				bean.setPrezzo(rs.getFloat("PREZZO"));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setDataUscita(rs.getString("DATAUSCITA"));
				bean.setTipo(rs.getString("TIPO"));
				bean.setImmagine(rs.getString("IMMAGINE"));
				bean.setLinkAcquisto(rs.getString("LINKACQUISTO"));
				
				
				products.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return products;
	}	
	
	//searchbar
	public synchronized ArrayList<ComponenteBean> doRetrieveBySearchTerm(String searchTerm) throws SQLException {
		PreparedStatement preparedStatement = null;
		
		ArrayList<ComponenteBean> products = new ArrayList<ComponenteBean>();
		
		try {
			Statement statement = connection.createStatement(); // Creating Statement

	        // Executing Statement
	        ResultSet rs = statement.executeQuery("SELECT * FROM " + ComponenteModelDM.TABLE_NAME + " WHERE Nome LIKE '%" + searchTerm + "%' OR Descrizione LIKE '%" + searchTerm + "%' OR Tipo LIKE '%" + searchTerm + "%' ORDER BY Componente.Nome ASC;");
			
			while (rs.next()) {
				
				ComponenteBean bean = new ComponenteBean();
				
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));
				bean.setNome(rs.getString("NOME"));
				bean.setPrezzo(rs.getFloat("PREZZO"));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setDataUscita(rs.getString("DATAUSCITA"));
				bean.setTipo(rs.getString("TIPO"));
				bean.setImmagine(rs.getString("IMMAGINE"));
				bean.setLinkAcquisto(rs.getString("LINKACQUISTO"));
				
				products.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return products;
	}
}