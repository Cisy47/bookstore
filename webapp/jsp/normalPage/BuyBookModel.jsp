<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Entity.Book" %>
<%@ page import="java.util.HashMap" %>

<% 
	Book[] bookArray = (Book[])request.getAttribute("bookArray");
	HashMap shopCart = (HashMap)session.getAttribute("shopCart");
%>

<script type="text/javascript">
	function addBook(bookId){
		$.ajax({
			url:"addBookToCart",
			data:{"bookId":bookId},
			success: function(data){		
				$("#number"+bookId).text(parseInt($("#number"+bookId).text()) + 1);
				$("#number"+bookId).show();
			}
		});
	}
	$(function() {
		$("div#hidden2").hide();
		$('[data-toggle="popover"]').popover({
			html: true,
			title: '书籍详情',
			trigger: 'hover focus',
			content: function () {
				return $("div#hidden2").html();
			}
		});
	})

</script>

<style>
	.no-border{
		border-top: 0px!important;
	}
	.label-name{
		width:30%;
	}
</style>

<div class="row" id="books">
<%
	for (int i = 0; i < bookArray.length; i++){
%>
					<div class="col-sm-12 col-lg-3 col-md-3" style="padding:10px" >
                        <div style="display:table-cell;vertical-align:top">
                            <img src="../resource/image/book.jpg" style="width:180px; height:220px">
							<div style="padding:2px">
								<table class="table">
									<tr>
										<td class="no-border label-name" ><strong>书名:</strong></td>
										<td class="no-border colorable"><%=bookArray[i].getName() %></td>
									</tr>
									<tr>
									<td class="no-border label-name"><strong>作者:</strong></td>
									<td class="no-border colorable"><%=bookArray[i].getAuthor() %></td>
									</tr>
									<tr>
									<td class="no-border label-name"><strong>出版社:</strong></td>
									<td class="no-border colorable"><%=bookArray[i].getPublisher() %></td>
									<%--</tr>--%>
									<%--<tr>--%>
										<%--<td class="no-border label-name"><strong>价格:</strong></td>--%>
										<%--<td class="no-border"><%=bookArray[i].getPrice() %></td>--%>
										<%--</tr>--%>
										<%--<tr>--%>
										<%--<td class="no-border label-name"><strong>库存:</strong></td>--%>
										<%--<td class="no-border"><%=bookArray[i].getStock() %></td>--%>
										<%--</tr>--%>
										<%--<tr>--%>
										<%--<td class="no-border label-name"><strong>类别:</strong></td>--%>
										<%--<td class="no-border"><%=bookArray[i].getType() %>--%>
	                            	<span class="pull-right">
	                            		<button class="btn btn-success" onclick="addBook(<%=bookArray[i].getId() %>)"><span class="glyphicon glyphicon-shopping-cart"></span></button>
	                            	</span>
										<% if (shopCart != null && shopCart.containsKey(bookArray[i].getId())) {%>
										<span id="number<%=bookArray[i].getId() %>" class="pull-right badge" style="background-color:#3A5FCD"><%=shopCart.get(bookArray[i].getId())%></span>
										<%}else{%>
										<span id="number<%=bookArray[i].getId() %>" class="pull-right badge" style="background-color:#3A5FCD;display:none"><%=0%></span>
										<%}%>
										</td>
									</tr>
								</table>
							</div>
							<div style="padding:2px" id=hidden2>
								<table class="table">
									<tr>
										<td class="no-border label-name" ><strong>书名:</strong></td>
										<td class="no-border colorable"><%=bookArray[i].getName() %></td>
									</tr>
									<tr>
										<td class="no-border label-name"><strong>作者:</strong></td>
										<td class="no-border colorable"><%=bookArray[i].getAuthor() %></td>
									</tr>
									<tr>
										<td class="no-border label-name"><strong>出版社:</strong></td>
										<td class="no-border colorable"><%=bookArray[i].getPublisher() %></td>
										</tr>
										<tr>
										<td class="no-border label-name"><strong>价格:</strong></td>
										<td class="no-border"><%=bookArray[i].getPrice() %></td>
										</tr>
										<tr>
										<td class="no-border label-name"><strong>库存:</strong></td>
										<td class="no-border"><%=bookArray[i].getStock() %></td>
										</tr>
										<tr>
										<td class="no-border label-name"><strong>类别:</strong></td>
										<td class="no-border"><%=bookArray[i].getType() %>
										</td>
									</tr>
								</table>
							</div>
						</div>
                    </div>		
<%
	}
%>
</div>



