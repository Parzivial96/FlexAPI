<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1" width="500" align="center">
		<tr>
			<th><b>ENDPOINTS</b></th>
			<th><b>METHOD</b></th>
		</tr>

		<%
		String[][] endpoints = { { "/getUser", "GET" }, { "/addUser", "POST" }, { "/dropUser", "POST" }, { "/editUser", "POST" } };

		for (int i = 0; i < endpoints.length; i++) {
		%>
		<tr>
			<td><%=endpoints[i][0]%></td>
			<td><%=endpoints[i][1]%></td>
		</tr>
		<%}%>
	</table>

	<form action="validateUser" method="post">
		<input type="text" placeholder="email" name="email" /> <input
			type="text" placeholder="password" name="password" /> <input
			type="submit">
	</form>


</body>
</html>