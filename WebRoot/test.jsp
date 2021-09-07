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
	//��ťloadingЧ��
	$(function()
	{
		$(".btn").click(function()
				{
			$(this).button('loading').delay(1000).queue(function(){
			// $(this).button('reset');
			// $(this).dequeue(); 
			});
		});
	});
	</script>
</head>

<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-2 column">
			<jsp:include page="menu.jsp" flush="true"><jsp:param value="" name=""/></jsp:include>
			<div class="alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">��</button>
				<h4>&nbsp;&nbsp;&nbsp;��ʾ��</h4>
					&nbsp;&nbsp;&nbsp;&nbsp;��������Ҳ����У������ѯ��ť����ȡ��Ĵ����Ϣ��
			</div>
		</div>
		<div class="col-md-10 column">
			<form id="dicInfoQueryForm" action="<%=path%>/dicInfoQuery.html" method="post">
			<!-- ���ݵ������� -->
				<table class="table" id="table">
					<caption>��ͷ</caption>
					<thead>
						<tr>
							<th>���</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>����</td>
						</tr>
						<tr>
							<td>
								<input type="submit" class="btn btn-info" name="next" value="��ѯ" 
								formaction="<%=path%>/dicInfoQuery.html">
							</td>
						</tr>
					</tbody>
				</table>
				<e:hidden name="uid" value="101"/>
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