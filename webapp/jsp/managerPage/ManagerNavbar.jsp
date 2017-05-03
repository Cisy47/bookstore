<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Entity.User" %>
<!--% if (session.getAttribute("user") != null) {
	User user = (User)session.getAttribute("user");%-->
<% String username =request.getUserPrincipal().getName();%>
	<!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">C.C.Bookstore</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="../normalPage/Center.jsp">用户界面</a>
                    </li>
                    <li>
                        <a href="../showUser">管理用户</a>
                    </li>
                    <li>
                        <a href="../showBookToManager">管理书籍</a>
                    </li>
                    <li>	
                    	<a href="../normalPage/StatisticsSaleModel.jsp">销售统计</a>
                    </li>
                    <!--%} %-->
                    <li>
                    <a href="#">联系我们</a>
                    </li>
                    <li>
                        <a href="logout">登出</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>