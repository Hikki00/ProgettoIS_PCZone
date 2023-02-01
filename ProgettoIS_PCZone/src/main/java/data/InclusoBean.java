package data;

import java.io.Serializable;
import java.util.Date;

public class InclusoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int IDOrdine;
	int IDComponente;
	

	public InclusoBean() {
		IDOrdine = -1;
		IDComponente = -1;
	}

	public InclusoBean(int IDOrdine, int IDComponente) {
		this.IDOrdine = IDOrdine;
		this.IDComponente = IDComponente;
	}
	
	public int getIDComponente() {
		return IDComponente;
	}

	public void setIDComponente(int ID) {
		this.IDComponente = ID;
	}
	
	public int getIDOrdine() {
		return IDOrdine;
	}

	public void setIDOrdine(int ID) {
		this.IDOrdine = ID;
	}
	
	@Override
	public String toString() {
		return "( "+ IDOrdine + ", " +IDComponente + ")";
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        InclusoBean prod = (InclusoBean) o;

        return IDComponente == prod.IDComponente && IDOrdine == prod.IDOrdine ;
    }
}
