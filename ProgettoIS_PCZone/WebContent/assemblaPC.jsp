<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/genericError.jsp"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Assembla PC</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/input.css" type="text/css"/>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/registration.css" type="text/css"/>
	<%@ page import ="java.util.ArrayList,java.util.Date,java.text.SimpleDateFormat,data.*" %>
	
		

	
</head>

	
<body>
	
	<%@ include file="../fragments/header.jsp" %>  
	<%
		if(session.getAttribute("user") == null){
	%>
		<div id="NoAccess">
			<h2> Impossibile assemblare il pc, effettua l'accesso. </h2>
		</div>
	<% } else {
	%>
	<body>
	 <br>
	<h2>Assembla PC</h2>
	<form id="dForm" action="effettuaOrdine" method="post">
	<div class="form-group">
	<fieldset style="border: 1px solid rgb(0,0,0); width: 650px; margin: 0 auto; padding: 10px;">
	<br>
	<% 
				InsiemeProdotti listaProd = (InsiemeProdotti) session.getAttribute("carrello");
				ArrayList<ComponenteBean> componenti = listaProd.getProdotti();
		
                GenereModelDM genereDAO = new GenereModelDM(ConnectToDB.createDBConnection());
                ArrayList<GenereBean> generi = genereDAO.doRetrieveAll();
                for(GenereBean bean : generi){
            %>
                <label class="h4" for="<%=bean.getNome() %>"> <%=bean.getNome() %> </label>
                          <select name="<%=bean.getNome() %>">
                              <% 
                             	int trovato = 0;
                              	//se presente componente
                              	for(ComponenteBean compTest : componenti){
                              		if(compTest.getTipo().equals(bean.getNome())) {
                                      %>
                                      	<option value="<%=compTest.getIDComponente() %>"><%=compTest.getNome() %> &euro;<%=Math.round((compTest.getPrezzo() * 100.0) / 100)%></option>
                                      <% 
                                      trovato = 1;
                                      break;
                              		}
                              	}
                              	if(trovato == 0){
                              %>
                              	<option value="default"> </option>
                              <% 
                              	}
                              	
                                ComponenteModelDM prodottoDAO = new ComponenteModelDM(ConnectToDB.createDBConnection());
                                ArrayList<ComponenteBean> prodotti = prodottoDAO.doRetrieveByCond(bean.getNome());

                                for(ComponenteBean comp : prodotti){
                           	  %>
                                <option value="<%=comp.getIDComponente() %>"><%=comp.getNome() %> &euro;<%=Math.round((comp.getPrezzo() * 100.0) / 100)%></option>
                              <% 
                                }
                              %>
                            </select><br>
            <% 
      }
	}
     %>
	<br>
	<div class="text-center">
	<input class="btn btn-lg btn-outline-danger text-center" type="submit" value="Effettua ordine">&nbsp;
    </div>
	</fieldset>
	</div>
	</form>
	<br>
	<br>
				
		
	<%@ include file="../fragments/footer.jsp" %>  

</body>
</html>
