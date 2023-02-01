package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GuidaModelDM implements GuidaModel {

	private static final String TABLE_NAME = "guida";
	private Connection connection;
	
	public GuidaModelDM(Connection conn) {
		this.connection = conn;
	}
	
	@Override
	public synchronized int doSave(GuidaBean news) throws SQLException {
		
		Date dat = new Date(0);
		PreparedStatement preparedStatement = null;
		int i = -1;
		String insertSQL = "INSERT INTO " + GuidaModelDM.TABLE_NAME
				+ " (TITOLO,DATA,ARTICOLO) VALUES (?, ?, ?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, news.getTitolo());
			preparedStatement.setDate(2, dat);
			preparedStatement.setString(3, news.getArticolo());
			
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
	public synchronized int doUpdate(GuidaBean guida) throws SQLException {
		
		int i = 0;
		PreparedStatement preparedStatement = null;

		String insertSQL = "UPDATE " + GuidaModelDM.TABLE_NAME
				+ " SET TITOLO = ? , ARTICOLO = ?  WHERE IDGuida = ?";

		try {
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, guida.getTitolo());	
			preparedStatement.setString(2, guida.getArticolo());
			preparedStatement.setInt(3, guida.getIDGuida());

			i = preparedStatement.executeUpdate();
			
			connection.commit();
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return i;	
	}
	
	public synchronized int doSaveSuggerisce(GuidaBean news,int prodottoID) throws SQLException {

		PreparedStatement preparedStatement = null;
		int i = -1;
		String insertSQL = "INSERT INTO " + "suggerisce"
				+ " (IDGuida,IDComponente) VALUES (?, ?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, news.getIDGuida());
			preparedStatement.setInt(2, prodottoID);
			
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
	
	public synchronized int doUpdateSuggerisce(int guidaID,int prodottoID, int compID) throws SQLException {

		PreparedStatement preparedStatement = null;
		int i = -1;
		String insertSQL = "UPDATE " + "suggerisce"
				+ " SET IDGuida = ? , IDComponente = ? WHERE IDGuida = ? AND IDComponente = ?";

		try {
			preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, guidaID);
			preparedStatement.setInt(2, prodottoID);
			preparedStatement.setInt(3, guidaID);
			preparedStatement.setInt(4, compID);
			
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
	public synchronized GuidaBean doRetrieveByKey(int IDGuida) throws SQLException {
		PreparedStatement preparedStatement = null;

		GuidaBean bean = new GuidaBean();
		String selectSQL = "SELECT * FROM " + GuidaModelDM.TABLE_NAME + " WHERE IDGuida = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDGuida);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setTitolo(rs.getString("Titolo"));
				bean.setIDGuida(rs.getInt("IDGuida"));
				bean.setData(rs.getDate("Data"));
				bean.setArticolo(rs.getString("Articolo"));
				
			}

		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return bean;
	}
	
	@Override
	public synchronized GuidaBean doRetrieveByTitolo(String titolo) throws SQLException {
		PreparedStatement preparedStatement = null;

		GuidaBean bean = new GuidaBean();
		String selectSQL = "SELECT * FROM " + GuidaModelDM.TABLE_NAME + " WHERE Titolo = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, titolo);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setTitolo(rs.getString("Titolo"));
				bean.setIDGuida(rs.getInt("IDGuida"));
				bean.setData(rs.getDate("Data"));
				bean.setArticolo(rs.getString("Articolo"));
				
			}

		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return bean;
	}
	

	@Override
	public synchronized boolean doDelete(int IDGuida) throws SQLException {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + GuidaModelDM.TABLE_NAME + " WHERE IDGuida = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, IDGuida);

			result = preparedStatement.executeUpdate();

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return (result != 0);
	}

	@Override
	public synchronized ArrayList<GuidaBean> doRetrieveAll(String order) throws SQLException {
		PreparedStatement preparedStatement = null;

		ArrayList<GuidaBean> news = new ArrayList<GuidaBean>();

		String selectSQL = "SELECT * FROM " + GuidaModelDM.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				GuidaBean bean = new GuidaBean();

				bean.setTitolo(rs.getString("Titolo"));
				bean.setIDGuida(rs.getInt("IDGuida"));
				bean.setData(rs.getDate("Data"));
				bean.setArticolo(rs.getString("Articolo"));
				
				news.add(bean);
			}

		} finally {
				if (preparedStatement != null)
					preparedStatement.close();
		}
		return news;
	}

	public synchronized ArrayList<ComponenteBean> doRetrieveByCond(int IDGuida) throws SQLException {
		PreparedStatement preparedStatement = null;
		
		ArrayList<ComponenteBean> listaConsigliata = new ArrayList<ComponenteBean>();
		
		String selectSQL = "SELECT C.IDComponente, C.Nome, C.Prezzo, C.Descrizione, C.DataUscita, C.Immagine, C.Tipo, C.LinkAcquisto FROM Componente AS C, Suggerisce, Guida WHERE Guida.IDGuida = ? AND Guida.IDGuida = Suggerisce.IDGuida AND Suggerisce.IDComponente = C.IDComponente ORDER BY C.Nome ASC;";
		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDGuida);
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
				
				listaConsigliata.add(bean);
			}

		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return listaConsigliata;
	}
	
	
	public synchronized ArrayList<SuggerisciBean> doRetrieveSuggerisceByCond(int IDGuida) throws SQLException {
		PreparedStatement preparedStatement = null;
		
		ArrayList<SuggerisciBean> listaConsigliata = new ArrayList<SuggerisciBean>();
		
		String selectSQL = "SELECT IDGuida,IDComponente FROM suggerisce WHERE IDGuida = ?";
		try {
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, IDGuida);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				
				SuggerisciBean bean = new SuggerisciBean();	
				bean.setIDGuida(rs.getInt("IDGUIDA"));
				bean.setIDComponente(rs.getInt("IDCOMPONENTE"));

				listaConsigliata.add(bean);			
			}
			//listaConsigliata.get(1).getIDComponente();
		} finally {
			if (preparedStatement != null)
				preparedStatement.close();
		}
		return listaConsigliata;
	}
	
}