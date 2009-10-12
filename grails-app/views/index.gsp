<html>
<head>
  <title>Welcome to Groovy-Cong</title>
  <meta name="layout" content="main" />
</head>
<body>
<h1 style="margin-left:20px;">
  <g:message code='info.savaged.cong.description' default='Cong (AKA Groovy-Cong), is designed as lean tooling for a JW congregation secretary.'/>
</h1>

<p>&nbsp;
</p>
<p style="margin-left:20px;width:80%">
  <g:message code='info.savaged.cong.instruction' default='Below is a list of controllers that are currently deployed in this application, click on each to execute its default action:'/>
</p>
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
<p>&nbsp;</p>
<p>&nbsp;</p>
<p style="margin-left:20px;width:80%">
Copyright (C) 2009 David Savage.
<br/>
In some respects it is similar to TSWIN, however hopefully less proprietary and platform dependent. It is also designed to be as light weight as possible to use, with only the most essential features.
<br/>
There's a bit of a learning curve getting it set-up, however there should be no problem getting help here. Plus you might also enjoy nosing around the code-base, which is entirely free open source, under the GNU GPL.
<br/>
Cong has been developed using Groovy &amp; Grails, with a desire to keep it as simple as possible. Hopefully, many will find it useful by using it and/or by checking out how it works internally.
<br/>
Initially github is being used to file share, and to allow people to peak at the source code. Of course, theres nothing stopping anyone from cloning or forking cong*. Patches are welcome, as are suggestions for enhancements or critiques on the specific coding or technical design employed.
<br/>
Enjoy,
savaged
<br/>
* Please see the README and the related COPYING file.
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
along with this program.  
If not, see <a href="http://www.gnu.org/licenses/">&lt;http://www.gnu.org/licenses/&gt;</a>.
</p>
</body>
</html>
