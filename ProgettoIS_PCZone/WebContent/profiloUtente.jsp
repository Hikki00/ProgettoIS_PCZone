
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<!DOCTYPE html>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Info utente</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<%@ page import="data.UserModelDM,data.UserBean" %>
	<%
		UserBean userbean = (UserBean) session.getAttribute("user");	
	 %>
	 
	 <style>
	 .center {
		 margin-left: auto;
 		 margin-right: auto;
	  }
	 </style>
	 
	 <script>

	function showPass() {
		  var x = document.getElementById("psw");
		  var y = document.getElementById("psw2");
		  
		  if (x.type === "password" && y.type === "password") {
		    x.type = "text";
		    y.type = "text";
		  } else {
		    x.type = "password";
		    y.type = "password";
		  }
		  
		}
		
	</script>
	 <script>
	function checkEmail(inputtxt) {
		var email = /[a-zA-Z][a-zA-Z0-9\.]*@([a-zA-Z]+)\.[a-zA-Z]+/;
		if (inputtxt.value.match(email))
			return true;
		return false;
	}
	
	function checkDesc(inputtxt) {
		var desc = /^.{0,1500}$/;
		if (inputtxt.value.match(desc))
			return true;
		return false;
	}
	
	function validate(obj) {
		var valid = true;
		
			
		var email = document.getElementsByName("email")[0];
			if(!checkEmail(email)) {
				valid = false;
				alert("Email non valida!");
				email.focus();
			}
			
		var desc = document.getElementsByName("descrizione")[0];
			if(!checkDesc(desc)) {
				valid = false;
				alert("Descrizione troppo lunga! Il massimo è 1500 caratteri!");
				desc.focus();
			}
       		 
		return valid;
	}
</script>
</head>
<body>
	<%@ include file="../fragments/header.jsp" %>
	
	<br>
	<section>
	<fieldset style="border: 1px solid rgb(0,0,0); width: 650px; margin: 0 auto; padding: 10px;">	
	<h2 class="text-center">Informazioni User</h2>
	
	<div class="row">
	<div class="col">		
	<p class="h4 mt-3">Nickname: <%= userbean.getNickname() %>	</p>		
	<p class="h4 mt-3">Email: <%= userbean.getEmail() %></p>	
	<p class="h4 mt-3">Foto di profilo:<%= userbean.getFoto() %></p>						
	<br><p class="h4 mt-3">Descrizione:<%= userbean.getDescrizione() %></p>	 
	</div>
	<div class="col" >
	<br>
	<form id="infoForm" action="CambioInformazioni" onsubmit="return validate(this)" method="post">
	<input class="form-control mt-1" type="email" name="email" id="email" placeholder="mariorossi@gmail.com" >
	<input class="form-control mt-1" type="text" name="foto" id="foto" placeholder="Link Foto" >	
	<br>	
	<input class="form-control mt-1" type="text" name="descrizione" id="descrizione" placeholder="Desc" >
		
	</div>
	</div>
	<div class="text-center">
	<input class="btn btn-lg btn-outline-danger" type="submit" id="modInfo"  value="Modifica informazioni"> 
	</div>
	</form>

	<br>
	<table class="center">
	<form id="infoForm" action="CambioPassword" onsubmit="return validate(this)" method="post">
	<tr><td><input type="password" id="psw" name="psw" placeholder="Inserisci Password Vecchia" required></td><td> <input type="password" id="psw2" name="psw2" placeholder="Inserisci Password Nuova" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" title="Minimo 8 caratteri. Devono comprendere un numero,una lettera maiuscola e una lettera minuscola." required></td><td><input class="btn btn-sm btn-outline-danger" type="submit" id="changePass" value="Modifica Password"></td></tr>
	<tr><td><input  type="checkbox" onchange="showPass()">Show Password</td><td><label id="samePass" hidden>Le due password sono uguali. Prova a usare una nuova per continuare.</label></td></tr>
	</form>
	</table>
	</fieldset>
	
	</section>
	
	
	<%@ include file="../fragments/footer.jsp" %>  
	
			
</body>
</html>