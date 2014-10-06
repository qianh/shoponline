<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${user.nickname}用户--个人信息查询</title>
</head>
<body>
<table width="600" class="thin-border" align="center">
	<tr>
	<td>用户名：${user.username }</td>
	</tr>
	<tr>
	<td>用户类型：
	  <c:if test="${user.type eq 0 }">普通用户</c:if>
	  <c:if test="${user.type eq 1 }">管理员</c:if>
	</td>
	</tr>
	<tr>
	<td>用户昵称：${user.nickname }</td>
	</tr>
	<tr>
	<td ><a href="orders.do?method=userList&id=${loginUser.id }">订单查询</a></td>
	</tr>
	<tr>
	<td >
	     联系方式
	   <c:if test="${user.id eq loginUser.id }">
	     <a href="address.do?method=addInput&userId=${user.id }">添加联系方式</a></c:if>
	</td>
	</tr>
	<c:if test="${empty user.address }">
	  <tr>
	     <td>还没有添加地址</td>
	  </tr>
	</c:if>
	<c:if test="${not empty user.address }">
	  <c:forEach items="${user.address }" var="address">
	    <tr>
	    <td >${address.name }[${address.postcode }](${address.phone })
	    <a href="address.do?method=updateInput&id=${address.id }">修改</a>&nbsp;
	    <a href="address.do?method=delete&id=${address.id }&userId=${user.id}">删除</a>
	    </td>
	    </tr>
	  </c:forEach>
	</c:if>
</table>
</body>
</html>