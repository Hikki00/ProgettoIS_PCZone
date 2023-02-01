
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Elenco guide</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
	<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>
	
</head>



<body style="background-color:#e5e5e5;">

		
	<%@ include file="../fragments/header.jsp" %>

<div>
  <div class="row">


<div class="col-sm-12">
		<div id="genereScelto">
			
		
		</div>

	<section class="text-center">
		
		<h3 class="p-2"> Elenco guide presenti 				
		<%	
			
				if(adminAccess != null){
		%>
					<a href="inserisciGuida.jsp" class="btn btn-primary btn-block btn-danger"> Inserisci guida </a>
		<%
					} 
		%>
		</h3> 	
		<div class="row row-cols-1 row-cols-md-3 g-4 p-2">
			<%
			GuidaModelDM guidaDAO = new GuidaModelDM(ConnectToDB.createDBConnection());
			ArrayList<GuidaBean> guide = guidaDAO.doRetrieveAll("Titolo");
					for(GuidaBean prod : guide){
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
					
				}
				
			%>
			
		</div>
		<hr>
		


	</section>
</div>	
	
	</div>
	</div>
	
	
	
	<%@ include file="../fragments/footer.jsp" %>  


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

   
</body>

</html>