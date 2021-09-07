<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e"%>
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
   //��һʵ����ѯ
   function onEdit(vuid)
   {
 	  var vform=document.getElementById("secQueryForm");
 	  vform.action="<%=path%>/secFindById.html?uid="+vuid;
 	  vform.submit();
   }
   //��һʵ��ɾ��
   function onDel(vuid)
   {
 	 var vform = document.getElementById("secQueryForm");
 	 vform.action="<%=path%>/secDelById.html?uid="+vuid;
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
		   <form id="secQueryForm" class="secQueryForm" action="<%=path%>/secQuery.html" method="post">
		      <!-- ���ݵ������� -->
		      <table id="table" class="table">
		      	 <caption>�������</caption>
		         <thead>
		         	<tr>
		            <td>���</td>
		            <td>����</td>
		            <td>������λ</td>
		            <td>�о�����</td>
		            <td>ְ��</td>
		            <td>���֤����</td>
		            <td>�ֻ�����</td>
		            <td>�ʼ�</td>
		            <td>��ɫ</td>
		            <td></td>
		            <td></td>
		         </tr>
		         </thead>
		         <tbody>
		         	<c:choose>
				        <c:when test="${empty dataList}">
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
				              </tr>
				           </c:forEach>
				        </c:when>
				        <c:otherwise>
				        <!-- ��ʾ��ѯ�������� -->
				           <c:forEach items="${dataList }" var="dataMap" varStatus="vs">
				              <tr>
				                 <td>${vs.count }</td>
			                     <td>${dataMap.name }</td>
							     <td>${dataMap.a601 }</td>
							     <td>${dataMap.a603 }</td>
							     <td>${dataMap.xa604}</td>
							     <td>${dataMap.a605 }</td>
							     <td>${dataMap.a608 }</td>
							     <td>${dataMap.a609 }</td>
							     <td>${dataMap.roles }</td>
							     <td>
							        <a href="#" onclick="onEdit('${dataMap.uid}')">�޸�</a>
							     </td>
							     <td>
							        <a href="#" onclick="onDel('${dataMap.uid}')">ɾ��</a>
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
				        <td colspan="12">
				           <input type="submit" name="next" value="��ѯ" class="btn btn-info"
				           formaction="<%=path%>/secQuery.html">
				           <input type="submit" name="next" value="�������" class="btn btn-info"
				           formaction="<%=path%>/secAddManage.jsp">
			            </td>
				     </tr>
				     <tr>
				     	<td colspan="12">${msg}</td>
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