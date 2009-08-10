
<%@ page import="info.savaged.cong.Member" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Member List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Member</g:link></span>
        </div>
        <div class="body">
            <h1>Member List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="lastname" title="Lastname" />
                        
                   	        <g:sortableColumn property="firstname" title="Firstname" />
                        
                   	        <th>Group Unit</th>
                   	    
                   	        <th>Bible Study Conductor</th>
                   	    
                   	        <g:sortableColumn property="pioneerSchoolDate" title="Pioneer School Date" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${memberInstanceList}" status="i" var="memberInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${memberInstance.id}">${fieldValue(bean:memberInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:memberInstance, field:'lastname')}</td>
                        
                            <td>${fieldValue(bean:memberInstance, field:'firstname')}</td>
                        
                            <td>${fieldValue(bean:memberInstance, field:'groupUnit')}</td>
                        
                            <td>${fieldValue(bean:memberInstance, field:'bibleStudyConductor')}</td>
                        
                            <td>${fieldValue(bean:memberInstance, field:'pioneerSchoolDate')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${memberInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
