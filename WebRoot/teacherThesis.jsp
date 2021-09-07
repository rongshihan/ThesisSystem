<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		var item2= document.getElementById("table2");
		var tbody2=item2.getElementsByTagName("tbody")[0];
		var trs2= tbody2.getElementsByTagName("tr");
		for(var i=0;i<trs2.length;i++)
		{  
			if(i%4==1)
			{  
				trs2[i].style.background="#d0e9c6";
			}
			else if(i%4==3)
			{
				trs2[i].style.background="#fcf8e3"; 
			}
			else
			{
				trs2[i].style.background="#FFFFFF";
       		}
		}  
		var uid = document.getElementById("uid");
		var msg = "${msg}";
		if(uid==null&&msg=="")
		{
			var vform = document.getElementById("myform");
			vform.action="<%=path%>/teacher_Thesis.html";
			vform.submit();
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
			<form id="myform" action="<%=path%>/teacher_Search.html" method="post">
				<table class="table" id="table">
					<caption>������Ϣ��ѯ</caption>
					<tbody>
						<tr>
							<td>����</td>
							<td><e:text name="b102" defval="${U.b102}" /></td>
						</tr>
						<tr>
							<td>�����</td>
							<td>
								<e:radio name="b107" value="ȫ��:'',δ���:0,ͨ��:1,δͨ��:2"
								defval="${U.aab107}" />
							</td>
						</tr>
						<tr>
							<td>������</td>
							<td>
								<e:radio name="b108" value="ȫ��:'',δ����:0,ͨ��:1,��ͨ��:2"
								defval="${U.aab108}" />
							</td>
						</tr>
						<tr>
							<td>�����</td>
							<td>
								<e:radio name="b109" value="ȫ��:'',δ���:0,ͨ��:1,��ͨ��:2"
								defval="${U.aab109}" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<button class="btn btn-info" type="submit" name="uid" value="2">ɸѡ</button>
							</td>
						</tr>
						<tr><td colspan="2">${msg}</td></tr>
					</tbody>
				</table>
			</form>
			<form id="myform" action="" method="post">
				<table class="table" id="table2">
					<thead>
						<tr>
						    <th>ѧ������</th>
							<th>����</th>
							<th>��ʦ�����</th>
							<th>������</th>
							<th>�����</th>
							<th>����</th>
						</tr>
					</thead>
					<tbody>
					<c:choose>
					<c:when test="${dataList!=null}">
					<c:forEach var="U" items="${dataList}">
						<tr>
						    <td>${U.name}<input id="uid" type="hidden"></td>
							<td><a href="<%=path%>/thesis_Details_Te.html?b101=${U.b101}">${U.b102}</a></td>
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
							<c:choose>
							<c:when test="${U.b109=='δ���'}">
								<p>${U.b109}</p>
							</c:when> 
							<c:otherwise>
								<a href="<%=path%>/view_Reply.html?b101=${U.b101}">${U.b109}</a>
							</c:otherwise>
							</c:choose>
							</td>
							<td>
								<a href="<%=path%>/view_Thesis.html?b101=${U.b101}" target="_blank">�����Ķ�</a>
							</td>
						</tr>
						</c:forEach>
						<c:forEach begin="${fn:length(dataList)+1 }" step="1" end="6">
						<tr>
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
				        <c:forEach begin="1" step="1" end="6">
		              	<tr>
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