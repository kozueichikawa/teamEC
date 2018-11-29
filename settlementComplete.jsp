<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv = "refresh" content = "3;URL = 'HomeAction'"/>
<title>決済完了</title>
</head>
<body>
	<s:include value= "header.jsp"/>
	<div class="contents">
		<div class="complete">
			<h1>決済完了</h1>
		<div class = "success">
			決済が完了しました<br>
			ご注文ありがとうございました
		</div>
	</div>
</div>
<s:include value="footer.jsp"/>
</body>
</html>