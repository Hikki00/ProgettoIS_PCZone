<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Registration</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/registration.css" type="text/css"/>
			
</head>

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
	
	function checkPassword(inputtxt,conf) {
		var passw = /^.{8,30}$/;
		if (inputtxt.value.match(passw) && inputtxt.value.match(conf.value))
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
	        
	   var pwdConf = document.getElementsByName("conferma")[0];
	    var pwd = document.getElementsByName("psw")[0];
	        if(!checkPassword(pwd,pwdConf)) {
	            valid = false;
	            alert("Password non valida oppure conferma password non è uguale alla password!'");
	            pwd.focus();
       		 }
       		 
		return valid;
	}
</script>
<body>
	
	<%@ include file="../fragments/header.jsp" %>  
	<br> 
	<h2>Registration</h2>
	<form id="dForm" action="Registration" onsubmit=" return validate(this)" method="post">
	<div class="form-group">
	<fieldset style="border: 1px solid rgb(0,0,0); width: 650px; margin: 0 auto; padding: 10px;">
	<label  class="h4" for="username">*Nickname:</label>
	<input class="form-control" type="text" name="username" placeholder="Nickname213" required>	
	<br>
    <label  class="h4" for="email">*Email:</label>
	<input class="form-control" type="email" name="email" placeholder="mariorossi@gmail.com" required>	
    <label  class="h4" for="psw">*Password:</label>
	<input class="form-control" type="password" id="psw" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,30}" name="psw" placeholder="Password123" title="Minimo 8 caratteri. Devono comprendere un numero,una lettera maiuscola e una lettera minuscola." required>
	
	<label  class="h4" for="conferma">* Conferma Password:</label>
	<input class="form-control" type="password" id="conferma" name="conferma" placeholder="Conferma la password inserita" required>
	<input  type="checkbox" onchange="showPass()">Show Password
	
	<br>
	<div id="message">
  	<h3>La Password deve contenere le seguenti:</h3>
 	 <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
 	 <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
 	 <p id="number" class="invalid">A <b>number</b></p>
 	 <p id="length" class="invalid">Minimum <b>8 characters</b></p>
	</div>
	<div class="text-center">
    <input  class="btn btn-lg btn-outline-danger text-center" type="submit" value="Registrati">
    </div >
	</fieldset>
	</div>
	</form>
	<br>
	
	
				
		
	<%@ include file="../fragments/footer.jsp" %>  
    	<script> 	
   	
    	var myInput = document.getElementById("psw");
    	var letter = document.getElementById("letter");
    	var capital = document.getElementById("capital");
    	var number = document.getElementById("number");
    	var length = document.getElementById("length");

    	myInput.onfocus = function() {
    	  document.getElementById("message").style.display = "block";
    	}

    	myInput.onblur = function() {
    	  document.getElementById("message").style.display = "none";
    	}

    	myInput.onkeyup = function() {
    	  var lowerCaseLetters = /[a-z]/g;
    	  if(myInput.value.match(lowerCaseLetters)) {  
    	    letter.classList.remove("invalid");
    	    letter.classList.add("valid");
    	  } else {
    	    letter.classList.remove("valid");
    	    letter.classList.add("invalid");
    	  }

    	  var upperCaseLetters = /[A-Z]/g;
    	  if(myInput.value.match(upperCaseLetters)) {  
    	    capital.classList.remove("invalid");
    	    capital.classList.add("valid");
    	  } else {
    	    capital.classList.remove("valid");
    	    capital.classList.add("invalid");
    	  }

    	  var numbers = /[0-9]/g;
    	  if(myInput.value.match(numbers)) {  
    	    number.classList.remove("invalid");
    	    number.classList.add("valid");
    	  } else {
    	    number.classList.remove("valid");
    	    number.classList.add("invalid");
    	  }
    	  
    	  // Validate length
    	  if(myInput.value.length >= 8) {
    	    length.classList.remove("invalid");
    	    length.classList.add("valid");
    	  } else {
    	    length.classList.remove("valid");
    	    length.classList.add("invalid");
    	  }
    	}
    	
    	function showPass() {
		  var x = document.getElementById("psw");
		  var y = document.getElementById("conferma");
		  
		  if (x.type === "password" && y.type === "password") {
		    x.type = "text";
		    y.type = "text";
		  } else {
		    x.type = "password";
		    y.type = "password";
		  }
		  
		}
	</script>
</body>
</html>
