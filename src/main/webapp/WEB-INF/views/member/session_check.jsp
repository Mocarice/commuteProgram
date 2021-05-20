<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String userid=(String)session.getAttribute("userid");
	if(userid==null) {
%>
	<script>
		alert("로그인한 사용자만 사용가능한 화면입니다.");
		location.href="login.jsp";
	</script>
<% 
	}
%>