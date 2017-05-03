<%--
  Created by IntelliJ IDEA.
  User: 47
  Date: 2017/3/19
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="../dict/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../dict/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="../dict/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../dict/jquery.json.min.js"></script>

<div class="row">
    <div class="col-md-3">
        <p class="lead">请选择您想浏览的书类</p>
        <div class="list-group">
            <a href="#" class="list-group-item" id="buyBook">我要买书</a>
            <a href="#" class="list-group-item" id="showCart">我的购物车</a>
            <a href="#" class="list-group-item" id="showOrder">我的订单</a>
            <%--<a href="Info.jsp" class="list-group-item">个人信息</a>--%>

        </div>
    </div>

    <div class="col-md-9" id="content">

    </div>
</div>
