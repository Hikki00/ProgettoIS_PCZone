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
		<h4 class="p-4"> Registrazione effettuata con successo. <br> Controlla la tua mail per confermare il profilo. <br> Puoi comunque continuare la navigazione nel sito usando il menù in alto.</h4>
	</section>
	

   
 <div style="padding-bottom:28%">
 </div>	
    
    <%@ include file="../fragments/footer.jsp" %>  
    
</body>
</html>
