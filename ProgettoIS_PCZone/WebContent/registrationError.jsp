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
		<h4 class="p-4"> Registrazione errata. </h4>
		
		<a href="registration.jsp" class="btn btn-outline-danger mb-2">Riprova</a>
	</section>
	

   
 <div style="padding-bottom:28%">
 </div>	
    
    <%@ include file="../fragments/footer.jsp" %>  
    
</body>
</html>
