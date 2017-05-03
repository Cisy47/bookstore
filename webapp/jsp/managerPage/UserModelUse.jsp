<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Entity.User" %>


<% 
	//User[] userArray = (User[])request.getAttribute("userArray");

%>

<script type="text/javascript">
	function submit(){
		$("#modal-form").modal("hide");
		$.ajax({
			url: "modifyUser",
			data:{"userId":$("#userId").text(),
				"coin":$("#coin").val(),
				"mobile":$("#mobile").val(),
				"email":$("#email").val(),
				"role":$("#priority").val()
				},
			success:function(data){
					var arr = $.evalJSON(data);
					if (arr.hasOwnProperty("warning"))
						showInfo(arr["warning"] + arr["message"]);
					else
						showInfo(arr["message"]);
					$("#tr"+$("#userId").text()).find(".coin").text($("#coin").val());
					$("#tr"+$("#userId").text()).find(".mobile").text($("#mobile").val());
				    $("#tr"+$("#userId").text()).find(".email").text($("#email").val());
					$("#tr"+$("#userId").text()).find(".priority").text($("#priority").val());
				} 
			});
	}
	function addUser(){
		$("#add-form").modal("hide");
		$.ajax({
			url: "addUser",
			data:{
				"username":$("#addusername").val(),
				"password":$("#addpassword").val(),
				"coin":$("#addcoin").val(),
				"mobile":$("#addmobile").val(),
				"email":$("#addemail").val(),
				"role":$("#addpriority").val()
			},
			success:function(data){
				var arr = $.evalJSON(data);
				if (arr.hasOwnProperty("warning"))
					showInfo(arr["warning"]);
				else{
					showInfo(arr["message"]);
			}
			}
		});

	}
	function showAdd(){
		$("#add-form").modal("show");
	}
	$(function(){
		$(".mouse-over-show").css("visibility","hidden");
		$(".delete-button").click(function(){
				var that = this;
				$.ajax({
					url: "removeUser",
					data: {"userId": $(this).parents(".tr").children(".id").text()},
					success:function(data){
							var arr = $.evalJSON(data);
							$(that).parents(".tr").css("display", "none");
							showInfo(arr["message"]);	
						} 
					});
			});
		$(".modify-button").click(function(){
			$("#userId").text($(this).parents("tr").children(".id").text());
			$("#userName").text($(this).parents("tr").children(".username").text());
			$("#coin").val($(this).parents("tr").children(".coin").text());
			$("#mobile").val($(this).parents("tr").children(".mobile").text());
			$("#email").val($(this).parents("tr").children(".email").text());
			$("#priority").val($(this).parents("tr").children(".priority").text());
			$("#modal-form").modal("show");
		});
		$(".tr").mouseover(function(){
				$(this).find(".mouse-over-show").css("visibility","visible");
			});
		$(".tr").mouseout(function(){
				$(this).find(".mouse-over-show").css("visibility","hidden");
			});	
	});
	
</script>

<!--div class="container"-->
<div class="row">
		<div class="span9">
			<h2 style="color:#1914aa"><strong>权限: 1->普通用户,2->管理员</strong></h2>
			</div>
	<div class="span3">
			<button class="btn btn-primary btn-success" onclick="showAdd()">添加用户</button>
		</div>
</div>
<table class="table">
	<tr>
		<th>ID</th>
		<th>用户名</th>
		<th>金币</th>
		<th>手机号</th>
		<th>邮箱</th>
		<th>权限</th>
		<th></th>
	</tr>
<% if(userArray!=null){
	for (int i = 0; i < userArray.length; i++){%>
	<tr class="tr" id="tr<%=userArray[i].getId()%>">
		<td class="id"><%=userArray[i].getId()%></td>
		<td class="username"><%=userArray[i].getUsername()%></td>
		<td class="coin"><%=userArray[i].getCoin()%></td> 
		<td class="mobile"><%=userArray[i].getMobile()%></td>
		<td class="email"><%=userArray[i].getEmail()%></td>
		<td class="priority"><%=userArray[i].getRole()%></td>
		<td>
			<button class="btn btn-sm btn-success pull-right mouse-over-show delete-button" style="margin-left:2px">删除</button>
			<button class="btn btn-sm btn-success pull-right mouse-over-show modify-button">修改</button>
		</td>
	</tr>
<%} }%>
</table>
<!--/div-->


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
								<label for="userId" class="col-sm-2 control-label">ID</label>
								<div class="col-sm-10">
									<p id="userId" style="padding-top:5px"></p>
								</div>
							</div>
							
							<div class="form-group ">
								<label for="userName" class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-10">
									<p id="userName" style="padding-top:5px"></p>
								</div>
							</div>
							
							<div class="form-group ">
								<label for="coin" class="col-sm-2 control-label">金币数</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name="coin" id="coin" placeholder="请输入金币数"/>
								</div>
							</div>
							
							<div class="form-group ">
								<label for="mobile" class="col-sm-2 control-label">手机号</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name="mobile" id="mobile" placeholder="请输入手机号"/>
								</div>
							</div>
					<div class="form-group ">
						<label for="email" class="col-sm-2 control-label">邮箱</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="email" id="email" placeholder="请输入邮箱"/>
						</div>
					</div>
							
							<div class="form-group ">
								<label for="priority" class="col-sm-2 control-label">权限</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" name="priority" id="priority" placeholder="请输入权限"/>
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
						<label class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="username" id="addusername" placeholder="请输入用户名"/>
						</div>
					</div>
					<div class="form-group ">
						<label class="col-sm-2 control-label">密码</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="password" id="addpassword" placeholder="请输入密码"/>
						</div>
					</div>
					<div class="form-group ">
						<label for="coin" class="col-sm-2 control-label">金币数</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="coin" id="addcoin" placeholder="请输入金币数"/>
						</div>
					</div>

					<div class="form-group ">
						<label for="mobile" class="col-sm-2 control-label">手机号</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="mobile" id="addmobile" placeholder="请输入手机号"/>
						</div>
					</div>
					<div class="form-group ">
						<label for="email" class="col-sm-2 control-label">邮箱</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="email" id="addemail" placeholder="请输入邮箱"/>
						</div>
					</div>

					<div class="form-group ">
						<label for="priority" class="col-sm-2 control-label">权限</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="priority" id="addpriority" placeholder="请输入权限"/>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="add" onclick="addUser()">提交</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!--/body-->