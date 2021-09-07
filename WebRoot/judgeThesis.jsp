<%@ page  language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://org.wangxg/jsp/extl"  prefix="e"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
			<form id="myform" action="<%=path%>/QueryJudge.html" method="post">
			<!-- ��ѯ������ -->
				<table class="table" id="table">
					<caption>ר�Ҳ�ѯ</caption>
					<tbody class="judgeThesis-tbody">
						<tr>
							<td colspan="6">��ѯ����</td>
						</tr>
						<tr>
						  	<td></td>
						  	<td></td>
						    <td>ר������</td>
						    <td></td>
						    <td>
						      <e:text name="qename"/>
						    </td>
						    <td></td>
					    </tr>
				    </tbody>
				</table>
				
				<!-- ���ݵ����� -->
				<table class="table" id="table2">
					<thead>
						<tr>
							<td></td><td></td>
						    <th>ר������</th>
						    <th>���ı���</th>
						    <td></td><td></td>
					    </tr>
					</thead>
					<tbody class="judgeThesis-tbody">
					<c:choose>
					<c:when test="${dataList!=null }">
					<!-- ��ʾʵ�ʲ�ѯ�������� -->
					<c:forEach items="${dataList }" var="ins" varStatus="vs">
						<tr>
							<td></td><td></td>
							<td>${ins.name }</td>
							<td>${ins.b102 }</td><td></td><td></td>
						</tr>
					</c:forEach>
					<!-- ������� -->
					<c:forEach begin="${fn:length(dataList)+1 }" step="1" end="10">
						<tr>
							<td></td><td></td><td></td><td></td><td></td><td></td>
						</tr>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<c:forEach begin="1" step="1" end="10">
						<tr>
							<td></td><td></td><td></td><td></td><td></td><td></td>
						</tr>
					</c:forEach>
					</c:otherwise>
					</c:choose>
						<tr>
							<td colspan="6">
								<input type="submit" class="btn btn-info" name="next" value="��ѯ">
								<input type="submit" class="btn btn-info" name="next" value="���������Ŵ�����" formaction="<%=path%>/PreviewExcel.xls">
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<form method="post" action="<%=path%>/ExcelJudge.htmn" encType="multipart/form-data">
				<table class="table">
					<tbody>
						<tr>
							<td>
								<input type="file" class="btn btn-info" name="uploadFile" required accept=".xls"/>
								<input class="filesubmit btn btn-info" type="submit"  value="Excel����" />
							</td>
						</tr>
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