<%--
  Created by IntelliJ IDEA.
  User: 47
  Date: 2016/6/5
  Time: 16:40
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
    <!--script type="text/javascript" src="../dict/bootstrap-modal.js"></script-->
</head>
<body>

<%@include file="../template/UserNavbar.jsp" %>

<script type="text/javascript">
    $(function(){
        function showBook(){
            $.ajax({
                url:"showAllBook",
                success:function(data){
                    $("#content").html(data);
                    $("div#hidden2").hide();
                    $('[data-toggle="popover"]').popover({
                        html: true,
                        title: '书籍详情',
                        trigger: 'hover focus',
                        content: function () {
                            return $("div#hidden2").html();
                        }
                    });

                }
            });
        }
        $("#buyBook").on("click", showBook);
        $("#showCart").on("click", function(){
            $.ajax({
                url:"showCart",
                success:function(data){
                    $("#content").html(data);
                }
            });
        });
        function categoryForm(value){
            $.ajax({
                url:"showCategoryBook",
                data:{"category":value},
                success:function(data){
                    $("#content").html(data);
                    $("div#hidden2").hide();
                    /*$('[data-toggle="popover"]').popover({
                        html: true,
                        title: '书籍详情',
                        trigger: 'hover focus',
                        content: function () {
                            return $("div#hidden2").html();
                        }
                    });*/
                }
            })
            return false;
        }
        $("#showOrder").on("click",
                function(){
            $.ajax({
                url:"showOrder",
                success:function(data){
                    $("#content").html(data);
                }
            });
        });
        function UserInitial(){
            $.ajax({
                url:"getUser",
                success:function(data){}
            });
        }
        UserInitial();
        $("#categorySave").on("click",function(){
            var obj = document.getElementsByName("optionsRadiosinline");

            for (var i=0;i<obj.length;i++)
            {
                if (obj[i].checked){
                    categoryForm(obj[i].value);
                }
            }
        })

    });
</script>

<style type="text/css">
    .slide-image {
        width: 100%;
    }
</style>
<!-- Page Content -->
<div class="container" style="padding-top:70px;">

    <div class="row">

        <div class="col-md-3">
            <p class="lead">欢迎来到书目大厅</p>
            <div class="list-group">
                <!--div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle"
                            data-toggle="dropdown">
                        我要买书 <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a class="ctform" href="javascript:void(0)" id="Fiction">小说</a></--li>
                        <li><a class="ctform" href="javascript:void(0)" id="poetry">诗歌</a></--li>
                        <!--li><a onclick="categoryForm(this.id)" id="java">编程</a></li>
                        <li><a onclick="categoryForm(this.id)" id="teaching">教材</a></li>
                    </ul>
                </div>
                </div-->
                    <!--a href="#" class="list-group-item" id="buyBook">我要买书</a-->
                    <a href="#example" data-toggle="modal" class="list-group-item">我要买书</a>
                    <a href="#" class="list-group-item" id="showCart">我的购物车</a>
                    <a href="#" class="list-group-item" id="showOrder">我的订单</a>
            </div>
        </div>

        <div class="col-md-9" id="content">

        </div>

    </div>

</div>
<!-- /.container -->

<div class="container">
    <hr>
    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p align="center">Copyright &copy; C.C.Bookstore 2016</p>
            </div>
        </div>
    </footer>

</div>

<script type="text/javascript">
    function showInfo(info){
        $("#modal-content").text(info);
        $("#modal").modal("show");
    }
</script>

<div class="container">
    <div id="example" class="modal fade" style="display: none; "
         tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="btnCancel">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    书籍种类选择
                </h4>
            </div>
            <div class="modal-body">
                <div>
                    <label class="checkbox-inline">
                        <input type="radio" name="optionsRadiosinline" id="optionsRadios1" value="fiction" checked> 科幻
                    </label>
                    <label class="checkbox-inline">
                        <input type="radio" name="optionsRadiosinline" id="optionsRadios2" value="poetry"> 诗歌
                    </label>
                    <label class="checkbox-inline">
                        <input type="radio" name="optionsRadiosinline" id="optionsRadios3" value="java"> Java
                    </label>
                    <label class="checkbox-inline">
                        <input type="radio" name="optionsRadiosinline" id="optionsRadios4" value="finance">金融
                    </label>

                </div>
            </div>
            <div class="modal-footer" style="border-top:none;">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="btnClose">关闭</button>
                <button type="submit" class="btn btn-primary" data-dismiss="modal" id="categorySave">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div>
    <!--div id="popDiv" class="radio-inline">
        <form id="categoryForm" action="" method="get">
            您想浏览哪类图书<br /><br/>
            <label><input name="fiction" type="radio" value="fiction" />小说</label>
            <label><input name="poetry" type="radio" value="poetry" />诗歌</label>
            <label><input name="java" type="radio" value="java" />编程</label>
            <label><input name="finance" type="radio" value="finance" />金融</label>
        </form>
        <button onclick="closeDivFun()">取消</button>
    </div-->

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



</body>
</html>
