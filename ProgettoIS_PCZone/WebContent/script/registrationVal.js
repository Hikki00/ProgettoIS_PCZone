

function checkCognome(inputtxt) {
	var cognome = /^[A-Za-z]+$/;
	if(inputtxt.value.match(cognome)) 
		return true;

	return false;	
}

function checkNome(inputtxt) {
	var nome = /^[A-Za-z]+$/;
	if(inputtxt.value.match(nome)) 
		return true;

	return false;	
}

function checkUsername(inputtxt) {
	var username = /^[a-z0-9_\.]+$/;
	if(inputtxt.value.match(username)) 
		return true;

	return false;	
}

function checkEmail(inputtxt) {
	var email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if(inputtxt.value.match(email)) 
		return true;
	
	return false;	
}

function checkPhonenumber(inputtxt) {
	var phoneno = /^([0-9]{3}-[0-9]{7})$/;
	if(inputtxt.value.match(phoneno)) 
		return true;
	
	return false;
}

function checkData(inputtxt) {
	var data = /^[0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
	if(inputtxt.value.match(data)) 
		return true;
	
	return false;
}


function validate(obj) {	
	var valid = true;	
	
	var cognome = document.getElementsByName("cognome")[0];
	if(!checkNamesurname(cognome)) {
		valid = false;
		name.classList.add("error");
	} else {
		name.classList.remove("error");
	}
	
	var username = document.getElementsByName("username")[0];
	if(!checkNamesurname(username)) {
		valid = false;
		name.classList.add("error");
	} else {
		name.classList.remove("error");
	}
	
	var nome = document.getElementsByName("nome")[0];
	if(!checkNamesurname(nome)) {
		valid = false;
		surname.classList.add("error");
	} else {
		surname.classList.remove("error");
	}
	
	var email = document.getElementsByName("email")[0];
	if(!checkEmail(email)) {
		valid = false;
		email.classList.add("error");
	} else {
		email.classList.remove("error");
	}	
	
	var numbers = document.getElementsByName("telefono");
	for(var i=0; i < numbers.length; i++) {
		if(!checkPhonenumber(numbers[i])) {
			valid = false;
			numbers[i].classList.add("error");
		} else  {
			numbers[i].classList.remove("error");
		}
	}
		
	var data = document.getElementsByName("dataNascita")[0];
	if(!checkEmail(data)) {
	valid = false;
	email.classList.add("error");
	} else {
	email.classList.remove("error");
	}	
	
	return valid;
 }
 

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