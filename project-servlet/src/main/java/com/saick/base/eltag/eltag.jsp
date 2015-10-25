<%@page import="com.saick.base.entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.com.saick.eltaglib" prefix="myeltag"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<%
	    boolean flag = true;
	    pageContext.setAttribute("flag", flag);
	    User user = new User();
	    //user.setUsername("刘保");
	    pageContext.setAttribute("user", user, PageContext.SESSION_SCOPE);
	%>
	<!-- 实现if判断 -->
	<br />
	<myeltag:myIf flag="${sessionScope.user.username==null}">
		您还没有登录!<br />
		<a href=''>请登录</a>
	</myeltag:myIf>
	<br />
	<myeltag:myIf flag="${sessionScope.user.username!=null}">
		欢迎 ${sessionScope.user.username} 先生,您已经登录!<br />
		<a href=''>注销</a>
	</myeltag:myIf>
	<br />

</body>
</html>
