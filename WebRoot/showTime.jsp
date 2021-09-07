<%@ page  language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://org.wangxg/jsp/extl"  prefix="e"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="edu.whu.system.tools.Tools" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ include file="topLine.jsp"%>
<html>
<head>
<title>˶ʿѧλ����������ϵͳ</title>
	<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/mycss.css">
	<script src="bootstrap-3.3.7-dist/js/jquery-3.3.1.min.js"></script>
	<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	<script>
	//ѭ�����ı��tr��ǩ�ı�����ɫ
	window.onload=function()
	{
		var item= document.getElementById("table");
		var tbody=item.getElementsByTagName("tbody")[0];
		var trs= tbody.getElementsByTagName("tr");
		for(var i=0;i<trs.length;i++){  
			if(i%4==1){  
				trs[i].style.background="#d0e9c6";
			}
			else if(i%4==3)
			{
				trs[i].style.background="#fcf8e3"; 
			}
			else
			{
				trs[i].style.background="#FFFFFF";
       		}
		}  
	}
	</script>
</head>

<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-2 column">
			<jsp:include page="menu.jsp" flush="true"><jsp:param value="" name=""/></jsp:include>
		</div>
		<div class="col-md-10 column">
			<form id="myform" action="<%=path%>/TimeManage.html" method="post">
				<!-- ��ѯ������ -->
				<table class="table" id="table">
					<caption>����ʱ���</caption>
					<tbody class="showTime-tbody">
						<tr>
							<td>����</td>
							<td>ְ��</td>
						<c:if test="${not empty applicationScope.date1}">
							<td>${applicationScope.date1}<br>����</td>
							<td>${applicationScope.date1}<br>����</td>
						</c:if>
						<c:if test="${empty applicationScope.date1}">
							<td></td>
							<td></td>
						</c:if>
						<c:if test="${not empty applicationScope.date2}">
							<td>${applicationScope.date2}<br>����</td>
							<td>${applicationScope.date2}<br>����</td>
						</c:if>
						<c:if test="${empty applicationScope.date1}">
							<td></td>
							<td></td>
						</c:if>
						<c:if test="${not empty applicationScope.date3}">
							<td>${applicationScope.date3}<br>����</td>
							<td>${applicationScope.date3}<br>����</td>
						</c:if>
						<c:if test="${empty applicationScope.date1}">
							<td></td>
							<td></td>
						</c:if>
						<c:if test="${not empty applicationScope.date4}">
							<td>${applicationScope.date4}<br>����</td>
							<td>${applicationScope.date4}<br>����</td>
						</c:if>
						<c:if test="${empty applicationScope.date1}">
							<td></td>
							<td></td>
						</c:if>
						<c:if test="${not empty applicationScope.date5}">
							<td>${applicationScope.date5}<br>����</td>
							<td>${applicationScope.date5}<br>����</td>
						</c:if>
						<c:if test="${empty applicationScope.date1}">
							<td></td>
							<td></td>
						</c:if>
						<c:if test="${not empty applicationScope.date6}">
							<td>${applicationScope.date6}<br>����</td>
							<td>${applicationScope.date6}<br>����</td>
						</c:if>
						<c:if test="${empty applicationScope.date1}">
							<td></td>
							<td></td>
						</c:if>
						<c:if test="${not empty applicationScope.date7}">
							<td>${applicationScope.date7}<br>����</td>
							<td>${applicationScope.date7}<br>����</td>
						</c:if>
						<c:if test="${empty applicationScope.date1}">
							<td></td>
							<td></td>
						</c:if>
						</tr>
						<!-- ���ݵ����� -->
						<c:choose>
						<c:when test="${not empty dataList}">
						<!-- ��ʾʵ�ʲ�ѯ�������� -->
						<c:forEach items="${dataList }" var="ins" varStatus="vs">	    	   	  
						<c:forEach items="${ins.a201 }" var="index" varStatus="vs">
						<tr>
							<td>${ins.name1 }</td>
							<td>��ʦ</td>
						<c:forEach items="${ins.a201.toCharArray() }" var="n" varStatus="vs">
						<c:choose >
						<c:when test="${n != '49' }">
							<td> </td>
						</c:when>       
						<c:otherwise>
							<td>��</td>
						</c:otherwise>
						</c:choose>
						</c:forEach>
						</tr>
						</c:forEach>
						<c:forEach items="${ins.a301 }" var="index" varStatus="vs">
						<tr>
							<td>${ins.name2 }</td>
							<td>����</td>
						<c:forEach items="${ins.a301.toCharArray() }" var="n" varStatus="vs">
						<c:choose >
						<c:when test="${n != '49' }">
							<td> </td>
						</c:when>       
						<c:otherwise>
							<td>��</td>
						</c:otherwise>
						</c:choose>
						</c:forEach>
						</tr>
						</c:forEach>
						<c:forEach items="${ins.a504 }" var="index" varStatus="vs">
						<tr>
							<td>${ins.name3 }</td>
							<td>ר��</td>
						<c:forEach items="${ins.a504.toCharArray() }" var="n" varStatus="vs">
						<c:choose >
						<c:when test="${n != '49' }">
							<td></td>
							</c:when>
						<c:otherwise>
							<td>��</td>
						</c:otherwise>
						</c:choose>
						</c:forEach>
						</tr>
						</c:forEach>
						</c:forEach>
						<c:forEach begin="${fn:length(dataList)+1 }" step="1" end="15">
							<tr>
								<td></td><td></td>
							<c:if test="${not empty applicationScope.date1}">
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date2}">
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date3}">
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date4}">
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date5}">
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date6}">
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date7}">
								<td></td>
								<td></td>
							</c:if>
							</tr>
						</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach begin="1" step="1" end="15">
							<tr>
								<td></td><td></td>
							<c:if test="${not empty applicationScope.date1}">
								<td></td><td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date2}">
								<td></td><td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date3}">
								<td></td><td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date4}">
								<td></td><td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date5}">
								<td></td><td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date6}">
								<td></td><td></td>
							</c:if>
							<c:if test="${not empty applicationScope.date7}">
								<td></td><td></td>
							</c:if>
							</tr>
							</c:forEach>
						</c:otherwise>
						</c:choose>
						<c:if test="${empty applicationScope.date1}">
						<tr>
							<td colspan="3"><input type="submit" class="btn btn-info" name="next" value="��ѯ"></td>
						</tr>
						</c:if>
						<c:if test="${not empty applicationScope.date7}">
						<tr>
							<td colspan="16"><input type="submit" class="btn btn-info" name="next" value="��ѯ"></td>
						</tr>
						</c:if>
						
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
		<jsp:include page="footer.jsp" flush="true"><jsp:param value="" name=""/></jsp:include>
		</div>
	</div>
</div>
</body>
</html>