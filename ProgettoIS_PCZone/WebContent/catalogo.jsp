
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Catalogo</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
	<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>
	
</head>



<body style="background-color:#e5e5e5;">

<%
	//scelta componenti dinamica
	ComponenteModelDM prodottiDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
	ArrayList<ComponenteBean> prodottiGenere = null;
	boolean genScelto = false;
	if(request.getParameter("genereScelto") != null){
		prodottiGenere = prodottiDAO.doRetrieveByCond(request.getParameter("genereScelto"));
		genScelto = true;
	}
%>
		
	<%@ include file="../fragments/header.jsp" %>

<div>
  <div class="row">


<div class="col-sm-9">
		<div id="genereScelto">
			
		
		</div>

	<section class="text-center">
		<h3 class="p-2"> Componenti presenti nel sito 				
		<%	
			
				if(adminAccess != null){
		%>
					<a href="regProdotto.jsp" class="btn btn-primary btn-block btn-danger"> Inserisci componente </a>
		<%
					} 
		%></h3> 	
		<div class="row row-cols-1 row-cols-md-4 g-3 p-2">
			<%
			ArrayList<ComponenteBean> prodotti = prodottiDAO.doRetrieveAll("IDComponente");
					for(ComponenteBean prod : prodotti){
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
					
				}
				
			%>
			
		</div>
		<hr>
		


	</section>
</div>

<div class="col-sm-3">
	<aside  style="background-color:#e5e5e5;">
	    
	    <form action="searchBar" method="post" class="d-flex text-center" role="search">
          <input class="form-control me-2" name="searchTerm" id="searchTerm" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-light" type="submit">Search</button>
        </form>
        
        
        
		<h2 class="text-center pt-2"> Generi disponibili </h2>
		<ul class="list-group">
			<%
			//creare query per prendere tutti i tipi distinct di componenti?
				GenereModelDM genereDAO = new GenereModelDM(ConnectToDB.createDBConnection());
				ArrayList<GenereBean> generi = genereDAO.doRetrieveAll();
				for(GenereBean bean : generi){
			%>
				  <li class="list-group-item" style="background-color:#e5e5e5;">
				    <input type="radio" name="generi" class="genere form-check-input me-0" value="<%= bean.getNome() %>" onclick="funzione(this.value)">
				    <%= bean.getNome() %> <br>
				  </li>
				
				
			<%
				}
				
			%>
			<li class="list-group-item" style="background-color:#e5e5e5;">
				    <input type="radio" id="saliGen" name="generi" class="genere form-check-input me-0" value="annulla" onclick="salisu()">
				    Annulla selezione<br>
			</li>
		</ul>
	</aside>
</div>	
	
	</div>
	</div>
	
	
	
	<%@ include file="../fragments/footer.jsp" %>  


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>


function salisu(){
    $("#genereScelto").slideUp("slow");
	
}
        
        
        function funzione(selected){
        	$("#genereScelto").slideUp("fast");
              $.ajax({
                url: "CambioGenere",
                type: "Post",
                dataType: "json",
                data: {"genereScelto":selected},
                success : function(data)
                    {
                		var aggiunto = 0;
                		const container = document.getElementById('genereScelto');
                		container.classList = 'text-center';
                		var pre = '<fieldset style="padding: 10px;border: 1px solid rgb(0,0,0);" class="m-4"> <h3 class="p-2">Componenti di tipo ' + selected + '</h3> <div class="row row-cols-1 row-cols-md-4 g-3 p-2"> ';
                		var bod = '';
                		for(i = 0; i < data.length; i++) {
	                		  
								  aggiunto++;
								  // Create card element
								  const card = document.createElement('div');
	                		  		card.classList = 'card-body';
	       
								  // Construct card content
		                		  var content = '<div class="col "><div class="card mh-100 bg-dark text-white"><h5 class="card-title m-0 p-2">' +  data[i].nome + ' </h5> <div class="card-body" style="padding:0px;"><img src="' + data[i].Immagine + '" class="card-img-top img-fluid " alt="Foto non disponibile"><div class="d-grid"><a href="gamepage.jsp?id='+ data[i].IDComponente +'" class="btn btn-primary btn-block btn-danger "> Visualizza </a></div></div></div></div>'; 
		
		                		  // Append newyly created card element to the container
		                		  bod += content;
	                		  
                		}
                		
                		container.innerHTML = pre + bod + ' </div> </fieldset>';
                		
                		if(data.length == 0 || aggiunto == 0) {
                			container.innerHTML = '<fieldset style="padding: 10px;border: 1px solid rgb(0,0,0);" class="m-4"> <h3 class="p-2"> Nessuna componente del tipo scelto </h3> </fieldset>';
                		}
						           		
                	
                    }   
		});
              
              $("#genereScelto").slideDown("slow");
        }
</script>
   
   
   
   
</body>

</html>