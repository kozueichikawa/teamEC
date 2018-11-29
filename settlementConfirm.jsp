<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/settlementconfirm.css" >
<title>決済確認</title>
</head>
<body>
<s:include value="header.jsp"/>
<div class="contents">
<s:if test="destinationInfoDtoList==null || destinationInfoDtoList.size()==0">
<div class="info">
宛先情報はありません。
</div>
</s:if>
<s:else>
<s:form action="SettlementCompleteAction">
<s:iterator value="destinationInfoDtoList" status="index">
<div id="button">
<s:if test="#index.first">
	<input type="radio" name="id" checked="checked" value='<s:property value="id"/>'/>お届け先情報
	</s:if>
<s:else>
	<input type="radio" name="id" value='<s:property value="id"/>'/>お届け先情報
</s:else>
</div>
<div id="confirm">
<table>
<tr>
	<th>名前</th>
	<td><s:property value="familyName"/> <s:property value = "firstName"/></td>
</tr>
<tr>
	<th>ふりがな</th>
	<td><s:property value="familyNameKana"/> <s:property value = "firstNameKana"/></td>
</tr>
<tr>
	<th>住所</th>
	<td><s:property value="userAddress"/></td>
</tr>
<tr>
	<th>電話番号</th>
	<td><s:property value="telNumber"/></td>
</tr>
<tr>
	<th>メールアドレス</th>
	<td><s:property value="email"/></td>
</tr>
</table>
</div>
</s:iterator>
<s:token/>
	<s:submit value = "決済" class="btn_blue"/>
</s:form>
</s:else>
<s:form action = "CreateDestinationAction">
	<s:submit value = "新規宛先登録" class="btn_gray"/>
</s:form>
</div>
<div id="footer">
	<s:include value="footer.jsp"/>
</div>
</body>
</html>