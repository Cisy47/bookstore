<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                        <a href="../normalPage/Center.jsp">书目大厅</a>
                    </li>
                    <!--% if (user.getRole()== 2) {%-->
                    <li>
                        <a href="../managerPage/ManagerMain.jsp">管理员界面</a>
                    </li>
                    <li>
                    <a href="counterTest">书店访问量</a>
                    </li>
                    <li>
                        <a href="../normalPage/chatRoom.jsp">聊天室</a>
                    </li>
                    <li>
                        <a href="../normalPage/Info.jsp">个人信息(<%=username %>)</a>
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
<!--%}else{%-->
	<!-- Navigation >
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Bookstore</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="Center.jsp">书目大厅</a>
                    </li>
                    <li>
                        <a href="#">联系我们</a>
                    </li>
                    <li>
                    <a href="Info.jsp">个人信息</a>
                    </li>
                    <li>
                        <a href="Welcome.jsp">登陆</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav-->
<!--%}%-->