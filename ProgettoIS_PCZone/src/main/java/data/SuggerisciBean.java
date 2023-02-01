package data;

import java.io.Serializable;
import java.util.Date;

public class SuggerisciBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int IDGuida;
	int IDComponente;
	

	public SuggerisciBean() {
		IDGuida = -1;
		IDComponente = -1;
	}

	public int getIDComponente() {
		return IDComponente;
	}

	public void setIDComponente(int ID) {
		this.IDComponente = ID;
	}
	
	public int getIDGuida() {
		return IDGuida;
	}

	public void setIDGuida(int ID) {
		this.IDGuida = ID;
	}
	
	@Override
	public String toString() {
		return "( " + IDGuida + ", " + IDComponente + " )";
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SuggerisciBean prod = (SuggerisciBean) o;

        return IDGuida == prod.IDGuida && IDComponente == prod.IDComponente;
    }
}
