package data;

import java.io.Serializable;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	String Nickname;
	String password;
	String email;
	String foto;
	String descrizione;
	int isGestore;
	
	

	public UserBean() {
		email = "";
		Nickname = "";
		password = "";
		foto = "";
		descrizione = "";
		isGestore = 0;
	}

	public int getIsGestore() {
		return isGestore;
	}

	public void setIsGestore(int id) {
		this.isGestore = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String em) {
		this.email = em;
	}
	
	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String ind) {
		this.Nickname = ind;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String em) {
		this.foto = em;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String em) {
		this.descrizione = em;
	}
	
	@Override
	public String toString() {
		return " (" + Nickname + "), " + password + ". " + email + ". " + foto + ". " + descrizione;
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserBean prod = (UserBean) o;

        return Nickname.equals(prod.Nickname);
    }
}
