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
	<script type="text/javascript">
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
			<form id="dicInfoQueryForm" action="<%=path%>/dicInfoQuery.html" method="post">
		      <!-- ���ݵ������� -->
		      <table id="table" class="table">
		         <caption>�����Ϣ��ѯ<hr class="ShortHR"></caption>
		         <thead>
			         <tr>
			            <td>���</td>
			            <td>���ְ��</td>
			            <td>ѧ������</td>
			            <td>���ı���</td>
			            <td>��ʦ����</td>
			         </tr>
		         </thead>
		         <tbody>
			         <c:choose>
				        <c:when test="${dataList!=null }">
				        <!-- ��ʾ��ѯ�������� -->
				           <c:forEach items="${dataList }" var="dataMap" varStatus="vs">
				              <tr>
				                 <td>${vs.count }</td>
				                 <td>${dataMap.sb302  }</td>
			                     <td>${dataMap.name1  }</td>
							     <td>${dataMap.b102   }</td>
							     <td>${dataMap.name2  }</td>
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
				           <c:forEach begin="1" step="1" end="15">
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
				     <tr>
				        <td colspan="5">
				           <input type="submit" class="btn btn-info" name="next" value="��ѯ" 
				           formaction="<%=path%>/dicInfoQuery.html">
			            </td>
				     </tr>
				     <tr>
				     	<td colspan="5">${msg }</td>
				     </tr>
		         </tbody>
		      </table>
		      <e:hidden name="uid" value="${cuid }"/>
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