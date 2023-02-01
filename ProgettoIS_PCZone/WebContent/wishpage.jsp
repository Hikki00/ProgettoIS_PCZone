<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Wishlist</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
</head>
<body>
		
	<%@ include file="../fragments/header.jsp" %>
	

	<section class="text-center">
		<%
		if(session.getAttribute("user") == null){
		%>
				<h4 class="p-4">Nessun login effettuato, impossibile mostrare wishlist.</h4>
		<%
		} else {
				WishlistModelDM wishlistDAO = new WishlistModelDM(ConnectToDB.createDBConnection());
				WishlistBean wish = wishlistDAO.doRetrieveByKey(((UserBean) session.getAttribute("user")).getNickname());
				
				ArrayList<ContieneBean> listaWish = wishlistDAO.doRetrieveAll(wish.getIDWishlist());
				
				if(listaWish.size() == 0) {
		%>
					<h4 class="p-4">La lista favoriti non presenta elementi. Esplora il catalogo per aggiungerci qualcosa!</h4>			
		<%
					} else {
					%>					
					<h4 class="p-4"> Wishlist di <%=wish.getNickname()%> </h4>
						
			<%
									ComponenteModelDM prodottoDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
																			for(ContieneBean wishItem : listaWish) {
																				ComponenteBean prod = prodottoDAO.doRetrieveByKey(wishItem.getIDComponente());
																				

									%>
					<fieldset style="padding: 10px;border: 1px solid rgb(0,0,0);" class="text-start m-5">
						<div class="cardProdotto">
							<h6><%= prod.getNome() %>  &euro;<%=Math.round((prod.getPrezzo() * 100.0) / 100) %> </h6>
							<a href="gamepage.jsp?id=<%=prod.getIDComponente() %>" class="btn btn-primary btn-block btn-danger"> Vai alla pagina della componente </a>
						</div>
					</fieldset>
			<%
					}
				}	
			}
		%>
	</section>
	

	
	<%@ include file="../fragments/footer.jsp" %>  
</body>
</html>