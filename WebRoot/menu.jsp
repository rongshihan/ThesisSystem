<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="nav nav-pills nav-stacked menu-ul">
	<li class="active"><a href="#">�����б�</a></li>
	<li><a href="main.jsp">��ҳ</a></li>
	<c:forEach items="${funclist }" var="func">
		<li>${func }</li>
	</c:forEach>
</ul>