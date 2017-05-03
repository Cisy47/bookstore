<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "Entity.User" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>个人信息</title>
		<link href="../dict/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="../dict/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="../dict/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="../dict/jquery.json.min.js"></script>
	</head>
	<body>
	<%@ include file="../template/UserNavbar.jsp" %>
	<%@ include file="../template/Tool.jsp" %>
	
	<% 
	if (session.getAttribute("user") == null){
		response.getWriter().println("请先登录!");
		return;
	}
	User user = (User)session.getAttribute("user");
	%>	
		<script type="text/javascript">
			$(function(){
				function showPersonInfo(){
					$.ajax({
						url:"PersonInfo.jsp",
						success: function(data){
							$("#content").html(data);
							}
						});
				}
				$("#my_info").on("click", showPersonInfo);
				$("#my_order").on("click",function(){
					$.ajax({
						url:"showOrder",
						success:function(data){
							$("#content").html(data);
						}
					});
				});
				$("#my_statistics").on("click",function(){
					$.ajax({
						url:"personalStatistic",
						success:function(data){
							$("#content").html(data);
						}
					});
				});
				showPersonInfo();
			});
//			function addPhoto(){
//				$.ajax({
//					url:"addUserPhoto",
//					method:"post",
//					success: function(data){
//						$("#content").html(data);
//					}
//				});
//			}
//			function initFileInput(ctrlName, uploadUrl) {
//				var control = $('#' + ctrlName);
//				control.fileinput({
//					language: 'zh', //设置语言
//					uploadUrl: uploadUrl, //上传的地址
//					allowedFileExtensions : ['jpg', 'png','gif'],//接收的文件后缀
//					showUpload: false, //是否显示上传按钮
//					showCaption: false,//是否显示标题
//					browseClass: "btn btn-primary", //按钮样式
//					previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
//				});
//			}
		</script>
		<div class="container" id="loadContent" style="padding-top:70px;">
			<div class="row">
				<!-- 左侧侧边栏 -->
				<div class="col-sm-12 col-md-3 col-md-offset-0 sidebar">
					 <div class="well sidebar-nav">
						<div style="padding-top:10px">
							<img  class="img-thumbnail" alt="头像" src="showUserPhoto" style="width: 200px; height: 200px;">
						</div>
						 <form action="addUserPhoto" method="post" enctype="multipart/form-data">
						 	 <input  type="file" name="file" label="选择图片" value="上传头像">
							 <input  type="submit" value="确定上传">
						 </form>
						<h3 id="nickname_title"><%=user.getUsername() %></h3>
						<ul class="nav nav-list">
							<li><a href="#" id="my_info"><span class="glyphicon  glyphicon-user" style="margin-right:5px"></span>我的信息</a></li>
							<li><a href="#" id="my_order"><span class="glyphicon  glyphicon-star" style="margin-right:5px"></span>我的订单</a></li>
							<li><a href="#" id="my_statistics"><span class="glyphicon  glyphicon-bell" style="margin-right:5px"></span>我的统计</a></li>              
						</ul>
					</div>
				</div>
				<!-- 右側界面 -->
				<div class="col-sm-12 col-md-9" id="content">
					
				</div><!-- 右侧界面结束 -->
			</div><!-- row -->
		</div><!-- container -->
	</body>
</html>