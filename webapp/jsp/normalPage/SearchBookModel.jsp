<%--
  Created by IntelliJ IDEA.
  User: 47
  Date: 2016/6/9
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="Entity.Book" %>
<%@ page import="com.mongodb.util.JSON" %>
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
    function getBookDetail(bookId) {
        $.ajax({
            url:"http://localhost:8080/rest/BookService/bookDetail/"+bookId,
            method:"GET",
            contentType:"application/json",
            success:function(data){
                $("#s_bookname").html(data.name);
                $("#s_author").html(data.author);
                $("#s_publisher").html(data.publisher);
                $("#s_price").html(data.price);
                $("#s_stock").html(data.stock);
                $("#s_des").html(data.desc);
                $("#searchModal").modal("show");
            }
        });
    }
    $(function(){
        $("#search").on("click", function(){
            $.ajax({
                url:"searchBook",
                data:{"content":$("#search-content").val()},
                success: function(data){
                    $("#books").html(data);
                    $(".colorable").each(function(){
                        var old = $(this).html();
                        var content = $("#search-content").val();
                        var re = new RegExp("(.*)(" + content + ")(.*)","i");
                        var arr = re.exec(old);
                        if (arr != null)
                            $(this).html(arr[1] + "<span style='color: red;'>" + content + "</span>"+ arr[3]);
                    });
                }
            });
        });
        $("div#hidden").hide();
        /*$('[data-toggle="popover"]').popover({
            html: true,
            title: '书籍详情',
            trigger:'hover focus',
            content: function(){
                return $("div#hidden").html();
            }
        })*/
        $("#searchDes").on("click",function(){
            d = $("#search-content").val();
            $.ajax({
                url:"http://localhost:8080/rest/BookService/searchBook?desc="+d,
                method:"POST",
                success: function(data){
                    //var cc = jQuery.parseJSON(data);
                    for(var i=1;i<data.length+1;i++){
                        b = data[i-1];
                        document.getElementById(i).style.display="";
                        $("#des_bookname"+i).html("书名："+b.name);
                        $("#des_des"+i).html("简介："+b.desc);
                    }
                    $("#descModal").modal("show");
                }
            });
        });
    });
</script>

<style>
    .no-border{
        border-top: 0px!important;
    }
    .label-name{
        width:30%;
    }
</style>

<div class="row" style="padding-bottom: 20px">
    <div class="col-lg-12">
        <form id="search-form">
            <div class = "input-group">
                <input id="search-content" type="text" class="form-control" placefolder="请输入搜索的关键字"/>
                <span id="searchDes"  class="input-group-addon glyphicon glyphicon-search"></span>
            </div>
        </form>
    </div>
</div>
<div id="books">
    <div class="row">
        <%
            for (int i = 0; i < bookArray.length; i++){
        %>
        <div class="col-sm-6 col-lg-3 col-md-3" style="padding:10px"  data-toggle="popover">
            <div style="display:table-cell;vertical-align:top">
                <img src="../resource/image/book.jpg" style="width:180px; height:220px">
                <div style="padding:2px">
                    <table class="table">
                        <tr>
                            <td class="no-border label-name"><strong>书名:</strong></td>
                            <td class="no-border"><%=bookArray[i].getName() %></td>
                        </tr>
                        <tr >
                        <td class="no-border label-name"><strong>作者:</strong></td>
                        <td class="no-border"><%=bookArray[i].getAuthor() %></td>
                        </tr>
                        <tr>
                        <td class="no-border label-name"><strong>出版社:</strong></td>
                        <td class="no-border"><%=bookArray[i].getPublisher() %></td>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td class="no-border label-name"><strong>价格:</strong></td>--%>
                            <%--<td class="no-border"><%=bookArray[i].getPrice() %>--%>
                                <%--</td></tr>--%>
                                <%--<tr>--%>
                                <%--<td class="no-border label-name"><strong>库存:</strong></td>--%>
                                <%--<td class="no-border"><%=bookArray[i].getStock() %></td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                <%--<td class="no-border label-name"><strong>类别:</strong></td>--%>
                                <%--<td class="no-border"><%=bookArray[i].getType() %>--%>
	                            	<span class="pull-right">
                                        <button class="btn btn-success" onclick="getBookDetail(<%=bookArray[i].getId() %>)"><span class="glyphicon glyphicon-hand-up"></span></button>
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
                <div style="padding:2px" id=hidden>
                    <table class="table">
                        <tr>
                            <td class="no-border label-name"><strong>书名:</strong></td>
                            <td class="no-border"><%=bookArray[i].getName() %></td>
                        </tr>
                        <tr >
                        <td class="no-border label-name"><strong>作者:</strong></td>
                        <td class="no-border"><%=bookArray[i].getAuthor() %></td>
                        </tr>
                        <tr>
                        <td class="no-border label-name"><strong>出版社:</strong></td>
                        <td class="no-border"><%=bookArray[i].getPublisher() %></td>
                        </tr>
                        <tr>
                            <td class="no-border label-name"><strong>价格:</strong></td>
                            <td class="no-border"><%=bookArray[i].getPrice() %>
                                </td></tr>
                                <tr>
                                <td class="no-border label-name"><strong>库存:</strong></td>
                                <td class="no-border"><%=bookArray[i].getStock() %></td>
                                </tr>
                                <tr>
                                <td class="no-border label-name"><strong>类别:</strong></td>
                                <td class="no-border"><%=bookArray[i].getType() %></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>

<!-- /.container -->
<div id="modal" class="modal fade">
    <div style="padding:1px">
        <table class="table">
            <tr>
                <td class="no-border label-name" ><strong>书名:</strong></td>
                <td id="bookname" class="no-border colorable"></td>
            </tr>
            <tr>
                <td class="no-border label-name"><strong>作者:</strong></td>
                <td id="author" class="no-border colorable"></td>
            </tr>
            <tr>
                <td class="no-border label-name"><strong>出版社:</strong></td>
                <td id="publisher" class="no-border colorable"></td>
            </tr>
            <tr>
                <td class="no-border label-name"><strong>价格:</strong></td>
                <td id="price" class="no-border"></td>
            </tr>
            <tr>
                <td class="no-border label-name"><strong>库存:</strong></td>
                <td id="stock" class="no-border"></td>
            </tr>
            <tr>
                <td class="no-border label-name"><strong>类别:</strong></td>
                <td id="type" class="no-border"></td>
            </tr>
        </table>
    </div>
</div><!-- /.modal -->

<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">书籍信息</h4>
            </div>
            <div class="modal-body">
                书名：<h4 id="s_bookname"></h4>
                作者：<h4 id="s_author"></h4>
                出版社：<h4 id="s_publisher"></h4>
                价格：<h4 id="s_price"></h4>
                库存：<h4 id="s_stock"></h4>
                简介：<h4 id="s_des"></h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="descModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" >搜索结果</h4>
            </div>
            <div class="modal-body" id="1" >
                <h4 id="des_bookname1" ></h4>
                <h4 id="des_des1" ></h4>
            </div>
            <div class="modal-body" id="2" >
                <h4 id="des_bookname2" ></h4>
                <h4 id="des_des2" ></h4>
            </div>
            <div class="modal-body" id="3" >
                <h4 id="des_bookname3" ></h4>
                <h4 id="des_des3" ></h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
