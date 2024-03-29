<%@ page contentType="text/html; charset=US-ASCII" %>
<%@ page isErrorPage="true" %>

<html>

<head>
	<title>Errore</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/structure.css" type="text/css"/>
</head>

<body>

	<%@ include file="../fragments/header.jsp" %>

	<div id="err" class="clear">
		<p>
			<b>An exception was raised!</b><br/>
			<%= exception.toString() %>
		</p>
		<p>
			<b>Exception message is:</b><br/>
			<%= exception.getMessage() %>
		</p>
		<p>
			<b>Stacktrace is:</b><br/>
<%
			// this will send trace to the browser's screen
			exception.printStackTrace(new java.io.PrintWriter(out));
			// this will send it to the log file
			exception.printStackTrace();
%>
		</p>

	</div>

	<%@ include file="../fragments/footer.jsp" %>

</body>
</html>