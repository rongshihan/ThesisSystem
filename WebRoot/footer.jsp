<%@ page language="java" pageEncoding="GBK"%>
<script type="text/javascript">
$(document).ready(function() {
	autoFooterHeight();
})
// ����ײ��Զ�����������
function autoFooterHeight() {
	// ��ȡ���ݵĸ߶�
	var bodyHeight = $("body").height();
	// ��ȡ�ײ������ĸ߶�
	var navHeight = $(".navbar").height();
	// ��ȡ��ʾ���ĸ߶�
	var iHeight = document.documentElement.clientHeight || document.body.clientHeight;
	// ������ݵĸ߶ȴ��ڣ����ڵĸ߶� - �����ĸ߶ȣ�,�Ƴ�������ʽ
	if (bodyHeight > (iHeight - navHeight)) {
		$("#footer").removeClass("navbar-fixed-bottom");
	}
}
</script>
<div style="height: 50px"><table>&#12288;</table></div>
<nav class="navbar navbar-default navbar-fixed-bottom footer">
	<p class="footer-P">��ϵ���ǣ�&#12288;</p>
	<img alt="" src="./images/qq.png" class="footer-img" width="17px" height="17px">
	<p class="footer-P">QQ:4543446&#12288;</p>
	<img alt="" src="./images/mail.png" class="footer-img" width="17px" height="17px">
	<p class="footer-P">���䣺82648912@ifa.com</p>
</nav>