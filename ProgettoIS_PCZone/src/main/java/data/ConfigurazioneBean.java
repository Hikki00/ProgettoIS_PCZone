package data;

import java.io.Serializable;
import java.sql.Timestamp;

public class ConfigurazioneBean implements Serializable {

	private static final long serialVersionUID = 1L;

	int IDOrdine;
	Timestamp dataCompletamento;
	String nickname;

	public ConfigurazioneBean() {
		IDOrdine = -1;
		dataCompletamento = null;
		nickname = "";
	}

	public int getIDOrdine() {
		return IDOrdine;
	}

	public void setIDOrdine(int ID) {
		this.IDOrdine = ID;
	}
	
	public Timestamp getDataCompletamento() {
		return dataCompletamento;
	}

	public void setDataCompletamento(Timestamp data) {
		this.dataCompletamento = data;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return " (" + IDOrdine + "), " + dataCompletamento + " " + nickname;
	}
	
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ConfigurazioneBean prod = (ConfigurazioneBean) o;

        return IDOrdine == prod.IDOrdine;
    }

}
