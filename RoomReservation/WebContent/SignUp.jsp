<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign up</title>

</head>
<body>
	<form name = "signupInfo" method = "GET" action ="SignupValidation">
		Name:<input type = "text" name = "username" value = "" ></br>
		Password:<input type = "text" name = "password"value = ""></br>
		<input type = "submit" name = "submit" value = "submit" onsubmit = "ValidFields()">
	</form>

</body>
</html>