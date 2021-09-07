<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="topLine.jsp" %>
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
		for(var i=0;i<trs.length;i++)
		{  
			if(i%4==1)
			{  
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
			<form id="myform" method="post" action="<%=path%>/DicExcel.htms" encType="multipart/form-data">
				<table class="table" id="table">
					<caption>��������Ϣ</caption>
					<thead>
						<tr>
							<th>���</th>
							<th>ѧ��ѧ��</th>
							<th>ѧ������</th>
							<th>��ʦ</th>
							<th>���ʱ��</th>
							<th>����ίԱ</th>
							<th>���ĳɼ�</th>
							<th>�����ϯ</th>
							<th>�������</th>
							<th>�����˴�</th>
							<th>�����˴�</th>
							<th>�ϸ��˴�</th>
							<th>���ϸ��˴�</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
						<c:when test="${dataList!=null }">
						<!-- ��ʾ��ѯ�������� -->
						<c:forEach items="${dataList }" var="dataMap" varStatus="vs">
						<tr>
							<td>${vs.count }</td>
							<td>${dataMap.a101 }</td>
							<td>${dataMap.name1 }</td>
							<td>${dataMap.name2 }</td>
							<td>${dataMap.b402 }</td>
							<td>${dataMap.name3}</td>
							<td>${dataMap.b202 }</td>
							<td>${dataMap.name4 }</td>
							<td>${dataMap.name5 }</td>
							<td>${dataMap.b404 }</td>
							<td>${dataMap.b405 }</td>
							<td>${dataMap.b406 }</td>
							<td>${dataMap.b407 }</td>
						</tr>
						</c:forEach>
						<!-- ������� -->
						<c:forEach begin="${fn:length(dataList)+1 }" step="1" end="15">
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:forEach>
						</c:when>
						<c:otherwise>
						<c:forEach begin="1" step="1" end="15">
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						</c:forEach>
						</c:otherwise>
						</c:choose>
						<tr>
							<td colspan="13">
								<input type="file" class="btn btn-info" name="uploadFile" accept=".xls"/>
								<input class="btn btn-info" type="submit" value="Excel����"/>
								<input class="btn btn-info" type="submit" formaction="<%=path%>/DicExcelQuery.html" value="��ѯ"/>
							</td>
						</tr>
						<tr><td colspan="13">${msg }</td></tr>
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