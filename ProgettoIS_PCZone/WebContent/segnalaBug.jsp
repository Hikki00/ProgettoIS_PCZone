<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

	<%@ page import="data.UserModelDM,data.UserBean" %>
	<%
		UserBean userbean = (UserBean) session.getAttribute("user");	
	 %>
	 
<head>
	<meta charset="ISO-8859-1">
	<title>Segnala Bug</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

	 <script>
	function checkTitolo(inputtxt) {
		var titolo = /^.{3,50}$/;
		if (inputtxt.value.match(titolo))
			return true;
		return false;
	}
	
	function checkDesc(inputtxt) {
		var desc = /^.{10,1000}$/;
		if (inputtxt.value.match(desc))
			return true;
		return false;
	}
	
	function validate(obj) {
		var valid = true;
		
			
		var titolo = document.getElementsByName("nome")[0];
			if(!checkTitolo(titolo)) {
				valid = false;
				alert("Titolo non valido! Deve contenere tra 3 e 50 caratteri.");
				titolo.focus();
			}
			
		var desc = document.getElementsByName("bug")[0];
			if(!checkDesc(desc)) {
				valid = false;
				alert("Descrizione non valida! Deve contenere tra 10 e 1000 caratteri.");
				desc.focus();
			}
       		 
		return valid;
	}
	</script>
<body>
	
	<%@ include file="../fragments/header.jsp" %>  
	<br>
	<h2>Segnala Bug</h2>
	
	<form action="InserireEmailQui" onsubmit="return validate(this)" method="post">
	<br> 
	  <div class="form-group   text-center ">
		<fieldset style="border: 1px solid rgb(0,0,0); width: 400px; margin: 0 auto; padding: 10px;">
			<label class="h4" for="nome">Titolo Segnalazione:</label>
			<input class="form-control" type="text" name="nome" id="nome" placeholder="Titolo della segnalazione" required>	
			<br>
			<label class="h4" for="bug">Descrizione:</label>
			<textarea class="form-control" rows="3" id="bug" placeholder="Descrizione del bug" name="bug" required></textarea>
			<br>
		     <input class="btn btn-lg btn-outline-danger" type="submit" value="Segnala bug"/>
		</fieldset>
	  </div>
	</form>
	
</form> 
 <div style="padding-bottom:28%">
 </div>	
	
	
	<%@ include file="../fragments/footer.jsp" %>  

</body>
</html>