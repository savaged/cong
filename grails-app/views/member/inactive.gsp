<html>
  <head>
    <title>Welcome to Groovy-Cong</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <h1 style="margin-left:20px;">Inactive Member Analysis</h1>
    <div class="nav">
      <span class="menuButton">
        <a href="/cong">Home</a>
      </span>
    </div>
    <p>&nbsp;</p>
    <p style="margin-left:20px;width:80%">
    Please enter the month and year for the inactive congregation member analysis.</p>
    <p>&nbsp;</p>
    <div style="margin-left:20px;width:60%;">
      <g:form action="inactive">
	<g:datePicker name="starting" precision="month" years="${2008..2014}" />
        <input type="submit" value="Submit" />
      </g:form>
      <span class="errors"><g:message code="${message}"></g:message></span>
    </div>
  </body>
</html>
