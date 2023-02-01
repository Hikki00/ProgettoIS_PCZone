
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<!DOCTYPE html>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Informazioni Componente</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	
	<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>
	
	<%
			if(session.getAttribute("AdminAccess") == null){
	%>
	<div id="NoAccess">
		<h2>Devi essere un gestore per accedere a questa pagina.</h2>
	</div>
	<% }
	%>
	 
	 <style>
	 .center {
		 margin-left: auto;
 		 margin-right: auto;
	  }
	 </style>
<%	 
	//prendiamo la guida dal DB
	int id = Integer.parseInt(request.getParameter("id"));
	ComponenteModelDM pDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
	ComponenteBean comp = pDAO.doRetrieveByKey(id);
	
%>
	 
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
	
	<br>
	<section>
	
		
	
	<fieldset style="border: 1px solid rgb(0,0,0); width: 650px; margin: 0 auto; padding: 10px;">
	
	<h2 class="text-center">Informazioni Componente</h2>
	

	<div class="col" >
	<form id="infoForm" action="modifInformComp" onsubmit="return validate(this)"  method="post">	<br>
	<label class="h4" for="nome">Nome Componente:</label>
	<input class="form-control" type="text" name="nome" value="<%= comp.getNome() %>" id="nome" placeholder="Nome Componente" required>	
	<br>
	<label class="h4" for="descrizione">Descrizione:</label>
	<textarea class="form-control" rows="3" id="descrizione" value="<%= comp.getDescrizione() %>" placeholder="Descrizione componentea" name="descrizione" required></textarea>
	<br>
	<label class="h4" for="prezzo">Prezzo:</label>
	<input class="form-control" id="prezzo" placeholder="Prezzo" value="<%= comp.getPrezzo() %>" name="prezzo" required></input>
	<br>
	<label class="h4" for="immagine">Immagine:</label>
	<input class="form-control"  id="immagine" placeholder="Immagine" value="<%= comp.getImmagine() %>" name="immagine" required></input>
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
	<input class="form-control"  id="data" placeholder="Data Uscita" name="data" value="<%= comp.getDataUscita() %>" required></input>
	<br>
	<label class="h4" for="linkAcquisto">Link Acquisto:</label>
	<input class="form-control" type="text" name="acquisto" id="acquisto" placeholder="Link Acquisto:" required>	
	<input  type="text" name="id" id="id" value="<%= comp.getIDComponente() %>" hidden>	
	<br>
	<div class="text-center"> 
	<input class="btn btn-lg btn-outline-danger"  type="submit" id="modInfo"  value="Modifica informazioni" >
	</form>
	<br>
	</table>
	</fieldset>
	
	</section>
	 <div style="padding-bottom:28%">
 	</div>	
	
	<%@ include file="../fragments/footer.jsp" %>  
	
		
</body>


</html>