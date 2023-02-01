<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Login</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>



<body>
	
	<%@ include file="../fragments/header.jsp" %>  
	<br>
	<h2>Login Utente</h2>
	
	<form action="Login" method="post">
	<br> 
	  <div class="form-group   text-center ">
		<fieldset style="border: 1px solid rgb(0,0,0); width: 400px; margin: 0 auto; padding: 10px;">
		     <label for="email" class="h4">Email</label>
		     <input class="form-control   text-center " id="email" type="email" name="email" placeholder="Enter email" required> 
		     <br>    
		     <label for="password" class="h4">Password</label>
		     <input class="form-control   text-center " id="password" type="password" name="password" placeholder="Enter password" required> 
		     <br><br>
		     <input class="btn btn-lg btn-outline-danger" type="submit" value="Login"/>
		</fieldset>
	  </div>
	</form>
	

<form action="LoginFilter" method="post"> 

</form> 
 <div style="padding-bottom:28%">
 </div>	
	
	
	<%@ include file="../fragments/footer.jsp" %>  

</body>
</html>