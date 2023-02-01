package data;

import java.io.Serializable;

public class GenereBean implements Serializable {

	private static final long serialVersionUID = 1L;

	String Nome;
	

	public GenereBean() {
		Nome = "";
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		this.Nome = nome;
	}
	
	@Override
	public String toString() {
		return " (" + Nome + ")";
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GenereBean prod = (GenereBean) o;

        return Nome.equals(prod.Nome);
    }
}
