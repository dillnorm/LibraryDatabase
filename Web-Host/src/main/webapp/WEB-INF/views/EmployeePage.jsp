<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv ="Content-Type" content="text/html; charset=UTF-8">
<title>Library Database</title>
</head>
<body>
<p>  </p>
Welcome Employee: ${name}
<p>  </p>
Your User Information:  
${info} 
<p>  </p>
Current Orders:  
${curr} 
<p>  </p>
<form action="/emp.do" method= "post">
Add book to Order List: <input name="Book Name" type="text" /><input type ="submit" value ="Submit"/>   
</form>
<p>  </p>
<form action="/emp.do" method= "post">
Log Out: <input type ="submit" value ="Log Off"/>
</form>
<p>  </p>
<h2>Currently Available books in the Library</h2>
${set}

</body>
</html>