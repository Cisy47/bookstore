<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="Entity.Book" %>
<%@ page import="Entity.ShopCartItem" %>
<!--%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %-->
<!--%
	pageContext.setAttribute("arrayList", (ArrayList)session.getAttribute("arrayList"));
%-->

<script type="text/javascript" src="../dict/jquery.json.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jsencrypt/2.3.1/jsencrypt.min.js"></script>
<script type="text/javascript">
	function addBookNum(bookId, bookNum){
		$.ajax({
			url:"addBookToCart",
			data:{"bookId":bookId,"bookNum":bookNum},
			success: function(data){	
				var num = parseInt($("#number"+bookId).text()) + parseInt(bookNum);
				if (num>0)
					$("#number"+bookId).text(num);
				else
					$("#tr"+bookId).hide();
			}
		});
	}
</script>

<table class="table">
	<tr>
		<th>书名</th>
		<th>作者</th>
		<th>数量</th>
		<th>价格</th>
		<th>总价</th>
	</tr>
	<% ArrayList<ShopCartItem> cart =(ArrayList<ShopCartItem>)request.getAttribute("arrayList");
		if (cart!=null){
			for(int i=0;i<cart.size();i++){
				ShopCartItem item=cart.get(i);
				Book b = item.getBook();
	%>

<!--c:forEach var="item" items="${arrayList}-->
	<tr id="tr<%=b.getId()%>" class="c">
		<td><%=b.getName()%> </td>
		<td><%=b.getAuthor()%> </td>
		<td>
			<button class="btn btn-success" style="padding:2px"
				onclick="addBookNum(<%=b.getId()%>,-1)">
				<span class="glyphicon glyphicon-minus"></span>
			</button>
			<span id="number<%=b.getId()%>"><%=item.getBookNum()%></span>
			<button class="btn btn-success" style="padding:2px"
				onclick="addBookNum(<%=b.getId()%>,1)">
				<span class="glyphicon glyphicon-plus"></span>
			</button>
		</td>
		<td><%=b.getPrice()%> </td>
		<td><%=b.getPrice()*item.getBookNum()%> </td>
	</tr>
<!--/c:forEach-->
	<%}
	}
	%>
    <!--
    <!--c:forEach var="item" items="${cart.keySet()}">
        <tr id="tr${item.id}" class="c">
            <td>${item.name} </td>
            <td>${item.author} </td>
            <td>
                <button class="btn btn-success" style="padding:2px"
                        onclick="addBookNum(${item.id},-1)">
                    <span class="glyphicon glyphicon-minus"></span>
                </button>
                <span id="number${item.book.id}">${item.bookNum}</span>
                <button class="btn btn-success" style="padding:2px"
                        onclick="addBookNum(${item.id},1)">
                    <span class="glyphicon glyphicon-plus"></span>
                </button>
            </td>
            <td>${item.price} </td>
            <td>${item.price*cart.get(item)} </td>
        </tr>
    <!--/c:forEach>
    -->
</table>

<script type="text/javascript">
	function createOrder(payPass){
		$.ajax({
			url:"createOrder",
			data:{"payPass":payPass},
		success:function(data){
				var arr = $.evalJSON(data);
				showInfo(arr["message"]);
				$("tr.c").hide();
			},
			error:function(data){
				var arr= $.evalJSON(data);
				showInfo("下单失败！"+arr["message"]);
			}
		});
	}
	function getPayPassword() {
		var publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAviSuCu4Yg/WAyjp06qiaE/ioI2M/ACT9UTUVxWtM7IZlXMQZPjLn0H1x0zmJ/VLIhnBliyb06QLvtrrBFRt4jnOJR5LjoTg/g8XYdVXN6a+XFjqFvOUPgzZ7OdywOoXxiO+M7WrvT0XgqyBqCnDADpY1eucDqfIDYYOBHKbtMkh0N4ZVBcfULb1Sm+Q7ed+jUa8eXPQPhMrWvhQkIeZJh+hCIrNjXUxyfZPh1tSvqoJYArbyHZs8LnbUtjIQCx9OlR9+xJTx3L9h89I4D+hqA4CZqxUzfibsu5XgYKnoSri2OCR2FefSfYlCd8Fysp0wET/r1L141qnhoMQtrUs8jwIDAQAB";
		var name=prompt("请输入付款密码","");
		if (name!=null && name != "")
		{
			/*var encrypt = new JSEncrypt();
			encrypt.setPublicKey(publicKey);
			var password  = encrypt.encrypt(name);
			//提交之前，检查是否已经加密。假设用户的密码不超过20位，加密后的密码不小于20位
			/if(password.length < 20) {
				alert("encryption failed");
			}
			createOrder(password);*/
			createOrder(name);
		}
	}
</script>
<div style="padding-left: 45%">
	<button class="btn btn-primary" id="createOrder" onclick="getPayPassword()">下单</button>
</div>



