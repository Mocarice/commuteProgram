<p><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type = "text/css">

table,td{
border: 1px solid red
}

table{
width:800px;
text-align:center;
}

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type = "text/javascript">

let resultObj = {};
let resultList = [];
let userObj = {};
let userList = [];


<c:forEach var = "CommuteVO" items = "${dataList}">
	resultObj = {};
	resultObj.userId = '${CommuteVO.userId}';
	resultObj.userName = '${CommuteVO.userName}';
	resultObj.date = '${fn:split(CommuteVO.date,'-')[0]}년 ${fn:split(CommuteVO.date,'-')[1]}월 ${fn:split(CommuteVO.date,'-')[2]}일';
	resultObj.inTime = '${fn:split(CommuteVO.inTime,'-')[0]}시 ${fn:split(CommuteVO.inTime,'-')[1]}분';
	resultObj.outTime = '${fn:split(CommuteVO.outTime,'-')[0]}시 ${fn:split(CommuteVO.outTime,'-')[1]}분';
	resultObj.totalTime = '총 ${CommuteVO.totalTime}';
	
	resultList.push(resultObj);
</c:forEach>
console.log(resultList);

<c:forEach var = "memberVO" items = "${userList}">
	userObj = {};
	userObj.userId = '${memberVO.userId}';
	userObj.userName = '${memberVO.userName}';
	
	userList.push(userObj);
</c:forEach>	

console.log(userList);


let _html = "";
let _index = 1;


function select() {
	return `
			<select name = "user" id = "user">
				<option value="">이름 선택</option>
			`
}

function selectbox(userName) {
	return `
			<option value="\${userName}">\${userName}</option>
			`
}

function search(){
		window.location.href = "db.do?userName=" + $("#user option:selected").val().toString();
}

function header() {
	return `
			</select>
			
			<button type = "button" id = "btnSearch" onclick = "search();">조회</button>
	
			<h1><a href = "/main.do">처음으로 돌아가기</a></h1>
			  
		    <table>
		        <thead>
		            <tr>
		                <th>아이디</th>
		                <th>이름</th>
		                <th>날짜</th>
		                <th>출근시간</th>
		                <th>퇴근시간</th>
		                <th>근무시간</th>
		            </tr>
		        </thead>
		        <tbody>
		    `
}
function body(userId, userName, date, inTime, outTime, totalTime){
	return `
				<tr>
			    <td>\${userId}</td>
			    <td>\${userName}</td>
			    <td>\${date}</td>
			    <td>\${inTime}</td>
			    <td>\${outTime}</td>
			    <td>\${totalTime}</td>
				</tr>
			`
}

function bottom(){
	return `
				</tbody>
			    </table>
		    `
}



_html += select();
for(let i = 0; i < userList.length; i++){
	_html += selectbox(userList[i].userName);
}

_html += header();
for(let i = 0; i < resultList.length; i++){
	let result = resultList[i];
	if(result.userName == '${param.userName}'){
	_html += body(result.userId, result.userName, result.date, result.inTime, result.outTime, result.totalTime);
	}
	if(i==resultList.length -1){
		_html += bottom();
	}	
}

$(function(){
	$(".insertHtml").append(_html);
	let _length = $(".appendPage").length;
	for(let i = 0; i < _length; i++){
		$($(".appendPage")[i]).text((i+1)+" / " +_length);
	}
	//window.print();
});

</script>
</head>
<body class = "insertHtml">

</body>
</html>
 
</p>