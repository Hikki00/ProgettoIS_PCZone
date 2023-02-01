package data;

import java.io.Serializable;
import java.util.Date;

public class GuidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int IDGuida;
	String titolo;
	Date data;
	String articolo;
	

	public GuidaBean() {
		IDGuida = -1;
		titolo = "";
		data = null;
		articolo = "";
	}

	
	
	public void setTitolo(String titol) {
		this.titolo = titol;
	}
	
	public String getTitolo() {
		return titolo;
	}

	public void setArticolo(String art) {
		this.articolo = art;
	}
	
	public String getArticolo() {
		return articolo;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date dat) {
		this.data = dat;
	}
	
	public int getIDGuida() {
		return IDGuida;
	}

	public void setIDGuida(int ID) {
		this.IDGuida = ID;
	}
	
	@Override
	public String toString() {
		return " (" + IDGuida + "), " + titolo + ". " + articolo;
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GuidaBean prod = (GuidaBean) o;

        return IDGuida == prod.IDGuida;
    }
}
