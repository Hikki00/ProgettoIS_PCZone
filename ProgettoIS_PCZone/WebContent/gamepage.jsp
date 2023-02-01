<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>

<%
	//prendiamo il prodotto dal DB
	int id = Integer.parseInt(request.getParameter("id"));
	ComponenteModelDM pDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
	ComponenteBean prodotto = pDAO.doRetrieveByKey(id);
	String statoPage = request.getParameter("changeStato");
	if(session.getAttribute("carrello") == null){
		InsiemeProdotti prod = new InsiemeProdotti();
		session.setAttribute("carrello", prod);
	}
	InsiemeProdotti listaProd = (InsiemeProdotti) session.getAttribute("carrello");
	if(statoPage != null){
		//controlla se è stato aggiunto al carrello o meno
		if(statoPage.equals("Aggiungi al carrello")){
			ArrayList<ComponenteBean> prodotti = listaProd.getProdotti();
			if(prodotti.indexOf(prodotto) == -1) {
				prodotti.add(prodotto);
			}
			listaProd.setProdotti(prodotti);
		} else if(statoPage.equals("Annulla aggiunta")){
			ArrayList<ComponenteBean> prodotti = listaProd.getProdotti();
			prodotti.remove(prodotto);
			listaProd.setProdotti(prodotti);
		}
	}
	
	int prodottoComprato = 0;
	ArrayList<ComponenteBean> prodottiStato = listaProd.getProdotti();
	if(prodottiStato.indexOf(prodotto) != -1) {
		prodottoComprato = 1;
	}
	//controllo per la wishlist
	int presenteWish = 0;
	Boolean userAccesso = (Boolean) session.getAttribute("userAccess");
	if(userAccesso == null){
		System.out.println("nessun login");
	} else {
	WishlistModelDM wishlistDAO = new WishlistModelDM(ConnectToDB.createDBConnection());
	WishlistBean wish = wishlistDAO.doRetrieveByKey(((UserBean) session.getAttribute("user")).getNickname());
	
	if(wish.getIDWishlist() == -1){
		//non esiste una wish, la creo
		WishlistBean wishlistNew = new WishlistBean();
		wishlistNew.setNickname(((UserBean) session.getAttribute("user")).getNickname());
		wishlistDAO.doSave(wishlistNew);
		
		wish = wishlistDAO.doRetrieveByKey(((UserBean) session.getAttribute("user")).getNickname());
	}
	
	if(request.getParameter("changeWish") == null ) {
		ContieneBean contenuto = wishlistDAO.doRetrieveByKey(wish.getIDWishlist(), prodotto.getIDComponente());
		String testoWish = "";
		
		//dopo aver preso il prodotto, vediamo se esiste nella wish
		if(contenuto.getIDComponente() == -1) {
			testoWish = "Aggiungi alla wishlist";
		} else {
			testoWish = "Rimuovi dalla wishlist";
			presenteWish = 1;
		}
	} else {
		//già siamo stati nella pag e abbiamo premuto
		int wishCambio = Integer.parseInt(request.getParameter("w"));
		if(wishCambio == 1){
			presenteWish = 0;
			wishlistDAO.doDelete(wish.getIDWishlist(), prodotto.getIDComponente());
		} else {
			presenteWish = 1;
			wishlistDAO.doSave(wish.getIDWishlist(), wish.getNickname(), prodotto.getIDComponente());
		}
	}
	}
	//se abbiamo un parametro che ci dice di cambiare la wish allora modifichiamo
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title><%=prodotto.getNome()%></title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
</head>
<body style="background-color:#e5e5e5;">
		
	<%@ include file="../fragments/header.jsp" %>
<div>
  <div class="row">


