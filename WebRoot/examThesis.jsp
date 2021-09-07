<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		var item= document.getElementById("mytable");
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

		var uid = document.getElementById("uid");
		var msg = "${msg}";
		if(uid==null&&msg=="")
		{
			var vform = document.getElementById("myform");
			vform.action="<%=path%>/exam_Thesis.html";
			vform.submit();
		}
		var select = document.getElementById("myselect");
		var selectID = localStorage.getItem('selectID');
		for (var i = 0; i < select.options.length; i++) {
			if (select.options[i].value == selectID) {
				select.options[i].selected = true;
				break;
			}
		}
		localStorage.removeItem('selectID');
	}

</script>
<script type="text/javascript">
function onSelect()
{
	var selectID = document.getElementById("myselect").value;
	localStorage.setItem('selectID',selectID);
	
	var vform = document.getElementById("myform");
	vform.action="<%=path%>/exam_Thesis.html";
		vform.submit();

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
			<form id="myform" action="" method="post">
				<select id="myselect" name="b108" onchange="onSelect('')">
					<option value="">ȫ��</option>
					<option value="0">δ����</option>
					<option value="1">ͨ��</option>
					<option value="2">δͨ��</option>
				</select>
				<table id="mytable" class="table">
					<caption>ѧ������</caption>
					<thead>
						<tr>
						    <th>ѧ������</th>
							<th>����</th>
							<th>�����</th>
							<th>������</th>
							<th>����</th>
						</tr>
					</thead>
					<tbody>
					<c:choose>
					<c:when test="${dataList!=null}">
					<c:forEach var="U" items="${dataList}">
						<tr>
						    <td>${U.name}<input id="uid" type="hidden"></td>
							<td><a href="<%=path%>/thesis_Details_Ex.html?b101=${U.b101}">${U.b102}</a></td>
							<td>${U.b107}</td>
							<td>
							<c:choose>
							<c:when test="${U.b108=='δ����'}">
								<p>${U.b108}</p>
							</c:when>
							<c:otherwise>
								<a href="<%=path%>/view_Review.html?b101=${U.b101}">${U.b108}</a>
							</c:otherwise>
							</c:choose>
							</td>
							<td>
								<a class="btn btn-info" href="<%=path%>/view_Thesis.html?b101=${dataMap.b101}" target="_blank">�����Ķ�</a>
							</td>
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
		                </tr>
	                </c:forEach>
	                </c:when>
	                <c:otherwise>
	                <c:forEach begin="1" step="1" end="10">
	                	<tr>
			                <td></td>
			                <td></td>
			                <td></td>
			                <td></td>
			                <td></td>
		                </tr>
	                </c:forEach>
			        </c:otherwise>
					</c:choose>
					<tr><td colspan="4">${msg }</td></tr>
				</tbody>
				</table>
				<input type="hidden" name="uid" value="${dataMap.uid}">
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