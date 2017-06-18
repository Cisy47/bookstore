<%@ page import="java.util.ResourceBundle" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% String username =request.getUserPrincipal().getName();%>
<!--%@ taglib uri='/struts-tags' prefix='s'%-->
<script type="text/javascript" src="../dict/js/jquery.i18n.properties-1.0.9.js"></script>

<script type="text/javascript" >
    var lan = "en";
    function  requestLocale() {
        if(lan=="en")
            lan="zh";
        else{
            lan="en";
        }
        loadProperties(lan);
    }
    function loadProperties(lan){
        //var lan=lan||navigator.language;
        jQuery.i18n.properties({// 加载资浏览器语言对应的资源文件
            name:'string_'+lan, // 资源文件名称
            path:'../resource/i18n/', // 资源文件路径
            mode:'map', // 用 Map 的方式使用资源文件中的值
            //language:lan,
            callback: function() {// 加载成功后设置显示内容
                $("[data-localize]").each(function() {
                    var elem =$(this);
                    var localizedValue = jQuery.i18n.map[elem.data("localize")];
                    elem.text(localizedValue);
                });

            }
        });
    }
    $(document).ready(function(){
        loadProperties('en');
    });
</script>

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
                        <a data-localize="homepage" href="../normalPage/Center.jsp">书目大厅</a>
                    </li>
                    <!--% if (user.getRole()== 2) {%-->
                    <li>
                        <a data-localize='managepage' href="../managerPage/ManagerMain.jsp">管理员界面</a>
                    </li>
                    <li>
                    <a data-localize='visitpage' href="counterTest">书店访问量</a>
                    </li>
                    <li>
                        <a data-localize='chatroom' href="../normalPage/chatRoom.jsp">聊天室</a>
                    </li>
                    <li>
                        <a data-localize='personinfo' href="../normalPage/Info.jsp">个人信息(<%=username %>)</a>
                    </li>
                    <li>
                        <a data-localize='logout' href="logout">登出</a>
                    </li>
                    <li>
                        <a data-localize="changeLanguage" onclick="requestLocale()">test</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
