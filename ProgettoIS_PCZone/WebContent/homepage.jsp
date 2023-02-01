
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Homepage</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
	
</head>



<body style="background-color:#e5e5e5;">

<%
	//scelta componenti dinamica
	ComponenteModelDM prodottiDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
	ArrayList<ComponenteBean> prodottiGenere = null;
%>
		
	<%@ include file="../fragments/header.jsp" %>

<div>
  <div class="row">


<div class="col-sm-12">

	<section class="text-center">
		<h1 class="p-3">Guide consigliate</h1>
		<div class="row row-cols-1 row-cols-md-3 g-4 p-2">
			<%
			GuidaModelDM guidaDAO = new GuidaModelDM(ConnectToDB.createDBConnection());
			ArrayList<GuidaBean> guide = guidaDAO.doRetrieveAll("Titolo");
			int conteggio = 0;
					for(GuidaBean prod : guide){
						conteggio++;
					%>
						<div class="col ">
							    <div class="card mh-100 bg-dark text-white">
							    <h5 class="card-title m-0 p-2"><%= prod.getTitolo() %></h5>
							      <div class="card-body" style="padding:0px;">
						    		<div class="d-grid">
									  <a href="guidaSelezionata.jsp?id=<%=prod.getIDGuida() %>" class="btn btn-primary btn-block btn-danger ">Visualizza guida</a>
									</div>
						    		
							      </div>
							    </div>
						</div>
					
					<% 
						if(conteggio == 3)
							break;
				}
				
			%>
						<div class="col ">
							    <div class="card mh-100 bg-dark text-white">
							      <div class="card-body" style="padding:0px;">
						    		<div class="d-grid">
									  <a href="elencoGuide.jsp" class="btn btn-primary btn-block btn-danger ">Visualizza elenco guide</a>
									</div>
						    		
							      </div>
							    </div>
						</div>
		</div>
		
		<hr>
		
		<h1 class="p-3">Ultime componenti</h1>
		<div class="row row-cols-1 row-cols-md-3 g-4 p-2">
			<%
			ArrayList<ComponenteBean> prodotti = prodottiDAO.doRetrieveAll("DataUscita");
			conteggio = 0;
					for(ComponenteBean prod : prodotti){
			
						conteggio++;
					%>
						<div class="col ">
							    <div class="card mh-100 bg-dark text-white">
							    <h5 class="card-title m-0 p-2"><%= prod.getNome() %></h5>
							      <div class="card-body" style="padding:0px;">
							        <img src="<%= prod.getImmagine() %>" class="card-img-top img-fluid " alt="Foto non disponibile">
						    		<div class="d-grid">
									  <a href="gamepage.jsp?id=<%=prod.getIDComponente() %>" class="btn btn-primary btn-block btn-danger "> Visualizza </a>
									</div>
						    		
							      </div>
							    </div>
						</div>
					
					<% 
						if(conteggio == 3)
							break;
				}
				
			%>
						<div class="col ">
							    <div class="card mh-100 bg-dark text-white">
							      <div class="card-body" style="padding:0px;">
						    		<div class="d-grid">
									  <a href="catalogo.jsp" class="btn btn-primary btn-block btn-danger ">Visualizza catalogo completo</a>
									</div>
						    		
							      </div>
							    </div>
						</div>
		</div>
		
		
		
		
		
		
		
		
		
		


	</section>
</div>	
	
	</div>
	</div>
	
	
	
	<%@ include file="../fragments/footer.jsp" %>  


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

   
</body>

</html>