<%@ page import="com.opensymphony.xwork2.ActionContext" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>书店访问量</title>
    <link href="../dict/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../dict/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="../dict/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../dict/jquery.json.min.js"></script>
</head>
<body>
<%@ include file="../template/UserNavbar.jsp" %>
<%@ include file="../template/Tool.jsp" %>
<%
    int counter=(Integer)request.getAttribute("hitCount");
%>
<script type="text/javascript">

</script>
<div class="container" id="loadContent" style="padding-top:70px;">
        <div class="row-fluid">
            <p><strong>C.C.Bookstore</strong></p>
                <dl>
                    一切问题请联系楼西皮 cisy47@sjtu.edu.cn
                    <dt>
                        访问量：<%=counter%>
                    </dt>
                </dl>
            </div>
        </div>
</div><!-- container -->
</body>
</html>