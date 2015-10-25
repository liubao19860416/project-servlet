<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
	<form action="${pageContext.request.contextPath}/RegisterServlet" method="post" enctype="application/x-www-form-urlencoded">
		<table border="2" align="center">		
			<tr>
				<th>用户名</th>
				<td><input type="text" name="username" maxlength="4"/></td>
			</tr>
			<tr>
				<th>性别</th>
				<td>
					<input type="radio" name="gender" value="男"/>男
					<input type="radio" name="gender" value="女" checked/>女
				</td>
			</tr>
			<tr>
				<th>城市</th>
				<td>
					<select name="city">
						<option value="北京">北京</option>
						<option value="上海" selected>上海</option>
						<option value="广州">广州</option>
						<option value="深圳">深圳</option>
					</select>	
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="登录"/>
				</td>
			</tr>
		</table>	
	</form>  	
  </body>
</html>
