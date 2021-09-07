<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e"%>
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
			<form action="<%=path %>/chooseRole" method="post">
				<table id="table" class="table">
					<tbody>
						<tr>
							<td>	
								��ӭ��ʹ�ñ�ϵͳ��${name }
							</td>
						</tr>
						<tr>
							<td>
								��ѡ�񱾴�˶ʿ�������������������뵣�εĹ���
							</td>
						</tr>
						<tr>
							<td>
								������ͬʱѡ�����������ѡ��һ�
							</td>
						</tr>
						<tr>
							<td>
								<e:groupbox name="roles" value="����ר��:4,���ר��:5"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="submit" name="next" class="btn btn-info" value="ȷ��">
							</td>
						</tr>
						<tr>
							<td><e:message/></td>
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