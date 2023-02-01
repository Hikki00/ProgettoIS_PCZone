<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Registra admin</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/registration.css" type="text/css"/>
	
	
	<script src="/script/registrationVal.js"></script>		

	
</head>

<body>
	
	<%@ include file="../fragments/header.jsp" %>  
	
	<script>
	
	function checkUsername(inputtxt) {
		var username = /[a-zA-Z0-9]{3,20}/;
		if (inputtxt.value.match(username))
			return true;
		return false;
	}
	
	function checkEmail(inputtxt) {
		var email = /[a-zA-Z][a-zA-Z0-9\.]*@([a-zA-Z]+)\.[a-zA-Z]+/;
		if (inputtxt.value.match(email))
			return true;
		return false;
	}
	
	function validate(obj) {
		var valid = true;
		
			
		var username = document.getElementsByName("username")[0];
			if (!checkUsername(username)) {
				valid = false;
				alert("Username non valido! Deve comprendere tra 3 e 20 caratteri e non deve avere contenere caratteri speciali!");
				username.focus();
			}
			
		var email = document.getElementsByName("email")[0];
			if(!checkEmail(email)) {
				valid = false;
				alert("Email non valida!");
				email.focus();
			}
       		 
		return valid;
	}
</script>

	<body>
	<br>
	<h2>Registrazione Nuovo Gestore</h2>
	<form id="dForm" action="RegistrationAdmin" onsubmit="return validate(this)" method="post">
	<div class="form-group">
	<fieldset style="border: 1px solid rgb(0,0,0); width: 650px; margin: 0 auto; padding: 10px;">
		<label  class="h4" for="username">Nickname:</label>
		<input class="form-control" type="text" name="username" id=""username"" placeholder=""Gestore123"" required>
		<br>
		<label class="h4" for="email">Email:</label>
		<input class="form-control" type="email" name="email" id="email" placeholder="Es: mariorossi@gmail.com" required>	
		<br>
		<label class="h4" for="pwd">Password:</label>
		<input class="form-control" type="password" id="psw" name="psw" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" title="Minimo 8 caratteri. Devono comprendere un numero,una lettera maiuscola e una lettera minuscola."  placeholder="Es: Password123" required>
		<br>

		<div class="text-center">
		<input class="btn btn-lg btn-outline-danger text-center" type="submit" value="Registra Gestore">&nbsp;
		</div>
		<div id="message" >
	  	<h3>Password must contain the following:</h3>
	 	 <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
	 	 <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
	 	 <p id="number" class="invalid">A <b>number</b></p>
	 	 <p id="length" class="invalid">Minimum <b>8 characters</b></p>
		</div>
	</fieldset>
	</div>
	</form>
	<br>
	<br>
	
	
				
		
	<%@ include file="../fragments/footer.jsp" %>  

</body>
</html>
