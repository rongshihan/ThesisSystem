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
   <script>
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
			<form id="outputExcelForm" class="outputExcelForm" method="post" action="<%=path%>/FinancialAdd.html">
		      <table id="table" class="table">
		      	<caption class="outputExcel-caption">���񱨱�
		      		<div class="outputExcel-list">
		      			<button type="button" class="btn btn-info" id="dropdownMenu1" data-toggle="dropdown">����
					        <span class="caret"></span>
					    </button>
					    <ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1">
					    	<li>
					    		<input type="submit" class="btn btn-info" name="next" value="���񱨱��ܱ�"
					  			formaction="<%=path%>/financialExcel.xls"> 
					    	</li>
					        <li role="presentation">
					            <input type="submit" class="btn btn-info" name="next" value="У��ר�ұ���"
					    		formaction="<%=path%>/financialExcelForInner.xls">
					        </li>
					        <li role="presentation">
					            <input type="submit" class="btn btn-info" name="next" value="У��ר�ұ���"
				       			formaction="<%=path%>/financialExcelForOuter.xls">
					        </li>
					    </ul>
		      		</div>
		      	</caption>
		      	<thead>
		      		<tr>
		      			<th>���</th>
		      			<th>ѧ��ѧ��</th>
		      			<th>ѧ������</th>
		      			<th>��ʦ</th>
		      			<th>ר��</th>
		      			<th>ְ��</th>
		      			<th>��λ</th>
		      			<th>���</th>
		      		</tr>
		      	</thead>
		      	<tbody>
		      		<c:choose>
				        <c:when test="${dataList!=null }">
				        <!-- ��ʾ��ѯ�������� -->
				           <c:forEach items="${dataList }" var="dataMap" varStatus="vs">
				              <tr>
				                 <td>${vs.count }</td>
			                     <td>${dataMap.b603 }</td>
							     <td>${dataMap.b602 }</td>
							     <td>${dataMap.b604 }</td>
							     <td>${dataMap.name}</td>
							     <td>${dataMap.fvalue }</td>
							     <td>${dataMap.a601 }</td>
							     <td>${dataMap.money }</td>
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
				              </tr>
				           </c:forEach>
				        </c:otherwise>
				    </c:choose>
				    <tr>
						<td colspan="8">
						    <input type="submit" class="btn btn-info" name="next" value="���ɲ�������">
						    <input type="submit" class="btn btn-info" name="next" value="��ѯ" formaction="<%=path%>/FinancialQuery.html">
						</td>
					</tr>
					<tr>
						<td colspan="8">${msg }</td>
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