<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="topLine.jsp"%>
<html>
<head>
<title>˶ʿѧλ����������ϵͳ</title>
</head>

<body>
	${msg}
	<script src="js/pdfobject.min.js"></script>
	<script>
		PDFObject.embed("<%=path%>/upload/${dataMap.b106}");
	</script>
</body>
</html>