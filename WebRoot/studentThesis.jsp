<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="topLine.jsp"%>
<!DOCTYPE html>
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
		var dataMap = "${dataMap.uid}";
		var msg = "${msg}";
		if(dataMap==""&&msg=="")
		{
			var vform = document.getElementById("myform");
			vform.action="<%=path%>/student_Thesis.html";
			vform.submit();
		}
		
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
			<form id="myform" action="" method="post">
				<table class="table" id="table">
					<caption>�ҵ�����</caption>
					<thead></thead>
					<tbody>
						<c:if test="${not empty dataMap.b101 }">
							<tr>
								<td>����</td>
								<td colspan="2">${dataMap.b102}</td>
							</tr>
							<tr height="74">
								<td>ժҪ</td>
								<td colspan="2" width="500" align="left">&#12288;&#12288;${dataMap.b103}</td>
							</tr>
							<tr>
								<td>�ؼ���</td>
								<td colspan="2">${dataMap.b104}</td>
							</tr>
							<tr>
								<td>����</td>
								<td colspan="2">${dataMap.b105}</td>
							</tr>
							<tr>
								<td>�����</td>
								<td colspan="2">${dataMap.b107}</td>
							</tr>
							<tr>
								<td>������</td>
								<td>
								<c:choose>
								<c:when test="${dataMap.b108=='δ����'}">
									<p>${dataMap.b108}</p>
								</c:when>
								<c:otherwise>
									<a class="btn btn-info" href="<%=path%>/view_Review.html?b101=${dataMap.b101}">${dataMap.b108}</a>
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							<tr>
								<td>�����</td>
								<td>
								<c:choose>
								<c:when test="${dataMap.b109=='δ���'}">
									<p>${dataMap.b109}</p>
								</c:when>
								<c:otherwise>
									<a class="btn btn-info" href="<%=path%>/view_Reply.html?b101=${dataMap.b101}">${dataMap.b109}</a>
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							<tr>
								<td>����</td>
								<td colspan="2">
							    <c:if test="${not empty dataMap.b101}">
								    <a class="btn btn-info" href="<%=path%>/view_Thesis.html?b101=${dataMap.b101}" target="_blank">�����Ķ�</a>
								</c:if>
								</td>
							</tr>
							</c:if>
						<tr>
						    <td>�ύ����</td>
						    <td>
						        <button class="btn btn-info" type="submit" formaction="<%=path%>/student_Upload.html">${empty dataMap.b101?'�µ��ύ':'�����ύ'}</button>
							</td>
						</tr>
						<tr><td colspan="3"></td></tr>
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