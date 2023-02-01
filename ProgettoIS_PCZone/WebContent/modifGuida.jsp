
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<!DOCTYPE html>

<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Informazioni Guida</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/structure.css" type="text/css"/>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	
	<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>
	<%
			if(session.getAttribute("AdminAccess") == null){
	%>
	<div id="NoAccess">
		<h2>Devi essere un gestore per acceddere a questa pagina.</h2>
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
	GuidaModelDM pDAO = new GuidaModelDM(ConnectToDB.createDBConnection());
	GuidaBean guida = pDAO.doRetrieveByKey(id);
	
	//prendiamo le componenti consigliate
	 ArrayList<SuggerisciBean> listaComponenti = pDAO.doRetrieveSuggerisceByCond(id);
	 
	%>

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
	
	<br>
	<section>
	
		
	
	<fieldset style="border: 1px solid rgb(0,0,0); width: 650px; margin: 0 auto; padding: 10px;">
	
	<h2 class="text-center">Informazioni Guida</h2>
	
	<div class="row">
	<div class="col" >
	<form id="infoForm" action="modifInformGuida" onsubmit="return validate(this)"  method="post">	<br>
	<label class="h4" for="nome">Titolo Guida:</label>
	<input class="form-control" type="text" name="nome" value="<%= guida.getTitolo() %>" id="nome" placeholder="Titolo della componente" required>	
	<br>
	<label class="h4" for="descrizione">Descrizione:</label>
	<textarea class="form-control" rows="3" id="descrizione" value="<%= guida.getArticolo() %>" placeholder="Descrizione della guida" name="descrizione" required></textarea>
	<br>
	<br>
	<% 
                GenereModelDM genereDAO = new GenereModelDM(ConnectToDB.createDBConnection());
                ArrayList<GenereBean> generi = genereDAO.doRetrieveAll();
                for(GenereBean bean : generi){
            %>
                <label class="h4" for="<%=bean.getNome() %>"> <%=bean.getNome() %> </label>
                          <select name="<%=bean.getNome() %>">
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
	<input  type="text" name="id" id="id" value="<%= guida.getIDGuida() %>" hidden>
	<div class="text-center"> 
	<input class="btn btn-lg btn-outline-danger"  type="submit" id="modInfo"  value="Modifica informazioni" >
	</div>
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