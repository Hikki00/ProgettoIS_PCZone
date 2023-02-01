package data;

import java.io.Serializable;
import java.util.Date;

public class ComponenteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int IDComponente;
	String nome;
	double Prezzo;
	String descrizione;
	String dataUscita;
	String Immagine;
	String tipo;
	String LinkAcquisto;
	
	int cancellato;
	

	public ComponenteBean() {
		IDComponente = -1;
		nome = "";
		descrizione = "";
		dataUscita = null;
		Prezzo = -1;
		cancellato = 0;
		Immagine = null;
		LinkAcquisto="";
		tipo = "";
	}

	public int getIDComponente() {
		return IDComponente;
	}

	public void setIDComponente(int ID) {
		this.IDComponente = ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String name) {
		this.nome = name;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String description) {
		this.descrizione = description;
	}

	public String getImmagine() {
		return Immagine;
	}

	public void setImmagine(String Immagine) {
		this.Immagine = Immagine;
	}
	
	public String getLinkAcquisto() {
		return LinkAcquisto;
	}

	public void setLinkAcquisto(String svil) {
		this.LinkAcquisto = svil;
	}
	
	public double getPrezzo() {
		return Prezzo;
	}

	public void setPrezzo(double price) {
		this.Prezzo = price;
	}

	public int getCancellato() {
		return cancellato;
	}
	
	public void setCancellato(int canc) {
		this.cancellato =  canc;
	}
	
	public String getDataUscita() {
		return dataUscita;
	}

	public void setDataUscita(String data) {
		this.dataUscita = data;
	}
	
	@Override
	public String toString() {
		return nome + " (" + IDComponente + "), " + Prezzo + " " + descrizione + ". " + dataUscita + ". " + cancellato;
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ComponenteBean prod = (ComponenteBean) o;

        return IDComponente == prod.IDComponente;
    }

}
