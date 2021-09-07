<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://org.wangxg/jsp/extl" prefix="e"%>
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
      var count=0;

      function onSelect(vstate)
      {
    	  vstate?count++:count--;
    	  var vdel=document.getElementById("del");
    	  vdel.disabled=(count==0);
      }
      
      function onEdit(vaab101)
      {
    	 var vform = document.getElementById("myform");
    	 vform.action="<%=path%>/findStuByID.html?uid="+vaab101;
    	 //alert(vform.action);
    	 vform.submit();
      }
      
      function onDel(vaab101)
      {
    	 var vform = document.getElementById("myform");
    	 vform.action="<%=path%>/delStuByID.html?uid="+vaab101;
    	 //alert(vform.action);
    	 vform.submit();
      } 
      
      function onDisable(vaab101)
      {
    	 var vform = document.getElementById("myform");
    	 vform.action="<%=path%>/disaStuByID.html?uid="+vaab101;
    	 //alert(vform.action);
    	 vform.submit();
      }
      
      function onAble(vaab101)
      {
     	 var vform = document.getElementById("myform");
    	 vform.action="<%=path%>/ableStuByID.html?uid="+vaab101;
		//alert(vform.action);
		vform.submit();
	}
    //ȫѡ/ȫ��ѡ����
      function setAllNo()
      {
          var box = document.getElementById("allAndNotAll");
          var boxes = document.getElementsByName("idlist");
          if(box.checked == false)
          {//ȫ��ѡ
              for (var i = 0; i < boxes.length; i++) 
              {
           	   if(boxes[i].checked==true)
          		   {
           		   onSelect(false);
          		   }
           	   boxes[i].checked = false;
              }
          }
          else
          {//ȫѡ
              for (var i = 0; i < boxes.length; i++) 
              {
           	   if(boxes[i].checked==false)
   		       {
                      onSelect(true);	   
   		       }
           	   boxes[i].checked = true;
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
			<form id="myform" action="<%=path%>/queryStu.html" method="post">
				<!-- ��ѯ������ -->
				<table class="table" id="table">
					<caption>ѧ����ѯ</caption>
					<tbody>
						<tr>
							<td colspan="4">��ѯ����</td>
						</tr>
						<tr>
							<td>����</td>
							<td><e:text name="qname" /></td>
							<td>ѧ��</td>
							<td><e:text name="qa101" /></td>
						</tr>
						<tr>
							<td>ѧԺ</td>
							<td><e:text name="qa104" /></td>
						</tr>
					</tbody>
				</table>
				<!-- ���ݵ����� -->
				<table class="table" id="table2">
					<thead>
						<tr>
							<th><input type="checkBox" id="allAndNotAll" onclick="setAllNo()"></th>
							<th>����</th>
							<th>ѧ��</th>
							<th>���֤����</th>
							<th>ѧ�����</th>
							<th>ѧԺ</th>
							<th>��ʦ</th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</thead>
			   		</tbody>
					<c:choose>
						<c:when test="${dataList!=null }">
							<!-- ��ʾʵ�ʲ�ѯ�������� -->
							<c:forEach items="${dataList }" var="ins" varStatus="vs">
								<tr>
									<td><input type="checkbox" name="idlist" value="${ins.uid }"
										onclick="onSelect(this.checked)"></td>
									<td>${ins.name }</td>
									<td>${ins.a101 }</td>
									<td>${ins.a102 }</td>
									<td>${ins.a103 }</td>
									<td>${ins.a104 }</td>
									<td>${ins.name2 }</td>
									<td><a href="#" onclick="onEdit('${ins.uid}')">�޸�</a></td>
									<c:choose>
										<c:when test="${ins.ustate eq '0'}">
											<td><a href="#" onclick="onAble('${ins.uid}')">����</a></td>
										</c:when>
										<c:otherwise>
											<td><a href="#" onclick="onDisable('${ins.uid}')">����</a></td>
										</c:otherwise>
									</c:choose>
									<td>
										<c:choose>
											<c:when test="${empty ins.b101}">
												<a href="#" onclick="onDel('${ins.uid}')">ɾ��</a>
											</c:when>
											<c:otherwise>
												<p>ɾ��</p>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
							<!-- ������� -->
							<c:forEach begin="${fn:length(dataList)+1 }" step="1" end="10">
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
							<c:forEach begin="1" step="1" end="10">
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
						<td colspan="10">
							<input type="submit" class="btn btn-info" name="next" value="��ѯ"> 
							<input type="submit" class="btn btn-info" name="next" value="���" formaction="<%=path%>/addStu.jsp"> 
							<input type="submit" class="btn btn-info" name="next" value="����" formaction="<%=path %>/ableStu.html">
							<input type="submit" class="btn btn-info" name="next" value="����" formaction="<%=path %>/disaStu.html">
							<input type="submit" class="btn btn-info" id="del" name="next" value="ɾ��" formaction="<%=path%>/delStu.html" disabled="disabled">
							<a class="btn btn-info" href="<%=path%>/infoManage.jsp">����</a>
						</td>
					</tr>
					</tbody>
				</table>
			</form>
			<form method="post" action="<%=path%>/StuExcel.htms" encType="multipart/form-data">
				<table class="table">
					<tbody>
						<tr><td>
							<input type="file" class="btn btn-info" name="uploadFile" accept=".xls" /> 
							<input class="filesubmit btn btn-info" type="submit" value="Excel����" />
						</td></tr>
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