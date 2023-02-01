package data;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public interface WishlistModel {
	
	public int doSave(WishlistBean wishlist) throws SQLException, ParseException;
	
	public int doSave(int IDWishlist, String username, int IDProdotto) throws SQLException;

	public boolean doDelete(int IDWishlist) throws SQLException;

	public boolean doDelete(String username) throws SQLException;

	public boolean doDelete(int IDWishlist, int IDProdotto) throws SQLException;

	public WishlistBean doRetrieveByKey(int IDWishlist) throws SQLException;
	
	public WishlistBean doRetrieveByKey(String username) throws SQLException;
	
	public ContieneBean doRetrieveByKey(int IDWishlist, int IDProdotto) throws SQLException;
	
	public ArrayList<ContieneBean> doRetrieveAll(int IDWishlist) throws SQLException;
}

