package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class WishlistBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int IDWishlist;
	String nickname;
	

	public WishlistBean() {
		IDWishlist = -1;
		nickname = null;
	}

	public int getIDWishlist() {
		return IDWishlist;
	}

	public void setIDWishlist(int ID) {
		this.IDWishlist = ID;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String usern) {
		this.nickname = usern;
	}
	
	@Override
	public String toString() {
		return " (" + IDWishlist + "), " + nickname;
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WishlistBean prod = (WishlistBean) o;

        return IDWishlist == prod.IDWishlist;
    }
}
