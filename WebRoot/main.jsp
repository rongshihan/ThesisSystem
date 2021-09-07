<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="topLine.jsp" %>
<%@page import="java.util.*"%>
<html>
<head>
<title>˶ʿѧλ����������ϵͳ</title>
   <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
   <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/mycss.css">
   <script src="bootstrap-3.3.7-dist/js/jquery-3.3.1.min.js"></script>
   <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
   <script>
   //��ul->li->a��ǩѭ��Ϳɫ
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
			<div align="center" id="message-list">
				<form id="dicInfoQueryForm" action="" method="post">
				<!-- ���ݵ������� -->
					<table class="table" id="table">
						<caption>��Ϣ�б�</caption>
						<thead>
							<tr>
								<th>ʱ��</th>
								<th>����</th>
								<th>�Ƿ��Ѷ�</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
							<c:when test="${empty messageList}">
							<c:forEach begin="1" step="1" end="9">
								<tr>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:forEach>
							<tr><td colspan="3">������Ϣ</td></tr>
							</c:when>
							<c:otherwise>
							<c:forEach items="${messageList }" var="message">
								<tr>
									<td>${message.time }</td>
									<td>${message.href }</td>
									<td>${message.state}</td>
								</tr>
							</c:forEach>
							<c:forEach begin="${fn:length(messageList)+1 }" step="1" end="10">
								<tr>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</c:forEach>
					        </c:otherwise>
					        </c:choose>
							<tr>
								<td colspan="3"></td>
							</tr>
							<tr>
								<td colspan="3">
									<input type="submit" class="btn btn-info" name="next" value="ˢ����Ϣ" formaction="<%=path%>/message">
								</td>
							</tr>
						</tbody>
					</table>
					<e:hidden name="uid" value="101"/>
				</form>
			</div>
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