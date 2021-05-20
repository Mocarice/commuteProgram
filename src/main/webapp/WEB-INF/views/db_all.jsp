<p><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>직원 출퇴근 기록</title>
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
let intime12 = 0;
let outitme12 = 0;

<c:forEach var = "CommuteVO" items = "${dataList}">
	resultObj = {};
	resultObj.userId = '${CommuteVO.userId}';
	resultObj.userName = '${CommuteVO.userName}';
	resultObj.year = parseInt("${fn:split(CommuteVO.date,'-')[0]}",10);
	resultObj.month = parseInt("${fn:split(CommuteVO.date,'-')[1]}",10);
	resultObj.day = parseInt("${fn:split(CommuteVO.date,'-')[2]}",10);
	resultObj.date = '${fn:split(CommuteVO.date,'-')[0]}년 ${fn:split(CommuteVO.date,'-')[1]}월 ${fn:split(CommuteVO.date,'-')[2]}일';
	if(parseInt("${fn:split(CommuteVO.inTime,'-')[0]}",10) > 12){
		inTime12 = parseInt("${fn:split(CommuteVO.inTime,'-')[0]}",10) - 12;
		resultObj.inTime = inTime12 + " : ${fn:split(CommuteVO.inTime,'-')[1]}";
	}else{
		resultObj.inTime = '${fn:split(CommuteVO.inTime,'-')[0]} : ${fn:split(CommuteVO.inTime,'-')[1]}';
	}
	if(parseInt("${fn:split(CommuteVO.outTime,'-')[0]}",10) > 12){
		outTime12 = parseInt("${fn:split(CommuteVO.outTime,'-')[0]}",10) - 12;
		resultObj.outTime = outTime12 + " : ${fn:split(CommuteVO.outTime,'-')[1]}"
	}else {
		resultObj.outTime = '${fn:split(CommuteVO.outTime,'-')[0]} : ${fn:split(CommuteVO.outTime,'-')[1]}';
	}
	resultObj.totalTime = '${CommuteVO.totalTime}';
	
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



function selectUser() {
	return `
			<select name = "user" id = "user">
				<option value="">이름 선택</option>
			`
}

function selectboxUser(userName) {
	return `
			<option value="\${userName}">\${userName}</option>
			`
}

function selectMonth() {
	return `
			</select>
			<select name = "month" id = "month">
				<option value="">월 선택</option>
			`
}

function selectboxMonth(month) {
	return `
			<option value="\${month}">\${month}월</option>
			`
}

function monthMaxDate(month){
	if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
		return 31;
	}else if(month == 4 || month == 6 || month == 9 || month == 11){
		return 30;
	}else if(month == 2){
		return 28; //바뀔 수 있음
	}
	
}

function search(){
	let maxDay = monthMaxDate($("#month option:selected").val().toString())
	console.log(maxDay);
	window.location.href = "db_all.do?userName=" + $("#user option:selected").val().toString() +"&month=" + $("#month option:selected").val().toString() + "&maxDay=" + maxDay;
}

function header() {
	return `
			</select>
			
			<button type = "button" id = "btnSearch" onclick = "search();">조회</button>
	
			<h1><a href = "/main.do">처음으로 돌아가기</a></h1>
			  
		    <table>
		        <thead>
		            <tr>
		                <th>날짜</th>
		                <th>출근시간</th>
		                <th>퇴근시간</th>
		                <th>근무시간</th>
		            </tr>
		        </thead>
		        <tbody>
		    `
}
function body(date, inTime, outTime, totalTime){
	return `
				<tr>
			    <td>\${date}일</td>
			    <td>\${inTime}</td>
			    <td>\${outTime}</td>
			    <td>\${totalTime}</td>
				</tr>
			`
}
function body_pre(index){
	return `
				<tr>
			    <td>\${index}일</td>
			    <td></td>
			    <td></td>
			    <td></td>
				</tr>
			`
}

function bottom(){
	return `
				</tbody>
			    </table>
		    `
}



let _html = "";
let _index = 1;

_html += selectUser();
for(let i = 0; i < userList.length; i++){
	_html += selectboxUser(userList[i].userName);
}
_html += selectMonth();
for(let i = 1; i <= 12; i++){
	_html += selectboxMonth(i);
}

_html += header();
for(let i = 0; i < resultList.length; i++){
	let result = resultList[i];
	if(result.userName == '${param.userName}'&& result.month == '${param.month}'){
		if(result.day != _index){
			for(_index;_index<result.day;_index++){
				_html += body_pre(_index);
				console.log(_index);
			}
		}
	_html += body(result.day, result.inTime, result.outTime, result.totalTime);
	_index++;
	}
	if(i==resultList.length -1){
		if(_index < '${param.maxDay}'){
			for(_index;_index<='${param.maxDay}';_index++){
				_html += body_pre(_index);
			}
		}
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