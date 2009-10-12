<html>
  <head>
    <title>Welcome to Groovy-Cong</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <h1 style="margin-left:20px;">Service Report Totals</h1>
    <div class="nav">
      <span class="menuButton">
        <a href="/cong">Home</a>
      </span>
    </div>
    <p>&nbsp;</p>
    <p style="margin-left:20px;width:80%">
        <g:message code='info.savaged.cong.monthservicereporttotals.instructions' default='Please enter the start date for the month of service reports to total.'/>
    </p>
    <p>&nbsp;</p>
    <div style="margin-left:20px;width:60%;">
      <g:form action="index" >
        <g:datePicker name="starting" precision="month" years="${2008..2014}" />
        <input type="submit" value="Submit" />
      </g:form>
      <span class="errors"><g:message code="${message}"></g:message></span>
    </div>
  </body>
</html>
