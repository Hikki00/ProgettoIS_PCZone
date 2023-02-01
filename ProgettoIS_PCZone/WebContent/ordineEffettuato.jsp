<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Ordine effettuato</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
</head>
<body>

	<%@ include file="../fragments/header.jsp" %> 
	
	<section class="text-center border border-danger border-3 m-3">
		<h4 class="p-4">Ordine effettuato!</h4>
	</section>
	
    
    <%@ include file="../fragments/footer.jsp" %> 
    <div style="padding-bottom:28%">
 </div>	 
</body>
</html>
