<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品修改</title>
</head>
<body>
<jsp:include page="inc.jsp"/>
<form action="product.do" method="post" enctype="multipart/form-data">
<input type="hidden" name="method" value="update"/>
<input type="hidden" name="id" value="${p.id }"/>
<table width="600" class="thin-border" align="center">
	<tr>
	<td colspan="2"><img src="<%=request.getContextPath()%>/img/${p.img}"/></td>
	</tr>
	<tr>
	<td>商品名称：</td><td><input type="text" name="name" value="${p.name }"/><span class="errorContainer">${errors.name}</span></td>
	</tr>
	<tr>
	<td>商品价格：</td><td><input type="text" name="price" value="${p.price }"/><span class="errorContainer">${errors.price}</span></td>
	</tr>
	<tr>
	<td>商品库存：</td><td><input type="text" name="stock" value="${p.stock }"/><span class="errorContainer">${errors.stock}</span></td>
	</tr>
	<tr>
	<td>商品类别：</td>
	<td>
	<select name="cid">
		<option value="">请选择商品类别</option>
		<c:forEach items="${cs }" var="c">
			<c:if test="${c.id eq p.category.id }">
				<option value="${c.id }" selected="selected">${c.name }</option>
			</c:if>
			<c:if test="${c.id ne p.category.id }">
				<option value="${c.id }">${c.name }</option>
			</c:if>
		</c:forEach>
	</select>
	<span class="errorContainer">${errors.cid}</span></td>
	</tr>
	<tr>
	<td>商品图片：</td><td><input type="file" name="img"/><span class="errorContainer">${errors.img}</span></td>
	</tr>
	<tr>
	<td colspan="2">商品介绍：</td>
	</tr>
	<tr>
	<td colspan="2"><textarea cols="90" rows="10" name="intro">${p.intro }</textarea></td>
	</tr>
	<tr>
	<td colspan="2">
		<input type="submit" value="修改"/><input type="reset"/>
	</td>
	</tr>
</table>
</form>
</body>
</html>