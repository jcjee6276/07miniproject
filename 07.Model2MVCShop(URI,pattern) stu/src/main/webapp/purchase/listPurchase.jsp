<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
<%@ page import="java.util.*" %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.common.util.*" %>
<%@ page import="com.model2.mvc.view.user.*" %>
<%


List<Purchase> list= (List<Purchase>)request.getAttribute("list");
Page resultPage=(Page)request.getAttribute("resultPage");

Search search = (Search)request.getAttribute("search");
//==> null �� ""(nullString)���� ����

%>
 --%>





<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetPurchaseList(resultPage.getCurrentPage()) {
		document.getElementById("resultPage.getCurrentPage()").value = resultPage.getCurrentPage();
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listUser.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">���� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">��ü ${resultPage.totalCount } �Ǽ�, ����  ${resultPage.currentPage } ������</td>
	</tr> 	
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ��ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ȭ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��������</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<%--
		int no = list.size();
		for(int i=0; i<list.size(); i++){
			Purchase vo = (Purchase)list.get(i);
	--%>
	 <c:set var="i" value="0" />
	<c:forEach var="pur" items="${list }">
	<c:set var="i" value="${ i+1 }" />
	<tr class="ct_list_pop">
		<td align="center">
			<a href="/purchase/getPurchase?tranNo=${pur.tranNo }"><c:out value="${i }"/></a>
		</td> 
		<td></td>
		<td align="left">
			<a href="/user/getUser?userId=${pur.buyer.userId }">${pur.buyer.userId } </a>
		</td>
		<td></td> 
		<td align="left">${pur.receiverName }</td>
		<td></td> 
		<td align="left">${pur.receiverPhone }</td>
		<td></td>  
		
		<c:choose>
		<c:when test="${empty pur.tranCode }">
		<td align="left">
		<c:out value="�Ǹ���" />
		</td>	
		</c:when>
		<c:when test="${'1' == pur.tranCode.trim() }">
		<td align="left">
		<c:out value="���ſϷ�" />
		</td>	
		</c:when>
		<c:when test="${'2' == pur.tranCode.trim() }">
		<td align="left">
		<c:out value="�����" />
		<a href="/purchase/updateTranCode?tranNo=${ pur.tranNo}&tranCode=3">���ǵ���</a>
		</td>	
		</c:when>
		<c:when test="${'3' ==pur.tranCode.trim() }">
		<td align="left">
		<c:out value="��ۿϷ�" />
		</td>	
		</c:when>
		</c:choose>
		
	</tr>
	</c:forEach>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	
	
	
	
	
	
	
	
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 
			<input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage }"/>
				<jsp:include page="../common/pageNavigatorPurchase.jsp"/>	
		
		</td>
	</tr>
</table>

<!--  ������ Navigator �� -->
</form>

</div>

</body>
</html>