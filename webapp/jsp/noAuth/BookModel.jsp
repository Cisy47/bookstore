<%--
  Created by IntelliJ IDEA.
  User: 47
  Date: 2016/6/11
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="Entity.Book" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>书目大厅</title>
    <link href="../dict/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../dict/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="../dict/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../dict/jquery.json.min.js"></script>
</head>
<body style="padding-top:50px">
<%@ page import="Entity.Book" %>
<%@ page import="java.util.HashMap" %>

<%
    Book[] bookArray = (Book[])request.getAttribute("bookArray");
%>

<%@include file="../template/UserNavbar.jsp" %>

<script type="text/javascript">
    function submit(){
        $("#modal-form").modal("hide");
        $.ajax({
            url: "modifyBook",
            data:{"bookId":$("#bookId").text(),
                "price":$("#price").val(),
                "stock":$("#stock").val(),
                "type":$("#type").val()
            },
            success:function(data){
                var arr = $.evalJSON(data);
                if (arr.hasOwnProperty("warning"))
                    showInfo(arr["warning"] + arr["message"]);
                else
                    showInfo(arr["message"]);
                $("#tr"+$("#bookId").text()).find(".price").text($("#price").val());
                $("#tr"+$("#bookId").text()).find(".stock").text($("#stock").val());
                $("#tr"+$("#bookId").text()).find(".type").text($("#type").val());
            }
        });
    }
    $(function(){
        $(".mouse-over-show").css("visibility","hidden");
        $(".delete-button").click(function(){
            var that = this;
            $.ajax({
                url: "removeBook",
                data: {"bookId": $(this).parents(".tr").children(".id").text()},
                success:function(data){
                    var arr = $.evalJSON(data);
                    $(that).parents(".tr").css("display", "none");
                    showInfo(arr["message"]);
                }
            });
        });
        $(".modify-button").click(function(){
            $("#bookId").text($(this).parents("tr").children(".id").text());
            $("#name").text($(this).parents("tr").children(".name").text());
            $("#author").val($(this).parents("tr").children(".author").text());
            $("#publisher").val($(this).parents("tr").children(".publisher").text());
            $("#price").val($(this).parents("tr").children(".price").text());
            $("#stock").val($(this).parents("tr").children(".stock").text());
            $("#type").val($(this).parents("tr").children(".type").text());
            $("#modal-form").modal("show");
        });
        $(".tr").mouseover(function(){
            $(this).find(".mouse-over-show").css("visibility","visible");
        });
        $(".tr").mouseout(function(){
            $(this).find(".mouse-over-show").css("visibility","hidden");
        });
    });
    function addBook(){
        $("#add-form").modal("hide");
        $.ajax({
            url: "addBook",
            data:{
                "name":$("#addbookname").val(),
                "author":$("#addauthor").val(),
                "publisher":$("#addpublisher").val(),
                "price": $("#addprice").val(),
                "stock":$("#addstock").val(),
                "type":$("#addtype").val()
            },
            success:function(data){
                var arr = $.evalJSON(data);
                if (arr.hasOwnProperty("warning"))
                    showInfo(arr["warning"] + arr["message"]);
                else
                    showInfo(arr["message"]);
            }
        });

    }
    function showAdd(){
        $("#add-form").modal("show");
    }

</script>
<div class="container">
    <div class="row">
        <div class="span12">
            <button class="btn btn-primary btn-success pull-right" onclick="showAdd()">添加书籍</button>
        </div>
    </div>
    <p></p>
    <table class="table">
        <tr>
            <th>ID</th>
            <th>书名</th>
            <th>作者</th>
            <th>出版商</th>
            <th>价格</th>
            <th>库存</th>
            <th>类别</th>
        </tr>
        <% if(bookArray!=null){
            for (int i = 0; i <bookArray.length; i++){%>
        <tr class="tr" id="tr<%=bookArray[i].getId()%>">
            <td class="id"><%=bookArray[i].getId()%></td>
            <td class="name"><%=bookArray[i].getName()%></td>
            <td class="author"><%=bookArray[i].getAuthor()%></td>
            <td class="publisher"><%=bookArray[i].getPublisher()%></td>
            <td class="price"><%=bookArray[i].getPrice()%></td>
            <td class="stock"><%=bookArray[i].getStock()%></td>
            <td class="type"><%=bookArray[i].getType()%></td>
            <td>
                <button class="btn btn-sm btn-success pull-right mouse-over-show delete-button" style="margin-left:2px">删除</button>
                <button class="btn btn-sm btn-success pull-right mouse-over-show modify-button">修改</button>
            </td>
        </tr>
        <%}} %>
    </table>
</div>

<script type="text/javascript">
    function showInfo(info){
        $("#modal-content").text(info);
        $("#modal").modal("show");
    }
</script>

<!-- /.container -->
<div id="modal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">信息</h4>
            </div>
            <div class="modal-body">
                <p id="modal-content"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- /.container -->
<div id="modal-form" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="form">
                    <div class="form-group ">
                        <label class="col-sm-2 control-label">ID</label>
                        <div class="col-sm-10">
                            <p id="bookId" style="padding-top:5px"></p>
                        </div>
                    </div>

                    <div class="form-group ">
                        <label class="col-sm-2 control-label">书名</label>
                        <div class="col-sm-10">
                            <p id="name" style="padding-top:5px"></p>
                        </div>
                    </div>

                    <div class="form-group ">
                        <label class="col-sm-2 control-label">作者</label>
                        <div class="col-sm-10">
                            <p id="author" style="padding-top:5px"></p>
                        </div>
                    </div>

                    <div class="form-group ">
                        <label for="publisher" class="col-sm-2 control-label">出版商</label>
                        <div class="col-sm-10">
                            <p id="publisher" style="padding-top:5px"></p>
                        </div>
                    </div>

                    <div class="form-group ">
                        <label for="price" class="col-sm-2 control-label">价格</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="price" id="price" placeholder="请输入书价"/>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="stock" class="col-sm-2 control-label">库存</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="stock" id="stock" placeholder="请输入库存"/>
                        </div>
                    </div>

                    <div class="form-group ">
                        <label for="type" class="col-sm-2 control-label">类别</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="type" id="type" placeholder="请输入类别"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="submit" onclick="submit()">提交</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- /.container -->
<div id="add-form" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="addform">
                    <div class="form-group ">
                        <label class="col-sm-2 control-label">书名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="bookname" id="addbookname" placeholder="请输入书名"/>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label class="col-sm-2 control-label">作者</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="author" id="addauthor" placeholder="请输入作者"/>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="publisher" class="col-sm-2 control-label">出版商</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="publisher" id="addpublisher" placeholder="请输入出版商"/>
                        </div>
                    </div>

                    <div class="form-group ">
                        <label for="price" class="col-sm-2 control-label">书价</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="price" id="addprice" placeholder="请输入书价"/>
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="stock" class="col-sm-2 control-label">库存</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="stock" id="addstock" placeholder="请输入库存"/>
                        </div>
                    </div>

                    <div class="form-group ">
                        <label for="type" class="col-sm-2 control-label">类别</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="type" id="addtype" placeholder="请输入类别"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="add" onclick="addBook()">提交</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
