
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 화면</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$("#btnLogin").click(function(){
		var userId = $("#userId").val();
		var userPw = $("#userPw").val();
		
		if(userId == ""){
			alert("아이디를 입력하세요.");
			$("#userId").focus(); //입력포커스 이동
			return;
		}
		if(userPw == ""){
			alert("비밀번호를 입력하세요.");
			$("#userPw").focus();
			return;
		}
		console.log("asdf");
		document.form1.action="${path}/member/loginCheck.do"
		document.form1.submit();
	});
});

</script>
</head>
<body>
<h1 style="text-align:center;">kongsis의 근태관리 사이트</h1>
<h4 style="text-align:right;"><%@ include file="../include/menu.jsp" %></h4>
<h5 style="text-align:center;">*회사 이외의 장소에서는 로그인 되지 않습니다.</h5>
<br><br><br>
 	<form name = "form1" method = "post">
 		<table style="text-align:center; margin: auto;" border = "1" width = "400px">
 			<tr>
 				<td>아이디</td>
 				<td><input name = "userId" id = "userId"></td>
 			</tr>
 			<tr>
 				<td>비밀번호</td>
 				<td><input type="password" name = "userPw" id = "userPw"></td>
 			</tr>
 			<tr>
 				<td colspan="2" align="center">
 					<button type = "button" id = "btnAdd" onclick = "location.href = 'add.do'">회원가입</button>
 					<button type = "button" id = "btnLogin">로그인</button>
 				<c:if test = "${msg == 'failure' }">
 					<div style="color:red">
 						아이디 또는 비밀번호가 일치하지 않습니다.
 					</div>
 				</c:if>
 				<c:if test = "${msg == 'logout' }">
 					<div style="color:red">
 						로그아웃되었습니다.
 					</div>
 				</c:if>
 				</td>
 			</tr>
 		</table>
 	</form>
</body>
</html>