
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.google.gson.Gson" %>
    
<%@ page import ="data.ComponenteModelDM,data.ComponenteBean,java.util.ArrayList,data.InsiemeProdotti,data.ComponenteBean,data.ComponenteModelDM" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Ricerca Componenti</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
	
</head>



<body style="background-color:#e5e5e5;">
		
	<%@ include file="../fragments/header.jsp" %>



	<section class="text-center">
		<h1 class="p-3">Componenti Ricercate</h1>
		
			<%
						InsiemeProdotti listaProd = (InsiemeProdotti) session.getAttribute("risRicerca");		
						  ArrayList<ComponenteBean> listaProdotti = listaProd.getProdotti();
						  System.out.println("arrivato");
						  if(listaProdotti.size() != 0){
					%>
					<div class="row row-cols-1 row-cols-md-3 g-4 p-2">	
						<%
							for(ComponenteBean prodotto : listaProdotti){
							%>
						    
						<div class="col ">
							    <div class="card mh-100 bg-dark text-white">
							    <h5 class="card-title m-0 p-2"><%= prodotto.getNome() %></h5>
							      <div class="card-body" style="padding:0px;">
							        <img src="<%= prodotto.getImmagine() %>" class="card-img-top img-fluid " alt="Foto non disponibile">
						    		<div class="d-grid">
									  <a href="gamepage.jsp?id=<%=prodotto.getIDComponente() %>" class="btn btn-primary btn-block btn-danger "> Visualizza </a>
									</div>
						    		
							      </div>
							    </div>
						</div>

						<%
				}
				%>
			</div>	
				<%
			  } else { 
				  %>	
				    <h4 class="p-4">Non sono state trovate componenti relativi alla ricerca fatta.</h4>
			<%  }
			%>
		
	</section>
	
	
	<%@ include file="../fragments/footer.jsp" %>  


</body>
</html>