<div class="col-sm-9">	
	

	
	<section class="text-center border border-dark border-2 m-2"> 
		<h2 class="p-2 m-2"> <%=prodotto.getNome()%>
		<%	
			
				if(adminAccess != null){
		%>
					<a href="modifComponente.jsp?id=<%=prodotto.getIDComponente() %>" class="btn btn-primary btn-block btn-danger"> Modifica componente </a>
		<%
					} 
		%>
		</h2>
		<img src="<%=prodotto.getImmagine()%>" style="max-width:80%;" class="mb-3" alt="immagine non disponibile">
	</section>

	<section class="mt-2 border border-dark m-2 text-start">
	<h5 class="p-2 m-2"> <%=prodotto.getDescrizione()%> </h5>
	
	</section>

	<section class="text-center mt-2 border border-dark m-2">
		<%
		if(userAccess == null) {
		%>
			<h2 class="p-2 m-2"> Non è possibile effettuare acquisti senza login. </h2>
		<%
		} else {
				InclusoModelDM inclusoDAO = new InclusoModelDM(ConnectToDB.createDBConnection());
				InclusoBean incl = inclusoDAO.doRetrieveByKey(prodotto.getIDComponente(), ( (UserBean) session.getAttribute("user")).getNickname()); 
				if(incl.getIDOrdine() != -1){
		%>
					<h2 class="p-2 m-2 border border-dark">
						Hai già comprato questo prodotto.
					</h2>
					<%
					} else {
					%>
				<div class="row g-3 p-2 mx-auto w-75">
					<%
					if(prodottoComprato == 0) {
					%>
						<form name="compraProdotto" action="gamepage.jsp" method="get" class="col-md-6">
							<input type="hidden" name="id" value="<%=prodotto.getIDComponente()%>" />
							<input type="submit" name="changeStato" value="Aggiungi al carrello" class="btn btn-primary btn-block btn-danger ">
						</form>
					<%
					} else {
					%>
						<form name="compraProdotto" action="gamepage.jsp?id=<%=prodotto.getIDComponente()%>" method="post" class="col-md-6">
							<input type="hidden" name="id" value="<%=prodotto.getIDComponente()%>" />
							<input type="submit" name="changeStato" value="Annulla aggiunta" class="btn btn-primary btn-block btn-danger ">
						</form>			
					<%
								}
																		//da mettere il controllo sulla wishlist
																			if(presenteWish == 0) {
								%>
							<form name="wishProdotto" action="gamepage.jsp" method="post" class="col-md-6">
								<input type="hidden" name="id" value="<%=prodotto.getIDComponente()%>" />
								<input type="hidden" name="w" value="<%=presenteWish%>" />
								<input type="submit" name="changeWish" value="Aggiungi alla wishlist" class="btn btn-primary btn-block btn-danger ">
							</form>
						<%
						} else {
						%>
							<form name="wishProdotto" action="gamepage.jsp" method="post" class="col-md-6">
								<input type="hidden" name="id" value="<%=prodotto.getIDComponente()%>" />
								<input type="hidden" name="w" value="<%=presenteWish%>" />
								<input type="submit" name="changeWish" value="Rimuovi dalla wishlist" class="btn btn-primary btn-block btn-danger ">
							</form>		
						<%
								}
								%>
					</div>
					<%
					}
										
									}
					%>
	</section>
	

	<br>
	<section class="text-center mt-2 border border-danger m-2 p-4">
	<div id="recensioniTotal">
			<%
			ReviewModelDM recensioniDAO = new ReviewModelDM(ConnectToDB.createDBConnection());
						ArrayList<ReviewBean> recensioni = recensioniDAO.doRetrieveByCond(prodotto.getIDComponente());
						

						if(recensioni.size() == 0) {
			%>
						<h2 class="p-2 m-2 border border-dark"> Non sono presenti recensioni per la componente </h2>
			<%
			} else {
			%>	
					<h2 class="p-2 m-2 border border-dark"> Recensioni presenti sulla componente </h2>
			<%
			for(ReviewBean bean : recensioni){
			%>
					<hr class="mx-auto w-75">
					<div class="reviewCard mx-auto w-75">
						<h2> <%=bean.getTitolo()%> </h2>
						<p> 
							Recensione di <%=bean.getNickname()%> || Data : <%=bean.getData()%> <br>
						 	<%=bean.getTesto()%> 
						 </p>
					</div>
					<hr class="mx-auto w-75">
			<%
			}
						}
			%>
	</div>
	<hr>
	<%
	if(userAccess == null){
	%>
			<h2 class="p-2 m-2 border border-dark"> Impossibile inserire una recensione, login non effettuato. </h2>
		<%
		} else {
			
				InclusoModelDM inclusoDAO = new InclusoModelDM(ConnectToDB.createDBConnection());
				InclusoBean incl = inclusoDAO.doRetrieveByKey(prodotto.getIDComponente(), ( (UserBean) session.getAttribute("user")).getNickname());
				
				if(incl.getIDOrdine() != -1){
		%>
		<h2 > Aggiungi una recensione! </h2>
		<div id="addRec">
			<input type="hidden" id="userRecensione" value="<%=((UserBean) session.getAttribute("user")).getNickname()%>">
			<input type="hidden" id="idProdottoRecensione" value="<%=prodotto.getIDComponente()%>">
			<div class="mb-3 text-start p-5 pt-0 pb-0">
			  <label for="formGroupExampleInput" class="form-label ">Titolo</label>
			  <input type="text" class="form-control" id="titoloRecensione" placeholder="inserisci titolo">
			</div>
			<div class="mb-3 text-start p-5 pt-0 ">
			  <label for="formGroupExampleInput2" class="form-label">Descrizione</label>
			  <textarea class="form-control" rows="5" id="testoRecensione" name="text" ></textarea>
			</div>
			<input type="button" class="btn btn-primary" onclick="funzione()" value="Aggiungi recensione">
		
	
		</div>
	
	<%
		} else {
		%>
		<h2> Impossibile inserire una recensione, la componente non è mai stata ordinata. </h2>
	<%
	}
		}
	%>
	
	</section>
	</div>
	
	<div class="col-sm-3 text-center p-2">
		<aside>

	</aside>
