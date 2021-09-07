<%@ page language="java" pageEncoding="GBK"%>
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
			<form action="" method="post">
				<table class="table" id="table">
					<caption>�����Ϣ</caption>
					<tbody class="previewInfo-tbody">
						<tr>
							<td></td>
							<td></td>
							<td>���ʱ��</td>
							<td>${dataMap.b402}</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td>����</td>
							<td>${dataMap.b408}</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td>����</td>
							<td>${dataMap.b409}</td>
							<td></td>
						</tr>
						<tr>
							<td></td><td></td>
							<td>���´��</td>
							<td>${dataMap.b410}</td>
							<td></td>
						</tr>
						<tr>
							<td></td><td></td>
							<td>��ע</td>
							<td>${dataMap.b403}</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="5">${msg }</td>
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