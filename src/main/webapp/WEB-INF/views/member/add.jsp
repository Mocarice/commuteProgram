<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$("#btnAdd").click(function(){
		var userId = $("#userId").val();
		var userPw = $("#userPw").val();
		var userName = $("#userName").val();
		
		
		if(userPw == ""){
			alert("이름을 입력하세요.");
			$("#userName").focus();
			return;
		}
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
		
		$.ajax({
			url:"addUser.do",
			type:"POST",
			data:"userName=" + userName + "&userId=" + userId + "&userPw=" + userPw,
			success:function(result){
				alert("회원가입 되었습니다.");
				window.location.href = "login.do"
			},error:function(){
				alert("이미 존재하는 ID입니다.");
			}
		});
	});
});

</script>

</head>
<body>
<form name = "form2" method = "post">
 		<table border = "1" width = "400px">
 			<tr>
 				<td>이름</td>
 				<td><input name = "userName" id = "userName"></td>
 			</tr>
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
 					<button type = "button" id = "btnAdd">회원가입</button>
 				<c:if test = "${msg == 'failure' }">
 					<div style="color:red">
 						아이디가 중복됩니다.
 					</div>
 				</c:if>
 				</td>
 			</tr>
 		</table>
 	</form>
</body>
</html>