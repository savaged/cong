<html>
<head>
  <title>Welcome to Groovy-Cong</title>
  <meta name="layout" content="main" />
</head>
<body>
<h1 style="margin-left:20px;">Welcome to Groovy-Cong</h1>
<p style="margin-left:20px;width:80%">
  Below is a list of controllers that are currently deployed in this application,
click on each to execute its default action:</p>
<p>&nbsp;</p>
<div class="dialog" style="margin-left:20px;width:60%;">
<ul>
<g:each var="c" in="${grailsApplication.controllerClasses}">
  <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
</g:each>
<li class="controller"><g:link controller="member" action="search">info.savaged.cong.MemberController.search</g:link></br></li>
<li class="controller"><g:link controller="member" action="inactive">info.savaged.cong.MemberController.inactive</g:link></br></li>
</ul>
</div>
</body>
</html>
