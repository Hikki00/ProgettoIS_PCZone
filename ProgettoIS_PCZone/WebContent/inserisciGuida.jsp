<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Registrazione Guida</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/registration.css" type="text/css"/>
	
	<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>
	
	<script src="/script/registrationVal.js"></script>		

	
</head>

	 <script>
	function checkTitolo(inputtxt) {
		var titolo = /^.{10,50}$/;
		if (inputtxt.value.match(titolo))
			return true;
		return false;
	}
	
	function checkDesc(inputtxt) {
		var desc = /^.{10,1500}$/;
		if (inputtxt.value.match(desc))
			return true;
		return false;
	}
	
	function validate(obj) {
		var valid = true;
		
			
		var titolo = document.getElementsByName("nome")[0];
			if(!checkTitolo(titolo)) {
				valid = false;
				alert("Titolo non valido! Deve contenere tra 10 e 50 caratteri.");
				titolo.focus();
			}
			
		var desc = document.getElementsByName("descrizione")[0];
			if(!checkDesc(desc)) {
				valid = false;
				alert("Descrizione non valida! Deve contenere tra 10 e 1500 caratteri.");
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
	<h2>Informazioni Guida</h2>
	<form id="dForm" action="regGuida" onsubmit="return validate(this)" method="post">
	<div class="form-group">
	<fieldset style="border: 1px solid rgb(0,0,0); width: 650px; margin: 0 auto; padding: 10px;">
	<label class="h4" for="nome">Titolo Guida:</label>
	<input class="form-control" type="text" name="nome" id="nome" placeholder="Titolo della componente" required>	
	<br>
	<label class="h4" for="descrizione">Descrizione:</label>
	<textarea class="form-control" rows="3" id="descrizione" placeholder="Descrizione della guida" name="descrizione" required></textarea>
	<br>
	<% 
                GenereModelDM genereDAO = new GenereModelDM(ConnectToDB.createDBConnection());
                ArrayList<GenereBean> generi = genereDAO.doRetrieveAll();
                for(GenereBean bean : generi){
            %>
                <label class="h4" for="<%=bean.getNome() %>"> <%=bean.getNome() %> </label>
                          <select name="<%=bean.getNome() %>">
                          	<option value="default"> </option>
                              <% 
                                  ComponenteModelDM prodottoDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
                                ArrayList<ComponenteBean> prodotti = prodottoDAO.doRetrieveByCond(bean.getNome());

                                for(ComponenteBean comp : prodotti){
                           	  %>
                                <option value="<%=comp.getIDComponente() %>"><%=comp.getNome() %></option>
                              <% 
                                }
                              %>
                            </select><br>
            <% 
      }
     %>
	<br>
	<div class="text-center">
	<input class="btn btn-lg btn-outline-danger text-center" type="submit" value="Completa guida">&nbsp;
    </div>
	</fieldset>
	</div>
	</form>
	<br>
	<br>
				
		
	<%@ include file="../fragments/footer.jsp" %>  

</body>
</html>
