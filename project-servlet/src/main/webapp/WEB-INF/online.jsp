<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
	<table border="2" align="center">
		<tr>
			<th>在线用户</th>
			<th>操作</th>
		</tr>
		<c:forEach var="entry" items="${applicationScope.map}">
			<tr>
				<th>${entry.value.username}</th>
				<td>
					<a href="${pageContext.request.contextPath}/KickServlet?sessionId=${entry.key}" style="text-decoration:none">
						踢TA
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<hr/>
  </body>
</html>
