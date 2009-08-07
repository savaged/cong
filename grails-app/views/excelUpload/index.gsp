<html>
<head>
  <title>Welcome to Groovy-Cong</title>
  <meta name="layout" content="main" />
</head>
<body>
<h1 style="margin-left:20px;">Excel Upload</h1>
<div class="nav">
  <span class="menuButton">
    <a href="/cong">Home</a>
  </span>
</div>
<p style="margin-left:20px;width:80%">
Please enter the location of the file containing the service reports.</br></p>
<div class="dialog" style="margin-left:20px;width:60%;">
  <g:form method="post" action="upload" enctype="multipart/form-data">
    <input type="file" name="myFile"/>
    <g:datePicker name="starting" precision="month" years="${2008..2014}" />
    <input type="submit" value="Upload"/>
  </g:form>
</div>
</body>
</html>