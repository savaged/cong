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
    <h3 style="margin-left:20px;">
      Current service year report totals for ${serviceYearTotals?.publisher}
    </h3>
    <p>&nbsp;</p>
    <table>
       <tr>
         <th>Books</th>
         <th>Brochures</th>
         <th>Hours</th>
         <th>Magazines</th>
         <th>Return Visits</th>
         <th>Studies</th>
       </tr>
          <td>${serviceYearTotals?.books}</td>
          <td>${serviceYearTotals?.brochures}</td>
          <td>${serviceYearTotals?.hours}</td>
          <td>${serviceYearTotals?.magazines}</td>
          <td>${serviceYearTotals?.returnVisits}</td>
          <td>${serviceYearTotals?.studies}</td>
        </tr>
      </tr>
    </table>
  </body>
</html>
