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
   //��������ɾ����ť
   var count=0;	
   function onSelect(vState)
   {
 	  vState?count++:count--;
 	  var vDel=document.getElementById("del");
 	  vDel.disabled=(count==0);
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
   function toggle(targetId) 
   {
	   if (document.getElementById)
	   {
		   target=document.getElementById(targetId);
		   if ( target.style.display=="none") 
		   {
			   target.style.display=""; 
		   } 
		   else
		   {
			   target.style.display="none";
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
			<form id="secAddForm" class="secAddForm" action="<%=path%>/secAdd.html" method="post">
		      <table id="table" class="table">
		         <caption>����${empty param.uid?'���':'�޸�'}</caption>
		         <tbody>
		         	<tr>
			            <td colspan="4">��д�������ݣ���û�У�����д</td>
			         </tr>
			         <tr>
			            <td>����</td>
			            <td>
			               <e:text name="name" required="true" autofocus="true" defval="${dataMap.name }"/>
			            </td>
			         </tr>
			         <tr>
			            <td>������ѧ</td>
			            <td>
			               <e:text name="a601" required="true" defval="${dataMap.a601 }"/>
			            </td>
			         </tr>
			         <tr>
			            <td>�о�����</td>
			            <td>
			               <e:text name="a603" defval="${dataMap.a603 }"/>
			            </td>
			         </tr>
			         <tr>
			            <td>ְ��</td>
			            <td>
			               <e:select name="a604" value="����:1,������:2,��ʦ:3" defval="${dataMap.a604 }"/>
			            </td>
			         </tr>
			         <tr>
			            <td>���֤��</td>
			            <td>
			               <e:text name="a605" required="true" defval="${dataMap.a605 }"/>
			            </td>
			         </tr>
			         <tr>
			            <td>�ֻ�����</td>
			            <td>
			               <e:text name="a608" required="true" defval="${dataMap.a608 }"/>
			            </td>
			         </tr>
			         <tr>
			            <td>����</td>
			            <td>
			               <e:email name="a609" required="true" defval="${dataMap.a609 }"/>
			            </td>
			         </tr>
			         <tr>
			            <td>������ɫ</td>
			            <td>
			               <e:groupbox name="roles" value="��ʦ:2,ר��:7" defval="${dataMap.roles }"/>
			            </td>
			         </tr>
			         <tr>
			            <td colspan="2" align="center">
			               <input type="submit" name="next" class="btn btn-info" value="ȷ��${empty param.uid?'���':'�޸�'}"
			                formaction="<%=path%>/sec${empty param.uid?'Add':'Modify'}.html">
			               <input type="submit" name="next" value="��ѯ����" class="btn btn-info" 
                            formaction="<%=path%>/secQuery.html" formnovalidate="formnovalidate">
			            </td>
			         </tr>
			         <tr>
				     	<td colspan="2">${msg}</td>
				     </tr>
		         </tbody>
		      </table>
		      <e:hidden name="uid" value="${dataMap.uid }"/>
              <e:hidden name="qname"/>
		      <e:hidden name="qa601"/>
		      <e:hidden name="qa603"/>
		      <e:hidden name="qa604"/>
		      <e:hidden name="qa605"/>
		      <e:hidden name="qa608"/>
		   </form>
		   <form enctype="multipart/form-data" method="post">
		   	<table class="table">
		   		<tbody>
			   		<tr>
			   			<td>
				   			<input type="file" class="btn btn-info" name="b106" accept="application/vnd.ms-excel">
				   			<input type="submit" name="next" value="Excel��������"
			                formaction="<%=path%>/SecAddByExcel.com" class="btn btn-info"
			                formnovalidate="formnovalidate">
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