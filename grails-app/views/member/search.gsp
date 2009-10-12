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
        <g:message code='info.savaged.cong.member.search.instruction' default='Please enter the last &amp; first name of a congregation member.' />
    </p>
    <p>&nbsp;</p>
    <div style="margin-left:20px;width:60%;">
      <g:form action="search" >
        Lastname <g:textField name="lastname" />
        Firstname <g:textField name="firstname" />
        <input type="submit" value="Search" />
      </g:form>
      <p>&nbsp;</p>
      <span class="errors">
        <g:message code="${flash.message}" args="${flash.args}" default="No match found"></g:message>
      </span>
    </div>
  </body>
</html>
