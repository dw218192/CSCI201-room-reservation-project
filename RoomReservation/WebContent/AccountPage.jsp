<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import ="webDataClasses.Room"%>
<%@ page import ="webDataClasses.RoomList"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>	Room reservation</title>
</head>
<body>


            <div style="display:block" id="loc1">
                
            </div>
            <div style="display:none" id="loc2">
                
            </div>

     
          
Location:
<select onchange="Show(parseInt(this.value), 'loc');">
  <option selected="selected" value="1">Doheny</option>
  <option value="2">LVL</option>
</select>
&nbspTime:
<select>
 <option value="9">9:00pm - 9:30pm</option>
 <option value="930">9:30pm - 10:00pm</option>
  <option value="1000">10:00pm - 10:30pm</option>
   <option value="1030">10:30pm - 11:00pm</option>
    <option value="1100">11:00pm - 11:30pm</option>
     <option value="1130">11:30pm - 12:00pm</option>
  <option value="12">12:00pm - 12:30pm</option>
  <option value="1230">12:30pm - 13:00pm</option>
  <option value="1300">13:00pm - 13:30pm</option>
   <option value="1330">13:30pm - 14:00pm</option>
      <option value="1400">14:00pm - 14:30pm</option>
     <option value="1430">14:30pm - 15:00pm</option>
      <option value="1500">15:00pm - 15:30pm</option>
      <option value="1530">15:30pm - 16:00pm</option>
  	<option value="1600">16:00pm - 16:30pm</option>
  	<option value="1630">16:30pm - 17:00pm</option>
  	<option value="1700">17:00pm - 17:30pm</option>
  	<option value="1730">17:30pm - 18:00pm</option>
  	<option value="1800">18:00pm - 18:30pm</option>
  	<option value="1830">18:30pm - 19:00pm</option>
  	<option value="1900">19:00pm - 19:30pm</option>
  	<option value="1900">19:30pm - 20:00pm</option>
</select>

<br>
<%
RoomList room =(RoomList) session.getAttribute("roomAvail");
 for(int i=0;i<room.size();i++){
%>

<% 
	if(room.getRooms().get(i).getName().length()==2){
%>
<script>
document.getElementById("loc1").innerHTML += "Room:&nbsp<%=room.getRooms().get(i).getName() %>";
</script>

<% 		
	}
	else{
%>
<script>
document.getElementById("loc2").innerHTML += "Room:&nbsp<%=room.getRooms().get(i).getName() %>";
</script>
<%
	}
%>
<br>


<% for(int j = 0;j<room.getRooms().get(i).getDate().size();j++){
%>
<% 
	if(room.getRooms().get(i).getName().length()==2){
%>
<script>
document.getElementById("loc1").innerHTML += "Date:&nbsp<%=room.getRooms().get(i).getDate().get(j) %>";
</script>

<% 		
	}
	else{
%>
<script>
document.getElementById("loc2").innerHTML += "Date:&nbsp<%=room.getRooms().get(i).getDate().get(j) %>";
</script>
<%
	}
%>

<br>
<%
}
%>
<br>
<%
 }
%>
</body>
<script>
function Show(page, tag)
{  
	var i = 1;
	var el; 
    while (el = document.getElementById(tag+i)) {
        if (i ==page )
            el.style.display = 'block';
        else
            el.style.display = 'none';
        i++;
    }
}
</script>
</html>