</div>	

	</div>
</div>

	<%@ include file="../fragments/footer.jsp" %>  
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script>
        
        function funzione(){

        	  const titolo = document.getElementById("titoloRecensione").value;
        	  console.log(titolo);
        	  const testo = document.getElementById("testoRecensione").value;
        	  console.log(testo);
        	  const idprodotto = document.getElementById("idProdottoRecensione").value;
        	  const user = document.getElementById("userRecensione").value;
        	  
        	  if(titolo == "" || testo == ""){
        		  window.alert("inserisci un titolo e una descrizione");
        		  return;
        	  }
        	   
              $.ajax({
                url: "AggiungiRecensione",
                type: "Post",
                dataType: "json",
                data: {"id":idprodotto,"titolo":titolo,"testo":testo,"username":user},
                success : function(data)
                    {
                		//nella servlet bisogna 1)aggiungere la recensione al db 2)prendere tutti con il retrieve all 3) passarla qui e stamparli
                		window.alert("Recensione aggiunta!");
                		
                		const container = document.getElementById('recensioniTotal');
                		container.innerHTML = "";
                		for(i = 0; i < data.length; i++) {
                			// Create card element
	                		  const card = document.createElement('div');
	                		  card.classList = 'card-body';
	      					
	                		  // Construct card content
	                		  var content = '<hr class="mx-auto w-75"> <div class="reviewCard mx-auto w-75"> <h2> ' + data[i].titolo + ' </h2> <p> Recensione di ' + data[i].username + ' || Data : ' + data[i].dataCreazione + ' <br>' + data[i].testo + ' </p></div><hr class="mx-auto w-75">';
	                		  
	                		  // Append newyly created card element to the container
	                		  container.innerHTML += content;
                		}
                		
                		
    					
                	
                    }   
		});
        }
	</script>	
	
	<div style="padding-bottom:28%">
 </div>	
</body>
</html>