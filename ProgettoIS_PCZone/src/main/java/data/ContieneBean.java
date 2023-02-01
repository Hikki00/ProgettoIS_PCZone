package data;

import java.io.Serializable;
import java.util.Date;

public class ContieneBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int IDWishlist;
	int IDComponente;
	

	public ContieneBean() {
		IDWishlist = -1;
		IDComponente = -1;
	}

	public int getIDWishlist() {
		return IDWishlist;
	}

	public void setIDWishlist(int ID) {
		this.IDWishlist = ID;
	}
	
	public int getIDComponente() {
		return IDComponente;
	}

	public void setIDComponente(int ID) {
		this.IDComponente = ID;
	}
	
	@Override
	public String toString() {
		return "( " + IDWishlist + ", " + IDComponente + " )";
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ContieneBean prod = (ContieneBean) o;

        return IDWishlist == prod.IDWishlist && IDComponente == prod.IDComponente;
    }
	

}
