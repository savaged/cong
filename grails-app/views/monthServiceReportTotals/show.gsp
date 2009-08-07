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
      Service report totals for
      <g:formatDate format="yyyy-MM" date="${flash.starting}" />
    </h3>
    <p>&nbsp;</p>
    <table>
      <tr>
        <th>Aggregation</th>
        <th>Count</th>
        <th>Books</th>
        <th>Brochures</th>
        <th>Hours</th>
        <th>Magazines</th>
        <th>Return Visits</th>
        <th>Studies</th>
      </tr>
      <g:each status="i" var="row" in="${serviceReportTotals?.rows}">
        <tr class="${ (i % 2) == 0 ? 'even' : 'odd'}">
          <td>${row?.category}</td>
          <td>${row?.publishers}</td>
          <td>${row?.books}</td>
          <td>${row?.brochures}</td>
          <td>${row?.hours}</td>
          <td>${row?.magazines}</td>
          <td>${row?.returnVisits}</td>
          <td>${row?.studies}</td>
        </tr>
      </g:each>
      <tr>
        <td>Active publishers</td>
        <td colspan="7">${serviceReportTotals.activePubCount}</td>
      </tr>
    </table>
  </body>
</html>