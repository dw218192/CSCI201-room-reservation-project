<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	
	<body>
		<h1> room reservation success! </h1>
		<form action="EmailSenderProcessor">
		<input type="hidden" name="capacity" value="8" />
		<input type="hidden" name="EmailType" value="verification" />
			<% for(int i=0; i<8; i++){ %>
				<input type="text" name="recipient<%=i%>" placeholder="example@gmail.com" /> <br/>
			<% } %>
		<input type= "submit" />
		</form>
	</body>
</html>