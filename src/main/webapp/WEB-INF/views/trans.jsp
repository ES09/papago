<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="/resources/css/test.css" >
		<title>번역기</title>
	</head>
	<body>
		
		<div class="content">
			<table >
			<tr>
			<td><h3>실시간 검색 순위 : </h3></td>
			<td><div id="rank" style="color:#663399"></div></td>
			</tr>
			</table> 
		<table>
		<tr>
		<th>번역할 언어</th>
		<td>
		<select class="select-box" id="transSource" >
			<c:forEach items="${codes}" var="codes">	
					<option value="${codes.codeNum}">${codes.codeLanguage}</option>	
			</c:forEach>
		</select>
		</td>
		<td></td>
		<th>번역될 언어</th>
		<td> 
		<select class="select-box" id="transTarget">
		<c:forEach items="${codes}" var="codes">	
				<option value="${codes.codeNum}">${codes.codeLanguage}</option>	
		</c:forEach>
		</select>
		</td>
		</tr>
		<tr>
		<th colspan="2"><textarea class="_twemoji_textarea" rows=10 cols=25 id="transText"></textarea></th>
		<th><button class="button" id="button" onclick="trans()">번역하기</button></th>
		<th colspan="2"><textarea class="_twemoji_textarea" rows=10 cols=25 id="transResult"></textarea></th>
		</tr> 
		</table>		
		</div>
		
			<script> 
				function trans(){
					var source = document.querySelector('#transSource').value;
					var target = document.querySelector('#transTarget').value; 
					var text = document.querySelector('#transText').value;
					var param = 'transSource='+source+'&transTarget='+target+'&transText='+text;
					var xhr = new XMLHttpRequest();
					xhr.open('POST','/trans');
					xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
					xhr.onreadystatechange = function(){
						if(xhr.readyState==4) {
							if(xhr.status==200){
								var res = xhr.response;
								console.log(res);
								document.querySelector('#transResult').value=res;
							}
						}
					}
					xhr.send(param);
				}
				
				window.onload = function(){
					var rank = new Array();
					var ranking = document.querySelector('#rank');
					var xhr = new XMLHttpRequest();
					xhr.open('GET','/rank');
					xhr.onreadystatechange = function(){
						if(xhr.readyState==4) {
							if(xhr.status==200){
								var res = JSON.parse(xhr.responseText);
								var str = '';
								for(var i=0;i<10;i++){
									str = (i+1)+"위 "+res[i].transText;
									rank[i] = str;
								}
							}
						}
					}
					xhr.send();
					var i=0;
					var interval = setInterval(function(){
						if(i>9){
							i=0;
						}
						ranking.innerHTML = "<h3>"+rank[i]+"<h3>";
						i++;
					},2000);
				}
			</script>
	</body>
</html>