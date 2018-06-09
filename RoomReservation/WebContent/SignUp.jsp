<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign up</title>

</head>
<body>
	<form name = "signupInfo" method = "GET" action ="signupServlet">
		Name:<input type = "text" name = "username" value = "" >
		Password:<input type = "text" name = "password"value = "">
		<input type = "submit" name = "submit" value = "submit">
	</form>

</body>
</html>