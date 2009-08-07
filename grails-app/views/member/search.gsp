<html>
  <head>
    <title>Welcome to Groovy-Cong</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <h1 style="margin-left:20px;">Member Search</h1>
    <div class="nav">
      <span class="menuButton">
        <a href="/cong">Home</a>
      </span>
    </div>
    <p>&nbsp;</p>
    <p style="margin-left:20px;width:80%">
    Please enter the first and last name of the congregation member.</p>
    <p>&nbsp;</p>
    <div style="margin-left:20px;width:60%;">
      <g:form action="search" >
        Firstname <g:textField name="firstname" />
        Lastname <g:textField name="lastname" />
        <input type="submit" value="Search" />
      </g:form>
      <span class="errors"><g:message code="${message}"></g:message></span>
    </div>
  </body>
</html>