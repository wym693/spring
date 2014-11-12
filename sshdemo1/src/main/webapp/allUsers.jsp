<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <table border="1px">
   		<tr>   			
   			<td style="text-align: center;">用户名</td>
   			<td style="text-align: center;">用户类型</td>
   		</tr>
   		<s:iterator var="user" value="users" status="status">
   			<s:if test="#status.odd">
   				<tr bgcolor="yellow">
   			</s:if>
   			<s:else>
   				<tr>
   			</s:else>   			
   				<td><s:property value="#user.username"/></td>
   				<td><s:property value="#user.group.name"/></td>
   			</tr>
   		</s:iterator>
   	</table>
  </body>
</html>
