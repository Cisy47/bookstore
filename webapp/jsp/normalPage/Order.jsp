<%--
  Created by IntelliJ IDEA.
  User: 47
  Date: 2016/6/9
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="Entity.Book" %>
<%@ page import="Entity.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!--%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %-->
<!--%
    pageContext.setAttribute("orderList", (List)request.getAttribute("orderList"));
%-->

<ul type="list-group">
    <% List<Order> orderList =(List<Order>)request.getAttribute("orderList");
    if (orderList!=null){
        for(int i=0;i<orderList.size();i++){
            Order order=orderList.get(i);
    %>

    <!--c:forEach var="order" items="${orderList}"-->
        <li class="list-group-item">
            <div>
                <p>
                    <strong>订单号:</strong> <%=order.getId()%> &nbsp;&nbsp;
                    <strong>价格: </strong><%=order.getPrice()%>&nbsp;&nbsp;
                    <strong>下单日期: </strong><%=order.getCreateDate()%>&nbsp;&nbsp;
		<span class="pull-right">
		<button class="btn btn-primary btn-xs"
                type="button" data-toggle="collapse"
                data-target="#collapse-content-<%=order.getId()%>"
                aria-expanded="false" aria-controls="collapse-content-<%=order.getId()%>"
        >
            显示内容
        </button>
		</span>
                </p>
            </div>
            <div class="collapse" id="collapse-content-<%=order.getId()%>" style="width:100%">
                <table class="table-bordered table">
                    <tr>
                        <th>书名</th>
                        <th>数量</th>
                        <th>价格</th>
                        <th>总价</th>
                    </tr>
                    <% Map<Book,Integer> books = order.getBooks();
                        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
                            Book book=entry.getKey();
                            int num=entry.getValue();
                        %>
                    <!--c:forEach var="book" items="${order.books}"-->
                        <tr>
                            <td><%=book.getName()%></td>
                            <td><%=num%></td>
                            <td><%=book.getPrice()%></td>
                            <td><%=num*book.getPrice()%></td>
                        </tr>
                    <!--/c:forEach-->
                    <%}%>
                </table>
            </div>
        </li>
    <!--/c:forEach-->
    <%}
            }%>
</ul>


