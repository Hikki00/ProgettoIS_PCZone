<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Registrazione completa</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
</head>
<body>

	<%@ include file="../fragments/header.jsp" %> 
	
	<section class="text-center border border-danger border-3 m-3">
		<h4 class="p-4"> Essendo un profilo Gestore, seleziona il tipo di login. </h4>
		
		<form id="dForm" action="sceltaLoginControl" method="post">
			<input type="hidden" name="w" value="1" />
			<input  class="btn btn-lg btn-outline-danger text-center" type="submit" value="Utente">
			
		</form>
		<br>
		<form id="dForm" action="sceltaLoginControl" method="post">
			<input type="hidden" name="w" value="2" />
			<input  class="btn btn-lg btn-outline-danger text-center" type="submit" value="Gestore">
		</form>
		
	</section>
	

   
 <div style="padding-bottom:28%">
 </div>	
    
    <%@ include file="../fragments/footer.jsp" %>  
    
</body>
</html>
