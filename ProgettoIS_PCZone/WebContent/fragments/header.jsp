<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<header>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
      <a class="navbar-brand" href="homepage.jsp">PC_ZONE</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
            <a class="nav-link" href="assemblaPC.jsp">Assembla PC</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="elencoGuide.jsp">Guide</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="catalogo.jsp">Catalogo</a>
          </li>
          <%
          // Check user credentials
          Boolean userAccess = (Boolean) session.getAttribute("userAccess");
            Boolean adminAccess = (Boolean) session.getAttribute("AdminAccess");
          if (userAccess == null & adminAccess == null)
          { //utente non ha fatto accesso	
          %>
          <li class="nav-item mr-auto">
            <a class="nav-link" href="registration.jsp">Registrati</a>
          </li>
           <li class="nav-item mr-auto">
            <a class="nav-link" href="login.jsp">Login</a>
          </li>
        </ul>
        <%  //utente ha fatto l'accesso 
         } else if (userAccess != null) {
        %>
         <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="true">
              Area Personale
            </a>
            <ul class="dropdown-menu " aria-labelledby="navbarDropdownMenuLink" data-bs-popper="static">
              <li><a class="dropdown-item" href="profiloUtente.jsp">Profilo</a></li>
              <li><a class="dropdown-item" href="wishpage.jsp">Favoriti</a></li>
            </ul>
         </li>
        <li class="nav-item">
          <a class="nav-link" href="Logout">Logout</a>
       </li>
      </ul>
       <%	
        } else if ((adminAccess != null)) {
        %>
        <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="profiloUtente.jsp">Area Personale</a>
        </li>
        <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="registrationAdmin.jsp">Registra Gestore</a>
        </li>
       <li class="nav-item">
        <a class="nav-link" href="Logout">Logout</a>
       </li>
       </ul>
       <%	
		} 
		%>
      </div>
    </div>
  </nav>
</header>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>