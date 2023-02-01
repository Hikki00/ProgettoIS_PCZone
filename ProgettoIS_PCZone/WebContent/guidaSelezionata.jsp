<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>



<%
	//prendiamo la guida dal DB
	int id = Integer.parseInt(request.getParameter("id"));
	GuidaModelDM pDAO = new GuidaModelDM(ConnectToDB.createDBConnection());
	GuidaBean guida = pDAO.doRetrieveByKey(id);
	
	//prendiamo le componenti consigliate
	 ArrayList<ComponenteBean> listaComponentiGuida = pDAO.doRetrieveByCond(guida.getIDGuida());
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title><%=guida.getTitolo()%></title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
</head>
<body style="background-color:#e5e5e5;">
		
	<%@ include file="../fragments/header.jsp" %>
<div>
  <div class="row">


<div class="col-sm-9">	
	

	
	<section class="text-center border border-dark border-2 m-2"> 
		<h2 class="p-2 m-2"> <%=guida.getTitolo()%>
				<%	
			
				if(adminAccess != null){
		%>

					<a href="modifGuida.jsp?id=<%=guida.getIDGuida() %>" class="btn btn-primary btn-block btn-danger"> Modifica guida </a>
		<%
					} 
		%></h2>
	</section>

	<section class="mt-2 border border-dark m-2 text-start">
	<h5 class="p-2 m-2"> <%=guida.getArticolo()%> </h5>
	
	</section>
	

	

	<section class="text-center mt-2 border border-dark m-2">
		<%	
				for(ComponenteBean bean : listaComponentiGuida){
		%>
					<fieldset style="padding: 10px;border: 1px solid rgb(0,0,0);" class="text-start m-5">
						<div class="cardProdotto">
							<h6><%= bean.getNome() %>  &euro;<%=Math.round((bean.getPrezzo() * 100.0) / 100) %> Tipo : <%= bean.getTipo() %></h6> 
							<a href="gamepage.jsp?id=<%=bean.getIDComponente() %>" class="btn btn-primary btn-block btn-danger"> Vai alla pagina della componente </a>
						</div>
					</fieldset>
					<%
					} 
					%>
	</section>
	

</div>
	
	<div class="col-sm-3 text-center p-2">
		<aside>
		
			<h2 class="text-center pt-2"> Immagini componenti </h2>
			<%	
			
					for(ComponenteBean bean : listaComponentiGuida){
			%>
						<div class="card-body" style="padding:0px;">
							<img src="<%= bean.getImmagine() %>" class="card-img-top img-fluid " alt="Foto non disponibile">    		
						</div>
						<br>
			<%
						} 
			%>
		</aside>
	</div>	

	</div>
</div>

	<%@ include file="../fragments/footer.jsp" %>  
		
	
</body>
</html>