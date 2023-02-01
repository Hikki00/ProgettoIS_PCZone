package data;

import java.util.ArrayList;

//sarebbe il carrello delle componenti
public class InsiemeProdotti {
	private ArrayList<ComponenteBean> prodotti;
	
	public InsiemeProdotti(){
		prodotti = new ArrayList<ComponenteBean>();
	}
	
	public ArrayList<ComponenteBean> getProdotti() {
		return prodotti;
	}
	
	public void setProdotti(ArrayList<ComponenteBean> prodottiNew) {
		this.prodotti = prodottiNew;
	}
}