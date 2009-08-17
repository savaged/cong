<html>
<head>
  <title>Welcome to Groovy-Cong</title>
  <meta name="layout" content="main" />
</head>
<body>
<h1 style="margin-left:20px;">Welcome to Groovy-Cong</h1>
<p style="margin-left:20px;width:80%">
Cong is lean tooling for a JW congregation secretary.
<br/>
Copyright (C) 2009 David Savage.
<br/>
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
<br/>
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
<br/>
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
</p>
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
