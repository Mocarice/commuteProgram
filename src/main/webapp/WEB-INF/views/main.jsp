<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>


</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type = "text/javascript">
	if(${getIp} == 0){
		alert("잘못된 장소에서 로그인 하셨습니다. 자동 로그아웃 됩니다.");
		document.location.href="<c:url value='/member/logout.do'/>"
	}else{
	alert('${msg}님 환영합니다.');
	}
</script>
<script type = "text/javascript">


let InTime = "";
let OutTime = "";

function checkInTime(){	
	let today = new Date();
	let year = today.getFullYear();
	let month = today.getMonth() + 1;
	let date = today.getDate();
	let hours = today.getHours();
	let minutes = today.getMinutes();
	
	let todayDate = year + "-" + month + "-" + date
	
	if(hours == 12){
		hours = 13;
		minutes = 0;
	}
	if(minutes >=0 && minutes <10){
		minutes = '0'+minutes
	}
	InTime = hours + "-" + minutes;
	
	$.ajax({
		url:"inTime.do",
		type:"POST",
		data:"date=" + todayDate + "&inTime=" + InTime,
		success:function(){
			alert("출근처리 되었습니다.")
		},error:function(){
			alert("error");
		}
	});
	
	
}

function checkOutTime(){	
	let today = new Date();
	let year = today.getFullYear();
	let month = today.getMonth() + 1;
	let date = today.getDate();
	let hours = today.getHours();
	let minutes = today.getMinutes();
	
	let todayDate = year + "-" + month + "-" + date
	
	if(hours == 12 || (hours == 11 && minutes >=30)){
		hours = 12;
		minutes = 0;
	}
	if(minutes >=0 && minutes <10){
		minutes = '0'+minutes
	}
	OutTime = hours + "-" + minutes;
	
	$.ajax({
		url:"outTime.do",
		type:"POST",
		data:"date=" + todayDate + "&outTime=" + OutTime,
		success:function(result){
			alert("퇴근 처리 되었습니다. 오늘 하루 고생하셨습니다.")
		}
	});
	
	
}



</script>

</head>
<body>

<h1>kongsis의 근태관리 사이트</h1>

<h4><%@ include file = "include/menu.jsp" %></h4>
<br><br>
<button type = "button" id = "btnin" onclick = "checkInTime();">출근</button>

<button type = "button" id = "btnout" onclick = "checkOutTime()">퇴근</button>

		<c:set var = "authorize" value = "${result}"/>
		<c:if test = "${authorize eq 1}">
		<h3>
            <a href="db_all.do">직원 출퇴근 기록 보기</a>
            <a href="db_modify.do">직원 출퇴근 기록 수정하기</a>
        </h3>
        </c:if>
        <c:if test = "${authorize eq 0}">
        <h3>
            <a href="db_my.do">나의 출퇴근 기록</a>
        </h3>
        </c:if>

</body>
</html>