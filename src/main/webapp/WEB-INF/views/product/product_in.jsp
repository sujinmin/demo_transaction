<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function delete_in(idx) {
		if(confirm("정말 입고를 취소하시겠습니까?")==false)return;
		
		location.href="delete_in.do?idx=" + idx;
	}
</script>
</head>
<body>

<table>
	<caption>::::입고목록::::</caption>
	<tr>
		<th width="35%">제품명</th>
		<th width="20%">수량</th>
		<th width="45%">입고일자</th>
	</tr>
	
	<c:if test="${ empty map.in_list }">
		<td colspan="3" align="center">
		   입고목록이 없습니다
		</td>
	</c:if>
	
	<c:forEach var="vo" items="${ requestScope.map.in_list }">
		<tr>
			<td>${ vo.name }</td>
			<td>${ vo.cnt }</td>
			<td>${ vo.regdate }
				<input type="button" value="x" onclick="delete_in('${ vo.idx }');">			
			</td>
		</tr>
	</c:forEach>
	
</table>


</body>
</html>