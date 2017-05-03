<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	pageContext.setAttribute("mapYear", (Map)request.getAttribute("mapYear")); 
	pageContext.setAttribute("mapMonth", (Map)request.getAttribute("mapMonth")); 
	pageContext.setAttribute("mapDay", (Map)request.getAttribute("mapDay"));
	Map typeBuy=(Map)request.getAttribute("typeBuy");
%>

<script type="text/javascript" src="../dict/js/Chart.min.js"></script>
		<p><strong>用户偏好(根据已购买书籍)</strong></p>
		<div id="canvas-holder">
			<canvas id="chart-area" width="300" height="300"/>
		</div>
<script type="text/javascript">
	color=["#F7464A","#46BFBD","#FDB45C","#949FB1"];
	highlight=["#FF5A5E","#5AD3D1","#FFC870","#A8B3C5"];
	label=["teaching","poetry","fiction","经典阅读"];
	var pieData = [
		<% Iterator<Map> t = typeBuy.entrySet().iterator(); int i=0;
		while (t.hasNext()) { Map.Entry<String,Integer> type=(Map.Entry)t.next();
		%>
			{
				value: <%=type.getValue()%>,
				color: color[<%=i %>],
				highlight: highlight[<%=i %>],
				label: label[<%=i%>]
			},
		<% i+=1; }%>
		           	];
	var ctx = document.getElementById("chart-area").getContext("2d");
	window.myPie = new Chart(ctx).Pie(pieData);
</script>

<style type="text/css">
	.thead {
		color:grey
	}
</style>

<table class="table">
	<p class="thead">按年</p>
<tr>
	<th>日期(年)</th>
	<th>订单数</th>
	<th>书的数目</th>
</tr>
<c:forEach var="entry" items="${mapYear}">
	<tr>
		<td>${entry.key}</td>
		<td>${entry.value[1]}</td>
		<td>${entry.value[0]}</td>
	</tr>
</c:forEach>
</table>
<table class="table">
<p class="thead">按月</p>
<tr>
	<th>日期(月)</th>
	<th>订单数</th>
	<th>书的数目</th>
</tr>
<c:forEach var="entry" items="${mapMonth}">
	<tr>
		<td>${entry.key}</td>
		<td>${entry.value[1]}</td>
		<td>${entry.value[0]}</td>
	</tr>
</c:forEach>
</table>
<table class="table">
<p class="thead">按日</p>
<tr>
	<th>日期(日)</th>
	<th>订单数</th>
	<th>书的数目</th>
</tr>
<c:forEach var="entry" items="${mapDay}">
	<tr>
		<td>${entry.key}</td>
		<td>${entry.value[1]}</td>
		<td>${entry.value[0]}</td>
	</tr>
</c:forEach>
</table>
