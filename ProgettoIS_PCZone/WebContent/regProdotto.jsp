<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Registrazione componente</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/registration.css" type="text/css"/>
	
	<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>
	
	<script src="/script/registrationVal.js"></script>		

	
</head>
	<script>
	function checkTitolo(inputtxt) {
		var titolo = /^.{10,70}$/;
		if (inputtxt.value.match(titolo))
			return true;
		return false;
	}
	
	function checkDesc(inputtxt) {
		var desc = /^.{10,750}$/;
		if (inputtxt.value.match(desc))
			return true;
		return false;
	}
	
	function validate(obj) {
		var valid = true;
		
			
		var titolo = document.getElementsByName("nome")[0];
			if(!checkTitolo(titolo)) {
				valid = false;
				alert("Titolo non valido! Deve contenere tra 10 e 70 caratteri.");
				titolo.focus();
			}
			
		var desc = document.getElementsByName("descrizione")[0];
			if(!checkDesc(desc)) {
				valid = false;
				alert("Descrizione non valida! Deve contenere tra 10 e 750 caratteri.");
				desc.focus();
			}
			
		
			
		return valid;
	}
	</script>
	
<body>
	
	<%@ include file="../fragments/header.jsp" %>  
	<%
			if(session.getAttribute("AdminAccess") == null){
	%>
	<div id="NoAccess">
		<h2>Devi essere un gestore per acceddere a questa pagina.</h2>
	</div>
	<% }
	%>
	<body>
	 <br>
	<h2>Informazioni Componente</h2>
	<form id="infoForm" action="regProdotto" onsubmit="return validate(this)" method="post">
	<div class="form-group">
	<fieldset style="border: 1px solid rgb(0,0,0); width: 650px; margin: 0 auto; padding: 10px;">
	<label class="h4" for="nome">Nome componente:</label>
	<input class="form-control" type="text" name="nome" id="nome" placeholder="Nome della componente" required>	
	<br>
	<label class="h4" for="prezzo">Prezzo:</label>
	<input class="form-control" type="text" name="prezzo" id="prezzo" placeholder="Prezzo della componente" required>	
	<br>
	<label class="h4" for="descrizione">Descrizione:</label>
	<textarea class="form-control" rows="3" id="descrizione" placeholder="Descrizione della componente" name="descrizione" required></textarea>
	<br>
	<label class="h4" for="immagine">Immagine:</label>
	<input class="form-control" type="text" name="immagine" id="immagine" placeholder="URL immagine desiderata" required>	
	<br>
	<label class="h4" for="tipo">Tipo:</label>
  	<select name="tipo">
              <% 
                GenereModelDM genereDAO = new GenereModelDM(ConnectToDB.createDBConnection());
                ArrayList<GenereBean> generi = genereDAO.doRetrieveAll();
                for(GenereBean bean : generi){


            %>
                <option value="<%=bean.getNome() %>"><%=bean.getNome() %></option>
            <% 
                }
            %>
            </select>
	<br>
	<label class="h4" for="data">Data Uscita:</label>
	<input type="date" name="data" id="data"" required>	
	<br>
	<label class="h4" for="linkAcquisto">Link Acquisto:</label>
	<input class="form-control" type="text" name="acquisto" id="acquisto" placeholder="Link Acquisto:" required>	
	<br>
	<input class="btn btn-lg btn-outline-danger text-center" type="submit" value="Aggiungi componente">&nbsp;
    </div>
	</fieldset>
	</div>
	</form>
	<br>
	<br>
				
		
	<%@ include file="../fragments/footer.jsp" %>  

</body>
</html>
