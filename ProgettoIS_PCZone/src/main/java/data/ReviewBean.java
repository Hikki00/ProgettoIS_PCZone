package data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ReviewBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String titolo;
	int punteggio;
	String testo;
	Timestamp dataCreazione;
	int IDComponente;
	String nickname;
	

	public ReviewBean() {
		IDComponente = -1;
		titolo = "";
		dataCreazione = null;
		testo = "";
		nickname = "";
		punteggio = -1;
	}

	
	public int getIDComponente() {
		return IDComponente;
	}

	public void setIDComponente(int ID) {
		this.IDComponente = ID;
	}
	
	public void setTitolo(String titol) {
		this.titolo = titol;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public String getTesto() {
		return testo;
	}

	public void setTesto(String test) {
		this.testo = test;
	}
	
	public Timestamp getData() {
		return dataCreazione;
	}

	public void setData(Timestamp data) {
		this.dataCreazione = data;
	}

	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String user) {
		this.nickname = user;
	}
	
	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punt) {
		this.punteggio = punt;
	}
	
	@Override
	public String toString() {
		return "(" + IDComponente + ". " + titolo + "), " + punteggio + " " + testo + ". " + dataCreazione + ". " + nickname;
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReviewBean prod = (ReviewBean) o;

        return IDComponente == prod.IDComponente && titolo.equals(prod.titolo);
    }
}
